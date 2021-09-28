package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.Transform

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.transform.CustomTransformConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.BaseWorker
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.custom.CustomBaseTransform
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.ReflectUtils
import org.apache.spark.sql.SparkSession

/**
 * <p>
 * 日期： 2019/11/22
 * <p>
 * 时间： 15:43
 * <p>
 * 星期：
 * <p>
 * 描述：
 * <p>
 * 作者： zhaokui
 *
 **/
object CustomTransformWorker {
  def apply: CustomTransformWorker = new CustomTransformWorker()
}

class CustomTransformWorker extends BaseWorker {
  override def process(item: BaseConfig)(implicit ss: SparkSession): Unit = {
    val config = item.asInstanceOf[CustomTransformConfig]
    ReflectUtils.apply.getInstance[CustomBaseTransform](config.clazz).doProcess(config)
    if (ss.catalog.tableExists(item.name)) {
      afterProcess(item)
    }
  }
}
