/*
 * @Author: lizheng
 * @Description: 查看日志
 * @Date: 2021-09-26
 */
export default {
  refName: 'scheduleViewLog',
  id: 'viewLog',
  isBorder: true,
  hasPage: true,
  maxHeight: 500,
  group: [
    {
      type: 'text',
      label: '任务名称',
      prop: 'taskInstanceName'
    },
    {
      type: 'text',
      label: '执行时间',
      prop: 'taskInstanceExecTime',
      width: 200
    },
    {
      type: 'slot',
      label: '执行状态',
      prop: 'taskInstanceState',
      width: 100
    },
    {
      type: 'text',
      label: '执行时长(秒)',
      prop: 'taskInstanceExecDuration',
      width: 110
    },
    {
      type: 'text',
      label: '同步数据量(条)',
      prop: 'totalSyncInstanceNum',
      width: 250
    },
    {
      type: 'text',
      label: '质检处理数据量',
      prop: 'totalQualityCheckInstanceNum',
      width: 150
    },
    {
      type: 'slot',
      label: '操作',
      prop: 'operation',
      width: 140,
      fixed: 'right',
      operationList: []
    }
  ]
}
