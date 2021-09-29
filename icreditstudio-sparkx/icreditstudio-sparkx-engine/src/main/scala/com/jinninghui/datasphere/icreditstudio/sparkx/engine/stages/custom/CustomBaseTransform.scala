package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.custom

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.transform.CustomTransformConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.BaseWorker
import org.apache.spark.sql.SparkSession

trait CustomBaseTransform extends BaseWorker {
  def process(bean: BaseConfig)(implicit ss: SparkSession): Unit = {}

  /**
   * 自定义处理任务，生成 SparkSQL 表
   *
   * @param bean
   * @param ss
   */
  def doProcess(bean: CustomTransformConfig)(implicit ss: SparkSession): Unit
}
