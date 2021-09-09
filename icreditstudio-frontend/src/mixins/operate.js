/*
 * @Author: lizheng
 * @Description:
 * @Date: 2021-08-25
 */
import API from '@/api/icredit'

export default {
  data() {
    return { detailLoading: false }
  },

  methods: {
    // 删除操作
    handleDeleteClick(methodName, params, dialogName) {
      API[methodName](params)
        .then(({ success }) => {
          if (success) {
            dialogName && this.$refs[dialogName].close()
            this.$notify.success({
              title: '操作结果',
              message: '删除成功！'
            })
            this.mixinRetrieveTableData()
          }
        })
        .finally(() => {
          dialogName && this.$refs[dialogName].btnLoadingClose()
        })
    },

    // 启用操作
    handleEnabledClick(methodName, params) {
      API[methodName](params)
        .then(({ success }) => {
          if (success) {
            this.$notify.success({
              title: '操作结果',
              message: '启用成功！'
            })
            this.mixinRetrieveTableData()
          }
        })
        .finally()
    },

    // 停用操作
    handleDisabledClick(methodName, params, dialogName) {
      API[methodName](params)
        .then(({ success }) => {
          if (success) {
            this.$notify.success({
              title: '操作结果',
              message: '停用成功！'
            })
            dialogName && this.$refs[dialogName].close()
            this.mixinRetrieveTableData()
          }
        })
        .finally(() => {
          dialogName && this.$refs[dialogName].btnLoadingClose()
        })
    },

    // 编辑操作
    handleEditClick(methodName, params, opType, dialogName) {
      console.log(dialogName, 'dialogName')
      this.detailLoading = true
      this[`btn${opType}Loading`] = true
      if (dialogName) {
        this.$refs[dialogName].detailLoading = true
        this.$refs[dialogName].$refs.baseDialog.open()
      }
      API[methodName](params)
        .then(({ success, data }) => {
          if (success) {
            this.mixinDetailInfo(data, opType)
          }
        })
        .finally(() => {
          this.detailLoading = false
          this[`btn${opType}Loading`] = false
        })
    },

    mixinDetailInfo(data) {
      console.log(data)
    }
  }
}
