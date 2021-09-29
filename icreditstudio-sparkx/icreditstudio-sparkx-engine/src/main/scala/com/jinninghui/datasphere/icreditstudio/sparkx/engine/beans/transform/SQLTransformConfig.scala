package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.transform

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.Transform.SQLTransformWorker

import scala.beans.BeanProperty

class SQLTransformConfig extends BaseTransformConfig {

  setWorkerClass(classOf[SQLTransformWorker].getName)

  @BeanProperty
  var dimKey: String = _

  @BeanProperty
  var allPlaceholder: String = "all"

  override protected def checkNoneIsBlank(): Unit = {
    validateNoneIsBlank("sql")
  }
}
