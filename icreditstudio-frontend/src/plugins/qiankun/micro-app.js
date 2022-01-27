/*
 * @Author: lizheng
 * @Description: 子应用入口参数配置
 * @Date: 2022-01-10
 */

import shared from './shared'

export default [
  {
    name: 'icreditstudio-datasource',
    entry: 'http://192.168.0.201:3333/',
    // entry: '//localhost:3333',
    activeRule: '/subapp/datasource/',
    container: '#subapp-container',
    props: shared
  },
  {
    name: 'icreditstudio-dictionary',
    // entry: 'http://192.168.0.201:3333/',
    entry: '//localhost:4444',
    activeRule: '/subapp/dictionary/',
    container: '#subapp-container',
    props: shared
  }
]
