/*
 * @Description: 编辑表结构
 * @Date: 2021-11-05
 */

import { fieldTypeMapping } from '../../data-manage/data-sync/contant'

export default that => ({
  refName: 'dataSyncEditTable',
  id: 'dataSyncEditTable',
  isBorder: true,
  hasPage: false,
  maxHeight: 370,
  group: [
    {
      type: 'selection',
      width: 40
    },
    {
      type: 'text',
      label: '字段名称',
      prop: 'fieldName'
    },
    {
      type: 'text',
      label: '字段类型',
      prop: 'fieldType',
      formatter: row => fieldTypeMapping[row?.fieldType[0]] || ''
    },
    {
      type: 'text',
      label: '来源表',
      prop: 'sourceTable'
    },
    {
      type: 'slot',
      label: '备注',
      prop: 'remark'
    },
    {
      type: 'operation',
      label: '操作',
      prop: 'operation',
      width: '100px',
      fixed: 'right',
      operationList: [
        {
          func: that.handleDelete,
          label: '删除',
          key: 'view'
        }
      ]
    }
  ]
})
