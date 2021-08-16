import Vue from 'vue'
import store from '@/store'

/**
 * auth指令 v-auth="Array or String"
 * 传入的权限码可以是数组或者是字符串
 * 此外还有两个修饰符 some 和 every
 * v-auth.some="Array" 表示满足其中一个资源即可（不设置修饰符情况下默认为some）
 * v-auth.every= "Array" 表示列表的所资源必须存在
 * 调用实例：
 *  <span v-auth.some="['admin1', 'admin2']"></span>
 *  <span v-auth.every="['admin1', 'admin2']"></span>
 *  <span v-auth="'admin1'"></span>
 */

// 删除节点dom
const remove = el => el.parentNode.removeChild(el)

Vue.directive('auth', {
  inserted: (el, binding) => {
    const access = store.getters['user/auth'] || []
    // 获取传入的权限码value（string or array）和修饰符modifiers
    const { value, modifiers } = binding
    let _currentAccess = []
    // 判断条件：当传入的值不是数组或者字符串时，直接隐藏元素
    if (
      !(
        typeof value === 'string' ||
        value instanceof Array
      ) ||
      !value
    ) {
      remove(el)
      return console.error('please set the value to a string or array.')
    }

    // 判断条件：如果传入的权限码是string则转化成数组
    if (typeof value === 'string') {
      _currentAccess.push(value)
    } else {
      _currentAccess = value
    }
    /**
     * 判断条件
     *  -修饰符为 every时 value数组只要有一个元素不存在access权限集内，隐藏元素
     *  -修饰符为 some或者没有时，value数组所有元素都不存在access权限集内，隐藏元素
     */
    if (
      (
        modifiers.every &&
        _currentAccess.some(v => !access.includes(v))
      ) ||
      (
        !modifiers.every &&
        _currentAccess.every(v => !access.includes(v))
      )
    ) {
      remove(el)
    }
  }
})
