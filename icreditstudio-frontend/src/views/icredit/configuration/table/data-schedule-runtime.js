/*
 * @Author: lizheng
 * @Description: 调度中心首页-一天运行时长排行
 * @Date: 2021-09-23
 */

export default {
  refName: 'data-schedule-runtime',
  id: 'data-schedule-runtime',
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
      label: '运行时长(分)',
      prop: 'taskBuildMode'
    },
    {
      type: 'text',
      label: '任务类型',
      prop: 'execMode'
    }
  ]
}
