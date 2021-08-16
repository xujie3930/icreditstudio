import config from './index'

(function () {
  // 挂在到window上
  window && (window.__JConfig = config)
})()
