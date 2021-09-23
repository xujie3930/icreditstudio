/*
 * @Author: lizheng
 * @Description: 菜单Icon配置
 * @Date: 2021-08-27
 */

// 一级菜单
const rootMenuArr = {
  '/': { icon: 'home', name: '首页' },
  '/a': { icon: 'home', name: '工作空间' },
  '/b': { icon: 'home', name: '数据管理' },
  '/c': { icon: 'home', name: '系统管理' }
}

// 二级菜单
export const secondMenuMapping = {
  '/workspace/space-setting': {
    name: '空间设置',
    icon: 'menu-workspace',
    iconActive: 'menu-workspace-active'
  },
  '/workspace/datasource': {
    name: '数据源管理',
    icon: 'menu-datasource',
    iconActive: 'menu-datasource-active'
  },
  '/data-manage/data-sync': {
    name: '数据同步',
    icon: 'menu-sync',
    iconActive: 'menu-sync-active'
  }
}

// 三级菜单
const thirdMenuArr = []

// 四级菜单
const fourthMenuArr = []

export default {
  rootMenuArr,
  secondMenuMapping,
  thirdMenuArr,
  fourthMenuArr
}
