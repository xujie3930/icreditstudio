package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.output

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.output.KafkaFieldOutputConfig
import org.apache.spark.sql.SparkSession

class KafkaFieldOutputWorker extends KafkaOutputWorker {
  override def process(config: BaseConfig)(implicit ss: SparkSession): Unit = {
    val item = config.asInstanceOf[KafkaFieldOutputConfig]
    ss.table(item.srcName).rdd.map(a => a.toSeq.mkString(item.fs)).foreachPartition { case iter =>
      sendData(iter, item.brokers, item.topic)
    }
  }
}
