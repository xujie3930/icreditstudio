package com.jinninghui.datasphere.icreditstudio.sparkx.engine.config

import com.jinninghui.datasphere.icreditstudio.sparkx.common.Logging
import com.typesafe.config.Config

abstract class BaseConfigLoader(optionsParser: Array[String] => Config) extends Logging {
  private var optionsConfig: Config = _

  def getInteger(key: String, default: Int = 0): Int = get(key, default, optionsConfig.getInt)

  def getDouble(key: String, default: Double = 0.0): Double = get(key, default, optionsConfig.getDouble)

  def getString(key: String, default: String = ""): String = get(key, default, optionsConfig.getString)

  def getBool(key: String, default: Boolean = false): Boolean = get(key, default, optionsConfig.getBoolean)

  private def get[T](key: String, default: T, impl: String => T): T = {
    if (optionsConfig.hasPath(key))
      impl(key)
    else {
      logger.debug(s"using default value for $key: $default")
      default
    }
  }

  def parse(options: Array[String]): Unit = {
    optionsConfig = optionsParser(options)
  }
}
