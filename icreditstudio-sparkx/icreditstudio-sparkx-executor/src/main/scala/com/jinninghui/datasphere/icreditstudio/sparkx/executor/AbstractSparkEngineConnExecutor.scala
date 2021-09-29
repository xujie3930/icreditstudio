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
 *
 * JNH Tech 2021.09.17
 */

package com.jinninghui.datasphere.icreditstudio.sparkx.executor

import java.util.concurrent.atomic.AtomicLong

import com.jinninghui.datasphere.icreditstudio.sparkx.common.Logging
import org.apache.spark.SparkContext


/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.common
 * ClassName: SparkEngineConnExecutor
 * Description:  SparkEngineConnExecutor类
 * Date: 2021/9/17 2:21 下午
 *
 * @author liyanhui
 */
abstract class AbstractSparkEngineConnExecutor(val sc: SparkContext) extends Logging{

  private var initialized: Boolean = false

  val queryNum = new AtomicLong(0)


  def init(): Unit = {
    info(s"Ready to init Spark Executor!")
    setInitialized()
  }

  protected def setInitialized(inited: Boolean = true): Unit = this.initialized = inited


  protected def getKind: Kind

}
