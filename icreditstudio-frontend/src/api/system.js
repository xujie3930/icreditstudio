/*
 * @Author: lizheng
 * @Description: 系统设置模块接口
 * @Date: 2021-06-28
 */
import { getAction, postAction } from './index'

// 系统配置查询
const querySystemSettings = params => getAction('/system/settings/info', params)

// 系统设置保存
const setSystemSettings = params => postAction('/system/settings/save', params)

// 系统logo上传
const uploadLogo = params => postAction('/system/settings/uploadLogo', params)

// 个性化设置查询
const queryPersonalizedSettings = params =>
  getAction('/system/shortcutmenu/info', params)

// 个性化设置保存
const savePersonalizedSettings = params =>
  postAction('/system/shortcutmenu/save', params)

// 个性化设置更新
const updatePersonalizedSettings = params =>
  postAction('/system/shortcutmenu/update', params)

// 个性化设置 - 快捷菜单开关状态
const settingUserShortMenuStatus = params =>
  postAction('/system/shortcutmenu/enableCustomMenu', params)

export {
  querySystemSettings,
  setSystemSettings,
  uploadLogo,
  queryPersonalizedSettings,
  savePersonalizedSettings,
  updatePersonalizedSettings,
  settingUserShortMenuStatus
}
