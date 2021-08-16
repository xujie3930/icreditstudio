export default _this => {
  return {
    refName: 'job',
    id: 'job',
    isBorder: true,
    hasPage: true,
    group: [
      {
        type: 'selection',
        width: '50px',
        prop: 'selection'
      },
      {
        type: 'index',
        label: '序号',
        width: '100px',
        prop: 'serialNumber'
      },
      {
        type: 'text',
        label: '任务名称',
        prop: 'jobName'
      },
      {
        type: 'text',
        label: '任务组',
        prop: 'jobGroup'
      },
      {
        type: 'text',
        label: '类名',
        prop: 'jobClassName'
      },
      {
        type: 'text',
        label: '表达式',
        prop: 'cronExpression'
      },
      {
        type: 'text',
        label: '开始时间',
        prop: 'startTime'
      },
      {
        type: 'text',
        label: '结束时间',
        prop: 'endTime'
      },
      {
        type: 'text',
        label: '下次运行时间',
        prop: 'nextFireTime'
      },
      {
        type: 'text',
        label: '上次运行时间',
        prop: 'prevFireTime'
      },
      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        operationList: [
          {
            func: _this.mixinHandleEdit,
            label: '修改',
            key: 'update',
            show: true
          },
          {
            func: _this.mixinHandleDelete,
            label: '删除',
            key: 'delete',
            show: true
          },
          {
            // func: pause(),
            label: '暂停',
            key: 'pause',
            show: true
          },
          {
            // func: resume(),
            label: '重启',
            key: 'resume',
            show: true
          },
          {
            // func: trigger(),
            label: '立即运行',
            key: 'trigger',
            show: true
          }
        ],
        fixed: 'right'
      }
    ]
  }
}
