package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.output

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.output.KafkaOutputConfig
import org.apache.spark.sql.SparkSession

class KafkaJsonOutputWorker extends KafkaOutputWorker {
  override def process(config: BaseConfig)(implicit ss: SparkSession): Unit = {
    val item = config.asInstanceOf[KafkaOutputConfig]
    ss.table(item.getSrcName).toJSON.rdd.foreachPartition { case iter =>
      sendData(iter, item.brokers, item.topic)
    }
  }
}
