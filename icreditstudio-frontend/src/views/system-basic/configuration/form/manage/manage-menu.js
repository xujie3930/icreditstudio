export default _this => {
  return [
    {
      type: 'select',
      label: '模块类型',
      ruleProp: 'type',
      model: '',
      options: [
        {
          value: 'D',
          label: '顶部模块'
        }, {
          value: 'M',
          label: '菜单'
        }, {
          value: 'B',
          label: '按钮'
        }
      ],
      change: _this.handleTypeChange
    },
    {
      type: 'text',
      label: '模块名称',
      model: '',
      ruleProp: 'name',
      isSearch: true
    },
    {
      type: 'text',
      label: '路径',
      model: '',
      ruleProp: 'url'
    },
    {
      type: 'cascader',
      label: '上级模块',
      model: '',
      ruleProp: 'parentId',
      props: {
        value: 'id',
        label: 'name',
        checkStrictly: true
      },
      options: []
    },
    {
      type: 'text',
      label: '文件路径',
      model: '',
      isHide: false,
      ruleProp: 'filePath'
    },
    {
      type: 'text',
      label: '重定向地址',
      model: '',
      isHide: false,
      ruleProp: 'redirectPath'
    },
    {
      type: 'select',
      label: '是否在菜单显示',
      isHide: false,
      ruleProp: 'isShow',
      model: 'Y',
      options: [
        {
          value: 'Y',
          label: '是'
        }, {
          value: 'N',
          label: '否'
        }
      ]
    },
    {
      type: 'select',
      label: '是否缓存',
      headerTooltip: 'keepAlive',
      ruleProp: 'keepAlive',
      model: 'N',
      isHide: false,
      options: [
        {
          value: 'Y',
          label: '是'
        }, {
          value: 'N',
          label: '否'
        }
      ]
    },
    // {
    //   type: 'select',
    //   label: '是否启用',
    //   model: 0,
    //   options: [
    //     {
    //       value: 1,
    //       label: '否'
    //     }, {
    //       value: 0,
    //       label: '是'
    //     }
    //   ],
    //   ruleProp: 'status'
    // },
    {
      type: 'inputNum',
      label: '排序',
      model: '',
      isHide: false,
      min: 0,
      ruleProp: 'sortNumber'
    },
    {
      type: 'icon',
      label: '图标',
      ruleProp: 'iconPath',
      model: '',
      isHide: false,
      options: []
    },
    {
      type: 'text',
      label: '按钮权限标识',
      ruleProp: 'authIdentification',
      model: '',
      isHide: true
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
}
