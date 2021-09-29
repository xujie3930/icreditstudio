package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input.FileInputConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.{AppUtil, HDFSUtils}
import org.apache.spark.sql.SparkSession

object HDFSTxtInputWorker {
  def apply(): HDFSTxtInputWorker = new HDFSTxtInputWorker()
}

class HDFSTxtInputWorker extends HDFSInputWorker {
  /**
   * 加载数据
   *
   * @param bean InputItemBean
   * @param ss   SparkSession
   */
  override def process(bean: BaseConfig)(implicit ss: SparkSession): Unit = {
    val item = bean.asInstanceOf[FileInputConfig]
    val data = HDFSUtils.apply.loadHdfsTXT(item.path, item.fs, item.nullable)(ss.sparkContext)
    AppUtil.rddToTable(data, item.fs, item.columns, item.name)
    afterProcess(item)
  }
}
