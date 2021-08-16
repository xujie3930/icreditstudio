export default [
  {
    type: 'text',
    label: '字典名称',
    model: '',
    ruleProp: 'codeName',
    isSearch: true
  },
  {
    type: 'text',
    label: '字典类型',
    model: '',
    ruleProp: 'codeType'
  },
  {
    type: 'text',
    label: '字典值',
    model: '',
    ruleProp: 'codeValue'
  },
  {
    type: 'inputNum',
    label: '排序',
    min: 0,
    model: undefined,
    ruleProp: 'codeSort'
  },
  {
    type: 'textarea',
    label: '备注',
    row: 3,
    maxlength: 200,
    // showWordLimit: true,
    model: '',
    resize: 'horizontal',
    ruleProp: 'codeRemark'
  }
]
