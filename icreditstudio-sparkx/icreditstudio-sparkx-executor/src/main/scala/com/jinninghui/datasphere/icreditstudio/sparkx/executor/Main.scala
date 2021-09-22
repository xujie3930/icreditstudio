/*
 * Copyright 2019 WeBank
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
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

import java.io.File
import java.lang.reflect.Constructor

import org.apache.commons.lang.StringUtils
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.util.SparkUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.executor
 * ClassName: Main
 * Description:  Main类
 * Date: 2021/9/22 3:12 下午
 *
 * @author liyanhui
 */
object Main extends SparkLogging {
  protected def createEngineConnSession(): SparkEngineSession = {
    val useSparkSubmit = true
    val sparkConf: SparkConf = new SparkConf(true)
    val master = sparkConf.getOption("spark.master").getOrElse(CommonVars("spark.master", "yarn").getValue)
    info(s"------ Create new SparkContext {$master} -------")
    val pysparkBasePath = SparkConfiguration.SPARK_HOME.getValue
    val pysparkPath = new File(pysparkBasePath, "python" + File.separator + "lib")
    val pythonLibUris = pysparkPath.listFiles().map(_.toURI.toString).filter(_.endsWith(".zip"))
    if (pythonLibUris.length == 2) {
      val sparkConfValue1 = Utils.tryQuietly(CommonVars("spark.yarn.dist.files", "").getValue)
      val sparkConfValue2 = Utils.tryQuietly(sparkConf.get("spark.yarn.dist.files"))
      if (StringUtils.isEmpty(sparkConfValue1) && StringUtils.isEmpty(sparkConfValue2))
        sparkConf.set("spark.yarn.dist.files", pythonLibUris.mkString(","))
      else if (StringUtils.isEmpty(sparkConfValue1))
        sparkConf.set("spark.yarn.dist.files", sparkConfValue2 + "," + pythonLibUris.mkString(","))
      else if (StringUtils.isEmpty(sparkConfValue2))
        sparkConf.set("spark.yarn.dist.files", sparkConfValue1 + "," + pythonLibUris.mkString(","))
      else
        sparkConf.set("spark.yarn.dist.files", sparkConfValue1 + "," + sparkConfValue2 + "," + pythonLibUris.mkString(","))
      //      if (!useSparkSubmit) sparkConf.set("spark.files", sparkConf.get("spark.yarn.dist.files"))
      //      sparkConf.set("spark.submit.pyFiles", pythonLibUris.mkString(","))
    }
    // Distributes needed libraries to workers
    // when spark version is greater than or equal to 1.5.0
    if (master.contains("yarn")) sparkConf.set("spark.yarn.isPython", "true")

    val outputDir = createOutputDir(sparkConf)

    // todo check scala sparkILoopInit
    //Utils.waitUntil(() => scalaExecutor.sparkILoopInited == true && scalaExecutor.sparkILoop.intp != null, new TimeType("120s").toDuration)

    info("print current thread name " + Thread.currentThread().getContextClassLoader.toString)
    val sparkSession = createSparkSession(outputDir, sparkConf)
    if (sparkSession == null) throw new SparkSessionNullException(40009, "sparkSession can not be null")

    val sc = sparkSession.sparkContext
    val sqlContext = createSQLContext(sc, sparkSession)
    sc.hadoopConfiguration.set("mapred.output.compress", "true")
    sc.hadoopConfiguration.set("mapred.output.compression.codec", "rg.apache.hadoop.io.compress.GzipCodec")
    println("Application report for " + sc.applicationId)
    SparkEngineSession(sc, sqlContext, sparkSession, outputDir)
  }

  def createSparkSession(outputDir: File, conf: SparkConf, addPythonSupport: Boolean = false): SparkSession = {
    val execUri = System.getenv("SPARK_EXECUTOR_URI")
    val sparkJars = conf.getOption("spark.jars")

    def unionFileLists(leftList: Option[String], rightList: Option[String]): Set[String] = {
      var allFiles = Set[String]()
      leftList.foreach { value => allFiles ++= value.split(",") }
      rightList.foreach { value => allFiles ++= value.split(",") }
      allFiles.filter {
        _.nonEmpty
      }
    }

    val master = conf.getOption("spark.master").getOrElse(SparkConfiguration.SPARK_MASTER.getValue)
    info(s"------ Create new SparkContext {$master} -------")
    if (StringUtils.isNotEmpty(master)) {
      conf.setMaster(master)
    }

    val jars = if (conf.get("spark.master").contains("yarn")) {
      val yarnJars = conf.getOption("spark.yarn.dist.jars")
      unionFileLists(sparkJars, yarnJars).toSeq
    } else {
      sparkJars.map(_.split(",")).map(_.filter(_.nonEmpty)).toSeq.flatten
    }
    if (outputDir != null) {
      conf.set("spark.repl.class.outputDir", outputDir.getAbsolutePath)
    }

    if (jars.nonEmpty) conf.setJars(jars)
    if (execUri != null) conf.set("spark.executor.uri", execUri)
    if (System.getenv("SPARK_HOME") != null) conf.setSparkHome(System.getenv("SPARK_HOME"))
    conf.set("spark.scheduler.mode", "FAIR")

    val builder = SparkSession.builder.config(conf)
    builder.enableHiveSupport().getOrCreate()
  }

  def createSQLContext(sc: SparkContext, sparkSession: SparkSession): SQLContext = {
    var sqlc: SQLContext = null
    val name = "org.apache.spark.sql.hive.HiveContext"
    var hc: Constructor[_] = null
    Utils.tryCatch {
      hc = getClass.getClassLoader.loadClass(name).getConstructor(classOf[SparkContext])
      sqlc = hc.newInstance(sc).asInstanceOf[SQLContext]
    } { e: Throwable =>
      logger.warn("Can't create HiveContext. Fallback to SQLContext", e)
      sqlc = sparkSession.sqlContext
    }
    sqlc
  }

  def createOutputDir(conf: SparkConf): File = {
    val rootDir = conf.get("spark.repl.classdir", System.getProperty("java.io.tmpdir"))
    Utils.tryThrow {
      val output = SparkUtils.createTempDir(root = rootDir, namePrefix = "repl")
      info("outputDir====> " + output)
      output.deleteOnExit()
      conf.set("spark.repl.class.outputDir", output.getAbsolutePath)
      output
    }(t => {
      warn("create spark repl classdir failed", t)
      throw new SparkCreateFileException(80002, s"spark repl classdir create exception", t)
      null
    })
  }

  def main(args: Array[String]): Unit = {
    var sparkEngineSession: SparkEngineSession = createEngineConnSession()
    var sparkScalaExecutor: SparkScalaExecutor = new SparkScalaExecutor(sparkEngineSession)
    sparkScalaExecutor.init()
    var executeResponse : ExecuteResponse = sparkScalaExecutor.executeLine("1+1")

  }
}
