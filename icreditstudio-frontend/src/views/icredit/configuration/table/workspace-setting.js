/*
 * @Author: lizheng
 * @Description: 空间设置
 * @Date: 2021-08-17
 */

export default _this => {
  const { workspaceCreateAuth, workspaceId } = _this
  return {
    refName: 'workspace-setting',
    id: 'setting',
    isBorder: true,
    isStripe: true,
    hasPage: true,
    size: 'small',
    customBtnConfig: [
      {
        label: '新增工作空间',
        type: 'primary',
        key: 'addWorkspace',
        disabled: !workspaceCreateAuth || workspaceId !== 'all',
        // isHide: !_this.workspaceCreateAuth,
        options: {
          eventType: 'click',
          eventName: 'handleAddWorkspace'
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
        label: '工作空间名称',
        prop: 'name'
      },
      {
        type: 'slot',
        label: '空间状态',
        prop: 'status',
        width: 100
      },
      {
        type: 'text',
        label: '包含业务流程数（个）',
        prop: 'businessFlowCount',
        width: 200
      },
      {
        type: 'text',
        label: '包含工作流个数（个）',
        prop: 'workFlowCount',
        width: 200
      },
      {
        type: 'text',
        label: '更新时间',
        prop: 'updateTime'
      },
      {
        type: 'text',
        label: '更新人',
        prop: 'updateUser'
      },
      {
        type: 'text',
        label: '描述',
        prop: 'descriptor'
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
}
