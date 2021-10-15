package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.output

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.output.KafkaJsonOutputWorker

class KafkaJsonOutputProperties extends KafkaOutputProperties {

  override def checkNoneIsBlank(): Unit = {
    validateNoneIsBlank("srcName", "brokers", "topic")
  }

  setWorkerClass(classOf[KafkaJsonOutputWorker].getName)
}
