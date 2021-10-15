package com.jinninghui.datasphere.icreditstudio.sparkx.test

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.transform.CustomTransformProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.custom.CustomBaseTransform
import org.apache.spark.sql.SparkSession


class Process2 extends CustomBaseTransform {
  override def doProcess(bean: CustomTransformProperties)(implicit ss: SparkSession): Unit = {
    val rdd = getRDDByTable("table2").map(_.toSeq.map(_.toString)).map(cols => (cols(0), cols(1), cols(2), "ttt"))
    import ss.implicits._
    rdd.toDF("count", "gend", "date", "added").createOrReplaceTempView(bean.getName)
  }
}
