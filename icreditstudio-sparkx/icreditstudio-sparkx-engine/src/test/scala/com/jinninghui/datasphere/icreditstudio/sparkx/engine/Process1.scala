package com.jinninghui.datasphere.icreditstudio.sparkx.engine

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.custom.CustomBaseInput
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

class Process1 extends CustomBaseInput {
  override def process(rdd: RDD[String], name: String)(implicit ss: SparkSession): Unit = {
    val filter = rdd.map(_.split(",", -1).map(_.trim)).filter(_.size == 3)
      .map(cols => (cols(0), cols(1), cols(2)))
    import ss.implicits._
    filter.toDF("name", "gend", "age").createOrReplaceTempView(name)
  }
}
