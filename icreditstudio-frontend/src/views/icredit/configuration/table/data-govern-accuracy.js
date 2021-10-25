/*
 * @Description: 治理首页-数据准确性分析
 * @Date: 2021-10-25
 */
export default {
  refName: 'dataGovernAccuracy',
  id: 'dataGovernAccuracy',
  isBorder: true,
  group: [
    {
      type: 'index',
      label: '序号',
      width: '100px'
    },
    {
      type: 'text',
      label: '数据名',
      prop: 'key'
    },
    {
      type: 'text',
      label: '质检表数',
      prop: 'value'
    },
    {
      type: 'text',
      label: '质检字段总数',
      prop: 'remark'
    },
    {
      type: 'text',
      label: '使用规则数',
      prop: 'remark1'
    },
    {
      type: 'text',
      label: '总检测数据量（万条）',
      prop: 'remark2'
    },
    {
      type: 'text',
      label: '总发现问题条数',
      prop: 'remark3'
    },
    {
      type: 'text',
      label: '质量检测总次数',
      prop: 'remark4'
    },
    {
      type: 'text',
      label: '数据质量平均分',
      prop: 'remark5'
    }
  ]
}
