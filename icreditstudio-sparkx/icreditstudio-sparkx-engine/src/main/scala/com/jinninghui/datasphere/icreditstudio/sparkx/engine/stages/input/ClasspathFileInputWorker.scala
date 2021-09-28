package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input.FileInputConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.BaseWorker
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.{AppUtil, HDFSUtils}
import org.apache.spark.sql.SparkSession

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
 **/
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
  override def process(bean: BaseConfig)(implicit ss: SparkSession): Unit = {
    val item = bean.asInstanceOf[FileInputConfig]
    val data = HDFSUtils.apply.loadClasspathFile(item.path, item.fs, item.nullable)(ss.sparkContext)
    AppUtil.rddToTable(data, item.fs, item.columns, item.name)
    afterProcess(item)
  }
}
