import { getAction, postAction } from '../index'

const queryFromGroup = params =>
  getAction('/system/process/form/group/get', params)
// 通过流程
const proctaskPass = params => postAction('/system/user/task/info/pass', params)
// 查询草稿箱
const procdefDraft = params => {
  const queryStr = getQueryStr(params)
  return getAction(`/process/definition/info/page/myself/draft?${queryStr}`)
}
function getQueryStr(obj) {
  if (!obj) return ''
  return cleanArray(
    Object.keys(obj).map(key => {
      if (obj[key] === undefined) return ''
      return `${key}=${obj[key]}`
    })
  ).join('&')
}
function cleanArray(actual) {
  const newArray = []
  for (let i = 0; i < actual.length; i++) {
    if (actual[i]) {
      newArray.push(actual[i])
    }
  }
  return newArray
}

// 删除客户管理历史流程
const customerDel = params =>
  postAction('/system/customer/form/delete ', params)

// 不通过流程
const proctaskNoPass = params =>
  postAction('/system/user/task/info/nopass', params)

// 分页查询流程实例 - 我发起的
const procinstMyself = params =>
  postAction('/system/procinst/page/myself', params)

// 分页查询流程实例 - 抄送我的
const procinstCarbon = params =>
  postAction('/system/procinst/page/cc/record', params)
// 客户管理导出
const downloadCustomer = params =>
  postAction('/system/downloadExcel/customerManageForm', params)

// 台账导出
const downloadFormIntance = params =>
  postAction('/system/customer/form/formIntance/download', params)
// 退回任务 （退回到指定节点）
const proctaskGoBack = params =>
  postAction('/system/user/task/info/back/node', params)

// 退回任务 （退回任务到上一个节点）
const proctaskPrevious = params =>
  postAction('/system/user/task/info/back/previous/node', params)

// 退回任务 （退回任务到发起人节点）
const proctaskStartNode = params =>
  postAction('/system/user/task/info/back/start/node', params)

// 撤销指派
const proctaskAssignCancel = params =>
  postAction('/system/user/task/info/assign/cancel', params)
// 查询流程定义 - 自己的
const procdefStart = params => {
  const queryStr = getQueryStr(params)
  return getAction(`/process/definition/info/page/myself/start?${queryStr}`)
}
// 查询草稿箱
// const procdefDraft = params => {
//   const queryStr = getQueryStr(params)
//   return fetch({
//     '/procdef/page/myself/draft?' + queryStr,
//     method: 'get'
//   )
// }

// 获取可回退节点

const proctaskfNodes = params =>
  getAction('/system/user/task/info/goback/nodes', params)

const ruleFormInfo = params => postAction('/system/rule/check/formInfo', params)

const procinstStart = params => postAction('/system/procinst/start', params)

const procinstAdd = params => postAction('/system/procinst/add', params)
const procinstAddInfo = params =>
  postAction('/system/procinst/add/process/info', params)

const queryAppList = params => postAction('/system/appManage/page', params)

const editAppItem = params => postAction('/system/appManage/update', params)

const addAppItem = params => postAction('/system/appManage/add', params)

const deleteAppItem = params => postAction('/system/appManage/delete', params)

const proctaskRecall = params =>
  postAction('/system/user/task/info/recall/previous/node', params)
// 校验是否有抄送按钮
const proctaskCarbon = params =>
  postAction('/system/proctask/check/manual/carbon', params)

const proctaskCarbonUser = params =>
  postAction('/system/proctask/query/carbon/user', params)

const proctaskManual = params =>
  postAction('/system/proctask/manual/carbon', params)

const proctaskAssign = params =>
  postAction('/system/user/task/info/assign', params)

const proctaskDone = params => postAction('/system/proctask/page/done', params)

const taskPageDone = params => postAction('/system/user/task/page/done', params)

const procinstRecordStatus = params =>
  postAction('/system/procinst/edit/carbon/record/status', params)
// 我已处理和我发起的，校验是否能撤回
const taskStartCheck = params =>
  postAction('/system/user/task/info/start/user/check', params)

// 我已处理，校验是否能撤销指派
const assignCheck = params =>
  postAction('/system/user/task/info/assign/check', params)

