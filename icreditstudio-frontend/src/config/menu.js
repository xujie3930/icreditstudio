/*
 * @Author: lizheng
 * @Description: 菜单Icon配置
 * @Date: 2021-08-27
 */

// 需要返回的页面
export const canBackPages = [
  '/workspace/detail',
  '/workspace/data-model/add',
  '/subapp/datasync/data-manage/sync-task',
  '/data-manage/add-task',
  '/data-manage/add-build',
  '/data-manage/add-transfer',
  '/data-manage/data-schedule/dag',
  '/data-quality/rule-category/add-rules',
  '/manage/userinfo',
  '/manage/personalized',
  '/manage/changepassword'
]

// 一级菜单
export const rootMenuMapping = {
  '/all': { icon: 'menu-product-white', name: '全部产品' },
  '/index': { icon: 'menu-home', iconActive: 'menu-home-active', name: '首页' },
  '/workspace': { icon: 'menu-space', name: '工作空间' },
  '/data-manage': { icon: 'menu-data', name: '数据管理' },
  '/data-quality': { icon: 'menu-govern-white', name: '数据治理' }
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
  '/subapp/datasource/list': {
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
  '/subapp/datasync/list': {
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
  },
  '/subapp/dictionary/list': {
    name: '字典表',
    icon: 'menu-dictionary',
    iconActive: 'menu-dictionary-active'
  },
  '/data-quality/summary': {
    name: '概览',
    icon: 'menu-summary',
    iconActive: 'menu-summary-active'
  },
  '/data-quality/rule-category': {
    name: '规则分类',
    icon: 'menu-rule',
    iconActive: 'menu-rule-active'
  },
  '/data-quality/quality-testing': {
    name: '质量检测',
    icon: 'menu-quality-test',
    iconActive: 'menu-quality-test-active'
  },
  '/data-quality/quality-result': {
    name: '质量结果',
    icon: 'menu-quality-result',
    iconActive: 'menu-quality-result-active'
  }
}

// 全部产品
export const ALL_PRODUCT_NAME = '全部产品'
export const allMenuNavMapping = [
  {
    label: '工作空间',
    children: [
      { icon: 'menu-summary-white', label: '概览', path: '' },
      {
        icon: 'menu-workspace-white',
        label: '空间设置',
        path: '/workspace/space-setting'
      },
      {
        icon: 'menu-datasource-white',
        label: '数据源管理',
        path: '/workspace/datasource'
      },
      {
        icon: 'menu-modeling-white',
        label: '数仓建模',
        path: '/workspace/data-model'
      },
      { icon: 'menu-member-white', label: '成员管理', path: '' },
      { icon: 'menu-auth-white', label: '权限管理', path: '' },
      { icon: 'menu-cluster-white', label: '集群管理', path: '' },
      { icon: 'menu-node-white', label: '节点管理', path: '' }
    ]
  },
  {
    label: '数据管理',
    children: [
      { icon: 'menu-summary-white', label: '概览', path: '' },
      {
        icon: 'menu-sync-white',
        label: '数据同步',
        path: '/data-manage/data-sync'
      },
      {
        icon: 'menu-develop-white',
        label: '数据开发',
        path: '/data-manage/data-develop'
      },
      {
        icon: 'menu-schedule-white',
        label: '调度管理',
        path: '/data-manage/data-schedule/index'
      },
      {
        icon: 'menu-dictionary-white',
        label: '字典表',
        path: '/data-manage/data-dictionary'
      }
    ]
  },
  {
    label: '数据治理',
    children: [
      { icon: 'menu-summary-white', label: '概览', children: [], path: '' },
      { icon: 'menu-rule-white', label: '规则分类', children: [], path: '' },
      { icon: 'menu-test-white', label: '质量检测', children: [], path: '' },
      { icon: 'menu-quality-white', label: '质检结果', children: [], path: '' }
    ]
  }
]

export default {
  canBackPages,
  rootMenuMapping,
  secondMenuMapping,
  allMenuNavMapping
}
