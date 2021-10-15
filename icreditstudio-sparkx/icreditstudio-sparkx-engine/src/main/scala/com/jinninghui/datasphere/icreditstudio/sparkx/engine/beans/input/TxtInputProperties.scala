package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input.HDFSTxtInputWorker

class TxtInputProperties extends FileInputProperties {
  setWorkerClass(classOf[HDFSTxtInputWorker].getName)
}
