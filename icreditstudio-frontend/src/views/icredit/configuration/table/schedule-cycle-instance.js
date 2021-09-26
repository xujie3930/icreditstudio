/*
 * @Description: 任务中心-周期实例
 * @Date: 2021-09-24
 */
export default that => {
  console.log(that)
  return {
    refName: 'cycle-instance',
    id: 'cycle-instance',
    isBorder: true,
    hasPage: true,
    group: [
      {
        type: 'text',
        label: '任务id',
        prop: 'userName'
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
        label: '执行状态',
        prop: 'telPhone'
      },
      {
        type: 'date',
        label: '总调度次数',
        prop: 'createTime'
      },
      {
        type: 'date',
        label: '当前同步节点',
        prop: 'createTime',
        width: 120
      },
      {
        type: 'date',
        label: '开始时间',
        prop: 'createTime',
        width: 150
      },
      {
        type: 'date',
        label: '结束时间',
        prop: 'createTime',
        width: 150
      },
      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        width: 120,
        fixed: 'right',
        operationList: [
          {
            label: '重跑',
            key: 'reRun',
            show: true
          },
          {
            label: 'DAG',
            key: 'dag',
            show: true
          },
          {
            label: '终止',
            key: 'stop',
            show: true
          },
          {
            label: '查看日志',
            key: 'stop',
            show: true
          }
        ]
      }
    ]
  }
}
