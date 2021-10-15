package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.output

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.output.HiveOutputProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.BaseWorker
import org.apache.spark.sql.SparkSession

import scala.collection.JavaConversions._

object HiveOutputWorker {
  def apply: HiveOutputWorker = new HiveOutputWorker()
}

class HiveOutputWorker extends BaseWorker {
  override def process(config: BaseProperties)(implicit ss: SparkSession): Unit = {
    // TODO 支持分区、分桶
    val item = config.asInstanceOf[HiveOutputProperties]
    item.tables.foreach { case (src, dist) =>
      // ss.catalog.refreshTable(s"${item.database}.$dist")
      ss.table(src).write.mode(item.mode).format("Hive").saveAsTable(s"${item.database}.$dist")
      logger.info(s"hive output, saved $src to ${item.database}.$dist success.")
    }
  }
}
