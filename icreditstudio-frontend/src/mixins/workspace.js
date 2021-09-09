/*
 * @Description: 更换工作空间重新加载页面数据
 * @Date: 2021-08-30
 */

import { mapState } from 'vuex'

export default {
  watch: {
    workspaceId(nVal) {
      nVal && this.mixinRetrieveTableData()
    }
  },

  computed: {
    ...mapState('user', ['workspaceId'])
  }
}
