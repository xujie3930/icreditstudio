/*
 * @Author: lizheng
 * @Description: 查看日志
 * @Date: 2021-09-26
 */
export default that => ({
  refName: 'schedule-view-log',
  id: 'viewLog',
  isBorder: true,
  hasPage: false,
  group: [
    {
      type: 'text',
      label: '任务名称',
      prop: 'taskInstanceName'
    },
    {
      type: 'slot',
      label: '执行时间',
      prop: 'taskInstanceExecTime'
    },
    {
      type: 'text',
      label: '执行状态',
      prop: 'taskInstanceState'
    },
    {
      type: 'text',
      label: '执行时长(秒)',
      prop: 'taskInstanceExecDuration'
    },
    {
      type: 'text',
      label: '同步数据量(条)',
      prop: 'totalSyncInstanceNum'
    },
    {
      type: 'text',
      label: '质检处理数据量',
      prop: 'totalQualityCheckInstanceNum'
    },
    {
      type: 'operation',
      label: '操作',
      prop: 'operation',
      width: '250px',
      fixed: 'right',
      operationList: [
        {
          func: that.handleViewLogDetail,
          label: '查看日志',
          key: 'viewLog'
        }
      ]
    }
  ]
})
