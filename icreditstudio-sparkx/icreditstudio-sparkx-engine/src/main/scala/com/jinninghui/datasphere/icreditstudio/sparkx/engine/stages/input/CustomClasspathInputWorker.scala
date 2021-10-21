package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input.CustomInputProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.BaseWorker
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.custom.CustomBaseInput
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.{HDFSUtils, ReflectUtils}
import org.apache.spark.sql.SparkSession

class CustomClasspathInputWorker extends BaseWorker {

  override def process(bean: BaseProperties)(implicit ss: SparkSession): Unit = {
    val item = bean.asInstanceOf[CustomInputProperties]
    val rdd = HDFSUtils.apply.loadClasspathFile(item.path)(ss.sparkContext)
    ReflectUtils.apply.getInstance[CustomBaseInput](item.clazz).process(rdd, item.getName)
    afterProcess(item)
  }
}
