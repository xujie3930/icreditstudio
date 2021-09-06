export default {
  refName: 'leftTable',
  id: 'userSet',
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
      label: '姓名',
      prop: 'userName'
    },
    {
      type: 'text',
      label: '部门',
      prop: 'orgNames',
      formatter({ orgNames = [] }) {
        return orgNames.join(';')
      }
    }
  ]
}
