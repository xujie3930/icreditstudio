export default _this => {
  return {
    refName: 'role',
    tableKey: 'lazy-true',
    id: 'role',
    isBorder: true,
    hasPage: false,
    lazy: true,
    defaultExpandAll: false,
    treeProps: { children: 'children', hasChildren: 'hasChildren' },
    load: _this.mixinHandleLazyTable,
    group: [
      {
        type: 'selection',
        width: '50px',
        prop: 'selection'
      },
      {
        type: 'index',
        label: '序号',
        width: '100px',
        prop: 'serialNumber'
      },
      {
        type: 'text',
        label: '角色名称',
        prop: 'roleName',
        align: 'left'
      },
      {
        type: 'text',
        label: '角色描述',
        prop: 'roleRemark'
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
        width: '300px',
        operationList: [
          {
            func: _this.mixinHandleUserSet,
            label: '配置用户',
            key: 'userSet',
            show: true
          },
          {
            func: _this.mixinHandleEdit,
            label: '修改',
            key: 'update',
            show: true
          },
          {
            func: _this.mixinAuthSet,
            label: '配置功能',
            key: 'auth',
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
