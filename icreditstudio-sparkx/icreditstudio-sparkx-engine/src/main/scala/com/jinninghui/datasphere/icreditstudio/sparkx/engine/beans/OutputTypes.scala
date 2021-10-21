package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans

object OutputTypes extends Enumeration {
  type OutputType = Value
  val kafkaJson, kafkaField, hdfsfile, hive, jdbc = Value
}
