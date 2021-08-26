import { getAction, postAction } from './index'

// const queryPermissionsByUser = params => postAction('/system/res/user/resource/treeify', params)
const queryPermissionsByUser = params =>
  postAction('/system/resources/initAuth', params)
const queryAllRoles = params =>
  postAction('/system/role/role/queryList', params)
const queryUserRoles = params => postAction('/system/sys/get/userRoles', params)

const setUserStatus = params =>
  postAction('/system/user/user/changeUserStatusByUserId', params)

const queryUserList = params => postAction('/system/res/user/page', params)
const queryAllOrgs = params =>
  postAction('/system/org/organization/queryList', params)
// 用户管理 导出空模板
const modelExport = params =>
  getAction('/system/user/user/exportModel', params, { responseType: 'blob' })

// 根据部门ID集合，返回各部门用户列表
const getUserInfosByOrgIds = params =>
  postAction('/system/user/user/getUserInfosByOrgIds', params)
// 给用户设置多个角色
const setUserToRoles = params =>
  postAction('/system/user/user/setUserToRoles', params)
// 修改用户信息
const editBaseUserInfo = params =>
  postAction('/system/user/user/editBase', params)
// 上传用户头像
const uploadPhoto = params =>
  postAction('/system/user/user/uploadPhoto', params, {
    headers: { 'Content-Type': 'application/json;charset=UTF-8' }
  })
// 修改密码
const editAccountPwd = params =>
  postAction('/system/user/useraccount/changePassword', params)
// 重置密码
const resetPassword = params =>
  postAction('/system/user/useraccount/resetPassword', params)
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
