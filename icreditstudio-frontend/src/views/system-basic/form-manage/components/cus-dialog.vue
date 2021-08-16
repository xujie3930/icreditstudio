<template>
  <div ref="cusDialog">
    <el-dialog
      :id="id"
      ref="elDialog"
      :class="isBackEdge ? 'cus-dialog-container' : ''"
      :title="title"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      append-to-body
      center
      custom-class="cus-dialog cus-dialog-scope"
      :width="width"
      :destroy-on-close="true"
    >
      <slot />
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: {
    visible: Boolean,
    loadingText: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    },
    width: {
      type: String,
      default: '600px'
    },
    form: {
      type: Boolean,
      default: true
    },
    action: {
      type: Boolean,
      default: true
    },
    isBackEdge: {
      type: Boolean,
      default: false
    }
  },

  data() {
    return {
      loading: false,
      dialogVisible: this.visible,
      id: `dialog_${new Date().getTime()}`,
      showForm: false
    }
  },

  computed: {
    show() {
      if (this.form) {
        return this.showForm
      } else {
        return true
      }
    }
  },

  watch: {
    dialogVisible(val) {
      if (!val) {
        this.loading = false
        this.$emit('on-close')
        setTimeout(() => {
          this.showForm = false
        }, 300)
      } else {
        this.showForm = true
      }
    },
    visible(val) {
      this.dialogVisible = val
    }
  },

  methods: {
    close() {
      this.dialogVisible = false
    },
    submit() {
      this.loading = true

      this.$emit('on-submit')
    },
    end() {
      this.loading = false
    }
  }
}
</script>

<style lang="scss">
@import '~@/styles/form-manage/border.scss';
.cus-dialog-scope .el-dialog__header {
  display: flex;
}
</style>
