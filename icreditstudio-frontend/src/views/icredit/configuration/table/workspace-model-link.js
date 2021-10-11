/*
 * @Author: lizheng
 * @Description: 关联字段表
 * @Date: 2021-10-11
 */

export default that => ({
  refName: 'modelLink',
  id: 'modelLink',
  isBorder: true,
  hasPage: false,
  group: [
    {
      type: 'text',
      label: 'tableA',
      prop: 'type'
    },
    {
      type: 'text',
      label: '关系',
      prop: 'fieldType'
    },
    {
      type: 'text',
      label: 'tableB',
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
