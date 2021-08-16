export default _this => {
  return {
    refName: 'approvalInitiate',
    id: 'approvalInitiate',
    isBorder: true,
    hasPage: false,
    treeProps: {
      children: 'formInfoResults'
    },
    group: [
      {
        type: 'index',
        label: '序号',
        width: '100px',
        prop: 'serialNumber'
      },
      {
        type: 'text',
        label: '名称',
        prop: 'name',
        align: 'left'
      },
      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        operationList: [
          {
            func: _this.handleFlowPublish,
            label: '发起审批',
            key: 'publish'
          }
        ],
        fixed: 'right'
      }
    ]
  }
}
