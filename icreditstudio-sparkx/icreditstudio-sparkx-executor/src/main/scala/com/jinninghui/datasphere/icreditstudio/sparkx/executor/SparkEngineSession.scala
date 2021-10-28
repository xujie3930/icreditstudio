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
 */

package com.jinninghui.datasphere.icreditstudio.sparkx.executor

import org.apache.spark.SparkContext
import org.apache.spark.sql.{SQLContext, SparkSession}

import java.io.File

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.common
 * ClassName: SparkEngineSession
 * Description:  SparkEngineSession类
 * Date: 2021/9/17 2:20 下午
 *
 * @author liyanhui
 */
case class SparkEngineSession(sparkContext: SparkContext,
                              sqlContext: SQLContext,
                              sparkSession: SparkSession,
                              outputDir: File)