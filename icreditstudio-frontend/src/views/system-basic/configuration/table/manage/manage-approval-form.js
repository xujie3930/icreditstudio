/*
 * @Author: lizheng
 * @Description: 流程定义 table 字段参数配置
 * @Date: 2021-07-05
 */
export default _this => {
  return {
    refName: 'approvalForm',
    id: 'approvalForm',
    isBorder: true,
    hasPage: false,
    treeProps: {
      children: 'formInfoResults'
    },
    customBtnConfig: [
      {
        label: '新增分组',
        type: 'primary',
        key: 'addGroup',
        options: {
          eventType: 'click',
          eventName: 'addGroup'
        }
      },
      {
        label: '删除分组',
        type: 'primary',
        key: 'deleteGroup',
        options: {
          eventType: 'click',
          eventName: 'deleteGroup'
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
        type: 'text',
        label: '分组名称',
        prop: 'name'
      },
      {
        type: 'text',
        label: '配置人',
        prop: 'name1'
      },
      {
        type: 'text',
        label: '流程绘制时间',
        prop: 'name2'
      },
      {
        type: 'text',
        label: '流程描述',
        prop: 'name3'
      },
      {
        type: 'text',
        label: '版本号',
        prop: 'name4'
      },
      {
        type: 'slot',
        label: '流程',
        prop: 'flow'
      },
      {
        type: 'slot',
        label: '操作',
        prop: 'operation',
        width: 260,
        operationList: [
          {
            func: _this.handleExpandConfigure,
            label: '扩展配置',
            key: 'expand'
          },
          {
            func: _this.handleExpandConfigure,
            label: '流程发起',
            key: 'initiation'
          },
          {
            func: _this.handleFlowPreview,
            label: '预览',
            key: 'flowPreview'
          },
          {
            func: ({ row }) => _this.handleGroupOperate('edit', row),
            label: '修改',
            key: 'edit'
          },
          {
            func: _this.mixinHandleDelete,
            label: '删除',
            key: 'delete'
          },
          {
            func: _this.mixinHandleDelete,
            label: '更多',
            key: 'more'
          }
        ],
        fixed: 'right'
      }
    ]
  }
}
