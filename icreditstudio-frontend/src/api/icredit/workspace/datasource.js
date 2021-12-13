/*
 * @Description: 工作空间管理-接口URL
 * @Date: 2021-08-26
 */
import { postAction, getAction } from '@/api'

// add datasource
const datasourceAdd = params => postAction('/datasource/save', params)

// delete datasource
const datasourceDelete = params => postAction('/datasource/delete', params)

// update datasource
const datasourceUpdate = params => postAction('/datasource/update', params)

// get item detail information of datasource
const datasourceDetail = params => getAction(`/datasource/info/${params}`)

// enabled or disabled datatsource
const datasourceEnabled = params =>
  postAction('/datasource/stopOrEnable', params)

// to sync item detail information of datasource
const datasourceSync = params => getAction(`/datasource/sync/${params}`)

// testing connection of datasource module
const datasourceTestLink = params =>
  postAction('/datasource/testConnect', params)

// 判断是否有重复工作空间
const verifyDatasourceName = params =>
  postAction('/datasource/hasExist', params)

// 数据源管理-查看数据源表结构
const datasourceTableDetail = params =>
  getAction(`/datasource/datasourceStructure/${params}`)

export default {
  datasourceAdd,
  datasourceDelete,
  datasourceUpdate,
  datasourceDetail,
  datasourceEnabled,
  datasourceSync,
  datasourceTestLink,
  datasourceTableDetail,
  verifyDatasourceName
}
