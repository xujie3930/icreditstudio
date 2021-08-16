export default (_this, { STATUS_ENUMS }) => {
  return [
    {
      type: 'text',
      label: '表单名称',
      model: '',
      ruleProp: 'formName',
      isSearch: true
    },
    {
      type: 'select',
      label: '表单状态',
      model: '',
      ruleProp: 'formStatus',
      isSearch: true,
      enums: STATUS_ENUMS
    },
    {
      type: 'text',
      label: '版本号',
      model: '',
      ruleProp: 'formVersion',
      isSearch: true
    }
  ]
}
