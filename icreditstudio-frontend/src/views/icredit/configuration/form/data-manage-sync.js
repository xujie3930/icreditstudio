/*
 * @Author: lizheng
 * @Description: 数据同步
 */
export default [
  {
    type: 'text',
    label: '工作空间ID',
    model: '',
    ruleProp: 'workspaceId',
    isSearch: false
  },
  {
    type: 'text',
    label: '任务名称',
    model: '',
    ruleProp: 'taskName',
    isSearch: true
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
      { value: '1', label: '草稿' },
      { value: '2', label: '停用' }
    ]
  },
  {
    type: 'select',
    label: '执行状态',
    ruleProp: 'execStatus',
    model: '',
    isSearch: true,
    options: [
      { value: '', label: '全部' },
      { value: '0', label: '成功' },
      { value: '1', label: '失败' },
      { value: '2', label: '执行中' }
    ]
  }
]
