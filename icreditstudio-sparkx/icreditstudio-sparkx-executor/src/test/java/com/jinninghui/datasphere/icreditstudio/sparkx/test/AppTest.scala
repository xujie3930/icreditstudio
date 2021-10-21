package com.jinninghui.datasphere.icreditstudio.sparkx.test

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.App
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.ReflectUtils
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.{Main, SparkEngineSession}

object AppTest {
  def main(args: Array[String]): Unit = {
//    setUp()
    testBatch1()
  }

//  def setUp(): Unit = {
//    val path2 = "src/test/resources/data"
//    ReflectUtils.apply.addClasspath(path2)
//  }

  def testBatch1(): Unit = {
    val configFile1 = "full-batch.yaml"
    val configFile2 = "variables.yaml"
    val date = "20191211"
    val sparkEngineSession = Main.createEngineConnSession
    implicit val sparkSession = sparkEngineSession.sparkSession
    App.run(Array("-d", date, "-c", configFile1, "--debug"))
  }

}
