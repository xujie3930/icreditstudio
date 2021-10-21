package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.output

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.output.KafkaFieldOutputProperties
import org.apache.spark.sql.SparkSession

class KafkaFieldOutputWorker extends KafkaOutputWorker {
  override def process(config: BaseProperties)(implicit ss: SparkSession): Unit = {
    val item = config.asInstanceOf[KafkaFieldOutputProperties]
    ss.table(item.srcName).rdd.map(a => a.toSeq.mkString(item.fs)).foreachPartition { case iter =>
      sendData(iter, item.brokers, item.topic)
    }
  }
}
