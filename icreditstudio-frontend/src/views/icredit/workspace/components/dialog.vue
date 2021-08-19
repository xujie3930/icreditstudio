<!--
 * @Description: 操作提示弹窗
 * @Date: 2021-08-18
-->

<template>
  <el-dialog :visible.sync="dialogVisible" width="480px" top="25vh">
    <div class="dialog-title" slot="title">{{ title }}</div>
    <div class="content">
      {{ operateMsg }}
      <span class="color-text">
        {{ workspaceName }}
      </span>
      在调度，请先下线工作流后再停用。
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button size="mini" @click="dialogVisible = false">取 消</el-button>
      <el-button size="mini" type="primary" @click="handleConfirm">
        确 定</el-button
      >
    </span>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      title: '',
      workspaceName: '',
      operateMsg: '',
      dialogVisible: false
    }
  },

  methods: {
    open(opType, name) {
      this.dialogVisible = true
      this.workspaceName = name
      this.title = `工作空间${opType === 'disabled' ? '停用' : '删除'}`
      this.operateMsg =
        opType === 'disabled'
          ? '当前数据源有工作流（'
          : '删除工作空间后，工作空间内的项目和工作流都将删除，请谨慎操作。确认要删除'
    },

    handleConfirm() {
      this.dialogVisible = false
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
</style>
