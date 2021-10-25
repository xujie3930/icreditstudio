<!--
 * @Description: 操作提示弹窗
 * @Date: 2021-08-18
-->

<template>
  <el-dialog
    :visible.sync="dialogVisible"
    width="480px"
    top="25vh"
    class="dialog"
  >
    <div class="dialog-title" slot="title">{{ title }}</div>
    <div class="content">
      {{ operateMsg }}
      <span class="color-text"> {{ workspaceName }} </span>吗？
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button size="mini" @click="close">取 消</el-button>
      <el-button
        size="mini"
        type="primary"
        :loading="btnLoading"
        @click="handleConfirm"
      >
        确 定
      </el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      row: '',
      opType: '',
      title: '',
      workspaceName: '',
      operateMsg: '',
      dialogVisible: false,
      btnLoading: false
    }
  },

  methods: {
    open(opType, row) {
      this.row = row
      this.dialogVisible = true
      this.workspaceName = row.name
      this.opType = opType
      this.title = `工作空间${opType === 'Disabled' ? '停用' : '删除'}`
      this.operateMsg =
        opType === 'Disabled'
          ? '停用工作空间后，工作空间中的项目和工作流都不再进行调度，请谨慎操作。确认要停用'
          : '删除工作空间后，工作空间内的项目和工作流都将删除，请谨慎操作。确认要删除'
    },

    close() {
      this.dialogVisible = false
      this.btnLoading = false
    },

    btnLoadingClose() {
      this.btnLoading = false
    },

    handleConfirm() {
      this.btnLoading = true
      this.$emit('onConfirm', this.opType, this.row)
    }
  }
}
</script>

<style lang="scss" scoped>
.dialog-title {
  width: 96px;
  height: 24px;
  font-size: 16px;
  font-family: PingFangSC, PingFangSC-Medium;
  font-weight: 500;
  text-align: left;
  color: rgba(0, 0, 0, 0.85);
  line-height: 24px;
}

.content {
  width: 432px;
  height: 44px;
  opacity: 1;
  font-size: 14px;
  font-family: PingFangSC, PingFangSC-Regular;
  font-weight: 400;
  text-align: left;
  line-height: 22px;
  color: rgba(0, 0, 0, 0.85);
}

.color-text {
  color: #1890ff;
}

.dialog {
  /deep/ .el-dialog__header {
    border-bottom: 1px solid rgba(0, 0, 0, 0.06);
  }
}
</style>
