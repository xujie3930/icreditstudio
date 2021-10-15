/*
 * @Author: lizheng
 * @Description: 数仓建模-表信息
 * @Date: 2021-10-08
 */
export default {
  refName: 'dataModeling',
  id: 'dataModeling',
  isBorder: true,
  hasPage: true,
  group: [
    {
      type: 'index',
      label: '序号',
      width: 80
    },
    {
      type: 'text',
      label: '表英文名',
      prop: 'taskName'
    },
    {
      type: 'slot',
      label: '表中文名',
      prop: 'runtime'
    },
    {
      type: 'text',
      label: '是否启用',
      prop: 'sourceTable'
    },
    {
      type: 'text',
      label: '生命周期',
      prop: 'fieldChineseName'
    },
    {
      type: 'text',
      label: '使用方式',
      prop: 'associateDict'
    },
    {
      type: 'text',
      label: '描述信息',
      prop: 'associateDict'
    }
  ]
}
