package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input.CustomFileInputWorker

class CustomHDFSInputProperties extends CustomInputProperties {
  setWorkerClass(classOf[CustomFileInputWorker].getName)
}
