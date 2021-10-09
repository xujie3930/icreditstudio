/*
 * @Author: lizheng
 * @Description: 菜单Icon配置
 * @Date: 2021-08-27
 */

// 一级菜单
export const rootMenuMapping = {
  '/': { icon: 'menu-home', iconActive: 'menu-home-active', name: '首页' },
  '/workspace': {
    icon: 'menu-space',
    iconActive: 'menu-space-active',
    name: '工作空间'
  },
  '/data-manage': {
    icon: 'menu-data',
    iconActive: 'menu-data-active',
    name: '数据管理'
  }
  // '/system': { icon: 'home', name: '系统管理' }
}

// 二级菜单
export const secondMenuMapping = {
  '/home': {
    name: '概述',
    icon: 'menu-summary',
    iconActive: 'menu-summary-active'
  },
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
  '/workspace/data-model': {
    name: '数仓建模',
    icon: 'menu-modeling',
    iconActive: 'menu-modeling-active'
  },
  '/data-manage/data-sync': {
    name: '数据同步',
    icon: 'menu-sync',
    iconActive: 'menu-sync-active'
  },
  '/data-manage/data-develop': {
    name: '数据开发',
    icon: 'menu-develop',
    iconActive: 'menu-develop-active'
  },
  '/data-manage/data-schedule': {
    name: '调度中心',
    icon: 'menu-schedule',
    iconActive: 'menu-schedule-active'
  },
  '/data-manage/data-dictionary': {
    name: '字典表',
    icon: 'menu-dictionary',
    iconActive: 'menu-dictionary-active'
  }
}

// 三级菜单
export const thirdMenuArr = []

// 四级菜单
export const fourthMenuArr = []

export default {
  rootMenuMapping,
  secondMenuMapping,
  thirdMenuArr,
  fourthMenuArr
}
