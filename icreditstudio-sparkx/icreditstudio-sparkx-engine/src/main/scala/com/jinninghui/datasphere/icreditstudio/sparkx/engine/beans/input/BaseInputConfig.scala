package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.{BaseConfig, NodeTypes}
import org.apache.commons.lang3.StringUtils

import scala.beans.BeanProperty

/**
 * <p>
 * 日期： 2020/5/19
 * <p>
 * 时间： 15:13
 * <p>
 * 星期： 星期二
 * <p>
 * 描述：
 * <p>
 * 作者： zhaokui
 *
 **/
class BaseInputConfig extends BaseConfig {
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
