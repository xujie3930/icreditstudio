import Vue from 'vue'

Vue.directive('input-limit', {
  bind(el, binding) {
    const _bindingVal = Number(binding.value)
    const wins_0 = /[^\d]/g // 整数判断
    const wins_1 = /[^\d^.]/g // 小数判断
    let flag = true
    let points = 0
    let lengths = 0
    let remainder = 0
    let no_int = 0
    const target = el instanceof HTMLInputElement ? el : el.querySelector('input')
    target.addEventListener('compositionstart', () => {
      flag = false
    })
    target.addEventListener('compositionend', () => {
      flag = true
    })
    target.addEventListener('input', e => {
      setTimeout(() => {
        if (flag) {
          if (_bindingVal === 0) {
            if (wins_0.test(e.target.value)) {
              // eslint-disable-next-line no-param-reassign
              e.target.value = e.target.value.replace(wins_0, '')
              e.target.dispatchEvent(new Event('input')) // 手动更新v-model值
            }
          }
          if (_bindingVal === 1) {
            if (wins_0.test(e.target.value.toString().replace(/\d+\.(\d*)/, '$1'))) {
              remainder = true
            }
            if ((e.target.value.split('.')).length - 1 > 1) {
              points = true
            }
            if (e.target.value.toString().split('.')[1] !== undefined) {
              if (e.target.value.toString().split('.')[1].length > 1) {
                lengths = true
              }
            }
            no_int = e.target.value.toString().indexOf('.') === -1;
            if (wins_1.test(e.target.value) || lengths || points || remainder) {
              if (!no_int) {
                // eslint-disable-next-line no-param-reassign
                e.target.value = e.target.value
                  .replace(wins_1, '')
                  .replace('.', '$#$')
                  .replace(/\./g, '')
                  .replace('$#$', '.')
                  .replace(/^(-)*(\d+)\.(\d\d).*$/, '$1$2.$3')
                  .substring(0, e.target.value.indexOf('.') + 2)
              } else {
                // eslint-disable-next-line no-param-reassign
                e.target.value = e.target.value.replace(wins_0, '')
              }
              e.target.dispatchEvent(new Event('input'))
            }
          }

          if (_bindingVal === 2) {
            if (wins_0.test(e.target.value.toString().replace(/\d+\.(\d*)/, '$1'))) {
              remainder = true
            }
            if ((e.target.value.split('.')).length - 1 > 1) {
              points = true
            }
            if (e.target.value.toString().split('.')[1] !== undefined) {
              if (e.target.value.toString().split('.')[1].length > 2) {
                lengths = true
              }
            }
            no_int = e.target.value.toString().indexOf('.') === -1;
            if (wins_1.test(e.target.value) || lengths || points || remainder) {
              if (!no_int) {
                // eslint-disable-next-line no-param-reassign
                e.target.value = e.target.value
                  .replace(wins_1, '')
                  .replace('.', '$#$')
                  .replace(/\./g, '')
                  .replace('$#$', '.')
                  .replace(/^(-)*(\d+)\.(\d\d).*$/, '$1$2.$3')
                  .substring(0, e.target.value.indexOf('.') + 3)
              } else {
                // eslint-disable-next-line no-param-reassign
                e.target.value = e.target.value.replace(wins_0, '')
              }
              e.target.dispatchEvent(new Event('input'))
            }
          }
        }
      }, 0)
    })
  }
})
