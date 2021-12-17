/*
 * @Author: lizheng
 * @Description: 字典表
 * @Date: 2021-09-28
 */
export default {
  refName: 'data-manage-dictionary',
  id: 'dataDictionary',
  isBorder: true,
  maxHeight: 350,
  group: [
    {
      type: 'slot',
      label: 'key',
      prop: 'columnKey'
    },
    {
      type: 'slot',
      label: 'value',
      prop: 'columnValue'
    },
    {
      type: 'slot',
      label: '备注',
      prop: 'remark'
    },
    {
      type: 'slot',
      label: '操作',
      prop: 'operation',
      fixed: 'right',
      width: 120
    }
  ]
}
