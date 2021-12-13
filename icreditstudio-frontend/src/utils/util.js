import LayoutMain from '@/components/layouts/LayoutMain'
import { sm4Config } from '@/config/index'

/**
 * @desc deepClone
 * @param data
 * @returns {{}|*}
 */
export function deepClone(data) {
  const type = getObjType(data)
  let obj
  if (type === 'array') {
    obj = []
  } else if (type === 'object') {
    obj = {}
  } else {
    // 不再具有下一层次
    return data
  }
  if (type === 'array') {
    for (let i = 0, len = data.length; i < len; i++) {
      obj.push(deepClone(data[i]))
    }
  } else if (type === 'object') {
    for (const key in data) {
      if (Object.prototype.hasOwnProperty.call(data, key)) {
        obj[key] = deepClone(data[key])
      }
    }
  }
  return obj
}

export function getBooleanByType(type) {
  if (typeof type === 'boolean') return type
  if (!['Y', 'N'].includes(type)) return new Error(`${type} is not Y or N`)
  return type === 'Y'
}

/**
 * @desc 获取数据类型
 * @param {*} obj
 * @returns {string|*}
 */
export function getObjType(obj) {
  const { toString } = Object.prototype
  const map = {
    '[object Boolean]': 'boolean',
    '[object Number]': 'number',
    '[object String]': 'string',
    '[object Function]': 'function',
    '[object Array]': 'array',
    '[object Date]': 'date',
    '[object RegExp]': 'regExp',
    '[object Undefined]': 'undefined',
    '[object Null]': 'null',
    '[object Object]': 'object'
  }
  if (obj instanceof Element) {
    return 'element'
  }
  return map[toString.call(obj)]
}

/**
 * @description 判读是否为外链
 * @param path
 * @returns {boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

// 生成首页路由
export function generateIndexRouter(data) {
  // TODO 路由处理不合理 待修改
  /* IFrame 1.1 添加模块功能导致这里需要过滤模块 ============= start */
  // const _data = data.reduce((res, e) => res.concat(e).concat(e.children || []), [])
  const _data = data.reduce((res, e) => {
    res.push(e)
    if (e.children) {
      res.push(...e.children)
    }
    return res
  }, [])
  /* IFrame 1.1 添加模块功能导致这里需要过滤模块 ============= end */
  const generateChildData = generateChildRouters(_data)
  // const generateChildData = generateChildRouters([...data, ..._data])
  // 将回来的路由数据处理成平铺数组结构，统一放到layoutMain下的children作为其子路由
  const tilePathData = treeToArray(generateChildData).filter(x => !!x.path)
  // 必要步骤：删除多余的children属性，否则vue-router会认为其是父级路由，会继续往下去匹配其子路由而不是本身
  for (const index in tilePathData) {
    if (tilePathData[index].children) {
      delete tilePathData[index].children
    }
  }
  return [
    // {
    //   path: '/',
    //   name: 'defaultHome',
    //   component: resolve => require(['@/components/layouts/LayoutMain'], resolve),
    //   meta: { title: '测试' },
    //   redirect: data[0].path,
    //   children: [
    //     ...generateChildRouters(data)
    //   ]
    // },
    // ...generateChildRouters(_data),
    {
      path: '/',
      name: 'layoutMain',
      hidden: true,
      component: LayoutMain,
      children: [...tilePathData]
    },
    {
      path: '*',
      redirect: '/404',
      hidden: true
    }
  ]
}

// 生成嵌套路由（子路由）

