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

            <!-- 操作按钮 -->
            <template #operationColumn="{row}">
              <el-button
                type="text"
                v-if="row.taskInstanceState === 2"
                @click="handleStopTask(row)"
              >
                终止
              </el-button>
              <el-button
                v-else
                :disabled="taskRow.taskStatus === 0"
                type="text"
                @click="handleReRuningTask(row)"
              >
                重跑
              </el-button>
              <el-button type="text" @click="handleViewLogDetail(row)">
                查看日志
              </el-button>
            </template>
          </j-table>
        </template>
      </crud-basic>
    </BaseDialog>

    <BaseDialog ref="detailLogDialog" width="90vw" hideFooter title="日志">
      <div
        class="log-detail"
        v-if="logDetail"
        v-loading="detailLoading"
        v-html="logDetail"
      ></div>
      <div v-else>暂无数据</div>
    </BaseDialog>

    <Message ref="message" @on-confirm="handleMessageCallback" />
  </div>
</template>

<script>
import API from '@/api/icredit'
import crud from '@/mixins/crud'
import BaseDialog from '@/views/icredit/components/dialog'
import tableConfiguration from '@/views/icredit/configuration/table/schedule-view-log'
import formOption from '@/views/icredit/configuration/form/schedule-view-log'
import { execStatusMapping } from '@/views/icredit/data-manage/data-sync/contant'
import Message from '@/views/icredit/components/message'

export default {
  mixins: [crud],
  components: { BaseDialog, Message },

  data() {
    return {
      taskRow: {},
      execStatusMapping,
      title: '历史执行情况',
      taskId: null,
      titleName: '',
      logDetail: '',
      detailLoading: false,
      tableConfiguration,

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
      console.log(row, 'row')
      this.taskRow = row
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

    // 重跑
    handleReRuningTask({ processInstanceId }) {
      const params = { processInstanceId, execType: 0 }
      API.dataScheduleSyncOperate(params).then(({ success, data }) => {
        if (success && data) {
          this.$message.success({
            duration: 5000,
            center: true,
            offset: 200,
            message: '重跑任务已提交，稍后请在日志中查看执行结果!'
          })
        }
        this.mixinRetrieveTableData()
      })
    },

    // 终止
    handleStopTask(row) {
      const options = {
        row,
        name: row.taskInstanceName,
        opType: 'Stop',
        title: '终止同步任务',
        afterTitleName: row.taskInstanceName,
        beforeOperateMsg: '终止同步任务',
        afterOperateMsg:
          '后，当前同步任务将杀掉进程且宣告任务失败，确认要终止吗？'
      }
      this.$refs.message.open(options)
    },

    // 终止操作弹窗提示回调
    handleMessageCallback(type, row) {
      const { processInstanceId } = row
      const params = { processInstanceId, execType: 1 }
      API.dataScheduleSyncOperate(params)
        .then(({ success }) => {
          if (success) {
            this.$message.success('同步任务终止成功！')
            this.$refs.message.close()
            this.mixinRetrieveTableData()
          }
        })
        .finally(() => {
          this.$refs.message.btnLoadingClose()
        })
    },

    // 日志详情
    handleViewLogDetail(row) {
      this.getLogDetailData(row.taskInstanceId)
      this.$refs.detailLogDialog.open()
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
  white-space: pre-wrap;

  ::v-deep {
    .iframe-layout-basic-header {
      padding: 0 !important;
    }

    .h100,
    .iframe-layout-basic-container {
      min-height: unset;
    }

    .el-dialog__body {
      max-height: 75vh;
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
