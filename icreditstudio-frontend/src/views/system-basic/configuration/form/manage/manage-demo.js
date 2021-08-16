export default _this => {
  return [
    {
      type: 'text',
      label: '用户姓名',
      model: '',
      ruleProp: 'name',
      isSearch: true
    },
    {
      type: 'text',
      label: '手机号码',
      model: '',
      ruleProp: 'phone',
      change: _this.phoneChange
    },
    {
      type: 'text',
      label: '不显示',
      model: '',
      ruleProp: 'phone',
      addHide: true,
      viewHide: true,
      editHide: true,
      isSearch: true
    },
    {
      type: 'text',
      label: '禁用控制',
      model: '',
      ruleProp: 'control',
      addDisabled: true,
      editDisabled: true
    },
    {
      type: 'select',
      label: '状态',
      ruleProp: 'status',
      model: '',
      options: [
        {
          value: '1',
          name: '启用'
        }, {
          value: '2',
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
      type: 'selectTree',
      label: '菜单',
      model: '',
      ruleProp: 'menus',
      defaultExpandAll: false,
      multiple: false,
      treeProps: {
        label: 'label'
      },
      treeData: [],
      isSearch: true
    },
    {
      type: 'textarea',
      label: '备注',
      row: 3,
      maxlength: 200,
      // showWordLimit: true,
      model: '',
      resize: 'horizontal',
      ruleProp: 'textarea'
    }
  ]
}
