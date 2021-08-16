export default _this => {
  return {
    refName: 'menu',
    id: 'menu',
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
        prop: 'serialNumber',
        width: 80
      },
      {
        type: 'text',
        label: '模块名称',
        prop: 'name'
      },
      {
        type: 'text',
        label: '模块类型',
        prop: 'typeStr',
        width: 100
      },
      // {
      //   type: 'text',
      //   label: '排序',
      //   prop: 'sortNumber',
      //   width: 80
      // },
      {
        type: 'text',
        label: '路径',
        headerTooltip: 'router path/ button key',
        prop: 'url'
      },
      {
        type: 'text',
        label: '文件路径',
        headerTooltip: 'vue 文件的目录（相对 views）',
        prop: 'filePath'
      },
      {
        type: 'text',
        label: '重定向地址',
        headerTooltip: 'router 的 redirect',
        prop: 'redirectPath'
      },
      {
        type: 'text',
        label: '是否在菜单显示',
        prop: 'isShowStr',
        width: 120
      },
      {
        type: 'text',
        label: '是否缓存',
        prop: 'keepAliveStr',
        width: 80
      },
      {
        type: 'switch',
        label: '是否启用',
        prop: 'deleteFlag',
        activeValue: 'N',
        inactiveValue: 'Y',
        width: 90,
        change: _this.handleStatusChange
      },
      {
        type: 'icon',
        label: '图标',
        prop: 'iconPath',
        width: 80
      },
      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        width: 200,
        operationList: [
          {
            func: _this.mixinHandleRoleSet,
            label: '配置角色',
            key: 'roleSet',
            show: true
          },
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
        ]
      }
    ]
  }
}
