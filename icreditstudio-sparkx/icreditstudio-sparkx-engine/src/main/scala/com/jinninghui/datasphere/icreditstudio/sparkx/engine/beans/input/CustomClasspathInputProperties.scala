package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input.CustomClasspathInputWorker

class CustomClasspathInputProperties extends CustomInputProperties {
  setWorkerClass(classOf[CustomClasspathInputWorker].getName)
}
