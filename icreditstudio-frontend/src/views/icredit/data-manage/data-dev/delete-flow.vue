<!--
 * @Author: lizheng
 * @Description: 删除业务流程
 * @Date: 2021-10-08
-->
<template>
  <BaseDialog
    :title="title"
    ref="editDialog"
    width="800px"
    @on-close="close"
    @on-confirm="confirm"
  >
    <h5>
      当前业务流程下有文件夹和工作流，请先删除对应的文件夹和工作后再删除业务流程
    </h5>
  </BaseDialog>
</template>

<script>
import BaseDialog from '@/views/icredit/components/dialog'

export default {
  components: { BaseDialog },

  data() {
    return {
      title: '',
      form: {},
      formRules: {
        taskName: [
          { required: true, message: '文件夹名称不能为空', trigger: 'blur' }
        ]
      }
    }
  },

  methods: {
    open({ title }) {
      this.title = title || '删除业务流程'
      this.$refs.editDialog.open()
    },
    close() {},
    confirm() {
      this.$refs.editDialog.close()
    }
  }
}
</script>

<style lang="scss" scoped></style>
