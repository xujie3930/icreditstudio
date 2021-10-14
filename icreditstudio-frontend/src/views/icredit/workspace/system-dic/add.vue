<!--
 * @Author: lizheng
 * @Description: 新增或编辑下级
 * @Date: 2021-10-09
-->
<template>
  <BaseDialog
    ref="baseDialog"
    width="600px"
    :title="title"
    @on-close="handleClose"
    @on-confirm="handleConfirm"
  >
    <!-- 查看详情 -->
    <el-form
      ref="addLevelForm"
      label-width="100px"
      :model="addLevelForm"
      :rules="rules"
    >
      <el-form-item label="父级节点" prop="parent">
        <el-input disabled v-model="addLevelForm.parent"></el-input>
      </el-form-item>

      <el-form-item label="名称" prop="name">
        <el-input
          placeholder="请输入名称"
          clearable
          v-model="addLevelForm.name"
        ></el-input>
      </el-form-item>

      <el-form-item label="备注" prop="remark">
        <el-input
          type="textarea"
          show-word-limit
          :maxlength="250"
          placeholder="请输入备注"
          v-model="addLevelForm.remark"
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
      title: '',
      opType: 'add',
      dialogVisible: false,
      addLevelForm: { parent: '数据标准', name: '', remark: '' },
      rules: {
        parent: [
          { required: true, message: '请输入父级节点', trigger: 'blur' }
        ],
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }]
      }
    }
  },

  methods: {
    open({ opType }) {
      this.title = `${opType === 'add' ? '添加' : '编辑'}下级`
      this.opType = opType
      this.$refs.baseDialog.open()
    },

    handleClose() {
      this.$refs.addLevelForm.resetFields()
      this.dialogVisible = false
    },

    handleConfirm() {
      this.handleClose()
      this.$emit('on-confirm')
    }
  }
}
</script>
