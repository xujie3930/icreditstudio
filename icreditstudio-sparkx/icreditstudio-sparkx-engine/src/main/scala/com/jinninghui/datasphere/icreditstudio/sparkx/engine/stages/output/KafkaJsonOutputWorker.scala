package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.output

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.output.KafkaOutputProperties
import org.apache.spark.sql.SparkSession

class KafkaJsonOutputWorker extends KafkaOutputWorker {
  override def process(config: BaseProperties)(implicit ss: SparkSession): Unit = {
    val item = config.asInstanceOf[KafkaOutputProperties]
    ss.table(item.getSrcName).toJSON.rdd.foreachPartition { case iter =>
      sendData(iter, item.brokers, item.topic)
    }
  }
}
