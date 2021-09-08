/*
 * @Description: 空间设置列表页面搜索表单
 * @Date: 2021-08-17
 */

export default [
  {
    type: 'text',
    label: '工作空间名称',
    model: '',
    ruleProp: 'name',
    isSearch: true,
    maxlength: 20
  },
  {
    type: 'text',
    label: '更新人',
    ruleProp: 'updateUser',
    model: '',
    isSearch: true
  },
  {
    type: 'date',
    label: '更新时间',
    model: '',
    ruleProp: 'updateTime',
    isSearch: true,
    format: 'yyyy-MM-dd',
    valueFormat: 'yyyy-MM-dd'
  }
]
