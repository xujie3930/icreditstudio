import { postAction } from './index'

const getAuthList = params =>
  postAction('/system/resources/resources/queryList', params)
// 设置模块的角色信息
const setRoleToResources = params =>
  postAction('/system/resources/resources/setRoleToResources', params)
// 根据模板id获取当前模板已配置的角色信息
const getRoleInfoByMenuIds = params =>
  postAction('/system/resources/resources/getRoleInfoByMenuIds', params)
const getAuthResourceList = params =>
  postAction('/system/res/auth/resource/query', params)
const getAllAuthResourceList = params =>
  postAction('/system/res/resource/query', params)
const addAuthResource = params =>
  postAction('/system/res/auth/resource/add', params)
const editAuthResource = params =>
  postAction('/system/res/auth/resource/edit', params)
const deleteAuthResource = params =>
  postAction('/system/res/auth/resource/delete', params)

export {
  getAuthList,
  setRoleToResources,
  getRoleInfoByMenuIds,
  getAuthResourceList,
  getAllAuthResourceList,
  editAuthResource,
  addAuthResource,
  deleteAuthResource
}
