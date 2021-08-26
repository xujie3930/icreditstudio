import workspaceApi from './workspace'
import datasourceApi from './datasource'

export default {
  ...workspaceApi,
  ...datasourceApi
}
