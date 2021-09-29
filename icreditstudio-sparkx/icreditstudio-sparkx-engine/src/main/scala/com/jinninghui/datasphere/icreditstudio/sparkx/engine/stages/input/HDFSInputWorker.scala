package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.BaseWorker
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.HDFSUtils
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

trait HDFSInputWorker extends BaseWorker {

  protected def loadAsText(path: String, nullable: Boolean)(implicit sc: SparkContext): RDD[String] = {
    if (nullable && !HDFSUtils.apply.exists(path)) {
      sc.emptyRDD[String]
    } else {
      sc.textFile(path)
    }
  }
}
