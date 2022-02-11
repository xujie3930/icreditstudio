import Vue from 'vue'
import { initGlobalState } from 'qiankun'

const initState = {
  workspaceId: Vue.ls.get('workspaceId') || ''
}

const actions = initGlobalState(initState)

export default actions
