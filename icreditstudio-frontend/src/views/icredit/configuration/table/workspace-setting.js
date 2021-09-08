export default _this => {
  console.log(_this.workspaceCreateAuth, 'workspaceCreateAuth')
  return {
    refName: 'workspace-setting',
    id: 'setting',
    isBorder: true,
    hasPage: true,
    customBtnConfig: [
      {
        label: '新增工作空间',
        type: 'primary',
        key: 'addWorkspace',
        isHide: !_this.workspaceCreateAuth,
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
        prop: 'name'
      },
      {
        type: 'slot',
        label: '空间状态',
        prop: 'status',
        width: 100
      },
      {
        type: 'text',
        label: '包含业务流程数（个）',
        prop: 'businessFlowCount',
        width: 200
      },
      {
        type: 'text',
        label: '包含工作流个数（个）',
        prop: 'workFlowCount',
        width: 200
      },
      {
        type: 'text',
        label: '更新时间',
        prop: 'updateTime'
      },
      {
        type: 'text',
        label: '更新人',
        prop: 'updateUser'
      },
      {
        type: 'text',
        label: '描述',
        prop: 'descriptor'
      },
      {
        type: 'slot',
        label: '操作',
        prop: 'operation',
        width: '250px',
        fixed: 'right',

        operationList: [
          {
            func: ({ row }) => _this.handleOperateClick(row, 'view'),
            label: '查看',
            key: 'view'
          },
          {
            func: ({ row }) => _this.handleOperateClick(row, 'disabled'),
            label: '停用',
            key: 'disabled',
            isHide: ({ row }) => row.status
          },
          {
            func: _this.handleDeleteClick,
            label: '删除',
            key: 'delete',
            isHide: ({ row }) => !row.status
          },
          {
            func: ({ row }) => _this.handleOperateClick(row, 'enabled'),
            label: '启用',
            key: 'enabled',
            show: false
          },
          {
            func: _this.mixinHandleEdit,
            label: '编辑',
            key: 'update',
            isHide: ({ row }) => !row.status
          }
        ]
      }
    ]
  }
}
