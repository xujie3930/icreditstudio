/*
 * @Author: lizheng
 * @Description: 表单历史版本
 * @Date: 2021-07-16
 */

const STATUS_ENUMS = { D: '草稿箱', P: '已发布', T: '已停用' }
export default _this => {
  return {
    refName: 'formHistory',
    id: 'formHistory',
    maxHeight: 500,
    isBorder: true,
    hasPage: true,
    group: [
      {
        type: 'index',
        label: '序号',
        width: '100px',
        prop: 'serialNumber'
      },
      {
        type: 'text',
        label: '版本号',
        width: 100,
        prop: 'formVersion'
      },
      // {
      //   type: 'text',
      //   label: '表单名称',
      //   prop: 'formName'
      // },
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
      {
        type: 'text',
        label: '状态',
        width: 100,
        prop: 'formStatus',
        formatter: row => {
          return STATUS_ENUMS[row.formStatus]
        }
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
            func: _this.handleFormPreview,
            label: '预览',
            isHide: false,
            key: 'preview'
          },
          {
            func: _this.handleFormDelete,
            label: '删除',
            key: 'delete'
          },
          {
            func: _this.handleFormExport,
            label: '导出',
            key: 'formExport'
          }
        ],
        fixed: 'right'
      }
    ]
  }
}
