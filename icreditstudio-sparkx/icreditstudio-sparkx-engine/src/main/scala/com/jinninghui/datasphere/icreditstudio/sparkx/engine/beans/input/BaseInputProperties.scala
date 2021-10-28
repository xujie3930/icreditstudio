package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.{BaseProperties, NodeTypes}
import org.apache.commons.lang3.StringUtils

import scala.beans.BeanProperty

class BaseInputProperties extends BaseProperties {
  tag = NodeTypes.inputs.toString

  @BeanProperty
  val nullable: Boolean = true

  override def nameCheck(): Unit = {
    super.nameCheck()
    require(StringUtils.isNotBlank(this.`type`), s"In node '${this.tag}', 'type' is required in item '${this.name}'!")
  }

  override def getDefinedTables(): List[String] = {
    List(name)
  }
}