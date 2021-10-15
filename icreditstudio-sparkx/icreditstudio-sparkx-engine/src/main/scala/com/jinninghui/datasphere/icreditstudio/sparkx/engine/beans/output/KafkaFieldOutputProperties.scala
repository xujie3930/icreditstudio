package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.output

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.output.KafkaFieldOutputWorker

import scala.beans.BeanProperty

class KafkaFieldOutputProperties extends KafkaOutputProperties {
  @BeanProperty
  var fs: String = _

  override def checkNoneIsBlank(): Unit = {
    validateNoneIsBlank("srcName", "brokers", "topic", "fs")
  }

  setWorkerClass(classOf[KafkaFieldOutputWorker].getName)
}
