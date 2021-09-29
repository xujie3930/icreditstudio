package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.output

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.output.HiveOutputWorker

import scala.beans.BeanProperty

class HiveOutputConfig extends BaseOutputConfig {

  @BeanProperty
  var database: String = _
  @BeanProperty
  var mode: String = "overwrite"
  /**
   * src table , dist table
   */
  @BeanProperty
  var tables: java.util.Map[String, String] = _

  override def checkNoneIsBlank(): Unit = {
    validateNoneIsBlank("database", "tables")
  }

  setWorkerClass(classOf[HiveOutputWorker].getName)
}
