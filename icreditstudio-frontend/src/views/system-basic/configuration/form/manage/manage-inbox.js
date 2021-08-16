export default (_this, { INFO_TYPE_ENUMS }) => {
  return [
    {
      type: 'text',
      label: '消息标题',
      model: '',
      ruleProp: 'infoTitle',
      isSearch: true,
      maxlength: 200
      // showWordLimit: true
    },
    {
      type: 'text',
      label: '接收人',
      model: '',
      // isSearch: true,
      viewHide: true,
      ruleProp: 'receiverName'
    },
    {
      type: 'text',
      label: '发送人',
      model: '',
      isSearch: true,
      ruleProp: 'senderName'
    },
    {
      type: 'select',
      label: '消息类型',
      ruleProp: 'infoType',
      model: '',
      options: Object.entries(INFO_TYPE_ENUMS).map(([value, label]) => {
        return { label, value }
      }),
      isSearch: true,
      addHide: true,
      viewHide: true
    },
    {
      type: 'radio',
      label: '消息类型',
      ruleProp: 'infoType',
      model: '',
      options: Object.entries(INFO_TYPE_ENUMS).map(([value, label]) => {
        return { label, value, disabled: value === 'S' }
      })
    },
    // {
    //   type: 'text',
    //   label: '发送人',
    //   ruleProp: 'senderName'
    // },
    {
      type: 'daterange',
      label: '发送时间',
      startPlaceholder: '开始日期',
      endPlaceholder: '结束日期',
      model: '',
      ruleProp: 'operateTime',
      format: 'yyyy-MM-dd',
      valueFormat: 'timestamp',
      addHide: true,
      viewHide: true,
      isSearch: true
    },
    {
      type: 'date',
      label: '发送时间',
      ruleProp: 'sendTime'
    },
    // {
    //   type: 'select',
    //   label: '阅读状态',
    //   ruleProp: 'readStatus',
    //   options: Object.entries(READ_STATUS_ENUMS).map(([value, label]) => {
    //     return { label, value }
    //   })
    // },
    // {
    //   type: 'date',
    //   label: '阅读时间',
    //   ruleProp: 'readTime'
    //   // format: 'yyyy-MM-dd hh:mm:ss'
    // },
    {
      type: 'textarea',
      label: '发送内容',
      model: '',
      rows: 5,
      resize: 'vertical',
      ruleProp: 'infoContent'
    }
  ]
}
