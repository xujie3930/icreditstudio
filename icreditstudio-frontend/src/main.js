import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import i18n from './plugins/process/i18n'
import iView from 'iview'
import mixin from './plugins/process/service/mixin'
import component from './plugins/process/component'

import '@/config/config.mount.js'

import JUI from '@jnh/j-ui'
import VueEditor from 'vue2-editor'

import '@/utils/vue.ls.js'
// import '@/mock'
import '@/router/permission'
import '@/components/svg/index'

// import '@jnh/j-ui/lib/theme/lib/index.css'; // 默认样式
import '@/styles/common/_iframe.scss' // 自定义主题色

// 公共样式、布局样式
import '@/styles/public/index.scss'
import '@/styles/project.scss'
import 'iview/dist/styles/iview.css'

import '@/directive/auth'

import LayoutMainBasicContainer from 'components/layouts/LayoutMainBasicContainer'
import CrudBasic from '@/components/crud/index'

Vue.use(JUI)
Vue.use(VueEditor)
Vue.use(iView, { i18n: (key, value) => i18n.t(key, value) })
Vue.mixin(mixin)
Vue.component(LayoutMainBasicContainer.name, LayoutMainBasicContainer)
Vue.component(CrudBasic.name, CrudBasic)
Vue.use(component)

Vue.config.productionTip = false

Vue.prototype.$t = (key, value) => i18n.t(key, value)

new Vue({
  router,
  store,
  i18n,
  render: h => h(App)
}).$mount('#app')
