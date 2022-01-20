import Main from '../components/layouts/LayoutMain'

/**
 * 走菜单，走权限控制
 * @type {{redirect: string, path: string, hidden: boolean}[]}
 */
export const asyncRouter = [
  // {
  //   path: '*',
  //   redirect: '/404',
  //   hidden: true
  // }
]

/**
 * 基础路由
 * @type {Router}
 */
export const constantRouter = [
  {
    path: '/login',
    name: 'login',
    component: () =>
      import(/* webpackChunkName: "login" */ '@/views/user/Login'),
    hidden: true
  },
  {
    path: '/',
    name: 'index',
    hidden: true,
    redirect: '/index',
    component: Main,
    meta: {
      name: '首页'
    },
    children: [
      {
        path: 'index',
        name: 'index_home',
        component: () => import(/* webpackChunkName: "home" */ '@/views/Home'),
        meta: {
          affix: true,
          name: '首页'
        }
      }
    ]
  }
  // {
  //   path: '/404',
  //   component: () =>
  //     import(/* webpackChunkName: "fail" */ '@/views/exception/404')
  // }
]
