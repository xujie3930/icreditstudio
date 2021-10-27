/*
 * @Description: 质量检测-质检总览
 * @Date: 2021-10-26
 */

export default {
  refName: 'qualityOverview',
  id: 'qualityOverview',
  isBorder: true,
  hasPage: false,
  isMore: false,
  append: false,
  maxHeight: 300,
  group: [
    {
      type: 'index',
      label: '序号',
      width: '100px'
    },
    {
      type: 'text',
      label: '表名称',
      prop: 'fieldName'
    },
    {
      type: 'text',
      label: '规则数',
      prop: 'fieldType'
    },
    {
      type: 'text',
      label: '检查字段个数',
      prop: 'sourceTable'
    },
    {
      type: 'text',
      label: '问题数据条数',
      prop: 'fieldChineseName'
    },
    {
      type: 'text',
      label: '数据检查时间',
      prop: 'associateDict'
    },
    {
      type: 'text',
      label: '质量绩效评分',
      prop: 'remark'
    }
  ]
}
