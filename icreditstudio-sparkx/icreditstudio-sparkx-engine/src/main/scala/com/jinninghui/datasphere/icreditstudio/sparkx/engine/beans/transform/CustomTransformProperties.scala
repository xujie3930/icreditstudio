package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.transform

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.Transform.CustomTransformWorker

class CustomTransformProperties extends BaseTransformProperties {

  setWorkerClass(classOf[CustomTransformWorker].getName)

  override protected def checkNoneIsBlank(): Unit = {
    validateNoneIsBlank("clazz")
  }
}
