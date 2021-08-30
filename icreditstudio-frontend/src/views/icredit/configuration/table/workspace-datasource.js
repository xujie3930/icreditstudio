const dataType = {
  1: 'MySQL',
  2: 'Oracle',
  3: 'progresql',
  4: 'SqlServer',
  5: 'SFTP',
  6: 'FTP',
  7: 'hive',
  8: 'MongoDB'
}

export default _this => {
  return {
    refName: 'workspace-datascource',
    id: '',
    isBorder: true,
    hasPage: true,
    customBtnConfig: [
      {
        label: '新增数据源',
        type: 'primary',
        key: 'addDataSource',
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
        width: '100px',
        prop: 'serialNumber'
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
        width: 300
      },
      {
        type: 'text',
        label: '是否启用',
        prop: 'status',
        formatter: row => (row.status ? '否' : '是'),
        width: 80
      },
      {
        type: 'text',
        label: '最近一次同步时间',
        prop: 'lastSyncTime'
      },
      {
        type: 'slot',
        label: '最近一次同步状态',
        prop: 'lastSyncStatus'
      },
      {
        type: 'text',
        label: '描述信息',
        prop: 'descriptor'
      },
      {
        type: 'slot',
        label: '操作',
        prop: 'operation',
        width: '250px',
        fixed: 'right',
        operationList: [
          {
            func: ({ row }) => _this.handleDetailClick(row, 'view'),
            label: '查看',
            key: 'view'
          },
          {
            func: ({ row }) => _this.handleSyncClick(row, 'sync'),
            label: '同步',
            key: 'sync',
            show: true
          },
          {
            func: ({ row }) => _this.handleOperateClick(row, 'disabled'),
            label: '停用',
            key: 'disabled',
            show: true
          },
          {
            func: ({ row }) => _this.handleOperateClick(row, 'delete'),
            label: '删除',
            key: 'delete',
            show: true
          },
          {
            func: ({ row }) => _this.handleEnabledClick(row, 'enabled'),
            label: '启用',
            key: 'enabled',
            show: true
          },
          {
            func: ({ row }) => _this.handleDetailClick(row, 'edit'),
            label: '编辑',
            key: 'edit',
            show: true
          }
        ]
      }
    ]
  }
}
