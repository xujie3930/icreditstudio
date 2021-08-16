import Vue from 'vue'
import router from './index'
import store from '../store'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import {
  ACCESS_TOKEN,
  INDEX_MAIN_PAGE_PATH,
  SET_ACTIVE_MODULE_ID
} from '@/store/mutation-types'
import {
  arrayToTree,
  generateIndexRouter,
  getBooleanByType
} from '@/utils/util'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist

function filterMenuWithoutModule(menus) {
  const _menusTree = arrayToTree(menus, '0')
  /*
   * 一级菜单和模块共存
   * 通过类型过滤
   * 把没有模块的一级菜单放到默认模块下 顶部模块不展示默认模块
   * */
  const _modules = _menusTree.filter(e => e.type === 'D')
  if (_menusTree.filter(e => e.type === 'M').length) {
    _modules.unshift({
      children: _menusTree.filter(e => e.type === 'M'),
      deleteFlag: 'N',
      filePath: '',
      hidden: true,
      iconPath: '',
      id: 'defaultModule',
      internalOrExternal: null,
      isCache: null,
      isShow: false,
      keepAlive: null,
      name: '',
      parentId: null,
      redirectPath: '',
      remark: '',
      type: 'D'
    })
  }
  return _modules
}

function renderModules(modules) {
  const topModules = [] // 顶部模块
  const menusForModule = {} // 顶部模块对应的菜单
  modules.forEach(item => {
    if (item.children) {
      const _hidden = !item.children.filter(i => !i.isShow).length
      Object.assign(item, { hidden: _hidden })
    }
  })
  modules.forEach(item => {
    // 顶部模块对应的数组
    topModules.push({
      label: item.name,
      id: item.id,
      path: item.url,
      iconPath: item.iconPath,
      redirectPath: item.redirectPath,
      children: item.children
    })
    // 顶部模块每一个对应的菜单
    menusForModule[item.id] = item.children
  })
  return {
    topModules,
    menusForModule
  }
}

router.beforeEach((to, from, next) => {
  NProgress.start() // start progress bar
  if (Vue.ls.get(ACCESS_TOKEN)) {
    /* has token */
    if (to.path === '/login') {
      next({ path: INDEX_MAIN_PAGE_PATH }) // TODO
      NProgress.done()
    } else if (store.getters['user/permissionList'].length === 0) {
      store
        .dispatch('user/getPermissionListAction')
        .then(async res => {
          const menuData = res.menus
          if (menuData === null || menuData === '' || menuData === undefined) {
            return
          }
          // 将 isShow 和 keepAlive 的值 转为boolean
          menuData.forEach(item => {
            Object.assign(item, {
              isShow: getBooleanByType(item.isShow),
              keepAlive: item.keepAlive && getBooleanByType(item.keepAlive)
            })
          })
          // 渲染模块
          const _modules = filterMenuWithoutModule(res.menus)
          const _moduleData = renderModules(_modules)
          const _short_modules = filterMenuWithoutModule(res.shortMenus)
          const _short_moduleData = renderModules(_short_modules)
          await store.dispatch(
            'permission/updateTopModules',
            _moduleData.topModules
          ) // 模块数组
          await store.dispatch(
            'permission/updateModuleMenus',
            store.getters['user/systemSetting'].enableCustomMenu === 'Y'
              ? _short_moduleData.menusForModule
              : _moduleData.menusForModule
          ) // 模块对应的菜单
          // 默认选中第一个模块
          const index = _moduleData.topModules.findIndex(
            item => item.children && item.children.length
          )
          store.commit(
            `permission/${SET_ACTIVE_MODULE_ID}`,
            _moduleData.topModules[index > 0 ? index : 0].id
          )
          const constRoutes = generateIndexRouter(_modules)
          // 添加主界面路由
          store
            .dispatch('permission/updateAppRouterAction', { constRoutes })
            .then(() => {
              // 根据roles权限生成可访问的路由表
              // 动态添加可访问路由表
              router.addRoutes(store.getters['permission/addRouters'])
              const redirect = decodeURIComponent(
                from.query.redirect || to.path
              )
              // store.getters['user/userInfo'].firstLogin
              if (store.getters['user/userInfo'].firstLogin) {
                next({ path: '/manage/changepassword' })
              } else if (to.path === redirect) {
                // hack方法 确保addRoutes已完成
                next({
                  ...to,
                  replace: true
                })
              } else {
                // 跳转到目的路由
                next({ path: redirect })
              }
            })
        })
        .catch(err => {
          console.log('getPermissionListAction -> err', err)
          store.dispatch('user/logoutAction').then(() => {
            next({
              path: '/login',
              query: { redirect: to.fullPath }
            })
          })
        })
    } else if (
      to.path !== '/manage/changepassword' &&
      store.getters['user/userInfo'].firstLogin
    ) {
      next({
        path: '/manage/changepassword',
        replace: true
      })
      NProgress.done()
    } else {
      next()
    }
  } else if (whiteList.indexOf(to.path) !== -1) {
    // 在免登录白名单，直接进入
    next()
  } else {
    next({
      path: '/login',
      query: { redirect: to.fullPath }
    })
    // if current page is login will not trigger afterEach hook, so manually handle it
    NProgress.done()
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})
