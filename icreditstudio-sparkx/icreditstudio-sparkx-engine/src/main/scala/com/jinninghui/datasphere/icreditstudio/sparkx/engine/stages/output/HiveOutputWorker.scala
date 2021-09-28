package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.output

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.output.HiveOutputConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.BaseWorker
import org.apache.spark.sql.SparkSession

import scala.collection.JavaConversions._

/**
 * <p>
 * 日期： 2019/11/22
 * <p>
 * 时间： 16:20
 * <p>
 * 星期：
 * <p>
 * 描述：
 * <p>
 * 作者： zhaokui
 *
 **/
object HiveOutputWorker {
  def apply: HiveOutputWorker = new HiveOutputWorker()
}

class HiveOutputWorker extends BaseWorker {
  override def process(config: BaseConfig)(implicit ss: SparkSession): Unit = {
    // TODO 支持分区、分桶
    val item = config.asInstanceOf[HiveOutputConfig]
    item.tables.foreach { case (src, dist) =>
      // ss.catalog.refreshTable(s"${item.database}.$dist")
      ss.table(src).write.mode(item.mode).format("Hive").saveAsTable(s"${item.database}.$dist")
      logger.info(s"hive output, saved $src to ${item.database}.$dist success.")
    }
  }
}
