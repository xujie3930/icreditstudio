package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.output

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.output.HdfsOutputWorker

import scala.beans.BeanProperty

class HDFSOutputProperties extends BaseOutputProperties {
  @BeanProperty
  var format: String = _
  @BeanProperty
  var path: String = _
  @BeanProperty
  var fs: String = "\u0001"
  @BeanProperty
  var srcName: String = _

  override def checkNoneIsBlank(): Unit = {
    validateNoneIsBlank("format", "path", "srcName")
  }

  setWorkerClass(classOf[HdfsOutputWorker].getName)
}
