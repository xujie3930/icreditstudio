<!--
 * @Description: 操作提示弹窗
 * @Date: 2021-08-18
-->

<template>
  <BaseDialog ref="baseDialog" width="480px" :title="title">
    <div class="content">
      {{ operateMsg }}
      <template v-if="opType === 'disabled'">
        （<span class="color-text"> {{ workspaceName }}</span
        >） 在调度，请先下线工作流后再停用。
      </template>
    </div>
  </BaseDialog>
</template>

<script>
import BaseDialog from '../components/dialog'

export default {
  components: { BaseDialog },

  data() {
    return {
      opType: '',
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
      this.opType = opType
      this.title = `数据源${opType === 'disabled' ? '停用' : '删除'}`
      this.operateMsg =
        opType === 'disabled'
          ? '当前数据源有工作流'
          : '数据源删除后将不在列表中展示，且不再参与工作流调度，确认删除吗？'
      this.$refs.baseDialog.open()
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
