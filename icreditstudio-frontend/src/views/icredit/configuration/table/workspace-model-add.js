/*
 * @Author: lizheng
 * @Description: 新增表字段
 * @Date: 2021-10-09
 */
export default {
  refName: 'dataModeling',
  id: 'dataModeling',
  isBorder: true,
  hasPage: false,
  group: [
    {
      type: 'selection',
      label: '序号',
      width: 80
    },
    {
      type: 'editInput',
      label: '字段名',
      prop: 'fieldName'
    },
    {
      type: 'select',
      label: '字段类型',
      prop: 'fieldType',
      options: [
        { label: 'date', value: 'DATE' },
        { label: 'string', value: 'STRING' }
      ]
    },
    {
      type: 'editInput',
      label: '长度',
      prop: 'length'
    },
    {
      type: 'select',
      label: '是否主键',
      prop: 'isKey',
      options: [
        { label: '是', value: '是' },
        { label: '否', value: '否' }
      ]
    },
    {
      type: 'editInput',
      label: '字段别名',
      prop: 'alias'
    },
    {
      type: 'editInput',
      label: '描述',
      prop: 'des'
    }
  ]
}
