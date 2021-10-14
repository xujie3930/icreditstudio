/*
 * @Description: 调度中心-同步任务调度
 * @Date: 2021-09-24
 */
export default _this => {
  return {
    refName: 'syncTask',
    id: 'syncTask',
    isBorder: true,
    hasPage: true,
    group: [
      {
        type: 'index',
        label: '任务ID',
        width: 100
      },
      {
        type: 'text',
        label: '同步任务名称',
        prop: 'syncTaskName'
      },
      {
        type: 'text',
        label: '任务版本',
        prop: 'accountIdentifier'
      },
      {
        type: 'text',
        label: '任务状态',
        prop: 'taskStatus',
        width: 80,
        formatter: (row, col, val) => (val ? '启用' : '停用')
      },
      {
        type: 'text',
        label: '调度类型',
        prop: 'orgName'
      },
      {
        type: 'date',
        label: '执行周期',
        prop: 'createTime'
      },
      {
        type: 'date',
        label: '任务创建时间',
        prop: 'createTime',
        width: 170
      },
      {
        type: 'date',
        label: '近一次调度时间',
        prop: 'createTime',
        width: 170
      },
      {
        type: 'date',
        label: '近一次执行状态',
        prop: 'createTime',
        width: 170
      },
      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        fixed: 'right',
        width: 200,
        operationList: [
          {
            func: _this.handleReRuningTask,
            label: '重跑',
            key: 'rerunning'
          },
          {
            func: _this.handleStopTask,
            label: '终止',
            key: 'stop'
          },
          {
            func: _this.handleViewLog,
            label: '历史日志',
            key: 'historyLog'
          }
        ]
      }
    ]
  }
}
