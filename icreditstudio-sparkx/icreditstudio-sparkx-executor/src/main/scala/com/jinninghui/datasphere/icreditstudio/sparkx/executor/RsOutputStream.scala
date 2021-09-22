/*
 * Copyright 2019 WeBank
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jinninghui.datasphere.icreditstudio.sparkx.executor

import java.io.OutputStream

import scala.collection.mutable.ArrayBuffer


class RsOutputStream extends OutputStream with SparkLogging {
  private val line = ArrayBuffer[Byte]()
  private var isReady = false
  private var writer: ResultSetWriter[_ <: MetaData, _ <: Record] = _
  override def write(b: Int) = if(isReady) synchronized {
    if(writer != null) {
      if (b == '\n') {
        val outStr = new String(line.toArray,"UTF-8")
        writer.addRecord(new LineRecord(outStr))
        line.clear()
      } else line += b.toByte
    }else{
       warn("writer is null")
    }
  }

  def ready() = isReady = true

  override def flush(): Unit = if(writer != null && line.nonEmpty) {
    val outStr = new String(line.toArray,"UTF-8")
    writer.addRecord(new LineRecord(outStr))
    line.clear()
  }

  override def toString = if(writer != null) writer.toString() else null

  override def close() = if(writer != null) {
    flush()
    writer.close()
    writer = null
  }
}
