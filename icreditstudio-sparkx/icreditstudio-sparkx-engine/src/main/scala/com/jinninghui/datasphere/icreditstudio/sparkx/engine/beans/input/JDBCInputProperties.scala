package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input.JdbcInputWorker

import scala.beans.BeanProperty
import scala.collection.JavaConversions._

class JDBCInputProperties extends BaseInputProperties {

  @BeanProperty
  var driver: String = _
  @BeanProperty
  var url: String = _
  @BeanProperty
  var user: String = _
  @BeanProperty
  var password: String = _
  /**
   * MySQL表 -> SparkSQL表
   */
  @BeanProperty
  var dbtable: java.util.Map[String, String] = _

  // TODO other param
  override def checkNoneIsBlank(): Unit = {
    validateNoneIsBlank("driver", "url", "user", "password", "dbtable")
  }

  setWorkerClass(classOf[JdbcInputWorker].getName)

  override def getDefinedTables(): List[String] = {
    dbtable.values().toList
  }
}
