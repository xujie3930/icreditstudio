// 登录状态
const LOGIN_STATUS_ENUMS = {
  S: '登录成功',
  F: '登录失败'
}

export default {
  refName: 'loginLog',
  id: 'loginLog',
  isBorder: true,
  hasPage: true,
  group: [
    {
      type: 'text',
      label: '登录账户',
      prop: 'userAccount'
    },
    {
      type: 'text',
      label: '用户姓名',
      width: 120,
      prop: 'userName'
    },
    {
      type: 'text',
      label: '登录时间',
      prop: 'loginTime'
    },
    {
      type: 'text',
      label: '登录状态',
      width: 120,
      prop: 'loginStatus',
      formatter(row) {
        return LOGIN_STATUS_ENUMS[row.loginStatus];
      }
    },
    {
      type: 'text',
      label: '失败原因',
      prop: 'errorInfo'
    },
    {
      type: 'text',
      label: '退出时间',
      prop: 'logoutTime'
    },
    // {
    //   type: 'text',
    //   label: 'token',
    //   prop: 'userToken'
    // },
    {
      type: 'text',
      label: '客户端IP',
      prop: 'userIp'
    }
  ]
}
