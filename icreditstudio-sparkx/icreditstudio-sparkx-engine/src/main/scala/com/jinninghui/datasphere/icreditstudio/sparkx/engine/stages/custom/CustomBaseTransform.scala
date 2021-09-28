package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.custom

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.transform.CustomTransformConfig
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.BaseWorker
import org.apache.spark.sql.SparkSession

/**
 * <p>
 * 日期： 2020/7/6
 * <p>
 * 时间： 17:52
 * <p>
 * 星期： 星期一
 * <p>
 * 描述：
 * <p>
 * 作者： zhaokui
 *
 **/
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
