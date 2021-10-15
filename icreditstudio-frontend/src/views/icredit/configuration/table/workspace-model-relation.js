/*
 * @Author: lizheng
 * @Description: 关联关系表
 * @Date: 2021-10-11
 */
export default that => ({
  refName: 'ModelingRelation',
  id: 'ModelingRelation',
  isBorder: true,
  hasPage: false,
  group: [
    {
      type: 'text',
      label: '关联类型',
      prop: 'type',
      width: 120
    },
    {
      type: 'text',
      label: '关联表',
      prop: 'fieldType'
    },
    {
      type: 'text',
      label: '关联字段',
      prop: 'length'
    },
    {
      type: 'operation',
      label: '操作',
      prop: 'operation',
      width: 80,
      operationList: [
        {
          func: that.handleViewLogDetail,
          label: '删除',
          key: 'delete'
        }
      ]
    }
  ]
})
