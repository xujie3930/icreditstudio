package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input.FileInputProperties
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
  override def process(bean: BaseProperties)(implicit ss: SparkSession): Unit = {
    val item = bean.asInstanceOf[FileInputProperties]
    val data = HDFSUtils.apply.loadHdfsTXT(item.path, item.fs, item.nullable)(ss.sparkContext)
    AppUtil.rddToTable(data, item.fs, item.columns, item.name)
    afterProcess(item)
  }
}
