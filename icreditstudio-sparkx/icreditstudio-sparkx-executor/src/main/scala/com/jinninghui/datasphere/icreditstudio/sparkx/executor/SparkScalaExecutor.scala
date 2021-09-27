/*
 * Copyright 2019 WeBank
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jinninghui.datasphere.icreditstudio.sparkx.executor

import com.jinninghui.datasphere.icreditstudio.sparkx.executor.response.{ErrorExecuteResponse, UnknownExecuteResponse, ExecuteResponse, IncompleteExecuteResponse, SuccessExecuteResponse}
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset.protocol.ProtocolOutputStream
import org.apache.commons.lang.exception.ExceptionUtils
import org.apache.spark.repl.SparkILoop
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.util.SparkUtils

import java.io.{BufferedReader, File}
import scala.tools.nsc.GenericRunnerSettings
import scala.tools.nsc.interpreter.Results.Result
import scala.tools.nsc.interpreter.{IMain, JPrintWriter, NamedParam, Results, SimpleReader, StdReplTags, isReplPower, replProps}

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.common
 * ClassName: SparkScalaExecutor
 * Description:  SparkScalaExecutor类
 * Date: 2021/9/17 2:19 下午
 *
 * @author liyanhui
 */
class SparkScalaExecutor(sparkEngineSession: SparkEngineSession) extends AbstractSparkEngineConnExecutor(sparkEngineSession.sparkContext) {

  private val sparkContext: SparkContext = sparkEngineSession.sparkContext

  private val _sqlContext: SQLContext = sparkEngineSession.sqlContext

  private val sparkSession: SparkSession = sparkEngineSession.sparkSession

  private val sparkConf: SparkConf = sparkContext.getConf

  private var sparkILoop: SparkILoop = _

  private var bindFlag: Boolean = false

  private var jobGroup: String = _

  private val lineOutputStream: ProtocolOutputStream = new ProtocolOutputStream

  private val jOut: JPrintWriter = new JPrintWriter(lineOutputStream, true)

  private var executeCount = 0

  var sparkILoopInited = false

  private val outputDir = sparkEngineSession.outputDir

  protected implicit val executor = Utils.newCachedExecutionContext(5, "Spark-Scala-REPL-Thread-", true)

  override def init(): Unit = {

    System.setProperty("scala.repl.name.line", ("$line" + this.hashCode).replace('-', '0'))
    if (sparkILoop == null) {
      synchronized {
        if (sparkILoop == null) createSparkILoop
      }
    }
    if (sparkILoop != null) {
      if (!sparkILoopInited) {
        sparkILoop synchronized {
          if (!sparkILoopInited) {
            Utils.tryCatch {
              initSparkILoop
            } {
              t =>
                logger.error("init failed: ", t)
                null
            }
            //TODO When an exception is thrown, is there a shutdown here? I need to think about it again.（当抛出异常时，此处是否需要shutdown，需要再思考一下）
            sparkILoopInited = true
          }
        }
      }
    } else {
      throw new SparkSessionNullException(40006, "sparkILoop is null")
    }
    Utils.waitUntil(() => sparkILoopInited && sparkILoop.intp != null, new TimeType("120s").toDuration)
    super.init()
  }

  override protected def getKind: Kind = SparkScala()

  def runCode(code: String): ExecuteResponse = {
    if (null != sparkILoop.intp && null != sparkILoop.intp.classLoader) {
      Thread.currentThread().setContextClassLoader(sparkILoop.intp.classLoader)
    }


    lazyLoadILoop
    lineOutputStream.ready()
    lineOutputStream.reset()

    var res: Result = null

    Utils.tryCatch {
      res = executeLine(code)
    } {
      case e: Exception =>
        sparkContext.clearJobGroup()
        var outs  = lineOutputStream.finalFlush()
        error("Interpreter exception", e)
        lineOutputStream.close()
        // _state = Idle()
        return new ErrorExecuteResponse(outs)
    }

    var outs  = lineOutputStream.finalFlush()

    res match {
      case Results.Success =>
        return new SuccessExecuteResponse(outs)
      case Results.Incomplete =>
        return new IncompleteExecuteResponse
      case Results.Error =>
        sparkContext.clearJobGroup()
        return new ErrorExecuteResponse(outs)
      case _ =>
        return new UnknownExecuteResponse
    }

    lineOutputStream.close()

    new UnknownExecuteResponse
  }

