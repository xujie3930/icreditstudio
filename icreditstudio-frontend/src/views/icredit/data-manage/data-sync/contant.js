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
  0: '手动执行',
  1: '周期执行'
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

// 同步任务构建-切换数据源选项
export const radioBtnOption = [
  { label: 0, className: 'btn btn-left', name: '外接数据库' },
  { label: 1, className: 'btn btn-center', name: '本地文件' },
  { label: 2, className: 'btn btn-right', name: '区块链数据' }
]

// 字段类型
export const fieldTypeOptions = [
  {
    value: 0,
    label: '数值类',
    children: [
      { label: 'TINYINT', value: 'TINYINT' },
      { label: 'SMALLINT', value: 'SMALLINT' },
      { label: 'INT', value: 'INT' },
      { label: 'BIGINT', value: 'BIGINT' },
      { label: 'FLOAT', value: 'FLOAT' },
      { label: 'DOUBLE', value: 'DOUBLE' },
      { label: 'DECIMAL', value: 'DECIMAL' }
    ]
  },
  {
    value: 1,
    label: '日期时间类',
    children: [
      { label: 'TIMESTAMP', value: 'TIMESTAMP' },
      { label: 'DATE', value: 'DATE' }
    ]
  },
  {
    value: 2,
    label: '字符串类',
    children: [
      { label: 'STRING', value: 'STRING' },
      { label: 'VARCHAR', value: 'VARCHAR' }
    ]
  }
]

// 关联类型icon名称映射
export const iconMapping = {
  0: { icon: 'left-link', name: '左关联' },
  1: { icon: 'cover-link', name: '内关联' },
  2: { icon: 'all-link', name: '全关联' }
}

// 任务详情接口字段映射
export const taskDetailInfo = [
  { key: 'taskName', label: '任务名', value: '' },
  { key: 'enable', label: '任务启用', value: '' },
  { key: 'createMode', label: '创建方式', value: '' },
  { key: 'taskDescribe', label: '任务描述', value: '' }
]

// 时间格式接口字段映射
export const dateFieldMapping = {
  hour: '时',
  day: '天',
  month: '月',
  year: '年'
}
