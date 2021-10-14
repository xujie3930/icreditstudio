/*
 * @Description: 调度中心
 * @Date: 2021-10-14
 */

import { getAction, postAction } from '@/api'

// 首页-近72小时内调度情况
const dataScheduleHomeRough = params =>
  postAction('/scheduler/homepage/rough', params)

// 首页-当日运行情况
const dataScheduleHomeRuntime = params =>
  postAction('/scheduler/homepage/situation', params)

// 首页-调度任务数量情况
const dataScheduleHomeCount = params =>
  getAction('/scheduler/homepage/situation', params)

// 首页-近一天运行时长排行
const dataScheduleHomeErrDay = params =>
  postAction('/scheduler/homepage/situation', params)

// 首页-近一月运行出错排行
const dataScheduleHomeErrMonth = params =>
  postAction('/scheduler/homepage/situation', params)

export default {
  dataScheduleHomeRough,
  dataScheduleHomeRuntime,
  dataScheduleHomeCount,
  dataScheduleHomeErrDay,
  dataScheduleHomeErrMonth
}
