/*
 * @Author: lizheng
 * @Description: 校验
 * @Date: 2021-09-02
 */

// const pattern = new RegExp('[\u4E00-\u9FA5]+')

// //验证是否是英文
// const pattern2 = new RegExp('[A-Za-z]+')

// //验证是否是数字
// const pattern3 = new RegExp('[0-9]+')

// 验证是否是中文
export const validStrZh = str => {
  const pattern = /[^\u4E00-\u9FA5]/g
  return pattern.test(str)
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
