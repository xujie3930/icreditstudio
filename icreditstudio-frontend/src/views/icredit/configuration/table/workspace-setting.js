export default _this => {
  return {
    refName: 'workspace-setting',
    id: 'setting',
    isBorder: true,
    hasPage: true,
    // maxHeight: '550',
    customBtnConfig: [
      {
        label: '新增工作空间',
        type: 'primary',
        key: 'addWorkspace',
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
        label: '工作空间名称',
        prop: 'userName'
      },
      {
        type: 'text',
        label: '空间状态',
        prop: 'orgName'
      },
      {
        type: 'text',
        label: '包含业务流程数（个）',
        prop: 'accountIdentifier'
      },
      {
        type: 'text',
        label: '包含工作流个数（个）',
        prop: 'telPhone'
      },
      {
        type: 'date',
        label: '更新时间',
        prop: 'createTime'
      },
      {
        type: 'date',
        label: '更新人',
        prop: 'createTime'
      },
      {
        type: 'date',
        label: '描述',
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
