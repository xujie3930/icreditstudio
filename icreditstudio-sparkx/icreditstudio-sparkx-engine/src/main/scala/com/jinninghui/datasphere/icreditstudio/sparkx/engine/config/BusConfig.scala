package com.jinninghui.datasphere.icreditstudio.sparkx.engine.config

import java.text.SimpleDateFormat
import java.util.Date

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.Context
import org.apache.commons.lang3.time.DateFormatUtils

object BusConfig {
  private var baseWorkdir: String = _
  private var eventDate: String = DateFormatUtils.format(new Date(), "yyyyMMdd")
  private var eventDate10: String = _
  private var busConfig: Context = _

  def apply: BusConfig = new BusConfig()
}

class BusConfig extends BaseConfigLoader(Options.parse) {


  /**
   * 解析输入参数
   *
   * @param options 输入参数
   * @return 参数封装的BusConfigBean
   */
  def parseOptions(options: Array[String]): Context = {
    parse(options)
    val configFile = getString("app.opts.config")
    BusConfig.eventDate = getString("app.opts.date")
    val isDebug = getBool("app.opts.debug")
    val config = AppConfigLoader.loadAppConfig(configFile)
    config.configFile = configFile
    config.eventDate = BusConfig.eventDate
    config.isDebug = isDebug
    BusConfig.busConfig = config
    BusConfig.baseWorkdir = config.persistDir
    logger.debug(s"pipeline config loaded from $configFile")
    config
  }

  /**
   * 获取工作目录
   *
   * @return 工作目录
   */
  def getBaseWorkdir(): String = {
    BusConfig.baseWorkdir
  }

  def getEventDate8(): String = {
    BusConfig.eventDate
  }

  def getEventDateByDate(): Date = {
    new SimpleDateFormat("yyyyMMdd").parse(BusConfig.eventDate)
  }

  def getEventDate10(): String = {
    val date = getEventDateByDate()
    val str = new SimpleDateFormat("yyyy-MM-dd").format(date)
    str
  }

  def getConfig(): Context = {
    BusConfig.busConfig
  }
}
