export default _this => {
  return {
    refName: 'demo',
    id: 'demo',
    isBorder: true,
    hasPage: true,
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
        prop: 'name'
      },
      {
        type: 'text',
        label: '手机号码',
        prop: 'phone'
      },
      {
        type: 'text',
        label: '状态',
        prop: 'status'
      },
      {
        type: 'text',
        label: '菜单',
        prop: 'menus'
      },
      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        operationList: [
          {
            func: _this.mixinHandleView, // 查看
            label: '<span>查看</span>',
            show: true
          },
          {
            func: _this.mixinHandleEdit, // 修改
            label: '编辑',
            show: true
          },
          {
            func: _this.mixinHandleDelete, // 修改
            label: '删除',
            show: true
          }
        ],
        fixed: 'right'
      }
    ]
  }
}
