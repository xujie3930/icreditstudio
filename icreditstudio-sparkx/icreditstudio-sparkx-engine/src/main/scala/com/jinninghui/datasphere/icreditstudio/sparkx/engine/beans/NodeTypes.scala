package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans

object NodeTypes extends Enumeration {
  type NodeTypes = Value
  val root,
  //  和 BusinessConfig 中属性一致
  inputs,
  //  和 BusinessConfig 中属性一致
  outputs,
  //  和 BusinessConfig 中属性一致
  processes = Value
}
