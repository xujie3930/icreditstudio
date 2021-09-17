/*
 * @Description: 数据源管理
 * @Date: 2021-08-18
 */

export default [
  {
    type: 'select',
    label: '数据源类型',
    ruleProp: 'type',
    model: '',
    isSearch: true,
    options: [
      { value: undefined, label: '全部' },
      { value: 1, label: 'MySQL' },
      { value: 2, label: 'oracle' },
      { value: 3, label: 'hive' },
      { value: 4, label: 'HDFS' },
      { value: 5, label: 'SFTP' },
      { value: 6, label: 'FTP' },
      { value: 7, label: 'progresql' },
      { value: 8, label: 'MongoDB' },
      { value: 9, label: '区块链' }
    ]
  },
  {
    type: 'text',
    label: '数据源自定义名称',
    model: '',
    ruleProp: 'name',
    isSearch: true
  },
  {
    type: 'select',
    label: '是否启用',
    ruleProp: 'status',
    model: '',
    isSearch: true,
    options: [
      { value: undefined, label: '全部' },
      { value: 0, label: '是' },
      { value: 1, label: '否' }
    ]
  }
]
