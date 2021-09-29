package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input

import scala.beans.BeanProperty

class CustomInputConfig extends BaseInputConfig {
  @BeanProperty
  var clazz: String = _
  @BeanProperty
  var path: String = _

  override def checkNoneIsBlank(): Unit = {
    validateNoneIsBlank("clazz", "path")
  }

}
