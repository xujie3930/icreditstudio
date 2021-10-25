/*
 * @Author: lizheng
 * @Description: 质量规则
 * @Date: 2021-10-25
 */
export default [
  {
    type: 'text',
    label: '规则名称',
    model: '',
    ruleProp: 'taskName',
    isSearch: true,
    maxlength: 20
  },
  {
    type: 'select',
    label: '规则对象',
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
    label: '规则强度',
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
    label: '规则类型',
    ruleProp: 'dispatchType',
    model: '',
    isSearch: true,
    options: [
      { value: '', label: '全部' },
      { value: '0', label: '手动执行' },
      { value: '1', label: '周期执行' }
    ]
  }
]
