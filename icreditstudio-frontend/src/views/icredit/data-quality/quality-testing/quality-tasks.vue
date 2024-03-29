<!--
 * @Author: lizheng
 * @Description: 质检任务
 * @Date: 2021-10-25
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
      @handleAddCustomRules="handleAddCustomRules"
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
          <!-- 操作按钮 -->
          <template #operationColumn="{row}">
            <el-button type="text" @click="handleViewClick(row, 'View')">
              查看
            </el-button>
            <el-button type="text" @click="handleViewClick(row, 'View')">
              停用
            </el-button>
            <el-button type="text" @click="handleViewClick(row, 'View')">
              启用
            </el-button>
            <el-button type="text" @click="handleAddCustomRules(row, 'Edit')">
              编辑
            </el-button>
            <el-button type="text" @click="handleDeleteBtnClick(row, 'Delete')">
              删除
            </el-button>
            <el-button type="text" @click="handleDeleteBtnClick(row, 'Delete')">
              停止执行
            </el-button>
            <el-button type="text" @click="handleDeleteBtnClick(row, 'Delete')">
              立即执行
            </el-button>
          </template>
        </j-table>
      </template>
    </crud-basic>

    <ViewRules ref="viewRules" />
    <Message ref="message" @on-confirm="handleConfirm" />
  </div>
</template>

<script>
import Message from '@/views/icredit/components/message'
import ViewRules from './view-rules'

import crud from '@/mixins/crud'
import workspace from '@/mixins/workspace'

import formOption from '@/views/icredit/configuration/form/govern-quality-tasks'
import tableConfiguration from '@/views/icredit/configuration/table/govern-quality-tasks'

import {
  taskStatusMapping,
  execStatusMapping
} from '@/views/icredit/data-manage/data-sync/contant'

export default {
  name: 'schedulePageList',
  mixins: [crud, workspace],
  components: { ViewRules, Message },

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

  created() {
    this.mixinRetrieveTableData()
  },

  methods: {
    handleConfirm(option) {
      this.$emit('confirm', option)
      this.$refs.message.close()
    },

    // 新增自定义规则
    handleAddCustomRules() {
      this.$router.push('/data-quality/rule-category/add-rules')
    },

    // 查看
    handleViewClick(row) {
      this.$refs.viewRules.open(row)
    },

    // 删除
    handleDeleteBtnClick(row, opType) {
      const options = {
        row,
        opType,
        title: '删除质量规则',
        beforeOperateMsg: '删除质量任务',
        afterOperateMsg:
          '后，所有引用了该规则的质检任务将默认不再执行，危险操作请谨慎处理，确认删除吗？',
        name: row.taskName
      }
      this.$refs.message.open(options)
    },

    handleReRuningTask(row) {
      console.log(row)
      this.$message.success({
        duration: 5000,
        center: true,
        offset: 200,
        message: '重跑任务已提交，稍后请在日志中查看执行结果!'
      })
    },

    handleStopTask(row) {
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
      this.$refs.message.open(options)
    }
  }
}
</script>
