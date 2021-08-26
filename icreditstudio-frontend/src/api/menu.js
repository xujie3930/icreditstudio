import { postAction } from './index'

const changeMenuStatus = params =>
  postAction('/system/resources/resources/changeResStatusByIds', params)

export { changeMenuStatus }
