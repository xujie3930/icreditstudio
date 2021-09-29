package com.jinninghui.datasphere.icreditstudio.sparkx.engine

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.ReflectUtils

object AppTest {
  def main(args: Array[String]): Unit = {
    setUp()
    testClickhouse()
  }

  def setUp(): Unit = {
    val path1 = "example/src/main/resources/localcluster"
//    ReflectUtils.apply.addClasspath(path1)
    val path2 = "example/src/main/resources/data"
    ReflectUtils.apply.addClasspath(path2)
  }

  def testBatch1(): Unit = {
    val configFile1 = "full-batch.yaml"
    val configFile2 = "variables.yaml"
    val date = "20191211"
    App.main(Array("-d", date, "-c", configFile1, "--debug"))
  }

  def testClickhouse(): Unit = {
    val configFile = "clickhouse_cluster_write.yaml"
//    val configFile = "clickhouse_cluster_read.yaml"
    val date = "20191211"
    App.main(Array("-d", date, "-c", configFile, "--debug"))
  }
}
