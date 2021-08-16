/*
 * @Author: lizheng
 * @Description: 流程定义列表 - 流程历史版本表格参数
 * @Date: 2021-07-09
 */
export default _this => {
  return {
    refName: 'flowHistoryVersion',
    id: 'flowHistoryVersion',
    maxHeight: 500,
    isBorder: true,
    hasPage: false,
    group: [
      // {
      //   type: 'selection',
      //   width: '50px',
      //   prop: 'selection'
      // },
      {
        type: 'text',
        label: '版本号',
        prop: 'name'
      },
      {
        type: 'text',
        label: '配置人',
        prop: 'name1'
      },
      {
        type: 'text',
        label: '配置时间',
        prop: 'name2'
      },
      {
        type: 'text',
        label: '流程名称',
        prop: 'name31'
      },
      {
        type: 'text',
        label: '流程描述',
        prop: 'name3'
      },
      {
        type: 'slot',
        label: '操作',
        prop: 'operation',
        width: 160,
        operationList: [
          {
            func: _this.handleFlowPreview,
            label: '预览',
            key: 'versionFlowView'
          },

          {
            func: _this.mixinHandleDelete,
            label: '删除',
            key: 'versionFlowDelete'
          }
        ]
      }
    ]
  }
}
