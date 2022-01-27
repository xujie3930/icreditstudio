/*
 * @Author: lizheng
 * @Description: 主子应用通信
 * @Date: 2022-01-25
 */

import Vue from 'vue'
import store from '@/store'

//主与子应用通信
const state = {
  workspaceId: store.getters['user/workspaceId'],
  userInfo: store.getters['user/userInfo'],
  token: store.getters['user/token'] || Vue.ls.get('ACCESS_TOKEN')
}

class Shared {
  constructor(state) {
    this.state = state
  }

  getStateProperty(key) {
    return key in this.state ? this.state[key] : null
  }
}

const shared = new Shared(state)

export default shared
