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
      type: 'select',
      label: '消息类型',
      ruleProp: 'infoType',
      options: Object.entries(INFO_TYPE_ENUMS).map(([value, label]) => {
        return { label, value }
      })
    },
    {
      type: 'text',
      label: '发送人',
      model: '',
      ruleProp: 'senderName'
    },
    // {
    //   type: 'select',
    //   label: '阅读状态',
    //   ruleProp: 'readStatus',
    //   options: Object.entries(READ_STATUS_ENUMS).map(([value, label]) => {
    //     return { label, value }
    //   })
    // },
    {
      type: 'date',
      label: '发送时间',
      ruleProp: 'createTime'
    },
    {
      type: 'textarea',
      label: '发送内容',
      placeholder: '',
      model: '',
      rows: 10,
      resize: 'vertical',
      ruleProp: 'infoContent'
    }
  ]
}
