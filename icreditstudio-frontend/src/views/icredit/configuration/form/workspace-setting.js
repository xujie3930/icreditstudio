export default [
  {
    type: 'text',
    label: '工作空间名称',
    model: '',
    ruleProp: 'name',
    isSearch: true,
    maxlength: 20
  },
  {
    type: 'select',
    label: '创建人',
    ruleProp: 'createUser',
    model: '',
    isSearch: true,
    options: [
      { value: 'All', label: '全部' },
      { value: 'Admin', label: 'admin' },
      { value: 'zhangsan', label: '张三' }
    ]
  },
  {
    type: 'date',
    label: '创建时间',
    model: '',
    ruleProp: 'createTime',
    isSearch: true,
    format: 'yyyy-MM-dd',
    valueFormat: 'yyyy-MM-dd'
  }
]
