/*
 * @Author: lizheng
 * @Description: 调度中心首页-近一个月运行出错排行
 * @Date: 2021-09-23
 */

export default {
  refName: 'data-schedule-run-error',
  id: 'data-schedule-run-error',
  isBorder: true,
  hasPage: false,
  isCustomEmpty: false,
  group: [
    {
      type: 'text',
      label: '任务id',
      prop: 'taskName'
    },
    {
      type: 'text',
      label: '任务名称',
      prop: 'taskName'
    },
    {
      type: 'text',
      label: '出错次数',
      prop: 'taskBuildMode'
    },
    {
      type: 'text',
      label: '任务类型',
      prop: 'execMode'
    }
  ]
}
