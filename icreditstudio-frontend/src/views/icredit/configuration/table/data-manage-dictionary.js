/*
 * @Author: lizheng
 * @Description: 字典表
 * @Date: 2021-09-27
 */
export default _this => {
  return {
    refName: 'dict',
    id: 'dict',
    isBorder: true,
    hasPage: true,
    customBtnConfig: [
      {
        label: '导入字典表',
        type: 'primary',
        key: 'importDict',
        options: {
          title: '导入字典表',
          opType: 'import',
          eventType: 'click',
          eventName: 'handleImportDict'
        }
      },
      {
        label: '新增字典表',
        type: 'primary',
        key: 'addDict',
        options: {
          title: '新增字典表',
          opType: 'add',
          eventType: 'click',
          eventName: 'handleAddDict'
        }
      }
    ],
    group: [
      {
        type: 'index',
        width: '80px',
        label: '序号'
      },

      {
        type: 'text',
        label: '字典表英文名称',
        prop: 'englishName'
      },
      {
        type: 'text',
        label: '字典表中文名称',
        prop: 'chineseName'
      },

      {
        type: 'text',
        label: '添加人',
        prop: 'createUserName'
      },
      {
        type: 'text',
        label: '添加时间',
        prop: 'createTime'
      },
      {
        type: 'text',
        label: '描述',
        prop: 'dictDesc'
      },

      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        width: 250,
        fixed: 'right',
        operationList: [
          {
            func: _this.handleViewClick,
            label: '查看',
            key: 'view',
            show: true
          },
          {
            func: ({ row }) =>
              _this.handleAddDict({ row, opType: 'edit', title: '编辑字典表' }),
            label: '编辑',
            key: 'edit',
            show: true
          },
          {
            func: _this.handleDeleteDict,
            label: '删除',
            key: 'delete',
            show: true
          }
        ]
      }
    ]
  }
}
