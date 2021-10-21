package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input.FileInputProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.BaseWorker
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.{AppUtil, HDFSUtils}
import org.apache.spark.sql.SparkSession

object ClasspathFileInputWorker {
  def apply(): ClasspathFileInputWorker = new ClasspathFileInputWorker()
}

class ClasspathFileInputWorker extends BaseWorker {
  /**
   * 加载数据
   *
   * @param bean InputItemBean
   * @param ss   SparkSession
   */
  override def process(bean: BaseProperties)(implicit ss: SparkSession): Unit = {
    val item = bean.asInstanceOf[FileInputProperties]
    val data = HDFSUtils.apply.loadClasspathFile(item.path, item.fs, item.nullable)(ss.sparkContext)
    AppUtil.rddToTable(data, item.fs, item.columns, item.name)
    afterProcess(item)
  }
}
