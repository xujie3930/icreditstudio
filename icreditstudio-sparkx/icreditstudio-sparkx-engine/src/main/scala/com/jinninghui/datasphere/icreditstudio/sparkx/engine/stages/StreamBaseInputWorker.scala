package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream

trait StreamBaseInputWorker extends BaseWorker {
  def process(bean: BaseConfig)(implicit ss: SparkSession): Unit = {}

  def initDS(bean: BaseConfig)(implicit ss: SparkSession, ssc: StreamingContext): DStream[ConsumerRecord[String, String]]
}
