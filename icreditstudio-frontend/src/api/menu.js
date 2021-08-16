import { postAction } from './index'

const changeMenuStatus = params => postAction('/resources/resources/changeResStatusByIds', params)

export {
  changeMenuStatus
}
