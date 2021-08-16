export default [
  {
    type: 'text',
    label: '部门名称',
    model: '',
    ruleProp: 'orgName',
    isSearch: true,
    maxlength: 20
  },
  {
    type: 'text',
    label: '部门编码',
    model: '',
    ruleProp: 'orgCode'
    // isSearch: true
  },
  {
    type: 'text',
    label: '部门地址',
    model: '',
    ruleProp: 'orgAddress'
  },
  {
    type: 'inputNum',
    label: '排序',
    min: 0,
    model: undefined,
    ruleProp: 'sortNumber'
  },
  {
    type: 'text',
    label: '联系人',
    model: '',
    ruleProp: 'linkManName',
    isSearch: true
  },
  {
    type: 'text',
    label: '联系方式',
    model: '',
    ruleProp: 'linkManTel'
  },
  {
    type: 'cascader',
    label: '上级部门',
    model: '',
    ruleProp: 'parentId',
    props: {
      value: 'id',
      label: 'orgName',
      checkStrictly: true
    },
    options: []
  },
  {
    type: 'textarea',
    label: '部门介绍',
    row: 3,
    maxlength: 200,
    // showWordLimit: true,
    model: '',
    resize: 'horizontal',
    ruleProp: 'orgRemark'
  }
]
