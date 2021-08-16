export default _this => {
  return {
    refName: 'approvalList',
    id: 'approvalList',
    isBorder: true,
    hasPage: true,
    group: [
      {
        type: 'text',
        label: '审批标题',
        prop: 'processInstanceName',
        align: 'left'
      },
      {
        type: 'text',
        label: '审批摘要',
        prop: 'approvalSummary',
        align: 'left'
      },
      {
        type: 'text',
        label: '发起时间',
        prop: 'startTime',
        align: 'left'
      },
      {
        type: 'text',
        label: '状态',
        prop: 'stateStr',
        align: 'left'
      },
      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        operationList: [
          {
            func: _this.approvalPreview,
            label: '查看',
            key: 'preview'
          }
        ],
        fixed: 'right'
      }
    ]
  }
}
