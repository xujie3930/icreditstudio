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
      { value: 'N', label: '全部' },
      { value: 'Y', label: 'MySQL' },
      { value: 'M', label: 'oracle' },
      { value: 'D', label: 'hive' },
      { value: 'HDFS', label: 'HDFS' },
      { value: 'SFTP', label: 'SFTP' },
      { value: 'FTP', label: 'FTP' },
      { value: 'progresql', label: 'progresql' },
      { value: 'mongodb', label: 'MongoDB' },
      { value: 'chain', label: '区块链' }
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
      { value: 'All', label: '全部' },
      { value: 'Y', label: '是' },
      { value: 'N', label: '否' }
    ]
  }
]
