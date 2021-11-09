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
        :table-pagination="tablePagination"
        @handleSizeChange="handleSizeChange"
        @handleCurrentChange="handleCurrentChange"
      >
        <!-- 执行状态 -->
        <template #taskInstanceStateColumn="{row: {taskInstanceState}}">
          <span
            :style="{
              color: [0, 1, 2].includes(taskInstanceState)
                ? execStatusMapping[taskInstanceState].color
                : '#606266'
            }"
          >
            {{
              [0, 1, 2].includes(taskInstanceState)
                ? execStatusMapping[taskInstanceState].label
                : '-'
            }}
          </span>
        </template>
      </j-table>
    </BaseDialog>

    <BaseDialog ref="detailLogDialog" width="800px" hideFooter title="日志">
      <div class="log-detail" v-if="logDetail" v-loading="detailLoading">
        {{ logDetail }}
      </div>
      <div v-else>暂无数据</div>
    </BaseDialog>
  </div>
</template>

<script>
import BaseDialog from '@/views/icredit/components/dialog'
import tableConfiguration from '@/views/icredit/configuration/table/schedule-view-log'
import API from '@/api/icredit'
import { execStatusMapping } from '@/views/icredit/data-manage/data-sync/contant'

export default {
  components: { BaseDialog },

  data() {
    return {
      execStatusMapping,
      title: '历史执行情况',
      taskId: null,
      titleName: '',
      logDetail: '',
      detailLoading: false,
      tableLoading: false,
      tableData: [],
      tableConfiguration: tableConfiguration(this),
      tablePagination: {
        currentPage: 1,
        pageSize: 10,
        total: 0,
        pagerCount: 5,
        handleSizeChange: this.handleSizeChange,
        handleCurrentChange: this.handleCurrentChange
      }
    }
  },

  methods: {
    open(row) {
      this.titleName = row.taskName
      this.logDetail = ''
      this.taskId = row.taskId
      this.getHistoryLogData(row.taskId)
      this.$refs.baseDialog.open()
    },

    handleSizeChange(size) {
      this.tablePagination.pageSize = size
      this.getHistoryLogData(this.taskId)
    },

    handleCurrentChange(pageIndex) {
      this.tablePagination.currentPage = pageIndex
      this.getHistoryLogData(this.taskId)
    },

    // 日志详情
    handleViewLogDetail({ row }) {
      this.getLogDetailData(row.taskInstanceId)
      this.$refs.detailLogDialog.open()
    },

    // 历史日志列表数据
    getHistoryLogData(taskId) {
      const { pageNum, pageSize } = this.tablePagination
      this.tableLoading = true
      API.dataScheduleSyncHistoryLog({ taskId, pageNum, pageSize })
        .then(({ success, data }) => {
          if (success && data) {
            this.tableData = data.list
            this.tablePagination.total = data.total || 0
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
          if (success) {
            this.logDetail = data
          }
        })
        .finally(() => {
          this.detailLoading = false
        })
    }
  }
}
</script>
