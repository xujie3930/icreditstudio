/*
 * @Author: lizheng
 * @Description: datasource-management
 * @Date: 2021-08-25
 */

import dataSyncApi from './data-sync'
import dataScheduleApi from './data-schedule'
import dataDictionaryApi from './data-dictionary'

export default {
  ...dataSyncApi,
  ...dataScheduleApi,
  ...dataDictionaryApi
}