  def executeLine(code: String): Result = synchronized {
    val kind: Kind = getKind
    var preCode = code
    val _code = Kind.getRealCode(preCode)
    info(s"Ready to run code with kind $kind.")
    jobGroup = String.valueOf("linkis-spark-mix-code-" + queryNum.incrementAndGet())
    //    val executeCount = queryNum.get().toInt - 1
    info("Set jobGroup to " + jobGroup)
    sc.setJobGroup(jobGroup, _code, true)
    //    val executeCount = queryNum.get().toInt - 1
    val response = Utils.tryFinally(executeLine0(_code)) {
      jobGroup = null
      sc.clearJobGroup()
    }
    //Post-execution hook
    response
  }

  def executeLine0(code: String): Result = {
    info(s"Start to run code $code")
    executeCount += 1
    var originalOut = System.out
    val result = scala.Console.withOut(lineOutputStream) {
      Utils.tryCatch(sparkILoop.interpret(code)) { t =>
        error("task error info:", t)
        val msg = ExceptionUtils.getRootCauseMessage(t)
        if (msg.contains("OutOfMemoryError")) {
          error("engine oom now to set status to shutdown")
        }
        Results.Error
      } match {
        case Results.Success =>
          lineOutputStream.flush()
          info("Succeed to execute code.")
          Results.Success
        case Results.Incomplete =>
          error("incomplete code.")
          Results.Incomplete
        case Results.Error =>
          lineOutputStream.flush()
          error("No error message is captured, please see the detailed log")
          Results.Error
      }
    }
    System.setOut(originalOut)
    result
  }


  private def createSparkILoop = {
    info("outputDir====> " + outputDir)
    sparkILoop = Utils.tryCatch {
      new SparkILoop(None, jOut)
    } {
      t =>
        logger.error("create ILoop failed", t)
        null
    }
  }

  private def lazyLoadILoop = { //lazy loaded.
    if (!bindFlag) {
      bindSparkSession
    }

  }

  private def initSparkILoop = {
    val settings = new GenericRunnerSettings(info(_))
    val sparkJars = sparkConf.getOption("spark.jars")
    val jars = if (sparkConf.get("spark.master").contains("yarn")) {
      val yarnJars = sparkConf.getOption("spark.yarn.dist.jars")
      SparkUtils.unionFileLists(sparkJars, yarnJars).toSeq
    } else {
      sparkJars.map(_.split(",")).map(_.filter(_.nonEmpty)).toSeq.flatten
    }
    val classpathJars = System.getProperty("java.class.path").split(":").filter(_.endsWith(".jar"))
    //.filterNot(f=> f.contains("spark-") || f.contains("datanucleus"))
    val classpath = jars.mkString(File.pathSeparator) + File.pathSeparator +
      classpathJars.mkString(File.pathSeparator)
    debug("Spark shell add jars: " + classpath)
    settings.processArguments(List("-Yrepl-class-based",
      "-Yrepl-outdir", s"${outputDir.getAbsolutePath}", "-classpath", classpath), true)
    settings.usejavacp.value = true
    settings.embeddedDefaults(Thread.currentThread().getContextClassLoader())
    sparkILoop.settings = settings
    sparkILoop.createInterpreter()
    val in0 = getDeclaredField(sparkILoop, "in0").asInstanceOf[Option[BufferedReader]]
    val reader = in0.fold(sparkILoop.chooseReader(settings))(r => SimpleReader(r,
      jOut, interactive = true))

    sparkILoop.in = reader
    sparkILoop.initializeSynchronous()
    sparkILoop.printWelcome()
    SparkScalaExecutor.loopPostInit(sparkILoop)
  }

  protected def getDeclaredField(obj: Object, name: String): Object = {
    val field = obj.getClass.getDeclaredField(name)
    field.setAccessible(true)
    field.get(obj)
  }

