/*
 * @Author: lizheng
 * @Description: 校验
 * @Date: 2021-09-02
 */

// var reg = new RegExp("^[a-zA-Z\d\u4E00-\u9FA5]+$"

// 剔除字符串中所有空格
export const strExcludeBlank = str => {
  return str.replace(/\s*/g, '')
}

// 验证字符串是否只输入英文
export const validStrEn = str => {
  const reg = /(^[a-zA-Z]+$)/
  return reg.test(str)
}

// 验证是否包含中文
export const validStrZh = str => {
  return encodeURI(str).indexOf('%u') < 0
}

// 表单校验只能输入中文
export const verifyStrzh = () => {}

export const isChina = str => {
  const regExp = /[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi
  // /.*[\u4e00-\u9fa5]+.*$/
  return regExp.exec(str)
}

// 验证特殊字符
export const validStrSpecial = str => {
  // 特殊符号
  const regStr = /[`~!@#$%^&*()_\-+=<>?:"{}|,./;'\\[\]·~！@#￥%……&*（）——\-+={}|《》？：“”【】、；‘’，。、]/gi
  // 表情包
  const emojiRegStr = /[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f\u2026\u2022\u20ac\r\n]/gi
  const isValid = regStr.test(str) || emojiRegStr.test(str)
  return isValid
}

// 验证IP地址是否合法
export const validIpAddress = ip => {
  const regStr = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
  return regStr.test(ip)
}

// 特殊字符表单校验
export const verifySpecialStr = (rule, value, cb) => {
  if (validStrSpecial(value)) {
    cb(new Error('该名称中包含不规范字符，请重新输入'))
  } else {
    cb()
  }
}

/**
 * 是否是数字
 * @param {any} value
 * @return {Boolean}
 */
export function isNumber(value) {
  return typeof value === 'number'
}

/**
 * 是否是空的
 * @param {any} value
 * @return {Boolean}
 */
export function isNull(value) {
  return value === undefined || value === null || value === ''
}
