import { postAction } from './index'

const setOrgStatus = params => postAction('/org/organization/status', params)

export {
  setOrgStatus
}
