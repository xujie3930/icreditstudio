export default _this => {
  return {
    refName: 'code',
    id: 'code',
    isBorder: true,
    hasPage: true,
    customBtnConfig: [
      {
        label: '删除', // 必填 按钮描述
        type: 'primary', // 按钮类型，默认‘primary’
        key: 'multipleDelete', // 必填 按钮鉴权标识
        options: {
          eventType: 'click', // 事件触发方式
          eventName: 'handleMultipleDelete', // 必填 事件名称，
          selectType: 'multiple', // 是否需要校验选择列表数据，默认none,其他可选项 single multiple
          validBeforeEvent: _this.validBeforeMultipleDelete// 自定义前置校验
        }
      }
    ],
    group: [
      {
        type: 'selection',
        width: '50px',
        prop: 'selection'
      },
      {
        type: 'text',
        label: '字典名称',
        width: 140,
        prop: 'codeName'
      },
      {
        type: 'text',
        width: 160,
        label: '字典类型',
        prop: 'codeType'
      },
      {
        type: 'text',
        label: '字典值',
        width: 250,
        prop: 'codeValue'
      },
      {
        type: 'text',
        label: '说明',
        prop: 'codeRemark'
      },
      // {
      //   type: 'text',
      //   label: '排序',
      //   width: 100,
      //   prop: 'codeSort'
      // },
      {
        type: 'switch',
        label: '状态',
        prop: 'deleteFlag',
        activeValue: 'N',
        inactiveValue: 'Y',
        width: 140,
        change: _this.handleStatusChange
      },
      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        width: 140,
        operationList: [
          {
            func: _this.mixinHandleEdit,
            label: '修改',
            key: 'update',
            show: true
          }
        ],
        fixed: 'right'
      }
    ]
  }
}
