export default [
  {
    type: 'text',
    label: '名称',
    model: '',
    ruleProp: 'name',
    isSearch: true
  },
  {
    type: 'select',
    label: '请求类型',
    ruleProp: 'method',
    model: '',
    options: [
      {
        value: 'GET',
        label: 'GET'
      }, {
        value: 'POST',
        label: 'POST'
      }, {
        value: 'PUT',
        label: 'PUT'
      }, {
        value: 'DELETE',
        label: 'DELETE'
      }
    ]
  },
  {
    type: 'select',
    label: '是否鉴权',
    ruleProp: 'needAuth',
    model: '',
    options: [
      {
        value: 0,
        label: '否'
      }, {
        value: 1,
        label: '是'
      }
    ]
  },
  {
    type: 'text',
    label: '接口归属模块',
    model: '',
    ruleProp: 'module'
  },
  {
    type: 'select',
    label: '鉴权方式',
    model: '',
    ruleProp: 'supportAuthType',
    options: [
      {
        value: 'token',
        label: 'token'
      }, {
        value: '-',
        label: '-'
      }
    ]
  },
  {
    type: 'text',
    label: '接口地址',
    model: '',
    ruleProp: 'uri'
  },
  {
    type: 'select',
    label: '类型',
    model: '',
    options: [
      {
        label: '接口地址',
        value: '0'
      },
      {
        label: '通配符',
        value: '1'
      }
    ],
    ruleProp: 'uriType'
  },
  {
    type: 'textarea',
    label: '备注',
    row: 3,
    maxlength: 200,
    // showWordLimit: true,
    model: '',
    resize: 'horizontal',
    ruleProp: 'remark'
  }
]
