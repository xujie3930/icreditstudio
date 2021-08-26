import { postAction, getAction } from './index'
// 消息中心messageNotice start ↓
// 未读消息轮询
const pollingUnreadInfos = params =>
  getAction('/system/information/information/pollingUnreadInfos', params)
// 获取消息阅读数量
const infoCount = params =>
  postAction('/system/information/information/infoCount', params)
// 删除所有消息
const deleteAllUserInfos = params =>
  postAction('/system/information/information/deleteAllUserInfos', params)
// 用户消息全部标记为已读
const userAllReadInfo = params =>
  postAction('/system/information/information/userAllReadInfo', params)
// 用户查看消息详情
const queryMessageNoticeInfo = params =>
  getAction('/system/information/information/user/info', params)

// 消息中心messageNotice end ↑

// 消息管理message start ↓

// 消息管理message end ↑

export {
  pollingUnreadInfos,
  infoCount,
  deleteAllUserInfos,
  userAllReadInfo,
  queryMessageNoticeInfo
}
