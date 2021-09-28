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
          eventType: 'click',
          eventName: 'handleImportDict'
        }
      },
      {
        label: '新增字典表',
        type: 'primary',
        key: 'addDict',
        options: {
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
        prop: 'enName'
      },
      {
        type: 'text',
        label: '字典表中文名称',
        prop: 'zhName'
      },

      {
        type: 'text',
        label: '添加人',
        prop: 'user'
      },
      {
        type: 'date',
        label: '添加时间',
        prop: 'telPhone'
      },
      {
        type: 'text',
        label: '描述',
        prop: 'sortNumber'
      },

      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        width: '250px',
        fixed: 'right',
        operationList: [
          {
            func: _this.handleViewClick,
            label: '查看',
            key: 'view',
            show: true
          },
          {
            func: ({ row }) => _this.handleAddDict({ row, opType: 'edit' }),
            label: '编辑',
            key: 'edit',
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
