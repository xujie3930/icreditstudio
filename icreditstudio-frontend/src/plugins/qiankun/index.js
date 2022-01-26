/*
 * @Author: lizheng
 * @Description: 启动qiankun以及配置相应参数
 * @Date: 2022-01-10
 */
import {
  registerMicroApps,
  addGlobalUncaughtErrorHandler,
  start
} from 'qiankun'
import microApp from './micro-app'
import lifeCycles from './life-cycles'
import store from '@/store'

// 注册子应用
store.dispatch('common/SET_LOAD_SUBAPP_STATE', true)
registerMicroApps(microApp, lifeCycles)

// 捕获异常
addGlobalUncaughtErrorHandler(evt => {
  const { message } = evt
  if (message?.includes('died in status')) {
    store.commit('common/SET_LOAD_SUBAPP_STATE', false)
    console.error('微应用加载失败，请检查应用是否可运行!')
  }
})

export default start
