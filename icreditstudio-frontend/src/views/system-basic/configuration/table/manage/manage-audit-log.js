// 操作类型
// 在页面方法interceptorsResponseTableData内做了处理
// const OPRATE_TYPE_ENUMS = {
//   C: '新增',
//   R: '删除',
//   U: '更新',
//   D: '查询'
// }
// const OPRAT_ERESULT_ENUMS = {
//   S: '成功',
//   N: '失败'
// }
export default _this => ({
  refName: 'aduitLog',
  id: 'aduitLog',
  isBorder: true,
  hasPage: true,
  group: [
    {
      type: 'text',
      label: '操作类型',
      prop: 'oprateType',
      width: 120
      // formatter(row) {
      //   return OPRATE_TYPE_ENUMS[row.oprateType];
      // }
    },
    {
      type: 'text',
      label: '操作人',
      width: 100,
      prop: 'userName'
    },
    {
      type: 'text',
      label: '操作时间',
      width: 250,
      prop: 'oprateTime'
    },
    {
      type: 'text',
      label: '执行结果',
      width: 100,
      prop: 'oprateResult'
      // formatter(row) {
      //   return OPRAT_ERESULT_ENUMS[row.oprateResult];
      // }
    },
    {
      type: 'text',
      label: '操作内容及说明',
      prop: 'oprateInfo',
      formatter(row) {
        return row.oprateInfo.replace(/,$/, '')
      }
    },
    {
      type: 'operation',
      label: '操作',
      prop: 'operation',
      fixed: 'right',
      width: 100,
      operationList: [
        {
          func: params => _this.mixinHandleView(params, '审计日志详情'),
          label: '详情',
          key: 'detail',
          show: true
        }
      ]
    }
  ]
})
