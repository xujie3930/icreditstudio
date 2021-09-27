/*
 * @Description: 调度中心-周期任务
 * @Date: 2021-09-24
 */
export default _this => {
  return {
    refName: 'cycle-task',
    id: 'cycle-task',
    isBorder: true,
    hasPage: true,
    group: [
      {
        type: 'index',
        label: '序号',
        width: 100
      },
      {
        type: 'text',
        label: '任务姓名',
        prop: 'userName'
      },
      {
        type: 'text',
        label: '任务类型',
        prop: 'orgName'
      },
      {
        type: 'text',
        label: '任务版本',
        prop: 'accountIdentifier'
      },
      {
        type: 'text',
        label: '任务状态',
        prop: 'telPhone'
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
        type: 'operation',
        label: '操作',
        prop: 'operation',
        fixed: 'right',
        width: 150,
        operationList: [
          {
            func: _this.handleOpenDagPage,
            label: 'DAG',
            key: 'dag'
          },
          {
            func: _this.handleViewLog,
            label: '查看日志',
            key: 'viewLog'
          }
        ]
      }
    ]
  }
}
