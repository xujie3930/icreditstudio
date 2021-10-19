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
      <!-- 操作按钮 -->
      <template #operationColumn="{row}">
        <!-- v-if="row.dispatchStatus === '执行中'" -->
        <el-button type="text" @click="handleStopTask(row, 'stop')">
          终止
        </el-button>
        <el-button type="text" @click="handleReRuningTask(row, 'rerunning')">
          重跑
        </el-button>
        <el-button type="text" @click="handleViewLog(row, 'historyLog')">
          查看日志
        </el-button>
      </template>
    </crud-basic>
    <ViewLog ref="viewLog" />
    <Message ref="message" @on-confirm="handleConfirm" />
  </div>
</template>

<script>
import ViewLog from './view'
import Message from '@/views/icredit/components/message'

import crud from '@/mixins/crud'
import workspace from '@/mixins/workspace'

import formOption from '@/views/icredit/configuration/form/schedule-sync-task'
import tableConfiguration from '@/views/icredit/configuration/table/schedule-sync-task'

export default {
  name: 'schedulePageList',

  mixins: [crud, workspace],

  components: { ViewLog, Message },

  data() {
    return {
      mixinTableData: [
        { taskName: 'ss', taskStatus: 0 },
        { taskName: 'sss', taskStatus: 1 }
      ],

      formOption,
      tableConfiguration
    }
  },

  props: {
    fetchConfiguration: {
      type: Object,
      default: () => ({})
    },

    searchConfiguration: {
      type: Object,
      default: () => ({})
    }
  },

  watch: {
    fetchConfiguration: {
      immediate: true,
      deep: true,
      handler(nVal) {
        this.fetchConfig = nVal
      }
    },

    searchConfiguration: {
      immediate: true,
      deep: true,
      handler(nVal) {
        this.mixinSearchFormConfig = nVal
      }
    }
  },

  // created() {
  //   this.mixinRetrieveTableData()
  // },

  methods: {
    handleConfirm(option) {
      this.$emit('confirm', option)
      this.$refs.message.close()
    },

    handleViewLog({ row }) {
      console.log('row', row, this.$refs)
      this.$refs.scheduleList.$refs.viewLog.open(row)
    },

    handleReRuningTask({ row }) {
      console.log(row)
      this.$message.success({
        duration: 5000,
        center: true,
        offset: 200,
        message: '重跑任务已提交，稍后请在日志中查看执行结果!'
      })
    },

    handleStopTask({ row }) {
      console.log(row, 'row')
      const options = {
        row,
        name: row.syncTaskName,
        opType: 'Delete',
        title: '终止同步任务',
        afterTitleName: row.syncTaskName,
        beforeOperateMsg: '终止同步任务',
        afterOperateMsg:
          '后，当前同步任务将杀掉进程且宣告任务失败，确认要终止吗'
      }
      this.$refs.scheduleList.$refs.message.open(options)
    },

    // 表格请求接口参数拦截
    interceptorsRequestRetrieve(params) {
      const { workspaceId } = this
      const { scheduleTime, ...restParams } = params
      const dispatchStartTime = scheduleTime?.length ? scheduleTime[0] : ''
      const dispatchEndTime = scheduleTime?.length
        ? scheduleTime[1] + 24 * 60 * 60 * 1000 - 1
        : ''
      return { workspaceId, dispatchStartTime, dispatchEndTime, ...restParams }
    }
  }
}
</script>
