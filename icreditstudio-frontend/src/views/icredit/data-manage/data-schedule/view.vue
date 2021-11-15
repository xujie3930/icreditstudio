<!--
 * @Description: 查看历史日志
 * @Date: 2021-09-26
-->
<template>
  <div class="log-dialog">
    <BaseDialog
      ref="baseDialog"
      width="90vw"
      hideFooter
      :before-title-name="titleName"
      :title="title"
    >
      <crud-basic
        ref="crud"
        :form-items-search="mixinSearchFormItems"
        :form-func-search="mixinSearchFormFunc"
        :form-config-search="mixinSearchFormConfig"
        :form-items-dialog="mixinDialogFormItems"
        :form-func-dialog="mixinDialogFormFunc"
        :form-config-dialog="mixinDialogFormConfig"
        :tableLoading="mixinTableLoading"
        :table-configuration="tableConfiguration"
        :table-pagination="mixinTablePagination"
        :table-data="mixinTableData"
        :dialog-type="mixinDialogType"
        :dialog-visible.sync="mixinDialog"
        :handleSizeChange="mixinHandleSizeChange"
        :handleCurrentChange="mixinHandleCurrentChange"
        :handleAdd="mixinHandleAdd"
        :handleSearch="mixinHandleSearch"
        :handleReset="mixinHandleReset"
        :handleImport="mixinHandleImport"
        :handleExport="mixinHandleExport"
        :handleUpdate="mixinHandleCreateOrUpdate"
        :handleCancel="mixinHandleCancel"
      >
        <template #content>
          <j-table
            ref="viewLogTable"
            v-loading="mixinTableLoading"
            :table-data="mixinTableData"
            :table-configuration="tableConfiguration"
            :table-pagination="mixinTablePagination"
            @handleSizeChange="mixinHandleSizeChange"
            @handleCurrentChange="mixinHandleCurrentChange"
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
        </template>
      </crud-basic>
    </BaseDialog>

    <BaseDialog ref="detailLogDialog" width="90vw" hideFooter title="日志">
      <div class="log-detail" v-if="logDetail" v-loading="detailLoading">
        {{ logDetail }}
      </div>
      <div v-else>暂无数据</div>
    </BaseDialog>
  </div>
</template>

<script>
import API from '@/api/icredit'
import crud from '@/mixins/crud'
import BaseDialog from '@/views/icredit/components/dialog'
import tableConfiguration from '@/views/icredit/configuration/table/schedule-view-log'
import formOption from '@/views/icredit/configuration/form/schedule-view-log'
import { execStatusMapping } from '@/views/icredit/data-manage/data-sync/contant'

export default {
  mixins: [crud],
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
      },

      // 表格与表单参数
      formOption,
      mixinSearchFormConfig: {
        models: {
          taskStatus: '',
          execTime: []
        }
      },
      fetchConfig: {
        retrieve: { url: '/dolphinscheduler/dispatch/log/page', method: 'post' }
      }
    }
  },

  methods: {
    open(row) {
      this.titleName = row.taskName
      this.logDetail = ''
      this.taskId = row.taskId
      this.mixinRetrieveTableData()
      this.$refs.baseDialog.open()
    },

    interceptorsRequestRetrieve(params) {
      const { taskStatus, execTime, ...rest } = params
      const newParams = {
        taskStatus,
        taskId: this.taskId,
        execTimeStart: execTime?.length ? execTime[0] : '',
        execTimeEnd: execTime?.length
          ? execTime[1] + 24 * 60 * 60 * 1000 - 1
          : '',
        ...rest
      }
      return newParams
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

<style lang="scss" scoped>
.log-dialog {
  ::v-deep {
    .iframe-layout-basic-header {
      padding: 0 !important;
    }

    .h100,
    .iframe-layout-basic-container {
      min-height: unset;
    }

    .el-dialog__body {
      max-height: 72vh;
    }

    .el-dialog {
      .iframe-form-item {
        width: 24%;
      }

      .iframe-form-btn {
        width: unset;
      }
    }
  }
}
</style>
