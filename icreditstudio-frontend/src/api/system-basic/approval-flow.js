import { getAction, postAction } from '../index'

// const queryFormGroupModelInfo = params => getAction('/system/form/group/get/modelInfo', params)
const queryApprovalOrgTree = params =>
  postAction('/system/resources/getAllOrgTreeInfo', params)
const queryApprovalRoles = params =>
  postAction('/system/resources/getAllRoleInfo', params)
const queryApprovalUsers = params =>
  postAction('/system/resources/getAllUserInfo', params)
// const queryRoleAllTreeify = params => postAction('/system/res/role/treeify/all', params)

const queryProcdef = params =>
  getAction('/system/process/definition/info/xml', params, {
    responseType: 'text',
    noErrorNotification: true
  })
const saveProcdef = params =>
  postAction('/system/process/definition/info/save', params, {
    headers: {
      'Content-Type': 'multipart/form-data',
      dataType: 'file',
      'x-userId': 1
    }
  })
const deleteProcdef = params =>
  postAction('/system/process/definition/form/deleteByKey', params)
const queryProcdefSponsorTaskDetail = params =>
  getAction('/system/process/definition/info/query/sponsor/task/detail', params)

const queryInterfaceList = params =>
  postAction('/system/data/interface/page', params)
const queryDataSourceList = params =>
  postAction('/system/dataSource/page', params)
const queryRuleList = params => getAction('/system/res/role/page', params)

const queryFormGroupModelInfo = params =>
  getAction('/system/processForm/queryByKey', params)
const addFormIntoGroup = params =>
  postAction('/system/processForm/add/binding', params)
const copyForm = params => postAction('/system/form/info/copy', params)
const editFormInfo = params => postAction('/system/form/info/edit', params)
const editFormState = params =>
  postAction('/system/processForm/edit/state', params)

const initiateApprovalSaveDraft = params =>
  postAction('/system/process/instance/info/add/process/info', params)
const initiateApprovalCheckFormRule = params =>
  postAction('/system/process/instance/info/add/process/info', params)
const initiateApprovalSubmit = params =>
  postAction('/system/process/instance/info/procdef/record/start', params)
const initiateApprovalTaskReplay = params =>
  postAction('/system/user/task/info/replay', params)

export {
  queryApprovalOrgTree,
  queryApprovalRoles,
  queryApprovalUsers,
  queryProcdef,
  saveProcdef,
  deleteProcdef,
  queryProcdefSponsorTaskDetail,
  queryInterfaceList,
  queryDataSourceList,
  queryRuleList,
  queryFormGroupModelInfo,
  addFormIntoGroup,
  copyForm,
  editFormInfo,
  editFormState,
  initiateApprovalSaveDraft,
  initiateApprovalCheckFormRule,
  initiateApprovalSubmit,
  initiateApprovalTaskReplay
}
