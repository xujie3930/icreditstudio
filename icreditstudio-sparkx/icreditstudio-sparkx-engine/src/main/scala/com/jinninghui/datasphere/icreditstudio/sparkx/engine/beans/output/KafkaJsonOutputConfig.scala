package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.output

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.output.KafkaJsonOutputWorker

class KafkaJsonOutputConfig extends KafkaOutputConfig {

  override def checkNoneIsBlank(): Unit = {
    validateNoneIsBlank("srcName", "brokers", "topic")
  }

  setWorkerClass(classOf[KafkaJsonOutputWorker].getName)
}
