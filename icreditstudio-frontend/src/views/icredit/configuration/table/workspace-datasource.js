export default _this => {
  return {
    refName: 'workspace-setting',
    id: 'setting',
    isBorder: true,
    hasPage: true,
    // maxHeight: '550',
    customBtnConfig: [
      {
        label: '新增数据源',
        type: 'primary',
        key: 'addDataSource',
        options: {
          eventType: 'click',
          eventName: 'handleAddWorkspace'
        }
      }
    ],
    group: [
      {
        type: 'index',
        label: '序号',
        width: '100px',
        prop: 'serialNumber'
      },
      {
        type: 'text',
        label: '数据源类型',
        prop: 'userName'
      },
      {
        type: 'text',
        label: '数据源自定义名称',
        prop: 'orgName'
      },
      {
        type: 'text',
        label: '连接信息',
        prop: 'accountIdentifier'
      },
      {
        type: 'text',
        label: '是否启用',
        prop: 'telPhone'
      },
      {
        type: 'date',
        label: '最近一次同步时间',
        prop: 'createTime'
      },
      {
        type: 'date',
        label: '最近一次同步状态',
        prop: 'createTime'
      },
      {
        type: 'date',
        label: '描述信息',
        prop: 'createTime'
      },
      // {
      //   type: 'switch',
      //   label: '是否启用',
      //   prop: 'deleteFlag',
      //   width: 100,
      //   activeValue: 'N',
      //   inactiveValue: 'Y',
      //   change: _this.handleStatusChange
      // },
      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        width: '250px',
        fixed: 'right',
        operationList: [
          {
            func: ({ row }) => _this.handleOperateClick(row, 'view'),
            label: '查看',
            key: 'view',
            show: true
          },
          {
            func: ({ row }) => _this.handleOperateClick(row, 'disabled'),
            label: '停用',
            key: 'disabled',
            show: true
          },
          {
            func: _this.handleDeleteClick,
            label: '删除',
            key: 'delete',
            show: true
          },
          {
            func: ({ row }) => _this.handleOperateClick(row, 'enabled'),
            label: '启用',
            key: 'enabled',
            show: true
          },
          {
            func: _this.mixinHandleEdit,
            label: '编辑',
            key: 'update',
            show: true
          }
        ]
      }
    ]
  }
}
