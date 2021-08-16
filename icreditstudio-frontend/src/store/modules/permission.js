import {
  SET_ROUTERS,
  SET_TOP_MODULES,
  SET_MODULE_MENUS,
  SET_ACTIVE_MODULE_ID
} from '../mutation-types'
import { asyncRouter, constantRouter } from '@/router/constant-route.js'

/**
 * 过滤账户是否拥有某一个权限，并将菜单从加载列表移除
 *
 * @param permission
 * @param route
 * @returns {boolean}
 */
function hasPermission(permission, route) {
  if (route.meta && route.meta.permission) {
    let flag = -1
    for (let i = 0, len = permission.length; i < len; i++) {
      flag = route.meta.permission.indexOf(permission[i])
      return flag >= 0
    }
    return false
  }
  return true
}

/**
 * 单账户多角色时，使用该方法可过滤角色不存在的菜单
 *
 * @param roles
 * @param route
 * @returns {*}
 */
// eslint-disable-next-line
function hasRole(roles, route) {
  if (route.meta && route.meta.roles) {
    return route.meta.roles.indexOf(roles.id)
  } else {
    return true
  }
}

function filterAsyncRouter(routerMap, roles) {
  return routerMap.filter(route => {
    if (hasPermission(roles.permissionList, route)) {
      if (route.children && route.children.length) {
        // eslint-disable-next-line no-param-reassign
        route.children = filterAsyncRouter(route.children, roles)
      }
      return true
    }
    return false
  })
}

const states = () => ({
  routers: constantRouter,
  addRouters: [],
  topModules: [],
  activeModuleId: '',
  moduleMenus: {}
})

const getters = {
  addRouters: state => state.addRouters,
  routers: state => state.routers,
  topModules: state => state.topModules,
  activeModuleId: state => state.activeModuleId,
  moduleMenus: state => state.moduleMenus
}

const mutations = {
  [SET_ROUTERS]: (state, data) => {
    state.addRouters = data
    state.routers = constantRouter.concat(data)
  },
  [SET_TOP_MODULES](state, topModules) {
    state.topModules = topModules
  },
  [SET_MODULE_MENUS](state, moduleMenus) {
    state.moduleMenus = moduleMenus
  },
  [SET_ACTIVE_MODULE_ID](state, activeModuleId) {
    console.log('activeModuleId', activeModuleId)
    state.activeModuleId = activeModuleId
  }
}

const actions = {
  generateRoutesAction({ commit }, data) {
    return new Promise(resolve => {
      const { roles } = data
      console.log('-----mutations---data----', data)
      const accessedRouters = filterAsyncRouter(asyncRouter, roles)
      console.log('-----mutations---accessedRouters----', accessedRouters)
      commit(SET_ROUTERS, accessedRouters)
      resolve()
    })
  },
  // 动态添加主界面路由，需要缓存
  updateAppRouterAction({ commit }, { constRoutes }) {
    return new Promise(resolve => {
      commit(SET_ROUTERS, constRoutes)
      resolve()
    })
  },
  // 顶部模块
  updateTopModules({ commit }, topModules) {
    return new Promise(resolve => {
      commit(SET_TOP_MODULES, topModules)
      resolve()
    })
  },
  // 顶部模块对应的左侧菜单
  updateModuleMenus({ commit }, moduleMenus) {
    return new Promise(resolve => {
      commit(SET_MODULE_MENUS, moduleMenus)
      resolve()
    })
  }
}

export default { state: states, getters, mutations, actions }
