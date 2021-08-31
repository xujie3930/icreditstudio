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
    handleDeleteClick(methodName, params) {
      API[methodName](params)
        .then(({ success }) => {
          if (success) {
            this.$refs.tipDialog.close()
            this.$notify.success({
              title: '操作结果',
              message: '工作空间删除成功！'
            })
            this.mixinRetrieveTableData()
          }
        })
        .finally(() => {})
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
        .finally()
    },

    // 编辑操作
    handleEditClick(methodName, params) {
      this.detailLoading = true
      API[methodName](params)
        .then(({ success, data }) => {
          if (success) {
            this.mixinDetailInfo(data)
          }
        })
        .finally(() => {
          this.detailLoading = false
        })
    },

    mixinDetailInfo(data) {
      console.log(data)
    }
  }
}
