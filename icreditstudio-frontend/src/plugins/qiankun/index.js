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
import LifeCycles from './life-cycles'

// 注册子应用
registerMicroApps(microApp, LifeCycles)

// 捕获异常
addGlobalUncaughtErrorHandler(evt => {
  const { message } = evt
  if (message?.includes('died in status')) {
    console.error('微应用加载失败，请检查应用是否可运行!')
  }
})

export default start
