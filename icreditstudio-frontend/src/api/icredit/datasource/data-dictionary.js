/*
 * @Description: 字典表
 * @Date: 2021-10-14
 */

import { postAction } from '@/api'

// 字典表保存
const dictionarySave = params => postAction('/datasync/dict/save', params)

// 字典表删除
const dictionaryDelete = params => postAction('/datasync/dict/del', params)

// 字典表更新
const dictionaryUpdate = params => postAction('/datasync/dict/update', params)

// 字典表列表
const dictionaryList = params => postAction('/datasync/dict/pageList', params)

// 字典表详情
const dictionaryInfo = params => postAction('/datasync/dict/info', params)

// 字典表信息
const dictionaryViewInfo = params =>
  postAction('/datasync/dict/lookInfo', params)

export default {
  dictionarySave,
  dictionaryDelete,
  dictionaryUpdate,
  dictionaryList,
  dictionaryInfo,
  dictionaryViewInfo
}
