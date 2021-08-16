import { postAction } from './index'

const getAuthList = params => postAction('/resources/resources/queryList', params)
// 设置模块的角色信息
const setRoleToResources = params => postAction('/resources/resources/setRoleToResources', params)
// 根据模板id获取当前模板已配置的角色信息
const getRoleInfoByMenuIds = params => postAction('/resources/resources/getRoleInfoByMenuIds', params)
const getAuthResourceList = params => postAction('/res/auth/resource/query', params)
const getAllAuthResourceList = params => postAction('/res/resource/query', params)
const addAuthResource = params => postAction('/res/auth/resource/add', params)
const editAuthResource = params => postAction('/res/auth/resource/edit', params)
const deleteAuthResource = params => postAction('/res/auth/resource/delete', params)

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
