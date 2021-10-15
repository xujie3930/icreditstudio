package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input.CustomInputProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.custom.CustomBaseInput
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.ReflectUtils
import org.apache.spark.sql.SparkSession

class CustomFileInputWorker extends HDFSInputWorker {
  /**
   * 加载数据
   *
   * @param bean BaseInputConfig
   * @param ss   SparkSession
   */
  override def process(bean: BaseProperties)(implicit ss: SparkSession): Unit = {
    val item = bean.asInstanceOf[CustomInputProperties]
    // 如果数据 path 不存在，且配置定义该数据允许为空，则给一个空的 rdd
    val rdd = loadAsText(item.path, item.nullable)(ss.sparkContext)

    ReflectUtils.apply.getInstance[CustomBaseInput](item.clazz).process(rdd, item.getName)
    afterProcess(item)
  }
}
