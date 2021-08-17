export default _this => {
  return {
    refName: 'user',
    id: 'user',
    isBorder: true,
    hasPage: true,
    customBtnConfig: [
      {
        label: '导出模板',
        type: 'primary',
        key: 'exportModel',
        options: {
          eventType: 'click',
          eventName: 'handleExportTemplate'
        }
      }
    ],
    group: [
      {
        type: 'img',
        label: '头像',
        imgBase64: true,
        prop: 'photo',
        width: 90,
        height: '32px'
      },
      {
        type: 'text',
        label: '用户姓名',
        prop: 'userName'
      },
      {
        type: 'text',
        label: '部门',
        prop: 'orgName'
      },
      // {
      //   type: 'text',
      //   label: '编码',
      //   prop: 'userCode'
      // },
      {
        type: 'text',
        label: '登录账号',
        prop: 'accountIdentifier'
      },
      {
        type: 'text',
        label: '手机号',
        prop: 'telPhone'
      },
      // {
      //   type: 'text',
      //   label: '排序',
      //   prop: 'sortNumber',
      //   width: 90
      // },
      {
        type: 'date',
        label: '创建时间',
        prop: 'createTime'
      },
      {
        type: 'switch',
        label: '是否启用',
        prop: 'deleteFlag',
        width: 100,
        activeValue: 'N',
        inactiveValue: 'Y',
        change: _this.handleStatusChange
      },
      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        width: '250px',
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
          },
          {
            func: _this.mixinHandleResetPwd,
            label: '重置密码',
            key: 'reset',
            show: true
          }
        ],
        fixed: 'right'
      }
    ]
  }
}
