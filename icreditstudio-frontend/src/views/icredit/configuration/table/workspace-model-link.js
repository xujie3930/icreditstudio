/*
 * @Author: lizheng
 * @Description: 关联字段表
 * @Date: 2021-10-11
 */

export default {
  refName: 'modelLink',
  id: 'modelLink',
  isBorder: true,
  hasPage: false,
  group: [
    {
      type: 'select',
      label: 'tableA',
      prop: 'type',
      options: [{ value: 'A' }]
    },
    {
      type: 'select',
      label: '关系',
      prop: 'fieldType',
      options: [{ value: '=' }]
    },
    {
      type: 'select',
      label: 'tableB',
      prop: 'length',
      options: [{ value: 'B' }]
    },
    {
      type: 'slot',
      label: '操作',
      prop: 'operation',
      width: 80
    }
  ]
}
