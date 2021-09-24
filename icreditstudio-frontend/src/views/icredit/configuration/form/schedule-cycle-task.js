/*
 * @Description: 周期任务调度-周期任务
 * @Date: 2021-09-24
 */
export default [
  {
    type: 'text',
    label: '任务姓名',
    model: '',
    ruleProp: 'taskName',
    isSearch: true,
    maxlength: 20
  },
  {
    type: 'select',
    label: '任务状态',
    ruleProp: 'taskStatus',
    model: '',
    isSearch: true,
    options: [
      { value: '', label: '全部' },
      { value: '0', label: '成功' },
      { value: '1', label: '失败' },
      { value: '2', label: '执行中' }
    ]
  },
  {
    type: 'select',
    label: '任务类型',
    ruleProp: 'taskType',
    model: '',
    isSearch: true,
    options: [
      { value: '', label: '全部' },
      { value: 'N', label: '同步任务' },
      { value: 'Y', label: '数据开发任务' }
    ]
  },
  {
    type: 'daterange',
    label: '调度时间',
    startPlaceholder: '开始日期',
    endPlaceholder: '结束日期',
    model: '',
    ruleProp: 'loginTime',
    isSearch: true,
    format: 'yyyy-MM-dd',
    valueFormat: 'timestamp'
  }
]
