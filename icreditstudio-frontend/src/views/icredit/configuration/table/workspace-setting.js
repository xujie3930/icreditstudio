export default _this => {
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
            func: _this.mixinHandleRoleSet,
            label: '查看',
            key: 'roleSet',
            show: true
          },
          {
            func: _this.mixinHandleEdit,
            label: '停用',
            key: 'update',
            show: true
          },
          {
            func: _this.mixinHandleDelete,
            label: '删除',
            key: 'delete',
            show: true
          },
          {
            func: _this.mixinHandleEdit,
            label: '启用',
            key: 'update',
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
