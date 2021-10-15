<!--
 * @Author: lizheng
 * @Description: 新建文件夹
 * @Date: 2021-10-08
-->

<template>
  <BaseDialog
    title="新建文件夹"
    ref="addDocDialog"
    width="800px"
    @on-close="close"
    @on-confirm="confirm"
  >
    <el-form
      class="add-doc-form"
      :model="form"
      :rules="formRules"
      ref="form"
      label-width="100px"
    >
      <el-form-item label="文件夹名称" prop="taskName">
        <el-input
          v-model.trim="form.taskName"
          placeholder="请输入文件夹名称"
          clearable
          :maxlength="50"
          show-word-limit
        ></el-input>
      </el-form-item>

      <el-form-item label="选择父节点" prop="createMode">
        <el-select
          v-model="form.createMode"
          placeholder="请选择父节点"
          style="width:100%"
        >
          <el-option
            v-for="item in nodeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="文件夹描述" prop="taskDescribe">
        <el-input
          clearable
          show-word-limit
          :maxlength="250"
          :rows="4"
          type="textarea"
          v-model.trim="form.taskDescribe"
          placeholder="请输入文件夹描述"
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
        ],
        createMode: [
          { required: true, message: '父节点不能为空', trigger: 'change' }
        ]
      },
      nodeOptions: [
        { label: '父节点1', value: 1 },
        { label: '父节点2', value: 2 }
      ]
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