// 台账 表单实例查询
const customerGroupQuery = params =>
  postAction('/system/customer/form/group/page', params)
// 台账 表单表头JSON保存
const formOptionsUpdate = params =>
  postAction('/system/formOptions/update', params)

const proctaskManualCC = params =>
  postAction('/system/processTask/manual/carbonCopy', params)
// 表单管理详情
const formGroupModelInfo = params => {
  const queryStr = getQueryStr(params)
  return getAction(`/form/group/get/modelInfo?${queryStr}`, params)
}
// 审批管理详情
const procinstInfo = params => {
  const queryStr = getQueryStr(params)
  return getAction(`/procinst/get/info?${queryStr}`, params)
}
// 台账 表单表头JSON获取
const formOptionsGet = params => {
  const queryStr = getQueryStr(params)
  return getAction(`/formOptions/get?${queryStr}`, params)
}
// 台账 表单选项查询
const customerOptions = params => {
  const queryStr = getQueryStr(params)
  return getAction(`/customer/form/options?${queryStr}`, params)
}

// 模糊查询用户
const proctaskAssignUsers = params =>
  postAction('/system/proctask/query/assign/users', params)

// 模糊查询用户
const proctaskAssignStartUsers = params =>
  postAction('/system/proctask/query/assign/start/users', params)
const customerVariables = params => {
  const queryStr = getQueryStr(params)
  return getAction(`/customerManager/query/variables?${queryStr}`, params)
}

const customerSearch = params => {
  const queryStr = getQueryStr(params)
  return getAction(`/customer/form/delete?${queryStr}`, params)
}

const procdefSponsor = params => {
  const queryStr = getQueryStr(params)
  return getAction(`/procdef/query/bpmn/sponsor?${queryStr}`, params)
}

const taskVariables = params => {
  return getAction(
    '/system/user/task/info/query/starter/task/variables',
    params
  )
}

const procdefQueryDetail = params => {
  const queryStr = getQueryStr(params)
  return getAction(
    `/process/definition/info/query/sponsor/task/detail?${queryStr}`
  )
}

// 流程管理 校验
const procdefVerify = params => {
  const queryStr = getQueryStr(params)
  return getAction(`/procdef/verify?${queryStr}`, params)
}

const formGroupQuerys = params =>
  postAction('/system/form/group/querys', params)

// 抄送-模糊查询用户
const userFilterMe = params =>
  postAction('/system/user/user/queryUserInfoByLikeName', params)

const procdefDraftDel = params =>
  postAction('/system/procdef/draft/delete', params)
const processExamples = params => {
  const queryStr = getQueryStr(params)
  return getAction(`/process/instance/info/examples?${queryStr}`)
}
export {
  // customerQuery,
  // customerAdd,
  // customerEdit,
  customerDel,
  proctaskNoPass,
  procinstMyself,
  procinstCarbon,
  downloadCustomer,
  procdefStart,
  procdefDraft,
  proctaskfNodes,
  proctaskGoBack,
  proctaskPrevious,
  proctaskStartNode,
  ruleFormInfo,
  procinstStart,
  procinstAdd,
  procinstAddInfo,
  customerVariables,
  queryAppList,
  editAppItem,
  addAppItem,
  deleteAppItem,
  proctaskRecall,
  customerSearch,
  procdefSponsor,
  proctaskCarbon,
  proctaskCarbonUser,
  proctaskManual,
  proctaskDone,
  procinstRecordStatus,
  taskPageDone,
  taskStartCheck,
  taskVariables,
  proctaskAssign,
  assignCheck,
  proctaskAssignCancel,
  customerGroupQuery,
  customerOptions,
  downloadFormIntance,
  formOptionsUpdate,
  formOptionsGet,
  proctaskAssignUsers,
  proctaskAssignStartUsers,
  proctaskManualCC,
  userFilterMe,
  formGroupModelInfo,
  procinstInfo,
  procdefVerify,
  formGroupQuerys,
  processExamples,
  procdefDraftDel,
  queryFromGroup,
  proctaskPass,
  procdefQueryDetail
}
