/*
 * @Description: 表格无限滚动
 * @Date: 2021-10-26
 */

import Vue from 'vue'

Vue.directive('loadmore', {
  bind(el, binding) {
    let p = 0
    let t = 0
    let down = true

    const selectWrap = el.querySelector('.el-table__body-wrapper')
    console.log(
      selectWrap,
      selectWrap.style,
      selectWrap.clientWidth,
      selectWrap.scrollHeight,
      'selectWrap'
    )
    const { scrollHeight, scrollTop, clientHeight } = selectWrap

    selectWrap.addEventListener('scroll', () => {
      // 判断是否向下滚动
      p = scrollTop
      down = t < p
      t = p // 判断是否到底
      const sign = 10
      console.log('dsdf=', scrollHeight, scrollTop, clientHeight)
      const scrollDistance = scrollHeight - scrollTop - clientHeight
      console.log('scrollDistance', scrollDistance)
      if (scrollDistance <= sign && down) {
        binding.value()
      }
    })
  }
})
