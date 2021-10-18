/*
 * @Description: 调度中心
 * @Date: 2021-10-14
 */

import { postAction } from '@/api'

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

export default {
  dataScheduleHomeRough,
  dataScheduleHomeRuntime,
  dataScheduleHomeCount,
  dataScheduleHomeRunDay,
  dataScheduleHomeErrMonth,

  dataScheduleSyncList
}
