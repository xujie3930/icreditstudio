<!--
 * @Description: 查看历史日志
 * @Date: 2021-09-26
-->
<template>
  <div class="log-dialog">
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

    <BaseDialog ref="detailLogDialog" width="800px" hideFooter title="日志">
      <div class="log-detail" v-loading="detailLoading">{{ logDetail }}</div>
    </BaseDialog>
  </div>
</template>

<script>
import BaseDialog from '@/views/icredit/components/dialog'
import tableConfiguration from '@/views/icredit/configuration/table/schedule-view-log'
import API from '@/api/icredit'

export default {
  components: { BaseDialog },

  data() {
    return {
      title: '历史执行情况',
      titleName: '',
      logDetail: 'sdddsds',
      detailLoading: false,
      tableLoading: false,
      tableData: [],
      tableConfiguration: tableConfiguration(this)
    }
  },

  methods: {
    open(row) {
      this.titleName = row.syncTaskName
      this.getHistoryLogData(row.taskId)
      this.$refs.baseDialog.open()
    },

    handleViewLogDetail(row) {
      console.log('detail', row)
      this.getLogDetailData(row.taskInstanceId)
      this.$refs.detailLogDialog.open()
    },

    // 历史日志列表数据
    getHistoryLogData(taskId) {
      this.tableLoading = true
      API.dataScheduleSyncHistoryLog({ taskId })
        .then(({ success, data }) => {
          if (success && data) {
            this.tableData = data
          }
        })
        .finally(() => {
          this.tableLoading = false
        })
    },

    // 某条历史日志详情数据
    getLogDetailData(taskInstanceId) {
      this.detailLoading = true
      API.dataScheduleSyncLogDetail({ taskInstanceId })
        .then(({ success, data }) => {
          if (success && data) {
            // this.logDetail = data
          }
        })
        .finally(() => {
          this.detailLoading = false
        })
    }
  }
}
</script>
