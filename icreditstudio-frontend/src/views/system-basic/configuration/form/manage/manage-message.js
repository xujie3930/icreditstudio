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
      isSearch: true,
      viewHide: true,
      addHide: true,
      ruleProp: 'receiverName'
    },
    {
      type: 'text',
      label: '接收人',
      model: '',
      ruleProp: 'receiverNames',
      readonly: true,
      placeholder: '请单击选择接收人',
      click: _this._handleChooseReceiver
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
    {
      type: 'text',
      label: '发送人',
      addHide: true,
      ruleProp: 'senderName'
    },
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
      addHide: true,
      ruleProp: 'sendTime'
    },
    {
      type: 'textarea',
      label: '发送内容',
      model: '',
      rows: 5,
      resize: 'vertical',
      ruleProp: 'infoContent',
      maxlength: 2000
      // showWordLimit: true
    }
  ]
}
