package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.output

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.{BaseProperties, HDFSOutputFormats}
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.output.HDFSOutputProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.BaseWorker
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.HDFSUtils
import org.apache.spark.sql.SparkSession

object HdfsOutputWorker {
  def apply: HdfsOutputWorker = new HdfsOutputWorker()
}

class HdfsOutputWorker extends BaseWorker {
  override def process(config: BaseProperties)(implicit ss: SparkSession): Unit = {
    val item = config.asInstanceOf[HDFSOutputProperties]
    var df = ss.table(item.srcName)
    Option(item.partations).filter(part => part > 0).foreach(part => df = df.repartition(part))
    logger.info(s"try to save to ${item.path}.")
    item.format match {
      case f if f == HDFSOutputFormats.txt.toString => HDFSUtils.apply.saveAsTXT(df, item.path, item.fs)(ss.sparkContext)
      case f if f == HDFSOutputFormats.lzo.toString => HDFSUtils.apply.saveAsLZO(df, item.path, item.fs)(ss.sparkContext)
      case f if f == HDFSOutputFormats.csv.toString => HDFSUtils.apply.saveAsCSV(df, item.path)(ss.sparkContext)
      case f if f == HDFSOutputFormats.json.toString => HDFSUtils.apply.saveAsJSON(df, item.path)(ss.sparkContext)
      case f if f == HDFSOutputFormats.parquet.toString => HDFSUtils.apply.saveAsParquet(df, item.path)(ss.sparkContext)
      case _ => throw new Exception(s"in outputs, unsupport format '${item.format}' for output '${item.srcName}'.")
    }
  }
}