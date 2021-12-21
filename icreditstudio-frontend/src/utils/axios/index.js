import Vue from 'vue'
import axios from 'axios'
import store from '@/store'
import router from '@/router'
import { MessageBox } from 'element-ui'
import { ElNotification as Notification } from '@/utils/notification'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import { baseConfig, uselessTokenConfig } from 'config'
import { removePendingAjax, addPendingAjax } from './cancel-token'
import { handleTrim } from '@/utils/util'
// 创建 axios 实例
const service = axios.create({
  // baseURL: baseConfig.baseUrl,
  baseURL: '/api',
  timeout: baseConfig.timeout // 请求超时时间
})

const notificationMsg = message => {
  new Notification().error({
    title: '系统提示',
    message: message || '接口请求异常',
    duration: 4000
  })
}

const err = error => {
  removePendingAjax(error.config || {})
  if (error.response) {
    const { data } = error.response
    const token = Vue.ls.get(ACCESS_TOKEN)
    console.log('------异常响应------', token)
    console.log('------异常响应------', error.response.status)
    switch (error.response.status) {
      case 401:
        notificationMsg('未授权，请重新登录')
        if (token) {
          store.dispatch('user/logoutAction').then(() => {
            setTimeout(() => {
              window.location.reload()
            }, 1500)
          })
        }
        break
      case 403:
        notificationMsg('拒绝访问')
        break
      case 404:
        notificationMsg('很抱歉，资源未找到!')
        break
      case 504:
        notificationMsg('网络超时')
        break
      default:
        notificationMsg(data.message)
        break
    }
  }
  return Promise.reject(error)
}

// request interceptor
service.interceptors.request.use(
  config => {
    const _config = config
    const _type = _config.method === 'get' ? 'params' : 'data'
    // 添加时间戳和应用id
    if (!(_config[_type] instanceof FormData)) {
      _config[_type] = {
        ...handleTrim(_config[_type]),
        _t: new Date().getTime(),
        applicationId: baseConfig.applicationId
      }
    }
    removePendingAjax(_config)
    addPendingAjax(_config)
    return _config
  },
  error => {
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(response => {
  removePendingAjax(response.config)
  if (response.data.success) return response.data
  if (response.data instanceof Blob) return response.data

  // token失效
  if (uselessTokenConfig.uselessTokenCode.includes(response.data.returnCode)) {
    const alertBoxDom = document.getElementsByClassName('el-message-box')
    if (alertBoxDom.length) return Promise.reject(response.data)
    try {
      if (!window.location.href.includes('/login')) {
        MessageBox.alert(
          uselessTokenConfig.uselessTokenAlertMessage,
          uselessTokenConfig.uselessTokenAlertTitle,
          {
            confirmButtonText: '确定',
            callback: () => {
              const maskDom = document.getElementsByClassName(
                'el-message-box__wrapper'
              )
              const popupDom = document.getElementsByClassName(
                'el-popup-parent--hidden'
              )
              alertBoxDom.length && popupDom[0].removeChild(maskDom[0])
              Vue.ls.remove(ACCESS_TOKEN)
              Vue.ss.remove('activeMenuConfig')
              router.replace({ path: '/login' })
            }
          }
        )
      } else {
        notificationMsg(response.data.returnMsg)
      }
      return Promise.reject(response.data)
    } catch (e) {
      window.location.reload()
      return Promise.reject(response.data)
    }
  } else {
    !response.config.noErrorNotification &&
      notificationMsg(response.data.returnMsg)
    return Promise.reject(response.data)
  }
}, err)

export { service as axios }
