import Vue from 'vue'

const preventReClick = Vue.directive('preventReClick', {
  inserted(el, binding) {
    el.addEventListener('click', () => {
      const _el = el
      if (!el.disabled) {
        _el.disabled = true
        setTimeout(() => {
          _el.disabled = false
        }, binding.value || 3000) // 传入绑定值就使用，默认3000毫秒内不可重复触发
      }
    })
  }
})

export { preventReClick }
