/*
 * @Author: lizheng
 * @Description: 数据同步
 */

export default _this => {
  return {
    refName: 'data-manage-sync',
    id: 'sync',
    isBorder: true,
    hasPage: true,
    customBtnConfig: [
      {
        label: '新增同步任务',
        type: 'primary',
        key: 'addSyncTask',
        options: {
          eventType: 'click',
          eventName: 'handleAddSyncTask'
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
        label: '同步任务名称',
        prop: 'userName'
      },
      {
        type: 'text',
        label: '任务状态',
        prop: 'orgName'
      },
      {
        type: 'text',
        label: '创建方式',
        prop: 'accountIdentifier'
      },
      {
        type: 'text',
        label: '采集方式',
        prop: 'telPhone'
      },
      {
        type: 'date',
        label: '同步方式',
        prop: 'createTime'
      },
      {
        type: 'date',
        label: '创建时间',
        prop: 'createTime'
      },
      {
        type: 'date',
        label: '近一次调度时间',
        prop: 'createTime'
      },
      {
        type: 'text',
        label: '执行状态',
        prop: 'deleteFlag'
      },
      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        width: '250px',
        fixed: 'right',
        operationList: [
          {
            func: ({ row }) => _this.handleDetailClick(row, 'view'),
            label: '查看',
            key: 'view',
            show: true
          },
          {
            func: ({ row }) => _this.handleDisabledClick(row, 'disabled'),
            label: '停用',
            key: 'disabled',
            show: true
          },
          {
            func: ({ row }) => _this.handleDeleteClick(row, 'delete'),
            label: '删除',
            key: 'delete',
            show: true
          },
          {
            func: ({ row }) => _this.handleEnabledClick(row, 'enabled'),
            label: '启用',
            key: 'enabled',
            show: true
          },
          {
            func: ({ row }) => _this.handleDetailClick(row, 'edit'),
            label: '编辑',
            key: 'edit',
            show: true
          }
        ]
      }
    ]
  }
}
