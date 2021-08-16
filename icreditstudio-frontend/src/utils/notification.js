import { Notification } from 'element-ui'

// 使用Symbol定义ElNotification对象的私有属性
const notify = Symbol('notify')

// 重写Element的Notification组件方法
// 确保相同的错误信息只弹出一次
class ElNotification {
  success(options, single = true) {
    this[notify]('success', options, single)
  }

  warning(options, single = true) {
    this[notify]('warning', options, single)
  }

  info(options, single = true) {
    this[notify]('info', options, single)
  }

  error(options, single = true) {
    this[notify]('error', options, single)
  }

  [notify](type, options, single) {
    const doms = document.getElementsByClassName('el-notification')
    const message = typeof options === 'string' ? options : options.message
    let isShow = true

    if (!single) return Notification[type](options)
    for (let i = 0; i < doms.length; i++) {
      const msg = doms[i].getElementsByClassName('el-notification__content')[0].innerHTML.replace(/<.+?>/gim, '')
      if (message === msg) isShow = false
    }
    if (!doms.length || isShow) Notification[type](options)
  }
}

export { ElNotification }
