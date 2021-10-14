<!--
 * @Description: 查看历史日志
 * @Date: 2021-09-26
-->
<template>
  <div>
    <BaseDialog
      ref="baseDialog"
      width="70vw"
      hideFooter
      :before-title-name="titleName"
      :title="title"
    >
      <j-table
        ref="viewLogTable"
        v-loading="tableLoading"
        :table-data="tableData"
        :table-configuration="tableConfiguration"
      ></j-table>
    </BaseDialog>

    <BaseDialog ref="detailLogDialog" width="800px" hideFooter title="日志" />
  </div>
</template>

<script>
import BaseDialog from '@/views/icredit/components/dialog'
import tableConfiguration from '@/views/icredit/configuration/table/schedule-view-log'

export default {
  components: { BaseDialog },

  data() {
    return {
      title: '历史执行情况',
      titleName: '',
      tableLoading: false,
      tableData: [{ taskName: 'KPLKPL' }],
      tableConfiguration: tableConfiguration(this)
    }
  },

  methods: {
    open(row) {
      this.titleName = row.syncTaskName
      this.$refs.baseDialog.open()
    },

    handleViewLogDetail(row) {
      console.log('detail', row)
      this.$refs.detailLogDialog.open()
    }
  }
}
</script>

<style></style>
