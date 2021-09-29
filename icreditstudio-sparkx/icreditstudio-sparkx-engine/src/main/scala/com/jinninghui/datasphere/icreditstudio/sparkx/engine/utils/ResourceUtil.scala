package com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils

import java.net.URL

import com.jinninghui.datasphere.icreditstudio.sparkx.common.Logging
import org.apache.commons.lang3.StringUtils

object ResourceUtil {
  def apply: ResourceUtil = new ResourceUtil()
}

class ResourceUtil extends Logging {
  var CLUSTER_FLAG = true

  def get(confFile: String): URL = {
    var url: URL = null
    var fileName = confFile
    Option(confFile).filter(StringUtils.isNotBlank).foreach(conf => {
      url = this.getClass.getClassLoader.getResource(confFile)
    })
    Option(confFile).filter(c => null == url).foreach(c => throw new RuntimeException(s"file $confFile not exist."))
    logger.info(s"confFileName = [$fileName], url = [$url].")
    url
  }

}
