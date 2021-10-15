package com.jinninghui.datasphere.icreditstudio.sparkx.engine

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans._
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.input._
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.transform._
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.output._

object ConfigMapping {
  // input 支持的类型
  val inputBeans = Map(InputTypes.classpathFile.toString -> classOf[ClasspathFileInputProperties],
    InputTypes.classpathFile.toString -> classOf[ClasspathFileInputProperties],
    InputTypes.hdfscsv.toString -> classOf[HDFSCsvInputProperties],
    InputTypes.hdfsfile.toString -> classOf[TxtInputProperties],
    InputTypes.hive.toString -> classOf[HiveInputProperties],
    InputTypes.jdbc.toString -> classOf[JDBCInputProperties],
    InputTypes.kafka.toString -> classOf[KafkaInputProperties],
    InputTypes.customClasspath.toString -> classOf[CustomClasspathInputProperties],
    InputTypes.customHdfs.toString -> classOf[CustomHDFSInputProperties]
  )

  // process 支持的类型
  val processBeans = Map(ProcessTypes.sql.toString -> classOf[SQLTransformProperties],
    ProcessTypes.clazz.toString -> classOf[CustomTransformProperties]
  )
  // output
  val outputBeans = Map(OutputTypes.hive.toString -> classOf[HiveOutputProperties],
    OutputTypes.jdbc.toString -> classOf[JDBCOutputProperties],
    OutputTypes.kafkaField.toString -> classOf[KafkaFieldOutputProperties],
    OutputTypes.kafkaJson.toString -> classOf[KafkaJsonOutputProperties],
    OutputTypes.hdfsfile.toString -> classOf[HDFSOutputProperties]
  )

  def getInputConfigClass(typeName: String): Class[_ <: BaseProperties] = {
    val config = inputBeans.getOrElse(typeName, null)
    config
  }

  def getProcessConfigClass(typeName: String): Class[_ <: BaseTransformProperties] = {
    val config = processBeans.getOrElse(typeName, null)
    config
  }

  def getOutputConfigClass(typeName: String): Class[_ <: BaseOutputProperties] = {
    val config = outputBeans.getOrElse(typeName, null)
    config
  }
}
