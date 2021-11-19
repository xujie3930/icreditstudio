/*
 * @Description: workspace-settings API URL
 * @Date: 2021-08-25
 */

import { postAction, getAction } from '@/api'

// add workapce
const workspaceAdd = params => postAction('/workspace/save', params)

// delete workapce
const workspaceDelete = params => postAction('/workspace/delete', params)

// update workspace
const workspaceUpdate = params => postAction('/workspace/update', params)

// get item detail information of workspace
const workspaceDetail = params => getAction(`/workspace/info/${params}`)

// 判断是否有重复工作空间
const verifyWorkspaceName = params => postAction('/workspace/hasExist', params)

// 负责人模糊查询
const getUserFluzzyQuery = params =>
  postAction('/system/user/user/queryUserRoleByLikeName', params)

// 首页工作台统计情况
const getHomeSpaceInfo = params => postAction('/workspace/workbench', params)

export default {
  workspaceAdd,
  workspaceDelete,
  workspaceUpdate,
  workspaceDetail,
  verifyWorkspaceName,
  getUserFluzzyQuery,
  getHomeSpaceInfo
}
