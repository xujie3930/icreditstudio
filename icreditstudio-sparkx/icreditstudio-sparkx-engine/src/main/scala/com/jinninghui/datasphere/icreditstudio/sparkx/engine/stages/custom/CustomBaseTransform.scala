package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.custom

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.transform.CustomTransformProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.BaseWorker
import org.apache.spark.sql.SparkSession

trait CustomBaseTransform extends BaseWorker {
  def process(bean: BaseProperties)(implicit ss: SparkSession): Unit = {}

  /**
   * 自定义处理任务，生成 SparkSQL 表
   *
   * @param bean
   * @param ss
   */
  def doProcess(bean: CustomTransformProperties)(implicit ss: SparkSession): Unit
}
