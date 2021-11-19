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
              v-if="row.taskStatus === 0"
              type="text"
              @click="handleRunBtnClick(row, 'Run')"
            >
              立即执行
            </el-button>
            <el-button type="text" @click="handleViewLog(row, 'historyLog')">
              历史日志
            </el-button>
          </template>
        </j-table>
      </template>
    </crud-basic>
    <ViewLog ref="viewLog" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import ViewLog from '../view'
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
  components: { ViewLog },

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
    // 历史日志
    handleViewLog(row) {
      this.$refs.viewLog.open(row)
    },

    // 立即执行
    handleRunBtnClick(row) {
      const { taskId } = row
      const params = { taskId }
      API.dataScheduleSyncRun(params).then(({ success, data }) => {
        if (success && data) {
          this.$notify.success({
            title: '操作结果',
            message: '任务立即执行成功!'
          })
          this.mixinRetrieveTableData()
        }
      })
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
