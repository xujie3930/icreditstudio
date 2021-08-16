import Vue from 'vue'
import JSvg from 'components/svg/src'

Vue.component(JSvg.name, JSvg)

// 获取icons下所有svg文件
const requireAll = requireContext => requireContext.keys().map(requireContext)
const req = require.context('@/assets/icons', false, /\.svg$/)
requireAll(req)
