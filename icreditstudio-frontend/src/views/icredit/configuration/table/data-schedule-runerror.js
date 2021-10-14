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
      prop: 'id'
    },
    {
      type: 'text',
      label: '任务名称',
      prop: 'name'
    },
    {
      type: 'text',
      label: '出错次数',
      prop: 'errorNum'
    },
    {
      type: 'text',
      label: '任务类型',
      prop: 'scheduleType',
      formatter: (row, col, val) => (val ? '数据开发' : '数据开发')
    }
  ]
}
