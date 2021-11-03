/*
 * @Author: lizheng
 * @Description: 综合分析-规则类型问题排行
 * @Date: 2021-10-28
 */

export default {
  refName: 'questionCountRanking',
  id: 'questionCountRanking',
  isBorder: true,
  hasPage: false,
  group: [
    {
      type: 'text',
      label: '排序',
      prop: 'fieldName',
      width: 88
    },
    {
      type: 'text',
      label: '规则类型',
      prop: 'fieldType',
      width: 160
    },
    {
      type: 'text',
      label: '问题数',
      prop: 'sourceTable'
      // width: 123
    },
    {
      type: 'text',
      label: '问题占比',
      prop: 'fieldChineseName'
      // width: 110
    }
  ]
}
