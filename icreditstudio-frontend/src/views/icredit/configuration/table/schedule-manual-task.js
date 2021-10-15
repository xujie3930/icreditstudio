/*
 * @Description: 手动任务调度-手动任务
 * @Date: 2021-09-24
 */
export default _this => {
  return {
    refName: 'manual-task',
    id: 'manual-task',
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
        label: '执行开始时间',
        prop: 'createTime'
      },
      {
        type: 'date',
        label: '执行结束时间',
        prop: 'createTime'
      },
      {
        type: 'date',
        label: '创建时间',
        prop: 'createTime'
      },
      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        width: '250px',
        fixed: 'right',
        operationList: [
          {
            func: _this.mixinHandleDelete,
            label: 'DAG',
            key: 'dag',
            show: true
          },
          {
            func: _this.mixinHandleDelete,
            label: '查看日志',
            key: 'stop',
            show: true
          }
        ]
      }
    ]
  }
}
