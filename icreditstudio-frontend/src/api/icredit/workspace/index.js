/*
 * @Author: lizheng
 * @Description: workspace module API
 * @Date: 2021-08-25
 */

import settingApi from './setting'
import datasourceApi from './datasource'

export default {
  ...settingApi,
  ...datasourceApi
}
