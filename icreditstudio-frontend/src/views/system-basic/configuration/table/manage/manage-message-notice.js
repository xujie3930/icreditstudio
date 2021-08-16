export default (_this, { READ_STATUS_ENUMS }) => {
  return {
    refName: 'messageNotice',
    id: 'messageNotice',
    isBorder: true,
    hasPage: true,
    customBtnConfig: [
      {
        label: '全部标记为已读',
        type: 'primary',
        key: 'readAll',
        options: {
          eventType: 'click',
          eventName: 'handleReadAll'
        }
      },
      {
        label: '全部删除',
        type: 'primary',
        key: 'deleteAll',
        options: {
          eventType: 'click',
          // selectType: 'multiple',
          eventName: 'handleDeleteAll'
        }
      }
    ],
    group: [
      // {
      //   type: 'selection',
      //   width: '50px',
      //   prop: 'selection'
      // },
      {
        type: 'text',
        label: '消息标题',
        prop: 'infoTitle'
      },
      {
        type: 'text',
        label: '消息内容',
        prop: 'infoContent'
      },
      // {
      //   type: 'text',
      //   label: '消息类型',
      //   prop: 'infoType',
      //   formatter: row => {
      //     return INFO_TYPE_ENUMS[row.infoType]
      //   }
      // },
      {
        type: 'text',
        label: '阅读状态',
        prop: 'readStatus',
        formatter: row => {
          return READ_STATUS_ENUMS[row.readStatus]
        }
      },
      {
        type: 'date',
        label: '发送时间',
        prop: 'createTime'
      },
      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        operationList: [
          {
            func: params => _this.handleView(params),
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
