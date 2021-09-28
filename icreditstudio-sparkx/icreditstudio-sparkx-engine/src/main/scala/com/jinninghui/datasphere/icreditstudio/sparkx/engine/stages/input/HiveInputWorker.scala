package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input.HiveInputConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.BaseWorker
import org.apache.spark.sql.SparkSession

import scala.collection.JavaConversions._

/**
 * <p>
 * 日期： 2019/11/22
 * <p>
 * 时间： 15:45
 * <p>
 * 星期：
 * <p>
 * 描述：
 * <p>
 * 作者： zhaokui
 *
 **/
object HiveInputWorker {
  def apply: HiveInputWorker = new HiveInputWorker()
}

class HiveInputWorker extends BaseWorker {
  override def process(bean: BaseConfig)(implicit ss: SparkSession): Unit = {
    val item = bean.asInstanceOf[HiveInputConfig]
    Option(item.dbtable).filter(_.nonEmpty).foreach(lst => {
      lst.foreach { case (src, dist) =>
        ss.catalog.refreshTable(s"${item.database}.$src")
        ss.table(s"${item.database}.$src").createOrReplaceTempView(dist)
        logger.info(s"load hive table '$src' to Spark table '$dist' success.")
      }
    })
    afterProcess(item)
  }
}
