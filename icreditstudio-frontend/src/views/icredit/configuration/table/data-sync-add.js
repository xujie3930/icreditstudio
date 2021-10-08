/*
 * @Author: lizheng
 * @Description: 新增同步任务
 * @Date: 2021-09-03
 */

export default {
  refName: 'data-sync-add',
  id: 'dataSyncAdd',
  isBorder: true,
  hasPage: false,
  maxHeight: 360,
  group: [
    {
      type: 'text',
      label: '序号',
      width: '100px',
      prop: 'sort'
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
      type: 'slot',
      label: '字段中文名称',
      prop: 'fieldChineseName'
    },
    {
      type: 'slot',
      label: '关联字典表',
      prop: 'associateDict'
    },
    {
      type: 'slot',
      label: '备注',
      prop: 'remark'
    }
  ]
}
