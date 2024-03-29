/* 基础配置 */
const baseConfig = {
  baseUrl: process.env.VUE_APP_BASE_URL || 'http://192.168.0.201:13249', // 开发环境
  // baseUrl: process.env.VUE_APP_BASE_URL || 'http://192.168.110.47:13249', // 明辉本地
  // baseUrl: process.env.VUE_APP_BASE_URL || 'http://192.168.110.226:13249', // 澎湃本地
  // 请求超时时间
  timeout: 20000,
  // 项目名称
  projectName: 'iCredit',
  // 应用id
  applicationId: '641012265471465786',
  // 是否显示控制台文档信息
  illustrate: false
}

/* axios token失效配置 */
const uselessTokenConfig = {
  uselessTokenCode: ['API07', 'UAA23'], // token失效状态码
  uselessTokenMessage: 'Token失效，请重新登录', // 失效后端提示信息 用于判断
  uselessTokenAlertMessage: '很抱歉，登录已过期，请重新登录', // 失效弹窗提示信息
  uselessTokenAlertTitle: '登录已过期' // 失效弹窗提示标题
}

const config = {
  ...baseConfig,
  ...uselessTokenConfig
}
/* sm4 密码配置 */
const sm4Config = {
  // 这里这个key值是跟后端要的
  key: 'JeF8U9wHFOMfs2Y8',
  // 加密的方式有两种，ecb和cbc两种，也是看后端如何定义的，不过要是cbc的话下面还要加一个iv的参数，ecb不用
  mode: 'ecb',
  cipherType: 'base64'
}

module.exports = {
  baseConfig,
  sm4Config,
  uselessTokenConfig,
  config
}
