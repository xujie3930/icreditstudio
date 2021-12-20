import Vue from 'vue'
import Storage from 'vue-ls'

Vue.use(Storage, {
  namespace: 'jnh__', // key prefix
  name: 'ss', // name variable Vue.[ls] or this.[$ls],
  storage: 'session' // storage name session, local, memory
})
