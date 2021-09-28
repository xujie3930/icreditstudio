package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.BaseWorker
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.HDFSUtils
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
 * <p>
 * 日期： 2020/9/17
 * <p>
 * 时间： 15:48
 * <p>
 * 星期： 星期四
 * <p>
 * 描述：
 * <p>
 * 作者： zhaokui
 **/
trait HDFSInputWorker extends BaseWorker {

  protected def loadAsText(path: String, nullable: Boolean)(implicit sc: SparkContext): RDD[String] = {
    if (nullable && !HDFSUtils.apply.exists(path)) {
      sc.emptyRDD[String]
    } else {
      sc.textFile(path)
    }
  }
}
