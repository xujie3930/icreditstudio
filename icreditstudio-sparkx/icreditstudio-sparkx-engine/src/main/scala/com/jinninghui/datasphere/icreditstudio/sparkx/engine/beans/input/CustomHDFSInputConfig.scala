package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input.CustomFileInputWorker

class CustomHDFSInputConfig extends CustomInputConfig {
  setWorkerClass(classOf[CustomFileInputWorker].getName)
}
