import { getAction, postAction } from './index'

// const queryPermissionsByUser = params => postAction('/res/user/resource/treeify', params)
const queryPermissionsByUser = params => postAction('/resources/initAuth', params)
const queryAllRoles = params => postAction('/role/role/queryList', params)
const queryUserRoles = params => postAction('/sys/get/userRoles', params)

const setUserStatus = params => postAction('/user/user/changeUserStatusByUserId', params)

const queryUserList = params => postAction('/res/user/page', params)
const queryAllOrgs = params => postAction('/org/organization/queryList', params)
// 用户管理 导出空模板
const modelExport = params => getAction('/user/user/exportModel', params, { responseType: 'blob' })

// 根据部门ID集合，返回各部门用户列表
const getUserInfosByOrgIds = params => postAction('/user/user/getUserInfosByOrgIds', params)
// 给用户设置多个角色
const setUserToRoles = params => postAction('/user/user/setUserToRoles', params)
// 修改用户信息
const editBaseUserInfo = params => postAction('/user/user/editBase', params)
// 上传用户头像
const uploadPhoto = params => postAction('/user/user/uploadPhoto', params, { headers: { 'Content-Type': 'application/json;charset=UTF-8' } })
// 修改密码
const editAccountPwd = params => postAction('/user/useraccount/changePassword', params)
// 重置密码
const resetPassword = params => postAction('/user/useraccount/resetPassword', params)
export {
  queryPermissionsByUser,
  queryUserRoles,
  queryAllRoles,
  queryUserList,
  queryAllOrgs,
  setUserStatus,
  modelExport,
  getUserInfosByOrgIds,
  setUserToRoles,
  editBaseUserInfo,
  uploadPhoto,
  editAccountPwd,
  resetPassword
}
