package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages

import com.alibaba.fastjson.JSON
import com.jinninghui.datasphere.icreditstudio.sparkx.common.Logging
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.{BaseProperties, PersistTypes}
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.config.{BusConfig, CacheConstants}
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.HDFSUtils
import org.apache.hadoop.fs.Path
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SaveMode, SparkSession}

trait BaseWorker extends Logging {
  protected var variables: java.util.HashMap[String, String] = _

  def process(bean: BaseProperties)(implicit ss: SparkSession): Unit

  protected def initVariables()(implicit ss: SparkSession): Unit = {
    val str = ss.sparkContext.getConf.get("com.jinninghui.datasphere.icreditstudio.sparkx.engine.variables")
    variables = JSON.parseObject(str, classOf[java.util.HashMap[String, String]])
  }

  protected def getVariableStr()(implicit ss: SparkSession): String = {
    ss.sparkContext.getConf.get("com.jinninghui.datasphere.icreditstudio.sparkx.engine.variables")
  }

  /**
   * spark table 转 RDD
   *
   * @param tableName
   * @param ss
   * @return
   */
  def getRDDByTable(tableName: String)(implicit ss: SparkSession): RDD[Row] = {
    ss.table(tableName).rdd
  }

  /**
   * partation、缓存、持久化、打印
   *
   * @param process ProcessItemBean
   * @param ss      SparkSession
   */
  protected def afterProcess(process: BaseProperties)(implicit ss: SparkSession): Unit = {
    val resultTables = process.name.split(",", -1).map(_.trim)
    val conf = BusConfig.apply.getConfig()
    resultTables.filter(t => ss.catalog.tableExists(t))
      .foreach(tableName => {
        var df = ss.table(tableName)
        // repartition
        if (process.partations > 0) {
          df = df.repartition(process.partations)
          df.createOrReplaceTempView(tableName)
        }
        // cache
        if (process.cache) {
          doCache(tableName)
        }
        // stored, 如果没有配置 cache, 先 cache, 再 store
        if (process.store && conf.isDebug) {
          if (!process.cache) {
            doCache(tableName)
          }
          val persistType = conf.getPersistType
          persistType match {
            case persistType if persistType == PersistTypes.hdfs.toString =>
              val path = new Path(BusConfig.apply.getConfig().persistDir, tableName).toString
              HDFSUtils.apply.saveAsCSV(df, path)(ss.sparkContext)
              logger.info(s"persist '$tableName' to hdfs '$path' success for debug.")
            case persistType if persistType == PersistTypes.hive.toString =>
              ss.table(tableName).write.mode(SaveMode.Overwrite).format("Hive").saveAsTable(s"${conf.getPersistHiveDb}.$tableName")
              logger.info(s"persist table '$tableName' to hive '${conf.getPersistHiveDb}.$tableName' success for debug.")
            case _ =>
          }
        }
        // show
        if (process.show > 0 && conf.getEnableShow) {
          println(s"show table '$tableName':")
          ss.table(tableName).show(process.show, false)
        }
      })
  }

  private def doCache(tableName: String)(implicit ss: SparkSession): Unit = {
    ss.sql(s"cache table $tableName")
    CacheConstants.tables.append(tableName)
    logger.info(s"cached table '$tableName' success.")
  }
}
