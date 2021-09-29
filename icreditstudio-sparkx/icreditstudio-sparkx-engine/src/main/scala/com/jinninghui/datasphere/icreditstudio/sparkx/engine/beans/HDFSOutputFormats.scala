package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans

object HDFSOutputFormats extends Enumeration {
  type HDFSOutputFormats = Value
  val csv, txt, lzo, json, parquet = Value
}
