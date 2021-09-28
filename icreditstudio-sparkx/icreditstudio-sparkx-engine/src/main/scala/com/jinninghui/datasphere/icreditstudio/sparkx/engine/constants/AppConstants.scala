package com.jinninghui.datasphere.icreditstudio.sparkx.engine.constants

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.config.BusConfig
import org.apache.commons.lang3.time.DateFormatUtils

/**
 * <p>
 * 日期： 2019/11/22
 * <p>
 * 时间： 11:30
 * <p>
 * 星期：
 * <p>
 * 描述：
 * <p>
 * 作者： zhaokui
 *
 **/
object AppConstants {
  var variables = new java.util.HashMap[String, String]()

  def apply: AppConstants = new AppConstants()
}

class AppConstants {
  val EVENT_DATE: String = if (null == BusConfig.apply.getEventDate8()) DateFormatUtils.format(new java.util.Date(), "yyyyMMdd") else BusConfig.apply.getEventDate8()
  val EVENT_DATE10: String = if (null == BusConfig.apply.getEventDate10()) DateFormatUtils.format(new java.util.Date(), "yyyy-MM-dd") else BusConfig.apply.getEventDate10()
  /*
   * 排除 sonar 运行时添加的变量
   */
  val SONAR_VARIABLE: Set[String] = Set("$jacocoData")
}
