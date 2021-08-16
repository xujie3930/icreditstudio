export default _this => {
  return {
    refName: 'org',
    id: 'org',
    isBorder: true,
    hasPage: false,
    defaultExpandAll: true,
    group: [
      {
        type: 'selection',
        width: '50px',
        prop: 'selection'
      },
      {
        type: 'text',
        label: '部门名称',
        prop: 'orgName'
      },
      {
        type: 'text',
        label: '部门编码',
        prop: 'orgCode',
        width: 100
      },
      {
        type: 'text',
        label: '部门地址',
        prop: 'orgAddress'
      },
      // {
      //   type: 'text',
      //   label: '排序',
      //   prop: 'sortNumber',
      //   width: 100
      // },
      {
        type: 'text',
        label: '联系人',
        prop: 'linkManName'
      },
      {
        type: 'text',
        label: '联系方式',
        prop: 'linkManTel'
      },
      {
        type: 'text',
        label: '部门介绍',
        prop: 'orgRemark'
      },
      {
        type: 'switch',
        label: '是否启用',
        prop: 'deleteFlag',
        activeValue: 'N',
        inactiveValue: 'Y',
        change: _this.handleStatusChange
      },
      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        operationList: [
          {
            func: _this.mixinHandleEdit,
            label: '修改',
            key: 'update',
            show: true
          },
          {
            func: _this.mixinHandleDelete,
            label: '删除',
            key: 'delete',
            show: true
          }
        ],
        fixed: 'right'
      }
    ]
  }
}
