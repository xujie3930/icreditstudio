package com.jinninghui.datasphere.icreditstudio.sparkx.engine.function

import com.jinninghui.datasphere.icreditstudio.sparkx.common.Logging
import org.apache.spark.sql.SparkSession

trait BaseUDF extends Logging {
  def setup()(implicit ss: SparkSession): Unit
}
