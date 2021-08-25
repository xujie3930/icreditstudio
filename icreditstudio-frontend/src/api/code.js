import { postAction } from './index'

const setCodeStatus = params => postAction('/system/code/code/status', params)

export { setCodeStatus }
