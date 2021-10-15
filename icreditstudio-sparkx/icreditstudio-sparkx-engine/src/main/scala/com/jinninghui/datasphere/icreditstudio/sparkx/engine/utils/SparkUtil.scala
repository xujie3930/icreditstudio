package com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils

import com.jinninghui.datasphere.icreditstudio.sparkx.common.Logging
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.Context
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.config.CacheConstants
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.constants.SysConstants
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

import scala.collection.JavaConversions._

object SparkUtil extends Logging {

  /**
   * 获取 sparkConf
   *
   * @param appConf   appConf
   * @param variables variables
   * @return
   */
  def getSparkConf(appConf: Context, variables: String, conf: SparkConf): Unit = {
    conf.set("com.jinninghui.datasphere.icreditstudio.sparkx.engine.variables", variables)
    conf.set("spark.sql.crossJoin.enabled", "true")
      .set("hive.exec.dynamic.partition", "true")
      .set("hive.exec.dynamic.partition.mode", "nonstrict")
    logger.info("enabled spark sql cross join")

    /**
     * 设置自定义参数
     */
    SysConstants.SYS_SPARK_CONFIG.foreach { case (key, v) =>
      logger.info(s"Spark config set - $key=$v.")
      conf.set(key, v)
    }
  }

  private[engine] def uncacheData()(implicit ss: SparkSession): Unit = {
    CacheConstants.tables.foreach { t =>
      ss.sql(s"uncache table $t")
      logger.info(s"uncached table '$t'.")
    }
    CacheConstants.rdds.foreach(rdd => rdd.asInstanceOf[RDD[Object]].unpersist())
  }
}
