export default [
  {
    type: 'text',
    label: '操作类型',
    model: '',
    ruleProp: 'oprateType'
  },
  {
    type: 'text',
    label: '操作人',
    model: '',
    ruleProp: 'userName',
    isSearch: true
  },
  {
    type: 'daterange',
    label: '操作时间',
    startPlaceholder: '开始日期',
    endPlaceholder: '结束日期',
    model: '',
    ruleProp: 'operateTime',
    format: 'yyyy-MM-dd',
    valueFormat: 'timestamp',
    viewHide: true,
    isSearch: true
  },
  {
    type: 'datetime',
    label: '操作时间',
    placeholder: '选择日期时间',
    model: '',
    ruleProp: 'createTime'
  },
  {
    type: 'text',
    label: '执行结果',
    model: '',
    ruleProp: 'oprateResult'
  },
  {
    type: 'text',
    label: '操作内容',
    model: '',
    rows: 10,
    ruleProp: 'oprateInfo',
    isSearch: true
  }
]
