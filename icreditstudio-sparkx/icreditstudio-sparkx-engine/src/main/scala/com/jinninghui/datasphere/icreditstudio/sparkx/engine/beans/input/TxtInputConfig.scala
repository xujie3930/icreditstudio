package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input.HDFSTxtInputWorker

class TxtInputConfig extends FileInputConfig {
  setWorkerClass(classOf[HDFSTxtInputWorker].getName)
}
