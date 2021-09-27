<!--
 * @Description: 查看日志
 * @Date: 2021-09-26
-->
<template>
  <div>
    <BaseDialog ref="baseDialog" width="70vw" hideFooter :title="title">
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
      title: 'xxx(工作流名称)历史执行情况',
      tableLoading: false,
      tableData: [{ taskName: 'KPLKPL' }],
      tableConfiguration: tableConfiguration(this)
    }
  },

  methods: {
    handleViewLogDetail(row) {
      console.log('detail', row)
      this.$refs.detailLogDialog.open()
    }
  }
}
</script>

<style></style>
