package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input.KafkaInputWorker

import scala.beans.BeanProperty

class KafkaInputConfig extends StreamInputConfig {
  @BeanProperty
  var items: java.util.List[KafkaInputItem] = _

  override def checkNoneIsBlank(): Unit = {
    validateNoneIsBlank("clazz", "items")
  }

  setWorkerClass(classOf[KafkaInputWorker].getName)
}
