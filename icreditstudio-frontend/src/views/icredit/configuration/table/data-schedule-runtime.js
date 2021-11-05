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
  maxHeight: 224,
  group: [
    {
      type: 'text',
      label: '任务id',
      prop: 'id',
      width: 200
    },
    {
      type: 'text',
      label: '任务名称',
      prop: 'name'
    },
    {
      type: 'text',
      label: '运行时长(分)',
      prop: 'speedTime',
      width: 120
    },
    {
      type: 'text',
      label: '任务类型',
      prop: 'scheduleType',
      width: 120,
      formatter: (row, col, val) => (val ? '数据开发' : '数据同步')
    }
  ]
}
