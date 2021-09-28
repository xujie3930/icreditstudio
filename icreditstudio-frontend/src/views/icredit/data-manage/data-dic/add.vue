<!--
 * @Author: lizheng
 * @Description:  查看
 * @Date: 2021-09-28
-->
<template>
  <BaseDialog
    ref="baseDialog"
    width="600px"
    :title="`${opType === 'add' ? '新增' : '编辑'}字典表`"
    :hide-footer="true"
    @onClose="handleClose"
    @onConfirm="handleConfirm"
  ></BaseDialog>
</template>

<script>
import BaseDialog from '@/views/icredit/components/dialog'

export default {
  components: { BaseDialog },

  data() {
    return {
      opType: ''
    }
  },

  methods: {
    open(options) {
      const { row, opType = 'add' } = options
      this.opType = opType
      console.log('deddeded', row, options)
      this.$refs.baseDialog.open()
    },

    handleClose() {
      this.$refs.baseDialog.close()
    },

    handleConfirm() {
      this.handleClose()
      this.$emit('on-confirm')
    }
  }
}
</script>

<style></style>
