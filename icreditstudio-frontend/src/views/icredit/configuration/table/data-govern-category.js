/*
 * @Description: 治理首页-质检问题分类
 * @Date: 2021-10-25
 */
export default {
  refName: 'dataGovernCaterogty',
  id: 'dataGovernCaterogty',
  isBorder: true,
  group: [
    {
      type: 'text',
      label: '质检问题分类',
      prop: 'key'
    },
    {
      type: 'text',
      label: '对应的规则',
      prop: 'value'
    },
    {
      type: 'text',
      label: '发现问题数',
      prop: 'remark'
    },
    {
      type: 'text',
      label: '总检查字段',
      prop: 'remark1'
    },
    {
      type: 'text',
      label: '问题占比',
      prop: 'remark2'
    }
  ]
}
