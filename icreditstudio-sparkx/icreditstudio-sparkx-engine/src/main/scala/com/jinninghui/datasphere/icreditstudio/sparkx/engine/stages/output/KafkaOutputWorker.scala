package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.output

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.BaseWorker
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.KafkaUtils

trait KafkaOutputWorker extends BaseWorker {
  def sendData(iter: scala.Iterator[String], brokers: String, topic: String): Unit = {
    val toSend = iter.toList
    if (toSend.nonEmpty) {
      val producer = KafkaUtils.getKafkaProducer(brokers)
      toSend.foreach(line => KafkaUtils.sendMessage(producer, topic, line))
      producer.close()
    }
  }
}
