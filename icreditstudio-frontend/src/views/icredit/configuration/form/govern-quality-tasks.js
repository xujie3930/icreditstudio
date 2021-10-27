/*
 * @Author: lizheng
 * @Description: 质量检测-质检任务
 * @Date: 2021-10-27
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
      { value: '2', label: '停用' },
      { value: '1', label: '草稿' }
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
    type: 'daterange',
    label: '执行时间',
    ruleProp: 'dispatchType',
    model: '',
    isSearch: true,
    format: 'yyyy-MM-dd'
    // valueFormat: 'yyyy-MM-dd'
  }
]
