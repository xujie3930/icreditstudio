/*
 * @Author: lizheng
 * @Description: 常量
 * @Date: 2021-09-06
 */

// 任务状态值映射
export const taskStatusMapping = {
  0: { label: '启用', color: '#52c41a' },
  1: { label: '草稿', color: '#999' },
  2: { label: '停用', color: '#ff4d4f' }
}

// 执行状态值映射
export const execStatusMapping = {
  0: { label: '成功', color: '#52c41a' },
  1: { label: '失败', color: '#ff4d4f' },
  2: { label: '执行中', color: '#faad14' }
}

// 创建方式
export const createModeMapping = {
  0: '可视化',
  1: 'SQL'
}

// 调度类型
export const scheduleTypeMapping = {
  0: '周期执行',
  1: '手动执行'
}

// 数据源树形组件ICON名映射
export const treeIconMapping = {
  0: ['database', 'table'],
  1: {
    excel: ['excel-icon', 'excel-icon-2'],
    csv: ['csv-icon', 'csv-icon-2'],
    txt: ['txt-icon', 'txt-icon-2']
  },
  2: []
}
