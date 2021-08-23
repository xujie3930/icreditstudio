<!--
 * @Description: 操作提示弹窗
 * @Date: 2021-08-18
-->

<template>
  <div>
    <el-dialog
      class="icredit-dialog"
      :visible.sync="dialogVisible"
      :width="width"
      top="25vh"
    >
      <div class="icredit-dialog-title" slot="title">{{ title }}</div>
      <div class="icredit-dialog-content">
        <slot />
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="close">取 消</el-button>
        <el-button size="mini" type="primary" @click="confirm">
          确 定
        </el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return { dialogVisible: false }
  },

  props: {
    opType: {
      type: String,
      default: 'view'
    },

    width: {
      type: String,
      default: '700'
    },

    title: {
      type: String,
      default: 'dialog title'
    }
  },

  methods: {
    open() {
      this.dialogVisible = true
    },

    close() {
      this.dialogVisible = false
      this.$emit('on-close')
    },

    confirm() {
      this.close()
      this.$emit('on-confirm')
    }
  }
}
</script>

<style lang="scss" scoped>
.icredit-dialog {
  ::v-deep {
    .el-dialog__header {
      padding: 16px 0;
    }

    .el-dialog__body {
      max-height: 70vh;
      overflow-y: auto;
    }
  }

  &-title {
    padding-bottom: 16px;
    padding-left: 20px;
    font-size: 16px;
    font-weight: 500;
    text-align: left;
    color: rgba(0, 0, 0, 0.85);
    border-bottom: 1px solid rgba(0, 0, 0, 0.06);
  }

  &-content {
    margin-top: -26px;
  }
}
</style>
