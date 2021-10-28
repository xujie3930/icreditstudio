/*
 * @Description: 质检结果
 * @Date: 2021-10-25
 */
export default {
  refName: 'syncTask',
  id: 'syncTask',
  isBorder: true,
  hasPage: true,

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
      type: 'text',
      label: '质检表',
      prop: 'taskStatus',
      width: 120
    },
    {
      type: 'text',
      label: '执行类型',
      prop: 'dispatchType',
      width: 120
    },
    {
      type: 'text',
      label: '检测类型',
      prop: 'dispatchPeriod',
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
      type: 'operation',
      label: '操作',
      fixed: 'right',
      width: 200,
      operationList: [
        {
          func: () => {},
          label: '历史质检报告',
          key: 'report'
        }
      ]
    }
  ]
}
