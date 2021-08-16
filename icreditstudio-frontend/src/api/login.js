import { postAction } from './index'

const login = params => postAction('/uaa/user/operateLogin', params)
// const login = params => postAction('/uaa/login', params)
const logout = params => postAction('/uaa/user/Session/logout', params)

export {
  login,
  logout
}
