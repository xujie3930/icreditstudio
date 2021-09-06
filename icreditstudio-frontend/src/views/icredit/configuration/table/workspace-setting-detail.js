/*
 * @Description: 用户角色成员信息
 * @Date: 2021-08-18
 */
export default {
  refName: 'workspaceSettingDetial',
  id: 'settingDetial',
  isBorder: true,
  hasPage: false,
  maxHeight: 250,
  group: [
    {
      type: 'index',
      label: '序号',
      width: '100px',
      prop: 'serialNumber'
    },
    {
      type: 'text',
      label: '用户',
      prop: 'username'
    },
    {
      type: 'text',
      label: '角色',
      prop: 'userRole'
    },
    {
      type: 'text',
      label: '功能权限',
      prop: 'functionalAuthority'
    },
    {
      type: 'text',
      label: '数据权限',
      prop: 'dataAuthority'
    },
    {
      type: 'date',
      label: '添加时间',
      prop: 'createTime',
      format: 'yyyy-MM-dd'
    }
  ]
}