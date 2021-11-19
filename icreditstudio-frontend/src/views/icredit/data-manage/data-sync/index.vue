<!--
 * @Description: 数据同步
 * @Date: 2021-08-23
-->

<template>
  <div class="data-sync w100">
    <crud-basic
      class="user-container"
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
      @handleAddSyncTask="handleAddSyncTask"
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
          <template #execStatusColumn="{row: {execStatus}}">
            <span
              :style="{
                color: [0, 1, 2].includes(execStatus)
                  ? execStatusMapping[execStatus].color
                  : '#606266'
              }"
            >
              {{
                [0, 1, 2].includes(execStatus)
                  ? execStatusMapping[execStatus].label
                  : '-'
              }}
            </span>
          </template>

          <!-- 操作按钮 -->
          <template #operationColumn="{row}">
            <el-button type="text" @click="handleViewBtnClick(row, 'View')">
              查看
            </el-button>
            <el-button
              v-if="row.taskStatus === 0"
              type="text"
              @click="handleDisabledBtnClick(row, 'Disabled')"
            >
              停用
            </el-button>
            <el-button
              v-if="row.taskStatus === 1"
              type="text"
              @click="handleEnabledBtnClick(row, 'Enabled')"
            >
              启用
            </el-button>
            <el-button
              type="text"
              v-if="row.taskStatus === 0 && row.execStatus !== 2"
              @click="handleRunBtnClick(row, 'Run')"
            >
              {{ row.execMode ? '执行' : '立即执行' }}
            </el-button>
            <el-button
              type="text"
              v-if="row.taskStatus === 0 && row.execStatus === 2"
              @click="handleStopBtnClick(row, 'Stop')"
            >
              停止执行
            </el-button>
            <el-button
              type="text"
              v-if="row.taskStatus !== 0"
              @click="handleEditBtnClick(row, 'Edit')"
            >
              编辑
            </el-button>
            <el-button
              v-if="[1, 2].includes(row.taskStatus)"
              type="text"
              @click="handleDeleteBtnClick(row, 'Delete')"
            >
              删除
            </el-button>
          </template>
        </j-table>
      </template>
    </crud-basic>

    <Message ref="operateMessage" @on-confirm="messageOperateCallback" />
    <Detail ref="dataDetail" />
  </div>
</template>

<script>
import crud from '@/mixins/crud'
import operate from '@/mixins/operate'
import workspace from '@/mixins/workspace'
import tableConfiguration from '@/views/icredit/configuration/table/data-manage-sync'
import formOption from '@/views/icredit/configuration/form/data-manage-sync'
import Message from '@/views/icredit/components/message'
import Detail from './detail'
import API from '@/api/icredit'
import { taskStatusMapping, execStatusMapping } from './contant'

export default {
  mixins: [crud, workspace, operate],
  components: { Message, Detail },

  data() {
    return {
      isSyncClick: false,
      timerId: null,
      sliderVal: 100,

      // 表格与表单参数
      formOption,
      mixinSearchFormConfig: {
        models: {
          taskName: '',
          taskStatus: '',
          execStatus: ''
        }
      },
      tableConfiguration,
      fetchConfig: { retrieve: { url: '/datasync/syncTasks', method: 'post' } },

      // 任务状态值映射
      taskStatusMapping,

      // 执行状态值映射
      execStatusMapping
    }
  },

  created() {
    this.mixinRetrieveTableData()
  },

  beforeDestroy() {
    this.handleClearInterval()
  },

  methods: {
    handleAddSyncTask() {
      this.$router.push('/data-manage/add-task?opType=add')
    },

    // 删除
    handleDeleteBtnClick(row, opType) {
      const options = {
        row,
        opType,
        title: '删除同步任务',
        beforeOperateMsg: '删除同步任务',
        afterOperateMsg: '后，同步任务不会在任务列表中展示，确认删除吗？',
        name: row.taskName
      }
      this.$refs.operateMessage.open(options)
    },

    // 查看操作
    handleViewBtnClick(row, opType) {
      this.$refs.dataDetail.open({ row, opType })
    },

    // 启用
    handleEnabledBtnClick(row) {
      this.handleEnabledClick('dataSyncEnabled', { taskId: row.taskId })
    },

    // 停用
    handleDisabledBtnClick(row, opType) {
      const options = {
        row,
        opType,
        title: '停用数据同步任务',
        beforeOperateMsg: '停用同步任务',
        afterOperateMsg:
          '后，不再从源表写入数据到目标表，即目标表将不再更新，确认停用吗？',
        name: row.taskName
      }
      this.$refs.operateMessage.open(options)
    },

    // 执行
    handleRunBtnClick(row) {
      const { taskId } = row
      const params = { taskId }
      API.dataSyncRun(params).then(({ success, data }) => {
        if (success && data) {
          this.$notify.success({
            title: '操作结果',
            message: '操作成功，执行结果请到调度中心查看！'
          })
          this.mixinRetrieveTableData()

          // 轮询
          this.handleClearInterval()
          this.getTableData()
        }
      })
    },

    // 停止
    handleStopBtnClick(row, opType) {
      console.log(row, opType)
      API.dataSyncStop({ taskId: row.taskId }).then(({ success, data }) => {
        if (success && data) {
          this.$notify.success({
            title: '操作结果',
            message: '任务已停止执行！'
          })
          this.mixinRetrieveTableData()
        }
      })
    },

    // 编辑
    handleEditBtnClick(row, opType) {
      console.log(row, opType)
      const params = {
        path: '/data-manage/add-task',
        query: { opType: 'edit', taskId: row.taskId }
      }
      this.$router.push(params)
    },

    // 清空定时器
    handleClearInterval() {
      this.timerId && clearInterval(this.timerId)
    },

    // 轮询查询表格数据
    getTableData() {
      this.timerId = setInterval(() => {
        this.mixinRetrieveTableData()
      }, 5000)
    },

    // 弹窗提示回调函数
    messageOperateCallback(opType, row) {
      console.log(row, opType, 'row')
      const methodName = `dataSync${opType}`
      const params = {
        Disabled: { taskId: row.taskId },
        Delete: { taskId: row.taskId }
      }
      this[`handle${opType}Click`](methodName, params[opType], 'operateMessage')
    }
  }
}
</script>
