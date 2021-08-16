
export default _this => {
  return [
    {
      type: 'text',
      label: '角色名称',
      model: '',
      ruleProp: 'roleName',
      isSearch: true,
      maxlength: 20
    },
    {
      type: 'select',
      label: '状态',
      ruleProp: 'deleteFlag',
      model: '',
      options: [
        {
          value: 'N',
          name: '启用'
        }, {
          value: 'Y',
          name: '禁用'
        }
      ],
      props: {
        label: 'name',
        value: 'value'
      },
      isSearch: true
    },
    {
      type: 'cascader',
      label: '上级角色',
      model: '',
      ruleProp: 'parentId',
      props: {
        lazy: true,
        checkStrictly: true,
        value: 'id',
        label: 'roleName',
        lazyLoad: (node, resolve) => { return _this.mixinHandleLazyTree({ node, resolve }) }
      },
      options: []
    },
    {
      type: 'textarea',
      label: '角色描述',
      row: 3,
      maxlength: 200,
      // showWordLimit: true,
      model: '',
      resize: 'horizontal',
      ruleProp: 'roleRemark'
    }
  ]
}
