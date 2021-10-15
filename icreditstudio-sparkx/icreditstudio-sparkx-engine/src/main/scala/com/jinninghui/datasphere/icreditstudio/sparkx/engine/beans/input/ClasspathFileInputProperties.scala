package com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.input.ClasspathFileInputWorker

class ClasspathFileInputProperties extends FileInputProperties {
  setWorkerClass(classOf[ClasspathFileInputWorker].getName)
}
