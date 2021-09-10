import Vue from 'vue'
import {
  ACCESS_TOKEN,
  SET_TOKEN,
  SET_AUTH,
  SET_USERINFO,
  SET_PERMISSION_LIST,
  SET_MESSAGE_NOTICE_INFO,
  SET_SYSTEM_SETTING,
  SET_USER_SHORTMENU,
  SET_WRKSPACE_LIST,
  SET_WRKSPACE_ID,
  SET_WRKSPACE_AUTH
} from '@/store/mutation-types'
import { arrayToTree } from 'utils/util'
import { login, logout } from '@/api/login'
import { queryPermissionsByUser } from '@/api/user'
import { font, theme } from '@/utils/theme'
import { DEFAULT_FONT_SIZE, DEFAULT_CSS_ID } from '@/config/constant'

const states = () => ({
  token: '',
  userInfo: {},
  systemSetting: {},
  auth: [],
  permissionList: [],
  info: {},
  messageNoticeInfo: {},
  shortMenus: [], // 快捷菜单列表
  workspaceList: [], // 工作空间下拉框
  workspaceId: null, // 当前选中工作空间ID
  workspaceCreateAuth: false // 当前登录账号是为root账号
})

const getters = {
  permissionList: state => state.permissionList,
  auth: state => state.auth,
  userInfo: state => state.userInfo,
  messageNoticeInfo: state => state.messageNoticeInfo,
  systemSetting: state => state.systemSetting,
  shortMenus: state => state.shortMenus,
  workspaceList: state => state.workspaceList,
  workspaceCreateAuth: state => state.workspaceCreateAuth
}

const mutations = {
  [SET_TOKEN](state, token) {
    state.token = token
  },
  [SET_USERINFO](state, userInfo) {
    state.userInfo = userInfo
  },
  [SET_AUTH](state, auth) {
    state.auth = auth
  },
  [SET_PERMISSION_LIST](state, permissionList) {
    state.permissionList = permissionList
  },
  [SET_MESSAGE_NOTICE_INFO](state, messageInfo) {
    state.messageNoticeInfo = messageInfo
  },
  [SET_SYSTEM_SETTING](state, systemSetting) {
    state.systemSetting = systemSetting
  },
  [SET_USER_SHORTMENU](state, shortMenus) {
    state.shortMenus = shortMenus
  },
  [SET_WRKSPACE_LIST](state, list) {
    state.workspaceList = list
  },
  [SET_WRKSPACE_ID](state, id) {
    state.workspaceId = id
  },
  [SET_WRKSPACE_AUTH](state, createAuth) {
    state.workspaceCreateAuth = createAuth
  }
}

const actions = {
  // 登录
  loginAction({ commit }, userInfo) {
    return new Promise((resolve, reject) => {
      login(userInfo)
        .then(response => {
          if (response.success) {
            Vue.ls.set(
              ACCESS_TOKEN,
              response.data.token,
              7 * 24 * 60 * 60 * 1000
            )
            commit(SET_TOKEN, response.data.token)
            resolve(response)
          } else {
            reject(response)
          }
        })
        .catch(error => {
          reject(error)
        })
    })
  },

  // 获取用户信息
  getPermissionListAction({ commit }) {
    return new Promise((resolve, reject) => {
      queryPermissionsByUser()
        .then(({ data }) => {
          const {
            menus,
            userInfo,
            authList,
            setting,
            shortMenus,
            workspaceList,
            workspaceCreateAuth
          } = data
          const _menusTree = arrayToTree(menus || [], '0')
          if (_menusTree && _menusTree.length > 0) {
            commit(SET_WRKSPACE_AUTH, workspaceCreateAuth)
            commit(SET_WRKSPACE_LIST, [
              { name: '全部', id: 'all' },
              ...workspaceList
            ])
            commit(SET_USERINFO, userInfo || {})
            commit(SET_AUTH, authList)
            commit(SET_PERMISSION_LIST, _menusTree)
            commit(SET_SYSTEM_SETTING, setting) // 系统字体字号、主题、logo
            commit(SET_USER_SHORTMENU, shortMenus) // 个性化设置-快捷菜单
            font(setting.fontSize || DEFAULT_FONT_SIZE) // 设置系统字体字号
            theme(setting.cssId || DEFAULT_CSS_ID) // 设置系统主题
          } else {
            reject(
              new Error(
                'getPermissionList: permissions must be a non-null array !'
              )
            )
          }
          commit(SET_USERINFO, userInfo || {})
          resolve(data)
        })
        .catch(error => {
          console.log('queryPermissionsByUser -> err', error)
          reject(error)
        })
    })
  },
  // 登出
  logoutAction({ commit }) {
    return new Promise(resolve => {
      commit(SET_TOKEN, '')
      commit(SET_PERMISSION_LIST, [])
      Vue.ls.remove(ACCESS_TOKEN)
      logout()
        .then(() => {
          resolve()
        })
        .catch(() => {
          resolve()
        })
    })
  },
  // 消息提醒
  setMessageNoticeInfo({ commit }, messageInfo) {
    commit(SET_MESSAGE_NOTICE_INFO, messageInfo)
  }
}

export default { state: states, getters, mutations, actions }
