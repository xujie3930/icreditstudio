const dataType = {
  1: 'MySQL',
  2: 'Oracle',
  3: 'ProgreSql',
  4: 'SqlServer',
  5: 'SFTP',
  6: 'FTP',
  7: 'Hive',
  8: 'MongoDB'
}

export default {
  refName: 'workspace-datascource',
  id: '',
  isBorder: true,
  hasPage: true,
  customBtnConfig: [
    {
      label: '新增数据源',
      type: 'primary',
      key: 'addDataSource',
      // disabled: that.workspaceId === '0',
      options: {
        eventType: 'click',
        eventName: 'handleAddDataSource'
      }
    }
  ],
  group: [
    {
      type: 'index',
      label: '序号',
      width: '80px'
    },
    {
      type: 'text',
      label: '数据源类型',
      prop: 'type',
      width: 100,
      formatter: row => dataType[row.type]
    },
    {
      type: 'text',
      label: '数据源自定义名称',
      prop: 'name'
    },
    {
      type: 'text',
      label: '连接信息',
      prop: 'uri',
      width: 350,
      formatter: ({ uri, type }) => {
        const uriMapping = {
          1: uri.split('?')[0],
          2: uri.split('|username')[0]
        }
        return uriMapping[type]
      }
    },
    {
      type: 'slot',
      label: '是否启用',
      prop: 'status',
      width: 80,
      formatter: row => (row.status ? '否' : '是')
    },
    {
      type: 'text',
      label: '最近一次同步时间',
      prop: 'lastSyncTime',
      width: 170,
      formatter: row => row.lastSyncTime || '-'
    },
    {
      type: 'slot',
      label: '最近一次同步状态',
      prop: 'lastSyncStatus',
      width: 150
    },
    {
      type: 'text',
      label: '描述信息',
      prop: 'descriptor',
      popperClass: 'descriptorPopper'
    },
    {
      type: 'slot',
      label: '操作',
      prop: 'operation',
      width: '250px',
      fixed: 'right'
    }
  ]
}
