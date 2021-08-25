import { postAction } from './index'

const setOrgStatus = params =>
  postAction('/system/org/organization/status', params)

export { setOrgStatus }
