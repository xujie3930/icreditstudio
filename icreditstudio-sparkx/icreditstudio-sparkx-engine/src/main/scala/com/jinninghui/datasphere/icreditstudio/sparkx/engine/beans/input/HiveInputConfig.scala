package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input.HiveInputWorker

import scala.beans.BeanProperty
import scala.collection.JavaConversions._

class HiveInputConfig extends BaseInputConfig {

  @BeanProperty
  var database: String = _
  @BeanProperty
  var dbtable: java.util.Map[String, String] = _

  override def checkNoneIsBlank(): Unit = {
    validateNoneIsBlank("database", "dbtable")
  }

  override def getDefinedTables(): List[String] = {
    dbtable.values().toList
  }

  setWorkerClass(classOf[HiveInputWorker].getName)
}
