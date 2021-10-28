/*
 * @Description: 质检结果
 * @Date: 2021-10-28
 */

export default that => ({
  refName: 'governResultReport',
  id: 'governResultReport',
  isBorder: true,
  group: [
    {
      type: 'text',
      label: '任务名称',
      prop: 'key'
    },
    {
      type: 'text',
      label: '执行时间',
      prop: 'value'
    },
    {
      type: 'text',
      label: '执行状态',
      prop: 'remark'
    },
    {
      type: 'text',
      label: '执行时长（秒）',
      prop: 'remark1'
    },
    {
      type: 'text',
      label: '处理数据量（条）',
      prop: 'remark2'
    },
    {
      type: 'text',
      label: '问题数据量（条）',
      prop: 'remark2'
    },
    {
      type: 'operation',
      label: '操作',
      fixed: 'right',
      width: 200,
      operationList: [
        {
          func: that.handleView,
          label: '预览质检报告',
          key: 'report'
        }
      ]
    }
  ]
})
