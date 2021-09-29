package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input.HDFSCsvInputWorker

import scala.beans.BeanProperty

class HDFSCsvInputConfig extends BaseInputConfig {

  @BeanProperty
  var columns: String = _
  @BeanProperty
  var path: String = _

  override def checkNoneIsBlank(): Unit = {
    validateNoneIsBlank("columns", "path")
  }

  setWorkerClass(classOf[HDFSCsvInputWorker].getName)
}
