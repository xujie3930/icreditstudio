import { postAction } from './index'

const login = params => postAction('/system/uaa/user/operateLogin', params)
// const login = params => postAction('/system/uaa/login', params)
const logout = params => postAction('/system/uaa/user/Session/logout', params)

export { login, logout }
