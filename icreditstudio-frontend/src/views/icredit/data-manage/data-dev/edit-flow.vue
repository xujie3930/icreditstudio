<!--
 * @Author: lizheng
 * @Description: 编辑业务流程
 * @Date: 2021-10-08
-->

<template>
  <BaseDialog
    title="编辑业务流程"
    ref="editDialog"
    width="800px"
    @on-close="close"
    @on-confirm="confirm"
  >
    <el-form ref="form" label-width="120px" :model="form" :rules="formRules">
      <el-form-item label="业务流程名称" prop="taskName">
        <el-input
          v-model.trim="form.taskName"
          placeholder="业务流程名称"
          clearable
          :maxlength="50"
          show-word-limit
        ></el-input>
      </el-form-item>

      <el-form-item label="业务流程描述" prop="taskDescribe">
        <el-input
          clearable
          show-word-limit
          :maxlength="250"
          :rows="4"
          type="textarea"
          v-model.trim="form.taskDescribe"
          placeholder="请输入业务流程描述"
        ></el-input>
      </el-form-item>
    </el-form>
  </BaseDialog>
</template>

<script>
import BaseDialog from '@/views/icredit/components/dialog'

export default {
  components: { BaseDialog },

  data() {
    return {
      form: {},
      formRules: {
        taskName: [
          { required: true, message: '文件夹名称不能为空', trigger: 'blur' }
        ]
      }
    }
  },

  methods: {
    close() {},
    confirm() {
      this.$refs.addDocDialog.close()
    }
  }
}
</script>

<style lang="scss" scoped></style>
