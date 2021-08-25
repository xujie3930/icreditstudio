import { postAction } from './index'

const setRoleStatus = params => postAction('/system/role/role/status', params)
const setRoleAuth = params =>
  postAction('/system/role/role/resource/set', params)
const getRoleAuth = params => postAction('/system/role/role/resource', params)
const getUserInfoByRoleId = params =>
  postAction('/system/role/role/getUserInfoByRoleId', params)
// 根据父角色id，查询子角色
const getChildrenRoles = params =>
  postAction('/system/role/role/getChildrenRoles', params)
// 给批量用户设置单个权限
const setRoleToUsers = params =>
  postAction('/system/role/role/setRoleToUsers', params)
// 根据用户Id，返回角色信息
const getRoleInfoByUserId = params =>
  postAction('/system/role/role/getRoleInfoByUserId', params)
// 查询当前角色及子角色下的权限
const getResourcesFromRole = params =>
  postAction('/system/role/role/getResourcesFromRole', params)
// 给角色配置权限
const setResourcesToRole = params =>
  postAction('/system/role/role/setResourcesToRole', params)

export {
  setRoleStatus,
  setRoleAuth,
  getRoleAuth,
  getUserInfoByRoleId,
  getChildrenRoles,
  setRoleToUsers,
  getRoleInfoByUserId,
  getResourcesFromRole,
  setResourcesToRole
}
