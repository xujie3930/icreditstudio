const MODULETYPES = {
  M: '菜单',
  D: '顶部模块',
  B: '按钮'
}
export default {
  refName: 'leftTable',
  id: 'authSet',
  isBorder: true,
  hasPage: false,
  maxHeight: '375px',
  group: [
    {
      type: 'selection',
      width: '50px',
      prop: 'selection'
    },
    {
      type: 'text',
      label: '模块名称',
      prop: 'name'
    },
    {
      type: 'text',
      label: '类型',
      prop: 'type',
      formatter(row) {
        return MODULETYPES[row.type];
      }
    },
    {
      type: 'text',
      label: '所属模块',
      prop: 'parentName'
    }
  ]
}
