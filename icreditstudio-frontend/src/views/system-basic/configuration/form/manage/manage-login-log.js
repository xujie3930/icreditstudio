export default [
  {
    type: 'text',
    label: '登录账户',
    model: '',
    ruleProp: 'userAccount',
    isSearch: true
  },
  {
    type: 'text',
    label: '用户姓名',
    model: '',
    ruleProp: 'userName',
    isSearch: true
  },
  {
    type: 'daterange',
    label: '登录时间',
    startPlaceholder: '开始日期',
    endPlaceholder: '结束日期',
    model: '',
    ruleProp: 'loginTime',
    isSearch: true,
    format: 'yyyy-MM-dd',
    valueFormat: 'timestamp'
  }
]
