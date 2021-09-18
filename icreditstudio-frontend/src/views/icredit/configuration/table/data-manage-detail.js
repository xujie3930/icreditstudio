/*
 * @Author: lizheng
 * @Description: 数据源详情
 * @Date: 2021-08-24
 */

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
      formatter: row => row?.fieldType[1] || ''
    },
    {
      type: 'text',
      label: '来源表',
      prop: 'sourceTable'
    },
    {
      type: 'text',
      label: '字段中文名称',
      prop: 'fieldChineseName'
    },
    {
      type: 'text',
      label: '关联字典表',
      prop: 'associateDict'
    },
    {
      type: 'text',
      label: '备注',
      prop: 'remark'
    }
  ]
}
