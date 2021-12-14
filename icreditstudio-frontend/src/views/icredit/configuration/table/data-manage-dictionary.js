/*
 * @Author: lizheng
 * @Description: 字典表
 * @Date: 2021-09-27
 */
export default {
  refName: 'dict',
  id: 'dict',
  isBorder: true,
  hasPage: true,
  customBtnConfig: [
    {
      label: '导入字典表',
      type: 'primary',
      key: 'importDict',
      options: {
        title: '导入字典表',
        opType: 'import',
        eventType: 'click',
        eventName: 'handleImportDict'
      }
    },
    {
      label: '新增字典表',
      type: 'primary',
      key: 'addDict',
      options: {
        title: '新增字典表',
        opType: 'Add',
        eventType: 'click',
        eventName: 'handleAddDict'
      }
    }
  ],
  group: [
    {
      type: 'index',
      width: '80px',
      label: '序号'
    },

    {
      type: 'text',
      label: '字典表英文名称',
      prop: 'englishName'
    },
    {
      type: 'text',
      label: '字典表中文名称',
      prop: 'chineseName'
    },

    {
      type: 'text',
      label: '添加人',
      prop: 'createUserName',
      width: 130
    },
    {
      type: 'text',
      label: '添加时间',
      prop: 'createTime'
    },
    {
      type: 'text',
      label: '描述',
      prop: 'dictDesc'
    },

    {
      type: 'slot',
      label: '操作',
      prop: 'operation',
      width: 185,
      fixed: 'right'
    }
  ]
}
