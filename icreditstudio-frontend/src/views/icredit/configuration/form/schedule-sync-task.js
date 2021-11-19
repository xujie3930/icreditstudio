/*
 * @Description: 周期任务调度-周期任务
 * @Date: 2021-09-24
 */
export default [
  {
    type: 'text',
    label: '任务名称',
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
      { value: '0', label: '启用' },
      { value: '1', label: '停用' },
      { value: '2', label: '草稿' }
    ]
  },
  {
    type: 'select',
    label: '执行状态',
    ruleProp: 'dispatchStatus',
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
    label: '调度类型',
    ruleProp: 'dispatchType',
    model: '',
    isSearch: true,
    options: [
      { value: '', label: '全部' },
      { value: '0', label: '手动执行' },
      { value: '1', label: '周期执行' }
    ]
  },
  {
    type: 'daterange',
    label: '调度时间',
    startPlaceholder: '开始日期',
    endPlaceholder: '结束日期',
    model: '',
    ruleProp: 'scheduleTime',
    isSearch: true,
    format: 'yyyy-MM-dd',
    valueFormat: 'timestamp'
  }
]
