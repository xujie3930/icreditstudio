export default _this => {
  return {
    refName: 'auth',
    id: 'auth',
    isBorder: true,
    hasPage: false,
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
        label: '名称',
        prop: 'name'
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
            func: _this.mixinSetMenu,
            label: '授予菜单',
            key: 'modal',
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
