/*
 * @Description: 表单实例Table参数
 * @Date: 2021-07-19
 */
export default _this => {
  return {
    refName: 'approvalForm',
    id: 'approvalForm',
    isBorder: true,
    hasPage: true,

    group: [
      {
        type: 'index',
        label: '序号',
        width: '100px',
        prop: 'serialNumber'
      },
      {
        type: 'text',
        label: '表单名称',
        prop: 'formName'
      },
      {
        type: 'text',
        label: '创建人',
        prop: 'userName'
      },
      {
        type: 'date',
        label: '创建时间',
        prop: 'createTime'
      },
      {
        type: 'date',
        label: '更新时间',
        prop: 'lastUpdateTime'
      },
      {
        type: 'text',
        label: '版本号',
        prop: 'formVersion'
      },
      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        fixed: 'right',
        operationList: [
          {
            func: ({ row }) => _this.handleFormPreview({ row, opType: 'edit' }),
            label: '编辑',
            key: 'edit'
          },
          {
            func: ({ row }) => _this.handleFormPreview({ row, opType: 'view' }),
            label: '查看',
            key: 'view'
          },
          {
            func: _this.mixinHandleDelete,
            label: '删除',
            key: 'delete'
          }
        ]
      }
    ]
  }
}
