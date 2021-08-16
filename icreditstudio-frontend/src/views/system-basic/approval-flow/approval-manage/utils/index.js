/* eslint-disable */

/**
 * 获取地址栏参数值
 * @param name
 * @returns {null}
 * @constructor
 */
// var config = require('../../config')
export function GetQueryString(name) {
  const reg = new RegExp(`(^|&)${ name }=([^&]*)(&|$)`)
  let r = window.location.search.substr(1).match(reg)
  const s = window.location.search
  const { href } = window.location
  const index = href.indexOf('?')
  if (s === '') {
    r = href.substr(index + 1).match(reg)
  }
  if (r != null) return unescape(r[2])
  return null
}

// 节流throttle代码：
export function throttle(fn) {
  let canRun = true // 通过闭包保存一个标记
  return function () {
    // 在函数开头判断标记是否为true，不为true则return
    if (!canRun) return
    // 立即设置为false
    canRun = false
    // 将外部传入的函数的执行放在setTimeout中
    setTimeout(() => {
      // 最后在setTimeout执行完毕后再把标记设置为true(关键)表示可以执行下一次循环了。
      // 当定时器没有执行的时候标记永远是false，在开头被return掉
      fn.apply(this, arguments)
      canRun = true
    }, 500)
  }
}

// export function GetServerBase() {
//   return GetEvn() ? config.build : config.dev
// }

// export function GetEvn() {
//   return process.env.NODE_ENV === 'production'
// }

/**
 * 日期时间格式化
 * @param {(Object|string|number)} time
 * @param {string} cFormat 格式 （非必填，默认格式：'{y}-{m}-{d} {h}:{i}:{s}'）
 * @returns {string | null}
 */
export function parseTime(time, cFormat) {
  if (!time || arguments.length === 0) {
    return null
  }
  const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
      time = parseInt(time)
    }
    if ((typeof time === 'number') && (time.toString().length === 10)) {
      time *= 1000
    }
    date = new Date(time)
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  const time_str = format.replace(/{([ymdhisa])+}/g, (result, key) => {
    const value = formatObj[key]
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') {
      return ['日', '一', '二', '三', '四', '五', '六'][value]
    }
    return value.toString().padStart(2, '0')
  })
  return time_str
}

/**
 * @param {number} time
 * @param {string} option
 * @returns {string}
 */
export function formatTime(time, option) {
  if ((`${ time}`).length === 10) {
    time = parseInt(time) * 1000
  }
  // debugger
  const d = new Date(time)
  const now = Date.now()

  const diff = (now - d) / 1000

  if (diff < 30) {
    return '刚刚'
  } else if (diff < 3600) {
    // less 1 hour
    return `${Math.ceil(diff / 60) }分钟前`
  } else if (diff < 3600 * 24) {
    return `${Math.ceil(diff / 3600) }小时前`
  } else if (diff < 3600 * 24 * 2) {
    return '1天前'
  }
  if (option) {
    return parseTime(time, option)
  } else {
    return (
      `${d.getMonth() +
      1
      }月${
      d.getDate()
      }日${
      d.getHours()
      }时${
      d.getMinutes()
      }分`
    )
  }
}

// 级联数据处理
export function getFathersById(id, data, prop = 'id', children = 'children') {
  const arrRes = []
  const rev = (data, nodeId) => {
    for (let i = 0, { length } = data; i < length; i++) {
      const node = data[i]
      if (node[prop] === nodeId) {
        arrRes.unshift(node[prop])
        return true
      } else if (node[children] && node[children].length) {
        if (rev(node[children], nodeId)) {
          arrRes.unshift(node[prop])
          return true
        }
      }
    }
    return false
  }
  rev(data, id)
  return arrRes
}

// 生成 uuid
export function uuidFun() {
  const s = []
  const hexDigits = '0123456789abcdef'
  for (let i = 0; i < 36; i++) {
    s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1)
  }
  s[14] = '4' // bits 12-15 of the time_hi_and_version field to 0010
  s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1) // bits 6-7 of the clock_seq_hi_and_reserved to 01
  s[8] = s[13] = s[18] = s[23] = '-'

  const uuid = s.join('')
  return uuid
}

export function addApproval(target) {
  const arrayToString = function (data, isCascader) {
    if (typeof data === 'string') {
      return data
    }
    if (data instanceof Array && data.length) {
      const processVariables = []
      data.forEach((k, index) => {
        if (typeof k === 'string') {
          // 判断是否级联
          if (isCascader) {
            if (index === data.length - 1) {
              processVariables.push(k)
            }
          } else {
            processVariables.push(k)
          }
        } else {
          processVariables.push(k[k.length - 1])
        }
      })
      return processVariables.join(',')
    }
  }
  if (!(target instanceof Array)) {
    return target
  }
  target.forEach(i => {
    if (i.processVariables.formJson) {
      const formJson = JSON.parse(i.processVariables.formJson)
      i.approvalList = []
      if (formJson.list.length) {
        formJson.list.forEach(j => {
          if (j.options.approval) {
            i.approvalList.push({
              label: j.name,
              value: arrayToString(i.processVariables[j.model], j.type === 'cascader'),
              model: j.model
            })
          }
          if (j.type === 'childForm') {
            j.tableColumns.forEach(k => {
              if (k.options.approval) {
                i.approvalList.push({
                  label: k.name,
                  value: i.processVariables[j.model][0][k.model],
                  model: k.model
                })
              }
            })
          }
          if (j.type === 'grid') {
            j.columns.forEach(k => {
              k.list.forEach(z => {
                i.approvalList.push({
                  label: z.name,
                  value: arrayToString(i.processVariables[z.model]),
                  model: z.model
                })
              })
            })
          }
        })
      }
    }
  })
  return target
}

export function debounce(func, wait = 800) {
  let timeout
  return function (event) {
    clearTimeout(timeout)
    timeout = setTimeout(() => {
      func.call(this, event)
    }, wait)
  }
}
