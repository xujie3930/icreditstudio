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
      prop: 'userName'
    },
    {
      type: 'text',
      label: '字段类型',
      prop: 'orgName'
    },
    {
      type: 'text',
      label: '来源表',
      prop: 'accountIdentifier'
    },
    {
      type: 'text',
      label: '字段中文名称',
      prop: 'telPhone'
    },
    {
      type: 'date',
      label: '关联字典表',
      prop: 'createTime'
    },
    {
      type: 'date',
      label: '备注',
      prop: 'createTime'
    }
  ]
}