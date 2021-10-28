/*
 * @Description: 质量检测-质量规则
 * @Date: 2021-10-25
 */
export default {
  refName: 'syncTask',
  id: 'syncTask',
  isBorder: true,
  hasPage: true,
  customBtnConfig: [
    {
      label: '新增任务',
      type: 'primary',
      key: 'addTask',
      options: {
        title: '',
        opType: 'import',
        eventType: 'click',
        eventName: 'handleAddCustomRules'
      }
    }
  ],
  group: [
    {
      type: 'index',
      label: '序号',
      width: 80
    },
    {
      type: 'text',
      label: '任务名称',
      prop: 'taskName',
      width: 250
    },
    {
      type: 'slot',
      label: '质量检测表',
      prop: 'taskStatus',
      width: 120
    },
    {
      type: 'text',
      label: '调度类型',
      prop: 'dispatchType',
      width: 120
    },
    {
      type: 'text',
      label: '执行周期',
      prop: 'dispatchPeriod',
      width: 120
    },
    {
      type: 'text',
      label: '近一次执行状态',
      prop: 'createTime'
    },
    {
      type: 'text',
      label: '近一次执行时间',
      prop: 'createTime1'
    },
    {
      type: 'text',
      label: '添加时间',
      prop: 'createTime2'
    },
    {
      type: 'slot',
      label: '操作',
      prop: 'operation',
      fixed: 'right',
      width: 200
    }
  ]
}
