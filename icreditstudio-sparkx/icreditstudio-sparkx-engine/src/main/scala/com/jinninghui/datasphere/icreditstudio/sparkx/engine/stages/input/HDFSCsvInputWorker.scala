package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseProperties
import org.apache.spark.sql.SparkSession

class HDFSCsvInputWorker extends HDFSInputWorker {
  /**
   * 加载数据
   *
   * @param bean BaseInputConfig
   * @param ss   SparkSession
   */
  // TODO
  override def process(bean: BaseProperties)(implicit ss: SparkSession): Unit = ???
}
