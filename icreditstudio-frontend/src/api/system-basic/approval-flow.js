import { getAction, postAction } from '../index'

// const queryFormGroupModelInfo = params => getAction('/form/group/get/modelInfo', params)
const queryApprovalOrgTree = params => postAction('/resources/getAllOrgTreeInfo', params)
const queryApprovalRoles = params => postAction('/resources/getAllRoleInfo', params)
const queryApprovalUsers = params => postAction('/resources/getAllUserInfo', params)
// const queryRoleAllTreeify = params => postAction('/res/role/treeify/all', params)

const queryProcdef = params => getAction('/process/definition/info/xml', params, { responseType: 'text', noErrorNotification: true })
const saveProcdef = params => postAction('/process/definition/info/save', params, { headers: { 'Content-Type': 'multipart/form-data', dataType: 'file', 'x-userId': 1 } })
const deleteProcdef = params => postAction('/process/definition/form/deleteByKey', params)
const queryProcdefSponsorTaskDetail = params => getAction('/process/definition/info/query/sponsor/task/detail', params)

const queryInterfaceList = params => postAction('/data/interface/page', params)
const queryDataSourceList = params => postAction('/dataSource/page', params)
const queryRuleList = params => getAction('/res/role/page', params)

const queryFormGroupModelInfo = params => getAction('/processForm/queryByKey', params)
const addFormIntoGroup = params => postAction('/processForm/add/binding', params)
const copyForm = params => postAction('/form/info/copy', params)
const editFormInfo = params => postAction('/form/info/edit', params)
const editFormState = params => postAction('/processForm/edit/state', params)

const initiateApprovalSaveDraft = params => postAction('/process/instance/info/add/process/info', params)
const initiateApprovalCheckFormRule = params => postAction('/process/instance/info/add/process/info', params)
const initiateApprovalSubmit = params => postAction('/process/instance/info/procdef/record/start', params)
const initiateApprovalTaskReplay = params => postAction('/user/task/info/replay', params)

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
