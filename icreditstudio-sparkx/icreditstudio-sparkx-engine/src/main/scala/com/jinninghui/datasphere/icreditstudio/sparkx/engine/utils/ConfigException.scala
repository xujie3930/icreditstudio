package com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils

import com.jinninghui.datasphere.icreditstudio.sparkx.common.Logging

class ConfigException extends Throwable with Logging {
  def this(msg: String, e: Throwable) {
    this()
    logger.error(msg, e)
  }

  def this(msg: String) {
    this()
    logger.error(msg)
  }
}