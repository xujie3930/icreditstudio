<!--
 * @Author: lizheng
 * @Description: 调度列表
 * @Date: 2021-09-24
-->
<template>
  <div class="w100 h100">
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
      :handleSearch="mixinHandleSearch"
      :handleReset="mixinHandleReset"
      :handleAdd="mixinHandleAdd"
    >
      <template #content>
        <j-table
          v-loading="mixinTableLoading"
          :table-configuration="tableConfiguration"
          :table-pagination="mixinTablePagination"
          :table-data="mixinTableData"
          @handleSizeChange="mixinHandleSizeChange"
          @handleCurrentChange="mixinHandleCurrentChange"
        >
          <!-- <template #empty>
            <Empty />
          </template> -->

          <!-- 任务状态 -->
          <template #taskStatusColumn="{row: {taskStatus}}">
            <span :style="{ color: taskStatusMapping[taskStatus || 0].color }">
              {{
                [0, 1, 2].includes(taskStatus)
                  ? taskStatusMapping[taskStatus].label
                  : ''
              }}
            </span>
          </template>

          <!-- 执行状态 -->
          <template #dispatchStatusColumn="{row: {dispatchStatus}}">
            <span
              :style="{
                color: [0, 1, 2].includes(dispatchStatus)
                  ? execStatusMapping[dispatchStatus].color
                  : '#606266'
              }"
            >
              {{
                [0, 1, 2].includes(dispatchStatus)
                  ? execStatusMapping[dispatchStatus].label
                  : '-'
              }}
            </span>
          </template>

          <!-- 操作按钮 -->
          <template #operationColumn="{row}">
            <el-button
              type="text"
              v-if="row.dispatchStatus === 1"
              @click="handleReRuningTask(row, '0')"
            >
              重跑
            </el-button>
            <el-button
              v-if="row.dispatchStatus === 2"
              type="text"
              @click="handleStopTask(row, '1')"
            >
              终止
            </el-button>
            <el-button type="text" @click="handleViewLog(row, 'historyLog')">
              历史日志
            </el-button>
          </template>
        </j-table>
      </template>
    </crud-basic>
    <ViewLog ref="viewLog" />
    <Message ref="message" @on-confirm="handleMessageCallback" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import ViewLog from '../view'
import Message from '@/views/icredit/components/message'
import crud from '@/mixins/crud'
import workspace from '@/mixins/workspace'
import formOption from '@/views/icredit/configuration/form/schedule-sync-task'
import tableConfiguration from '@/views/icredit/configuration/table/schedule-sync-task'
import API from '@/api/icredit'
import {
  taskStatusMapping,
  execStatusMapping
} from '@/views/icredit/data-manage/data-sync/contant'

export default {
  name: 'schedulePageList',
  mixins: [crud, workspace],
  components: { ViewLog, Message },

  data() {
    return {
      formOption,
      tableConfiguration,
      searchFormConfig: {
        models: {
          taskName: '',
          taskStatus: '',
          dispatchStatus: '',
          dispatchType: '',
          scheduleTime: []
        }
      },
      fetchConfig: {
        retrieve: {
          url: '/dolphinscheduler/dispatch/page',
          method: 'post'
        }
      },
      taskStatusMapping,
      execStatusMapping
    }
  },

  computed: {
    ...mapGetters('user', ['userInfo'])
  },

  created() {
    this.mixinRetrieveTableData()
  },

  methods: {
    // 弹窗提示回调
    handleMessageCallback(type, row) {
      const params = { taskId: row.taskId, execType: 1 }
      API.dataScheduleSyncOperate(params)
        .then(({ success }) => {
          if (success) {
            this.$message.success('同步任务终止成功！')
            this.$refs.message.close()
          }
        })
        .finally(() => {
          this.$refs.message.btnLoadingClose()
        })
    },

    // 历史日志
    handleViewLog(row) {
      this.$refs.viewLog.open(row)
    },

    // 重跑
    handleReRuningTask({ taskId }, execType) {
      const params = { taskId, execType }
      API.dataScheduleSyncOperate(params)
        .then(({ success, data }) => {
          if (success && data) {
            this.$message.success({
              duration: 5000,
              center: true,
              offset: 200,
              message: '重跑任务已提交，稍后请在日志中查看执行结果!'
            })
          }
        })
        .finally(() => {})
    },

    // 终止
    handleStopTask(row) {
      const options = {
        row,
        name: row.taskName,
        opType: 'Stop',
        title: '终止同步任务',
        afterTitleName: row.taskName,
        beforeOperateMsg: '终止同步任务',
        afterOperateMsg:
          '后，当前同步任务将杀掉进程且宣告任务失败，确认要终止吗？'
      }
      this.$refs.message.open(options)
    },

    // 表格请求接口参数拦截
    interceptorsRequestRetrieve(params) {
      const { id: currLoginUserId } = this.userInfo
      const { workspaceId } = this
      const { scheduleTime, ...restParams } = params
      const dispatchStartTime = scheduleTime?.length ? scheduleTime[0] : ''
      const dispatchEndTime = scheduleTime?.length
        ? scheduleTime[1] + 24 * 60 * 60 * 1000 - 1
        : ''
      return {
        workspaceId,
        currLoginUserId,
        dispatchStartTime,
        dispatchEndTime,
        ...restParams
      }
    }
  }
}
</script>
