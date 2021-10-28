/*
 * @Description: 多维度分析
 * @Date: 2021-10-28
 */

export default _this => ({
  refName: 'multipleAnalysis',
  id: 'multipleAnalysis',
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
      label: '任务名称',
      prop: 'taskName',
      width: 250
    },
    {
      type: 'text',
      label: '质检表',
      prop: 'taskStatus',
      width: 120
    },
    {
      type: 'text',
      label: '调度类型',
      prop: 'taskStatus',
      width: 120
    },
    {
      type: 'text',
      label: '执行类型',
      prop: 'dispatchType',
      width: 120
    },
    {
      type: 'text',
      label: '质量规则个数',
      prop: 'dispatchPeriod',
      width: 120
    },
    {
      type: 'text',
      label: '累计处理数据条数(万)',
      prop: 'dispatchPeriod',
      width: 180
    },
    {
      type: 'text',
      label: '总调度次数',
      prop: 'createTime'
    },
    {
      type: 'text',
      label: '质量综合平均分',
      prop: 'createTime1'
    },
    {
      type: 'operation',
      label: '操作',
      fixed: 'right',
      width: 250,
      operationList: [
        {
          func: _this.handleJumpPage,
          label: '问题以及趋势',
          key: 'question'
        },
        {
          func: _this.handleJumpPage,
          label: '各时期同比环比',
          key: 'rate'
        }
      ]
    }
  ]
})
