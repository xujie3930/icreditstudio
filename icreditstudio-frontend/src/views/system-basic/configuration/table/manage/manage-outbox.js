export default (_this, { INFO_TYPE_ENUMS }) => {
  return {
    refName: 'message',
    id: 'message',
    isBorder: true,
    hasPage: true,
    defaultExpandAll: true,
    customBtnConfig: [
      {
        label: '批量删除',
        type: 'primary',
        key: 'multipleDelete',
        options: {
          eventType: 'click',
          selectType: 'multiple',
          eventName: 'handleMultipleDelete'
        }
      }
    ],
    group: [
      // {
      //   type: 'selection',
      //   width: '50px',
      //   prop: 'selection'
      // },
      // {
      //   type: 'index',
      //   label: '序号',
      //   width: '100px',
      //   prop: 'serialNumber'
      // },
      {
        type: 'text',
        label: '消息标题',
        prop: 'infoTitle'
      },
      {
        type: 'text',
        label: '发送内容',
        prop: 'infoContent'
      },
      // {
      //   type: 'text',
      //   label: '发送人',
      //   prop: 'senderName'
      // },
      {
        type: 'date',
        label: '发送时间',
        prop: 'sendTime'
      },
      {
        type: 'text',
        label: '消息类型',
        prop: 'infoType',
        formatter: row => {
          return INFO_TYPE_ENUMS[row.infoType]
        }
      },
      {
        type: 'text',
        label: '接收人',
        prop: 'receiverNames'
      },
      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        operationList: [
          {
            func: _this.mixinHandleView,
            label: '详情',
            key: 'detail',
            show: true
          },
          {
            func: _this.mixinHandleDelete,
            label: '删除',
            key: 'delete',
            show: true
          }
        ],
        fixed: 'right'
      }
    ]
  }
}