  def bindSparkSession = {
    require(sparkContext != null)
    require(sparkSession != null)
    require(_sqlContext != null)
    //Wait up to 10 seconds（最多等待10秒）
    val startTime = System.currentTimeMillis()
    Utils.waitUntil(() => sparkILoop.intp != null && sparkILoop.intp.isInitializeComplete, new TimeType("30s").toDuration)
    warn(s"Start to init sparkILoop cost ${System.currentTimeMillis() - startTime}.")
    sparkILoop.beSilentDuring {
      sparkILoop.command(":silent")
      sparkILoop.bind("sc", "org.apache.spark.SparkContext", sparkContext, List("""@transient"""))
      sparkILoop.bind("spark", "org.apache.spark.sql.SparkSession", sparkSession, List("""@transient"""))
      sparkILoop.bind("sqlContext", "org.apache.spark.sql.SQLContext", _sqlContext, List("""@transient"""))
      sparkILoop.bind("jobGroup", "java.lang.StringBuilder", jobGroup)

      sparkILoop.interpret("import org.apache.spark.SparkContext")
      sparkILoop.interpret("import org.apache.spark.SparkContext._")
      sparkILoop.interpret("import org.apache.spark.sql.SparkSession")
      sparkILoop.interpret("import org.apache.spark.sql.SQLContext")
      sparkILoop.interpret("import org.apache.spark.sql.DataFrame")
      sparkILoop.interpret("import sqlContext.sql")
      sparkILoop.interpret("import sqlContext._")
      sparkILoop.interpret("import spark.implicits._")
      sparkILoop.interpret("import spark.sql")
      sparkILoop.interpret("import org.apache.spark.sql.functions._")
      sparkILoop.interpret("import org.apache.spark.sql.execution.datasources.csv._")
      sparkILoop.interpret("import org.apache.spark.sql.UDFRegistration")
      sparkILoop.interpret("implicit val sparkSession = spark")
      bindFlag = true
      warn(s"Finished to init sparkILoop cost ${System.currentTimeMillis() - startTime}.")
    }
  }

  protected def getExecutorIdPreFix: String = "SparkScalaExecutor_"
}

object SparkScalaExecutor {

  private def loopPostInit(sparkILoop: SparkILoop): Unit = {
    import StdReplTags._
    import scala.reflect.{classTag, io}

    val intp = sparkILoop.intp
    val power = sparkILoop.power
    val in = sparkILoop.in

    def loopPostInit() {
      // Bind intp somewhere out of the regular namespace where
      // we can get at it in generated code.
      intp.quietBind(NamedParam[IMain]("$intp", intp)(tagOfIMain, classTag[IMain]))
      // Auto-run code via some setting.
      (replProps.replAutorunCode.option
        flatMap (f => io.File(f).safeSlurp())
        foreach (intp quietRun _)
        )
      // classloader and power mode setup
      intp.setContextClassLoader()
      if (isReplPower) {
        replProps.power setValue true
        unleashAndSetPhase()
        asyncMessage(power.banner)
      }
      // SI-7418 Now, and only now, can we enable TAB completion.
      in.postInit()
    }

    def unleashAndSetPhase() = if (isReplPower) {
      power.unleash()
      intp beSilentDuring phaseCommand("typer") // Set the phase to "typer"
    }

    def phaseCommand(name: String): Results.Result = {
      callMethod(
        sparkILoop,
        "scala$tools$nsc$interpreter$ILoop$$phaseCommand",
        Array(classOf[String]),
        Array(name)).asInstanceOf[Results.Result]
    }

    def asyncMessage(msg: String): Unit = {
      callMethod(
        sparkILoop, "asyncMessage", Array(classOf[String]), Array(msg))
    }

    loopPostInit()
  }

  def callMethod(obj: Object, name: String,
                 parameterTypes: Array[Class[_]],
                 parameters: Array[Object]): Object = {
    val method = obj.getClass.getMethod(name, parameterTypes: _ *)
    method.setAccessible(true)
    method.invoke(obj, parameters: _ *)
  }
}
