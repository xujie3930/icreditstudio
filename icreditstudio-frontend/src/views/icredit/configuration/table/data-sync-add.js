/*
 * @Author: lizheng
 * @Description: 新增同步任务
 * @Date: 2021-09-03
 */

export default {
  refName: 'data-sync-add',
  id: '',
  isBorder: true,
  hasPage: false,
  maxHeight: 360,
  group: [
    {
      type: 'index',
      label: '序号',
      width: '100px',
      prop: 'serialNumber'
    },
    {
      type: 'text',
      label: '字段名称',
      prop: 'fieldName'
    },
    {
      type: 'slot',
      label: '字段类型',
      prop: 'fieldType'
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
      type: 'slot',
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
