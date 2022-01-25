/*
 * @Author: lizheng
 * @Description: 子应用-数据源路由
 * @Date: 2022-01-20
 */

export default [
  {
    path: '/subapp',
    title: 'adout',
    children: [
      {
        path: '/subapp/datasource',
        title: '数据源',
        children: []
      },
      {
        path: '/subapp/data-sync',
        title: '数据同步',
        children: [{ path: '/sub-app/data-sync/about', title: 'about' }]
      }
    ]
  }
]
