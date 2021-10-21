package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans

object InputTypes extends Enumeration {
  type InputType = Value
  val classpathFile, customHdfs, customClasspath, hdfscsv, hdfsfile, hive, jdbc, kafka = Value
}
