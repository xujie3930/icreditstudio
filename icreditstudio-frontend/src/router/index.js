/*
 * @Author: lizheng
 * @Description: 路由入口
 * @Date: 2021-10-14
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
import { constantRouter } from '@/router/constant-route.js'

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouter
})

// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
export default router
