import THEME_CONFIG from '../config/theme' // 引入主题配置文件
import _ from 'lodash'
/**
 * @desc changeFontSize
 * @param fontSize
 * @returns
 */
export function font(val) {
  const getHandler = (variable, id) => {
    return () => {
      const newStyle = updateFont(variable, val)

      let styleTag = document.getElementById(id)
      if (!styleTag) {
        styleTag = document.createElement('style')
        styleTag.setAttribute('id', id)
        document.head.appendChild(styleTag)
      }
      styleTag.innerText = newStyle
    }
  }
  const fontChalk = '' // 用于保存样式字符串 动态插入
  const chalkHandler = getHandler(fontChalk, 'chalk-style-font')
  chalkHandler()

  function updateFont(style, size) {
    let newStyle = style
    // 需要变换字体的元素
    newStyle += `
      .base-label{font-size:${size}px!important}
      .label{font-size:${size}px!important}
      .name{font-size:${size}px!important}
      .el-button{font-size:${size}px!important}
      .el-submenu__title{font-size:${size}px!important}
      .el-submenu__title i{font-size:${size}px!important}
      .el-menu-item{font-size:${size}px}
      .el-submenu [class^=el-icon-]{font-size:${size}px!important}
      .el-table{font-size:${size}px!important}
      .el-radio__label{font-size:${size}px!important}
      `
    return newStyle
  }
}

/**
 * @desc changeThemeColor
 * @param color
 * @returns
 */
const { version } = require('element-ui/package.json')
// element-ui version 用于锁定版本
const ORIGINAL_THEME = '#409EFF' // 默认颜色值
export async function theme(val) {
  if (!val) return
  const colorVal = _.find(THEME_CONFIG, { value: val }).color // 将cssId转为颜色值
  let chalk = '' // 用于保存样式字符串 动态插入
  const oldVal = ORIGINAL_THEME
  if (typeof colorVal !== 'string') return
  const themeCluster = getThemeCluster(colorVal.replace('#', ''))
  const originalCluster = getThemeCluster(oldVal.replace('#', ''))
  const getHandler = (variable, id) => {
    return () => {
      const originCluster = getThemeCluster(ORIGINAL_THEME.replace('#', ''))
      const newStyle = updateStyle(variable, originCluster, themeCluster)

      let styleTag = document.getElementById(id)
      if (!styleTag) {
        styleTag = document.createElement('style')
        styleTag.setAttribute('id', id)
        document.head.appendChild(styleTag)
      }
      styleTag.innerText = newStyle
    }
  }

  if (!chalk) {
    const url = `https://unpkg.com/element-ui@${version}/lib/theme-chalk/index.css`
    await getCSSString(url, chalk)
  }

  const chalkHandler = getHandler(chalk, 'chalk-style')

  chalkHandler()

  /* eslint-disable */
  const styles = [].slice
    .call(document.querySelectorAll('style'))
    .filter(style => {
      const text = style.innerText
      return new RegExp(oldVal, 'i').test(text) && !/Chalk Variables/.test(text)
    })

  styles.forEach(style => {
    const { innerText } = style
    if (typeof innerText !== 'string') return
    style.innerText = updateStyle(innerText, originalCluster, themeCluster)
  })

  function updateStyle(style, oldCluster, newCluster) {
    let newStyle = style
    oldCluster.forEach((color, index) => {
      newStyle = newStyle.replace(new RegExp(color, 'ig'), newCluster[index])
    })
    // 菜单栏样式需要单独处理
    // .iframe-layout-aside{background-color: #${newCluster[0]}!important;}
    // .el-menu{background-color: #${newCluster[0]}!important;}
    // .el-menu-item{background-color: #${newCluster[0]}!important;}
    // .el-menu-item:hover{background-color: ${
    //   newCluster[newCluster.length - 1]
    // }!important;}
    // .el-submenu__title{background-color: #${newCluster[0]}!important;}
    // .el-submenu__title:hover{background-color: ${
    //   newCluster[newCluster.length - 1]
    // }!important;}
    // background-color:${newCluster[newCluster.length - 1]};
    newStyle += `
      .el-pagination {padding-top: 20px}
      .el-pager li.active {border-color:${
        newCluster[newCluster.length - 1]
      };color: #FFFFFF;
    }
      `
    return newStyle
  }

  // 获取ElmentUI默认主题颜色文件的内容
  function getCSSString(url) {
    return new Promise(resolve => {
      const xhr = new XMLHttpRequest()
      xhr.onreadystatechange = () => {
        if (xhr.readyState === 4 && xhr.status === 200) {
          chalk = xhr.responseText.replace(/@font-face{[^}]+}/, '')
          resolve()
        }
      }
      xhr.open('GET', url)
      xhr.send()
    })
  }

  // 根据给定的颜色值获取颜色簇
  function getThemeCluster(theme) {
    const tintColor = (color, tint) => {
      let red = parseInt(color.slice(0, 2), 16)
      let green = parseInt(color.slice(2, 4), 16)
      let blue = parseInt(color.slice(4, 6), 16)

      if (tint === 0) {
        // when primary color is in its rgb space
        return [red, green, blue].join(',')
      } else {
        red += Math.round(tint * (255 - red))
        green += Math.round(tint * (255 - green))
        blue += Math.round(tint * (255 - blue))

        red = red.toString(16)
        green = green.toString(16)
        blue = blue.toString(16)

        return `#${red}${green}${blue}`
      }
    }

    const shadeColor = (color, shade) => {
      let red = parseInt(color.slice(0, 2), 16)
      let green = parseInt(color.slice(2, 4), 16)
      let blue = parseInt(color.slice(4, 6), 16)

      red = Math.round((1 - shade) * red)
      green = Math.round((1 - shade) * green)
      blue = Math.round((1 - shade) * blue)

      red = red.toString(16)
      green = green.toString(16)
      blue = blue.toString(16)

      return `#${red}${green}${blue}`
    }

    const clusters = [theme]
    for (let i = 0; i <= 9; i++) {
      clusters.push(tintColor(theme, Number((i / 10).toFixed(2))))
    }
    clusters.push(shadeColor(theme, 0.1))
    return clusters
  }
}

// 获取当前主题颜色值
export const getSystemTheme = themeId => {
  const theme = THEME_CONFIG.find(item => item.value === themeId)
  return theme.color || 'unset'
}
