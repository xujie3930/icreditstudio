/*
 * @Author: lizheng
 * @Description: 数据源详情
 * @Date: 2021-08-24
 */

import { fieldTypeMapping } from '../../data-manage/data-sync/contant'

export default {
  refName: 'data-manage-detial',
  id: '',
  isBorder: true,
  hasPage: false,
  maxHeight: 350,
  group: [
    {
      type: 'index',
      label: '序号',
      width: '100px'
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
      label: '表名称',
      prop: 'tableName'
    },
    {
      type: 'text',
      label: '字段中文名称',
      prop: 'fieldChineseName'
    },
    {
      type: 'text',
      label: '备注',
      prop: 'remark'
    }
  ]
}
