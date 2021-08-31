/*
 * @Author: lizheng
 * @Description: 数据同步
 */

const taskModeMapping = {
  0: '可视化',
  1: 'SQL'
}

const execModeMapping = {
  0: '手动执行',
  1: '周期执行'
}

const syncModeMapping = {
  0: '增量同步',
  1: '全量同步'
}

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
        prop: 'taskName'
      },
      {
        type: 'slot',
        label: '任务状态',
        prop: 'taskStatus',
        width: 120
      },
      {
        type: 'text',
        label: '创建方式',
        prop: 'taskBuildMode',
        width: 120,
        formatter: row => taskModeMapping[row.taskBuildMode]
      },
      {
        type: 'text',
        label: '采集方式',
        prop: 'execMode',
        width: 120,
        formatter: row => execModeMapping[row.syncMode]
      },
      {
        type: 'text',
        label: '同步方式',
        prop: 'syncMode',
        width: 120,
        formatter: row => syncModeMapping[row.syncMode]
      },
      {
        type: 'date',
        label: '创建时间',
        prop: 'createTime'
      },
      {
        type: 'date',
        label: '近一次调度时间',
        prop: 'lastScheduleTime'
      },
      {
        type: 'slot',
        label: '执行状态',
        prop: 'execStatus',
        width: 100
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
