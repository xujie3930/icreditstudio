package com.jinninghui.datasphere.icreditstudio.sparkx.common

import org.slf4j.LoggerFactory

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.common
 * ClassName: SparkLogging
 * Description:  SparkLogging类
 * Date: 2021/9/17 2:24 下午
 *
 * @author liyanhui
 */
trait Logging {
  protected lazy implicit val logger = LoggerFactory.getLogger(getClass)

  def trace(message: => String) = {
    if (logger.isTraceEnabled) {
      logger.trace(message)
    }
  }

  def debug(message: => String): Unit = {
    if (logger.isDebugEnabled) {
      logger.debug(message)
    }
  }

  def info(message: => String): Unit = {
    if (logger.isInfoEnabled) {
      logger.info(message)
    }
  }

  def info(message: => String, t: Throwable): Unit = {
    logger.info(message, t)
  }

  def warn(message: => String): Unit = {
    logger.warn(message)
  }

  def warn(message: => String, t: Throwable): Unit = {
    logger.warn(message, t)
  }

  def error(message: => String, t: Throwable): Unit = {
    logger.error(message, t)
  }

  def error(message: => String): Unit = {
    logger.error(message)
  }
}
