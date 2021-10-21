package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.Transform

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.transform.CustomTransformProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.BaseWorker
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.custom.CustomBaseTransform
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.ReflectUtils
import org.apache.spark.sql.SparkSession

object CustomTransformWorker {
  def apply: CustomTransformWorker = new CustomTransformWorker()
}

class CustomTransformWorker extends BaseWorker {
  override def process(item: BaseProperties)(implicit ss: SparkSession): Unit = {
    val config = item.asInstanceOf[CustomTransformProperties]
    ReflectUtils.apply.getInstance[CustomBaseTransform](config.clazz).doProcess(config)
    if (ss.catalog.tableExists(item.name)) {
      afterProcess(item)
    }
  }
}
