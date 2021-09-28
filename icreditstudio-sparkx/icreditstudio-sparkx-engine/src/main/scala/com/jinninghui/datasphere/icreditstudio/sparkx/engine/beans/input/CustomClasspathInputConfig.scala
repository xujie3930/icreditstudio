package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input.CustomClasspathInputWorker

class CustomClasspathInputConfig extends CustomInputConfig {
  setWorkerClass(classOf[CustomClasspathInputWorker].getName)
}
