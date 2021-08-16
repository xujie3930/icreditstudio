export default (_this, { STATUS_ENUMS }) => {
  return {
    refName: 'approvalForm',
    id: 'approvalForm',
    isBorder: true,
    hasPage: true,
    // treeProps: {
    //   children: 'formInfoResults'
    // },
    customBtnConfig: [
      {
        label: '批量删除',
        type: 'primary',
        key: 'multipleDelete',
        options: {
          eventType: 'click',
          eventName: 'handleMultipleDelete',
          selectType: 'multiple'
          // validBeforeEvent: _this.validBeforeMultipleDelete// 自定义前置校验
        }
      }
    ],
    group: [
      {
        type: 'selection',
        width: '50px',
        prop: 'selection'
      },
      {
        type: 'index',
        label: '序号',
        width: '100px',
        prop: 'serialNumber'
      },
      {
        type: 'text',
        label: '表单名称',
        prop: 'formName'
      },
      {
        type: 'text',
        label: '创建人',
        prop: 'userName'
      },
      {
        type: 'date',
        label: '发布时间',
        prop: 'formPublishTime'
      },
      // {
      //   type: 'date',
      //   label: '更新时间',
      //   prop: 'formUpdateTime'
      // },
      {
        type: 'text',
        label: '状态',
        prop: 'formStatus',
        formatter: row => {
          return STATUS_ENUMS[row.formStatus]
        }
      },
      {
        type: 'text',
        label: '版本号',
        prop: 'formVersion'
      },
      {
        type: 'text',
        label: '模板ID',
        prop: 'id'
      },
      {
        type: 'text',
        label: '描述',
        prop: 'formDesc'
      },
      {
        type: 'operation',
        label: '操作',
        prop: 'operation',
        operationList: [
          {
            func: _this.handleFormEdit,
            label: '修改',
            key: 'edit'
          },
          {
            label: '更多',
            key: 'edit',
            dropTrigger: 'click',
            children: [
              {
                func: ({ row }) => _this.handleFormPreview(row),
                label: '预览',
                isHide: false,
                key: 'preview'
              },
              {
                func: option =>
                  _this.mixinHandleDelete(
                    option,
                    option.row.formStatus === 'P'
                      ? '该表单处于发布中，请确认是否删除？'
                      : '表单删除后， 所有的历史版本都将删除，请确认是否删除？'
                  ),
                label: '删除',
                key: 'delete'
              },
              {
                func: ({ row }) => _this.handlePublishOperate(row),
                label: '发布',
                isHide: ({ row }) => row.formStatus === 'P',
                key: 'publish'
              },
              {
                func: _this.handleFormDisable,
                label: '停用',
                isHide: ({ row }) => row.formStatus !== 'P',
                key: 'disable'
              },
              {
                func: _this.handleFormExport,
                label: '导出',
                key: 'formExport'
              },
              {
                func: _this.OpenHistoryListModal,
                label: '历史版本',
                key: 'historyVersion'
              },
              {
                func: _this.createFormInstance,
                isHide: ({ row }) => row.formStatus !== 'P',
                label: '创建实例',
                key: 'formInstance'
              }
            ]
          }
        ],
        fixed: 'right'
      }
    ]
  }
}
