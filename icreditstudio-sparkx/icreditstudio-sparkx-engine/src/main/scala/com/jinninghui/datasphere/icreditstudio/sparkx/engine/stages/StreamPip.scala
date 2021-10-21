package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages

import com.jinninghui.datasphere.icreditstudio.sparkx.common.Logging
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.{BaseProperties, Context}
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input.{BaseInputProperties, StreamInputProperties}
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.function.BaseUDF
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.custom.CustomBaseInput
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.{ReflectUtils, SparkUtil}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.{CanCommitOffsets, HasOffsetRanges}

import scala.collection.JavaConversions._

object StreamPip extends Logging {

  /**
   * 开始处理加工逻辑
   *
   * @param config BusConfigBean
   * @param ss     SparkSession
   */
  def startPip(config: Context)(implicit ss: SparkSession, ssc: StreamingContext): Unit = {
    logger.info(s"pipline ${config.configFile} ${config.eventDate} start.")
    // 加载 udf
    Option(config.udf).filter(_.nonEmpty).foreach(clazzs =>
      clazzs.foreach { case udf =>
        ReflectUtils.apply.getInstance[BaseUDF](udf).setup()
      })
    // 加载输入数据，注册成表
    val kafkaInput = config.inputs.head.asInstanceOf[StreamInputProperties]
    val dstream = getDstream(kafkaInput)
    dstream.map(record => record.value()).foreachRDD(rdd => {
      logger.info(s"batch start.")
      if (!rdd.isEmpty()) {
        ReflectUtils.apply.getInstance[CustomBaseInput](kafkaInput.getClazz).process(rdd, kafkaInput.name)
        logger.info("----------------------start processes----------------------")
        processStage(config.processes, StageType.processes.toString)
        logger.info("----------------------start outputs----------------------")
        processStage(config.outputs, StageType.outputs.toString)
        SparkUtil.uncacheData()
        logger.info(s"batch finished.")
      }
    })

    if (!config.isDebug) {
      dstream.foreachRDD { rdd =>
        val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
        dstream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
      }
    }
    ssc.start()
    ssc.awaitTermination()
  }

  def getDstream(item: BaseProperties)(implicit ss: SparkSession, ssc: StreamingContext): DStream[ConsumerRecord[String, String]] = {
    val bean = item.asInstanceOf[BaseProperties]
    ReflectUtils.apply.getInstance[StreamBaseInputWorker](bean.workerClass).initDS(bean)
  }

  /**
   * pipeline 处理
   *
   * @param items     items
   * @param stageName stageName
   * @param ss        ss
   */
  def processStage(items: java.util.List[_ <: BaseProperties], stageName: String)(implicit ss: SparkSession): Unit = {
    Option(items).filter(!_.isEmpty).foreach(lst => {
      for (i <- lst.indices) {
        val item = lst(i)
        logger.info(s"start $stageName, step${i + 1}, item '${item.name}'.")
        ReflectUtils.apply.getInstance[BaseWorker](item.workerClass).process(item)
      }
    })
  }
}
