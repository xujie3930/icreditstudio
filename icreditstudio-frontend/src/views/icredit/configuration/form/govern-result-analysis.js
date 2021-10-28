/*
 * @Description: 多维度分析
 * @Date: 2021-10-28
 */

export default [
  {
    type: 'text',
    label: '质检任务名称',
    model: '',
    ruleProp: 'taskName',
    isSearch: true,
    maxlength: 20
  },
  {
    type: 'select',
    label: '调度类型',
    ruleProp: 'taskStatus',
    model: '',
    isSearch: true,
    options: [
      { value: '', label: '全部' },
      { value: '0', label: '启用' },
      { value: '2', label: '停用' },
      { value: '1', label: '草稿' }
    ]
  },
  {
    type: 'select',
    label: '质检表名',
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
    type: 'daterange',
    label: '调度日期',
    ruleProp: 'dispatchType',
    model: '',
    isSearch: true,
    format: 'yyyy-MM-dd'
    // valueFormat: 'yyyy-MM-dd'
  }
]
