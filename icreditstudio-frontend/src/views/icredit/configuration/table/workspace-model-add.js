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
      type: 'index',
      label: '序号',
      width: 80
    },
    {
      type: 'text',
      label: '字段名',
      prop: 'taskName'
    },
    {
      type: 'slot',
      label: '字段类型',
      prop: 'runtime'
    },
    {
      type: 'text',
      label: '是否主键',
      prop: 'sourceTable'
    },
    {
      type: 'text',
      label: '长度/设置',
      prop: 'fieldChineseName'
    },
    {
      type: 'text',
      label: '字段别名',
      prop: 'associateDict'
    },
    {
      type: 'text',
      label: '字段规则',
      prop: 'associateDict'
    },
    {
      type: 'text',
      label: '描述',
      prop: 'associateDict'
    }
  ]
}
