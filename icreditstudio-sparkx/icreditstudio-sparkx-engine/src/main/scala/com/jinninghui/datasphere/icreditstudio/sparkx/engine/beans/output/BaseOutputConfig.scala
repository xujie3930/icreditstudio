package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.output

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.{BaseConfig, NodeTypes}
import org.apache.commons.lang3.StringUtils

class BaseOutputConfig extends BaseConfig {
  tag = NodeTypes.outputs.toString

  override def nameCheck(): Unit = {
    super.nameCheck()
    require(StringUtils.isNotBlank(this.`type`), s"In node '${this.tag}', 'type' is required in item '${this.name}'!")
  }
}
