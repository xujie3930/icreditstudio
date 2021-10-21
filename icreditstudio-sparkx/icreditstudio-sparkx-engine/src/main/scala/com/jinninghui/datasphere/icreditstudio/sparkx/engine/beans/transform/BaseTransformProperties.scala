package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.transform

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.{BaseProperties, NodeTypes}

import scala.beans.BeanProperty

class BaseTransformProperties extends BaseProperties {
  tag = NodeTypes.processes.toString

  @BeanProperty
  var clazz: String = _

  @BeanProperty
  var sql: String = _

  override protected def checkAnyIsNotBlank(): Unit = {
    validateAnyIsNotBlank("clazz", "sql")
  }

  override def getDefinedTables(): List[String] = {
    List(name)
  }
}
