package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.{BaseConfig, InputTypes}
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input.JDBCInputConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.BaseWorker
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.JDBCSparkUtils
import org.apache.spark.sql.SparkSession

import scala.collection.JavaConversions._

class JdbcInputWorker extends BaseWorker {
  /**
   * 加载 jdbc 数据，参考 http://spark.apache.org/docs/latest/sql-data-sources-jdbc.html
   * val jdbcDF = spark.read
   * .format("jdbc")
   * .option("url", "jdbc:postgresql:dbserver")
   * .option("dbtable", "schema.tablename")
   * .option("user", "username")
   * .option("password", "password")
   * .load()
   *
   * @param bean InputItemBean
   * @param ss   SparkSession
   */
  override def process(bean: BaseConfig)(implicit ss: SparkSession): Unit = {
    val item = bean.asInstanceOf[JDBCInputConfig]
    val filterd = JDBCSparkUtils.filterValues(item)
    item.dbtable.foreach { case (src, dist) =>
      filterd.put("dbtable", src)
      val reader = ss.sqlContext.read.format(InputTypes.jdbc.toString).options(filterd)
      val df = reader.load()
      df.createOrReplaceTempView(dist)
      logger.info(s"inputs, load jdbc table '$src' to Spark table '$dist' success.")
    }
    afterProcess(item)
  }


}
