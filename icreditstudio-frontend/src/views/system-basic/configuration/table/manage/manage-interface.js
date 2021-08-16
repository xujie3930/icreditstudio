export default _this => {
  return {
    refName: 'interface',
    id: 'interface',
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
        label: '名称',
        prop: 'name'
      },
      {
        type: 'text',
        label: '是否需要鉴权',
        prop: 'needAuthStr'
      },
      {
        type: 'text',
        label: '接口URI',
        prop: 'uri'
      },
      {
        type: 'text',
        label: '接口地址/通配符',
        prop: 'uriTypeStr'
      },
      {
        type: 'text',
        label: '请求类型',
        prop: 'method'
      },
      {
        type: 'text',
        label: '接口归属系统模块',
        prop: 'module'
      },
      {
        type: 'text',
        label: '备注',
        prop: 'remark'
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
        ]
      }
    ]
  }
}
