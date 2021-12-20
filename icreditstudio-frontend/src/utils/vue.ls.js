import Vue from 'vue'
import Storage from 'vue-ls'
import { cloneDeep } from 'lodash'

// 使用localeStorage
Vue.use(cloneDeep(Storage), {
  namespace: 'jnh__', // key prefix
  name: 'ls', // name variable Vue.[ls] or this.[$ls],
  storage: 'local' // storage name session, local, memory
})
