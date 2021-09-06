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

// 同步任务构建详情
const dataSyncBuildDetial = params =>
  postAction('/datasync/taskBuildInfo', params)

// 同步任务调度详情
const dataSyncDispatchDetial = params =>
  postAction('/datasync/taskScheduleInfo', params)

export default {
  dataSyncAdd,
  dataSyncDelete,
  dataSyncDisabled,
  dataSyncEnabled,
  dataSyncRun,
  dataSyncStop,
  dataSyncDefineDetial,
  dataSyncBuildDetial,
  dataSyncDispatchDetial
}
