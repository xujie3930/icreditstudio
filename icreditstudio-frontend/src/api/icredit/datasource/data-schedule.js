/*
 * @Description: 调度中心
 * @Date: 2021-10-14
 */

import { getAction, postAction } from '@/api'

// 首页-近72小时内调度情况
const dataScheduleHomeRough = params =>
  postAction('/dolphinscheduler/homepage/rough', params)

// 首页-当日运行情况
const dataScheduleHomeRuntime = params =>
  postAction('/dolphinscheduler/homepage/situation', params)

// 首页-调度任务数量情况
const dataScheduleHomeCount = params =>
  postAction('/dolphinscheduler/homepage/taskCount', params)

// 首页-近一天运行时长排行
const dataScheduleHomeRunDay = params =>
  postAction('/dolphinscheduler/homepage/runtimeRank', params)

// 首页-近一月运行出错排行
const dataScheduleHomeErrMonth = params =>
  postAction('/dolphinscheduler/homepage/runErrorRank', params)

// 同步任务调度-列表
const dataScheduleSyncList = params =>
  postAction('/dolphinscheduler/dispatch/page', params)

// 同步任务调度-重跑或终止
const dataScheduleSyncOperate = params =>
  postAction('/dolphinscheduler/dispatch/execInstance', params)

// 同步任务调度-历史日志
const dataScheduleSyncHistoryLog = params =>
  postAction('/dolphinscheduler/dispatch/log/page', params)

// 同步任务调度-日志详情
const dataScheduleSyncLogDetail = params =>
  getAction('/dolphinscheduler/log/detail', params)

// 同步任务调度-立即执行
const dataScheduleSyncRun = params =>
  getAction('/dolphinscheduler/dispatch/schedule/nowRun', params)

export default {
  dataScheduleHomeRough,
  dataScheduleHomeRuntime,
  dataScheduleHomeCount,
  dataScheduleHomeRunDay,
  dataScheduleHomeErrMonth,

  dataScheduleSyncList,
  dataScheduleSyncOperate,
  dataScheduleSyncLogDetail,
  dataScheduleSyncHistoryLog,
  dataScheduleSyncRun
}
