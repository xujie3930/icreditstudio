package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input.ClasspathFileInputWorker

class ClasspathFileInputConfig extends FileInputConfig {
  setWorkerClass(classOf[ClasspathFileInputWorker].getName)
}
