/*
 * @Author: lizheng
 * @Description: 关联字段表
 * @Date: 2021-10-11
 */

export default that => ({
  refName: 'modelLink',
  id: 'modelLink',
  isBorder: true,
  hasPage: false,
  group: [
    {
      type: 'select',
      label: that.aTableName,
      prop: 'left',
      width: 200,
      placeholder: '请选择关联字段',
      options: that.aTableOption,
      change: that.handleChangeLeftSelect
    },
    {
      type: 'select',
      label: '关系',
      prop: 'associate',
      placeholder: '请选择关联关系',
      width: 180,
      options: that.conditionsOptions
    },
    {
      type: 'select',
      label: that.bTableName,
      prop: 'right',
      width: 200,
      placeholder: '请选择关联字段',
      options: that.bTableOption,
      change: that.handleChangeRightSelect
    },
    {
      type: 'slot',
      label: '操作',
      prop: 'operation',
      width: 80
    }
  ]
})
