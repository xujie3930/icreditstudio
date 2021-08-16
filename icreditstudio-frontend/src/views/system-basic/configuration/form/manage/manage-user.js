export default [
  {
    type: 'text',
    label: '用户姓名',
    model: '',
    ruleProp: 'userName',
    isSearch: true,
    maxlength: 20
  },
  {
    type: 'text',
    label: '工号',
    model: '',
    ruleProp: 'userCode'
  },
  {
    type: 'text',
    label: '账号',
    model: '',
    editDisabled: true,
    isSearch: true,
    ruleProp: 'accountIdentifier'
  },
  {
    type: 'date',
    label: '生日',
    model: '',
    ruleProp: 'userBirth',
    valueFormat: 'yyyy-MM-dd',
    format: 'yyyy-MM-dd'
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
    label: '手机号码',
    model: '',
    ruleProp: 'telPhone',
    isSearch: true
  },

  // TODO 待后台删除该字段
  {
    type: 'text',
    label: 'identityType',
    ruleProp: 'identityType',
    model: '1',
    addHide: true,
    editHide: true,
    viewHide: true
  },
  {
    type: 'radio',
    label: '性别',
    model: '',
    ruleProp: 'userGender',
    selectValue: '',
    options: [
      {
        value: '1',
        label: '男'
      },
      {
        value: '0',
        label: '女'
      }
    ],
    selectOptions: []
  },
  {
    type: 'select',
    label: '状态',
    ruleProp: 'deleteFlag',
    model: '',
    options: [
      {
        value: 'N',
        label: '启用'
      },
      {
        value: 'Y',
        label: '禁用'
      }
    ]
  },
  {
    type: 'cascader',
    label: '部门',
    model: [],
    ruleProp: 'orgList',
    props: {
      value: 'id',
      label: 'orgName',
      multiple: true,
      checkStrictly: true
    },
    isSearch: true,
    options: []
  },
  {
    type: 'textarea',
    label: '备注',
    row: 3,
    maxlength: 200,
    // showWordLimit: true,
    model: '',
    resize: 'horizontal',
    ruleProp: 'userRemark'
  }
]
