import axios from 'axios'

// 存储等待中的请求 用于取消之前的请求
const pendingAjax = new Map()

const duplicatedKeyFn = config => `${config.method}${config.url}`

/**
 * add request to pendingAjax
 * @param {Object} config
 */
export const addPendingAjax = config => {
  const duplicatedKey = JSON.stringify({
    duplicatedKey: config.duplicatedKey || duplicatedKeyFn(config)
  });
  // eslint-disable-next-line no-param-reassign
  config.cancelToken = config.cancelToken || new axios.CancelToken(cancel => {
    if (duplicatedKey && !pendingAjax.has(duplicatedKey)) {
      pendingAjax.set(duplicatedKey, cancel);
    }
  });
}

/**
 * remove the request in pendingAjax
 * @param {Object} config
 */
export const removePendingAjax = config => {
  const duplicatedKey = JSON.stringify({
    duplicatedKey: config.duplicatedKey || duplicatedKeyFn(config)
  });
  if (duplicatedKey && pendingAjax.has(duplicatedKey)) {
    const cancel = pendingAjax.get(duplicatedKey);
    cancel(duplicatedKey);
    pendingAjax.delete(duplicatedKey);
  }
}
