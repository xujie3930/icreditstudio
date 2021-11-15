/*
 * @Author: lizheng
 * @Description: 调度管理-同步任务调度-历史执行情况
 * @Date: 2021-09-24
 */
export default [
  {
    type: 'daterange',
    label: '执行时间',
    ruleProp: 'execTime',
    model: '',
    isSearch: true,
    format: 'yyyy-MM-dd',
    valueFormat: 'timestamp'
  },
  {
    type: 'select',
    label: '执行状态',
    ruleProp: 'taskStatus',
    model: '',
    isSearch: true,
    options: [
      { value: '', label: '全部' },
      { value: '0', label: '成功' },
      { value: '1', label: '失败' },
      { value: '2', label: '执行中' }
    ]
  }
]
