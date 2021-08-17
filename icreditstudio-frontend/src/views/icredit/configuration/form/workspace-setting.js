export default [
  {
    type: 'text',
    label: '工作空间名称',
    model: '',
    ruleProp: 'userName',
    isSearch: true,
    maxlength: 20
  },
  {
    type: 'select',
    label: '创建人',
    ruleProp: 'deleteFlag',
    model: '',
    isSearch: true,
    options: [
      { value: 'N', label: '全部' },
      { value: 'Y', label: 'admin' },
      { value: 'M', label: '张三' }
    ]
  },
  {
    type: 'date',
    label: '创建时间',
    model: '',
    ruleProp: 'loginTime',
    isSearch: true,
    format: 'yyyy-MM-dd',
    valueFormat: 'timestamp'
  }
]
