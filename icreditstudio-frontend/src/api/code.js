import { postAction } from './index'

const setCodeStatus = params => postAction('/code/code/status', params)

export {
  setCodeStatus
}
