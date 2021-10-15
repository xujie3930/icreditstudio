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
      prop: 'taskName'
    },
    {
      type: 'slot',
      label: '执行时间',
      prop: 'runtime'
    },
    {
      type: 'text',
      label: '执行状态',
      prop: 'sourceTable'
    },
    {
      type: 'slot',
      label: '执行时长(秒)',
      prop: 'fieldChineseName'
    },
    {
      type: 'slot',
      label: '同步数据量(条)',
      prop: 'associateDict'
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
