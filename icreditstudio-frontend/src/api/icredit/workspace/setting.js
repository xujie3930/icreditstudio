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

export default {
  workspaceAdd,
  workspaceDelete,
  workspaceUpdate,
  workspaceDetail
}
