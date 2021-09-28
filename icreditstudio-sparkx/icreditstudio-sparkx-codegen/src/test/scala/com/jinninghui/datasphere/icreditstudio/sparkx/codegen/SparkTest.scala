package com.jinninghui.datasphere.icreditstudio.sparkx.codegen

import org.apache.spark.sql.SparkSession
import org.slf4j.LoggerFactory

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.codegen
 * ClassName: SparkTest
 * Description:  SparkTest类
 * Date: 2021/9/28 11:16 上午
 *
 * @author liyanhui
 */
class SparkTest {

  private val LOGGER = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {

    args.foreach(x =>
      LOGGER.info(s"****args: ${x} ****")
    )
    val inputSql = args(0)
    val inputSchema = args(1).split(",")
    val defaultPartitions = args(2).toInt


    val sc = SparkSession.builder().enableHiveSupport().getOrCreate()

    try {
      val inputDocsDF = sc.sql(inputSql).repartition(defaultPartitions)
        .rdd.map {
        row =>
          val docSql = row.toSeq.asInstanceOf[Seq[String]].toArray
          docSql

      }


    } finally {
      if (null != sc) {
        //清空persist的rdd
        sc.sparkContext.getPersistentRDDs.foreach(x => x._2.unpersist())
        sc.stop
      }
    }
  }

}
