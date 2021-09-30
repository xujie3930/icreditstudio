package com.jinninghui.datasphere.icreditstudio.sparkx.engine

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializeFilter
import com.jinninghui.datasphere.icreditstudio.sparkx.common.Logging
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BusinessConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.config.{BusConfig, CacheConstants}
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.constants.{AppConstants, SysConstants}
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.{BatchPip, StreamPip}
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.SparkUtil
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}

object App extends Logging {
  def run(args: Array[String])(implicit sparkSession: SparkSession): Unit = {
    val appConfig = BusConfig.apply.parseOptions(args)
    logger.info(s"load config success, event date is ${appConfig.eventDate}, config file is ${appConfig.configFile}.")
    val confMap: java.util.Map[String, String] = AppConstants.variables
    val strConf = JSON.toJSONString(confMap, new Array[SerializeFilter](0))
    SparkUtil.getSparkConf(appConfig, strConf, sparkSession.sparkContext.getConf)
    if (appConfig.isStreaming) {
      stream(appConfig)
    } else {
      batch(appConfig)
    }
    cleanUp()
    logger.info(s"context exit success.")
  }

  private def batch(appConfig: BusinessConfig)(implicit sparkSession: SparkSession): Unit = {
    logger.info("start batch process.")
    BatchPip.startPip(appConfig)
  }

  private def stream(appConfig: BusinessConfig)(implicit sparkSession: SparkSession): Unit = {
    logger.info("start stream process.")
    implicit val ssc: StreamingContext = new StreamingContext(sparkSession.sparkContext, Seconds(appConfig.streamBatchSeconds))
    StreamPip.startPip(appConfig)
  }

  private def cleanUp(): Unit = {
    AppConstants.variables.clear()
    SysConstants.SYS_SPARK_CONFIG.clear()
    SysConstants.SYS_DEFALUT_VARIABLES.clear()
    SysConstants.SYS_DEFINED_TABLES.clear()
    CacheConstants.rdds.clear()
    CacheConstants.tables.clear()
  }

  def version(): String = {
    "0.0.1"
  }
}