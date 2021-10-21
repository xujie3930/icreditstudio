package com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.Transform

import com.jinninghui.datasphere.icreditstudio.sparkx.engine.utils.{AppUtil, DimUtils}
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.BaseProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.beans.transform.SQLTransformProperties
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.constants.SysConstants
import com.jinninghui.datasphere.icreditstudio.sparkx.engine.stages.BaseWorker
import org.apache.commons.lang3.StringUtils
import org.apache.spark.sql.SparkSession

object SQLTransformWorker {
  def apply: SQLTransformWorker = new SQLTransformWorker()
}

class SQLTransformWorker extends BaseWorker {


  override def process(item: BaseProperties)(implicit ss: SparkSession): Unit = {
    initVariables()
    val process = item.asInstanceOf[SQLTransformProperties]
    var sql = process.sql
    if (StringUtils.isNotBlank(process.dimKey)) {
      sql = getDimSQLs(process.sql, process.dimKey, process.allPlaceholder).mkString(" union all ")
    }
    logger.info(s"sql script:${System.lineSeparator()}$sql")
    ss.sql(sql).createOrReplaceTempView(process.name)
    afterProcess(process)
  }

  def getDimSQLs(sql: String, dimkey: String, allPlaceholder: String) = {
    val regexMatchMap = AppUtil.regexFindWithRule(SysConstants.apply.VARIABLE_REGEX, sql).zip(AppUtil.regexFindWithoutRule(SysConstants.apply.VARIABLE_REGEX, sql))
    val dimFields = regexMatchMap.filter(_._2 == SysConstants.apply.DIM_FIELDS_EXPR).head._1
    val group = regexMatchMap.filter(_._2 == SysConstants.apply.DIM_GROUP_EXPR).head._1
    val dims = dimkey.split(",", -1).filter(StringUtils.isNotBlank).map(_.trim).toList
    val dimMap = DimUtils.getCombinations(dims, allPlaceholder)
    dimMap.map { case (k, v) =>
      sql.replace(dimFields, k).replace(group, v)
    }
  }
}
