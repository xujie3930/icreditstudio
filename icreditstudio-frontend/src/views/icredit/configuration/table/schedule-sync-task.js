/*
 * @Description: 调度中心-同步任务调度
 * @Date: 2021-09-24
 */
export default {
  refName: 'syncTask',
  id: 'syncTask',
  isBorder: true,
  hasPage: true,
  group: [
    {
      type: 'text',
      label: '任务ID',
      prop: 'taskId',
      width: 150
    },
    {
      type: 'text',
      label: '同步任务名称',
      prop: 'taskName'
    },
    {
      type: 'text',
      label: '任务版本',
      prop: 'taskVersion'
    },
    {
      type: 'text',
      label: '任务状态',
      prop: 'taskStatus',
      width: 80
    },
    {
      type: 'text',
      label: '调度类型',
      prop: 'dispatchType'
    },
    {
      type: 'text',
      label: '执行周期',
      prop: 'dispatchPeriod'
    },
    {
      type: 'text',
      label: '任务创建时间',
      prop: 'createTime',
      width: 170
    },
    {
      type: 'text',
      label: '近一次调度时间',
      prop: 'lastDispatchTime',
      width: 170
    },
    {
      type: 'text',
      label: '近一次执行状态',
      prop: 'dispatchStatus',
      width: 150
    },
    {
      type: 'slot',
      label: '操作',
      prop: 'operation',
      fixed: 'right',
      width: 200,
      operationList: [
        {
          label: '重跑',
          key: 'rerunning'
        },
        {
          label: '终止',
          key: 'stop'
        },
        {
          label: '历史日志',
          key: 'historyLog'
        }
      ]
    }
  ]
}
