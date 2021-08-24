/*
 * @Author: lizheng
 * @Description: 数据同步
 */
export default [
  {
    type: 'text',
    label: '任务名称',
    model: '',
    ruleProp: 'userName',
    isSearch: true,
    maxlength: 20
  },
  {
    type: 'select',
    label: '任务状态',
    ruleProp: 'deleteFlag1',
    model: '',
    isSearch: true,
    options: [
      { value: 'N', label: '全部' },
      { value: 'Y', label: '启用' },
      { value: 'M', label: '停用' },
      { value: 'D', label: '草稿' }
    ]
  },
  {
    type: 'select',
    label: '执行状态',
    ruleProp: 'deleteFlag',
    model: '',
    isSearch: true,
    options: [
      { value: 'N', label: '全部' },
      { value: 'Y', label: '成功' },
      { value: 'M', label: '失败' },
      { value: 'D', label: '执行中' }
    ]
  }
]
