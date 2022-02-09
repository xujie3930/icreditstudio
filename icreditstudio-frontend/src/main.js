/*
 * @Author: lizheng
 * @Description:
 * @Date: 2021-10-14
 */
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import '@/config/config.mount.js'

import JUI from '@jnh/icredit-ui'
import VueEditor from 'vue2-editor'

import '@/utils/vue.ls.js'
import '@/utils/vue.ss.js'
// import '@/mock'
import '@/router/permission'
import '@/components/svg/index'

// import '@jnh/icredit-ui/lib/theme/lib/index.css'; // 默认样式
// import 'iview/dist/styles/iview.css'
import '@/styles/common/_icredit.scss' // 自定义主题色

// 公共样式、布局样式
import '@/styles/public/index.scss'
import '@/styles/project.scss'
import 'element-ui/lib/theme-chalk/index.css'

import '@/directive/auth'
import '@/directive/table-scroll'

import LayoutMainBasicContainer from 'components/layouts/LayoutMainBasicContainer'
import CrudBasic from '@/components/crud/index'

Vue.use(JUI)
Vue.use(VueEditor)
Vue.component(LayoutMainBasicContainer.name, LayoutMainBasicContainer)
Vue.component(CrudBasic.name, CrudBasic)

Vue.config.productionTip = false

Vue.prototype.$ELEMENT = { size: 'small' }

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
