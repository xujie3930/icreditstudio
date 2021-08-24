<!--
 * @Author: lizheng
 * @Description: 操作提示弹窗
 * @Date: 2021-08-24
-->

<template>
  <BaseDialog ref="baseDialog" width="480px" :title="title">
    <div class="content">
      {{ beforeOperateMsg }}
      <span class="color-text">{{ name }}</span>
      {{ afterOperateMsg }}
    </div>
  </BaseDialog>
</template>

<script>
import BaseDialog from './dialog'

export default {
  components: { BaseDialog },

  data() {
    return {
      title: '',
      name: '',
      afterOperateMsg: '',
      beforeOperateMsg: '',
      dialogVisible: false
    }
  },

  methods: {
    open(options) {
      const { title, name, afterOperateMsg, beforeOperateMsg } = options
      this.name = name
      this.title = title
      this.afterOperateMsg = afterOperateMsg
      this.beforeOperateMsg = beforeOperateMsg
      this.dialogVisible = true
      this.$refs.baseDialog.open()
    },

    handleConfirm() {
      this.dialogVisible = false
      this.$emit('on-confirm')
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
