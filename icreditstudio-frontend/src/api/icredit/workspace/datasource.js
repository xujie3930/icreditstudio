/*
 * @Description: 数据源管理-接口URL
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

// to sync item detail information of datasource
const datasourceSync = params => getAction(`/datasource/sync/${params}`)

// testing connection of datasource module
const datasourceTestLink = params =>
  getAction('/datasource/testConnect', params)

export default {
  datasourceAdd,
  datasourceDelete,
  datasourceUpdate,
  datasourceDetail,
  datasourceSync,
  datasourceTestLink
}
