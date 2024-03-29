/*
 * @Author: lizheng
 * @Description: 数据同步
 * @Date: 2021-09-01
 */

import { postAction } from '@/api'

// 新增同步任务
const dataSyncAdd = params => postAction('/datasync/save', params)

// 停用同步任务
const dataSyncDisabled = params => postAction('/datasync/stop', params)

// 启用同步任务
const dataSyncEnabled = params => postAction('/datasync/enable', params)

// 删除同步任务
const dataSyncDelete = params => postAction('/datasync/remove', params)

// 立即执行同步任务
const dataSyncRun = params => postAction('/datasync/run', params)

// 停止执行同步任务
const dataSyncStop = params => postAction('/datasync/cease', params)

// 同步任务定义详情
const dataSyncDefineDetial = params =>
  postAction('/datasync/taskDefineInfo', params)

// 同步任务详情
const dataSyncTaskDetial = params =>
  postAction('/datasync/taskDetailInfo', params)

// 同步任务构建详情
const dataSyncBuildDetial = params =>
  postAction('/datasync/taskBuildInfo', params)

// 同步任务调度详情
const dataSyncDispatchDetial = params =>
  postAction('/datasync/taskScheduleInfo', params)

// 数据源模糊搜索
const dataSyncFluzzySearch = params =>
  postAction('/datasource/datasourceSearch', params)

// 数字字典模糊搜索
const dataSyncFluzzyDictionary = params =>
  postAction('/system/code/code/associatedDict', params)

// 数据源目录
const dataSyncCatalog = params =>
  postAction('/datasource/getDatasourceCatalogue', params)

// 数据源目录
const dataSyncTargetSource = params =>
  postAction('/metadata/targetSources', params)

// 识别宽表或执行SQL
const dataSyncGenerateTable = params =>
  postAction('/datasync/generateWideTable', params)

// 关联类型
const dataSyncLinkType = params =>
  postAction('/datasync/dialectAssociatedSupport', params)

// 表字段查询
const dataSyncFieldSearch = params =>
  postAction('/datasource/getTableInfo', params)

// 任务名称重复性校验
const dataSyncVerifyName = params =>
  postAction('/datasync/checkRepeatTaskName', params)

// sql方式识别宽表前置接口-校验表之间是否同一个IP地址
const dataSyncVerifyHost = params =>
  postAction('/datasync/preSqlPositionDataSource', params)

// 关联字典表
const dataSyncDictionary = params =>
  postAction('/datasync/dict/associatedDict', params)

export default {
  dataSyncAdd,
  dataSyncDelete,
  dataSyncDisabled,
  dataSyncEnabled,
  dataSyncRun,
  dataSyncStop,
  dataSyncDefineDetial,
  dataSyncBuildDetial,
  dataSyncDispatchDetial,
  dataSyncFluzzySearch,
  dataSyncFluzzyDictionary,
  dataSyncCatalog,
  dataSyncTargetSource,
  dataSyncGenerateTable,
  dataSyncLinkType,
  dataSyncFieldSearch,
  dataSyncTaskDetial,
  dataSyncVerifyName,
  dataSyncVerifyHost,
  dataSyncDictionary
}
