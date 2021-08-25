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
        prop: 'businessFlowCount'
      },
      {
        type: 'text',
        label: '包含工作流个数（个）',
        prop: 'workFlowCount'
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
            isHide: ({ row }) => !row.status
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