function generateChildRouters(data) {
  const routers = []
  for (const item of data) {
    let component = ''
    component = `views/${item.filePath}`
    // eslint-disable-next-line
    // let URL = (item.meta.url || '').replace(/{{([^}}]+)?}}/g, (s1, s2) => eval(s2)) // URL支持{{ window.xxx }}占位符变量
    // if (isURL(URL)) {
    //   item.meta.url = URL
    // }
    // const componentPath = resolve => require([`@/${component}.vue`], resolve)
    const menu = {
      path: item.url,
      name: item.name,
      redirect: item.redirectPath,
      // component: componentPath,
      hidden: !getBooleanByType(item.isShow),
      src: item.src,
      component: () => import(`@/${component}.vue`),
      meta: {
        title: item.title,
        name: item.name,
        icon: item.iconPath,
        hidden: !getBooleanByType(item.isShow),
        url: item.url,
        permissionList: item.permissionList,
        keepAlive: !getBooleanByType(item.keepAlive),
        internalOrExternal: item.internalOrExternal
      }
    }
    if (item.alwaysShow) {
      menu.alwaysShow = true
      menu.redirect = menu.redirectPath
    }
    if (item.children && item.children.length > 0) {
      menu.children = [...generateChildRouters(item.children)]
    }
    // 根据后台菜单配置，判断是否路由菜单字段，动态选择是否生成路由（为了支持参数URL菜单）
    // 判断是否生成路由
    if (item.route && item.route === '0') {
      // console.log(' 不生成路由 item.route：  '+item.route);
      // console.log(' 不生成路由 item.path：  '+item.path);
    } else {
      routers.push(menu)
    }
  }
  return routers
}

/**
 * Check if an element has a class
 * @param {HTMLElement} ele
 * @param {string} cls
 * @returns {boolean}
 */
export function hasClass(ele, cls) {
  return !!ele.className.match(new RegExp(`(\\s|^)${cls}(\\s|$)`))
}

/**
 * Add class to element
 * @param {HTMLElement} ele
 * @param {string} cls
 */
export function addClass(ele, cls) {
  // eslint-disable-next-line no-param-reassign
  if (!hasClass(ele, cls)) ele.className += ` ${cls}`
}

/**
 * Remove class from element
 * @param {HTMLElement} ele
 * @param {string} cls
 */
export function removeClass(ele, cls) {
  if (hasClass(ele, cls)) {
    const reg = new RegExp(`(\\s|^)${cls}(\\s|$)`)
    // eslint-disable-next-line no-param-reassign
    ele.className = ele.className.replace(reg, ' ')
  }
}

/**
 * 首字母大写
 * @param str
 * @return {string}
 */
export function upperCaseFirst(str) {
  return str.charAt(0).toUpperCase() + str.slice(1)
}

/**
 * 一维数组转树结构
 * @param arr {[]} 数据源
 * @param rootVal {null | Number | String} 根节点id
 * @param pidKey {String} 父节点id的key
 * @return {[]}
 */
export function arrayToTree(arr, rootVal, pidKey = 'parentId') {
  const tree = []
  let temp
  for (let i = 0; i < arr.length; i++) {
    if (arr[i][pidKey] === rootVal) {
      const obj = arr[i]
      temp = arrayToTree(arr, arr[i].id)
      if (temp.length > 0) {
        obj.children = temp
      }
      tree.push(obj)
    }
  }
  return tree
}

/**
 * @desc 树结构转平铺数组
 * @param tree {array} 数据源
 * @param field {string} 子节点key,默认'children'
 * @param filterKey {string} 不传时将整条装入，传入key时仅把item[key]值装入数组
 */
export function treeToArray(tree, field = 'children', filterKey = null) {
  const arr = []
  const expanded = datas => {
    if (datas && datas.length > 0) {
      datas.forEach(e => {
        arr.push(filterKey ? e[filterKey] : e)
        expanded(e[field])
      })
    }
  }
  expanded(tree)
  return arr
}

/**
 * 防抖
 * @param {Function} fn 要执行函数
 * @param {Number} delay 延迟
 * @return {function(): void}
 */
export const debounce = (fn, delay = 800) => {
  let timer = null
  // eslint-disable-next-line space-before-function-paren
  return function(...arg) {
    if (timer) clearTimeout(timer)
    timer = setTimeout(() => {
      fn.apply(this, arg)
    }, delay)
  }
}

