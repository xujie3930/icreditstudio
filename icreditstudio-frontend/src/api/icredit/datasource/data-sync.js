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

// 同步任务定义详情
const dataSyncDefineDetial = params =>
  postAction('/datasync/taskDefineInfo', params)

// 同步任务定义详情
const dataSyncBuildDetial = params =>
  postAction('/datasync/taskDefineInfo', params)

export default {
  dataSyncAdd,
  dataSyncDisabled,
  dataSyncDefineDetial,
  dataSyncBuildDetial
}
