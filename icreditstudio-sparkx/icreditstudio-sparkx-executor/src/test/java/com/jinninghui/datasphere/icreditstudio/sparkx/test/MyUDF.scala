package com.jinninghui.datasphere.icreditstudio.sparkx.test

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.function.BaseUDF
import org.apache.spark.sql.{SparkSession, functions}

class MyUDF extends BaseUDF {
  def addSuffix(str: String): String = {
    s"${str}_suffix"
  }

  override def setup()(implicit ss: SparkSession): Unit = {
    ss.udf.register("myudf1", functions.udf[String, String](addSuffix))
  }
}


