/*
 * @Description: 质量规则
 * @Date: 2021-10-25
 */
export default {
  refName: 'syncTask',
  id: 'syncTask',
  isBorder: true,
  hasPage: true,
  customBtnConfig: [
    {
      label: '新增自定义规则',
      type: 'primary',
      key: 'addCustomRules',
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
      label: '规则名称',
      prop: 'taskName',
      width: 250
    },
    {
      type: 'slot',
      label: '规则强度',
      prop: 'taskStatus',
      width: 120
    },
    {
      type: 'text',
      label: '规则对象',
      prop: 'dispatchType',
      width: 120
    },
    {
      type: 'text',
      label: '规则类型',
      prop: 'dispatchPeriod',
      width: 120
    },
    {
      type: 'text',
      label: '规则描述',
      prop: 'createTime',
      align: 'left'
    },
    {
      type: 'slot',
      label: '操作',
      prop: 'operation',
      fixed: 'right',
      width: 150
    }
  ]
}
