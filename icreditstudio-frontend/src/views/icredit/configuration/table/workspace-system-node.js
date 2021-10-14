/*
 * @Author: lizheng
 * @Description: 叶子节点信息
 * @Date: 2021-10-13
 */

export default that => ({
  id: 'leafNodeInfo',
  refName: 'leafNodeInfo',
  isBorder: true,
  hasPage: true,
  group: [
    {
      type: 'selection',
      width: 50
    },
    {
      type: 'text',
      label: '名称',
      prop: 'name'
    },
    {
      type: 'text',
      label: '分类编号',
      prop: 'orgName'
    },
    {
      type: 'text',
      label: '父级节点',
      prop: 'parent'
    },
    {
      type: 'text',
      label: '更新人',
      prop: 'updateUser'
    },
    {
      type: 'date',
      label: '更新时间',
      prop: 'updateTime'
    },
    {
      type: 'editInput',
      label: '备注',
      prop: 'remark'
    },
    {
      type: 'operation',
      label: '操作',
      prop: 'operation',
      width: 120,
      fixed: 'right',
      operationList: [
        { label: '编辑', key: 'edit', func: that.handleAddNextLevel },
        {
          label: '删除',
          key: 'delete',
          func: ({ row }) => that.handleDeletLevel({ row, opType: 'delete' })
        }
      ]
    }
  ]
})