// 参数去空格 - source为空字符串或对象
export const handleTrim = source => {
  const obj = {}
  const type = getObjType(source)
  if (type === 'string') return source.trim()
  if (type === 'object') {
    for (const [key, value] of Object.entries(source)) {
      obj[key] = getObjType(value) === 'string' ? value.trim() : value
    }
    return obj
  }
}

/**
 * base64字符串过滤展示
 */
export const base64UrlFilter = url => {
  let result = ''
  if (url.startsWith('blob:') || /^[\s\S]*\/img\//.test(url)) {
    // blob格式||本地文件，不处理
    result = url
  } else if (url.startsWith('dataimage/')) {
    // base64全路径储存的格式
    const curType = url.split('dataimage/')[1].split('base64')[0]
    const before = `dataimage/${curType}base64`
    const after = `data:image/${curType};base64,`
    result = url.replace(new RegExp(before, 'g'), after)
  } else {
    // base64截取base64后路径储存的格式,统一转成png格式展示
    result = url.startsWith('data:image/')
      ? url
      : `data:image/png;base64,${url}`
  }
  return result
}

// 数据库连接URI切割
export const uriSplit = (uri, dataSource) => {
  let ds = {}
  const { type } = dataSource

  switch (type) {
    case 1:
      ds = mysqlUriSplit(uri, dataSource)
      break
    case 2:
      ds = oracleUriSplit(uri, dataSource)
      break
    default:
      ds = dataSource
      break
  }

  return ds
}

// mySql数据库连接URI切割
export const mysqlUriSplit = (uri, dataSource) => {
  const paramsObj = {}
  const [beforeStr, afterStr] = uri.split('?')

  // 处理查询参数
  const newAfterStr = afterStr.replaceAll('|', '&')
  newAfterStr.split('&').forEach(item => {
    if (item.includes('password=')) {
      paramsObj.password = decrypt(item.split('password=')[1])
    } else {
      const [key, val] = item.split('=')
      paramsObj[key] = val
    }
  })

  // 处理uri类型以及IP以及端口号
  const [databaseType, ipPort, databaseName] = beforeStr
    .split('/')
    .filter(item => item && item.trim())

  const [ip, port] = ipPort.split(':')
  const newDataSource = {
    databaseType,
    ip,
    port,
    databaseName,
    ...dataSource,
    ...paramsObj
  }

  return newDataSource
}

// oracle数据库连接URI切割
export const oracleUriSplit = (uri, dataSource) => {
  const paramsObj = {}
  const [baseStr, nameStr, pwdStr] = uri.split('|')
  const [, databaseType, , ip, port, databaseName] = baseStr.split(':')
  const [username, name] = nameStr.split('=')
  const [, pwd] = pwdStr.split('password=')

  paramsObj[username] = name
  paramsObj.password = decrypt(pwd)

  return {
    ...dataSource,
    ...paramsObj,
    port,
    databaseType,
    databaseName,
    ip: ip.split('@')[1]
  }
}

// 生成从minNum到maxNum的随机数
// eslint-disable-next-line space-before-function-paren
export const randomNum = function(minNum, maxNum) {
  switch (arguments.length) {
    case 1:
      return parseInt(Math.random() * minNum + 1, 10)
    case 2:
      return parseInt(Math.random() * (maxNum - minNum + 1) + minNum, 10)
    default:
      return 0
  }
}

// 数组去重
export const unique = arr => {
  const obj = {}
  return arr.filter(item => {
    // eslint-disable-next-line no-prototype-builtins
    const flag = obj.hasOwnProperty(typeof item + item)
      ? false
      : (obj[typeof item + item] = true)

    return flag
  })
}

const SM4 = require('gm-crypt').sm4
// 解密
export const encrypt = str => {
  const sm4 = new SM4(sm4Config)
  return sm4.encrypt(str)
}
// 解密
export const decrypt = str => {
  const sm4 = new SM4(sm4Config)
  const plianStr = str ? sm4.decrypt(str) : null
  return plianStr
}
