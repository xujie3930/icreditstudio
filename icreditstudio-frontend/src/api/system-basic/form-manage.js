/*
 * @Author: lizheng
 * @Description: 表单管理模块相关接口API
 * @Date: 2021-07-14
 */
import { getAction, postAction } from '../index'

// 表单配置 - 表单保存或发布
const formManageOperate = params => postAction('/form/definition/save', params)

// 表单配置 - 数字字典
const formManageDictionary = params =>
  getAction('/code/code/getInfoByKey', params)

// 表单列表 - 更多 - 发布
const formListPublish = params => postAction('form/definition/publish', params)

// 根据id查询表单详情
const qryFormDetail = params => postAction('form/definition/formDetail', params)

// 表单停用
const formDisable = params => postAction('form/definition/disable', params)

// 表单列表 - 更多 - 预览
const formListPreview = params =>
  postAction('form/definition/formDetail', params)

// 表单历史版本-删除
const formHistoryDelete = params =>
  postAction('form/hi/definition/delete', params)

// 表单历史版本-预览
const formHistoryPreview = params =>
  postAction('form/hi/definition/formHiDetail', params)

// 表单列表-更多-创建实例-保存
const formInstanceCreate = params => postAction('form/instance/save', params)

// 表单列表-更多-创建实例-详情
const formInstanceCreateView = params =>
  postAction('form/definition/formDetail', params)

// 表单实例-操作-查看
const formInstanceView = params =>
  postAction('form/instance/instanceDetail ', params)

export {
  formManageOperate,
  formListPublish,
  formManageDictionary,
  formListPreview,
  qryFormDetail,
  formDisable,
  formHistoryDelete,
  formHistoryPreview,
  formInstanceCreate,
  formInstanceCreateView,
  formInstanceView
}
