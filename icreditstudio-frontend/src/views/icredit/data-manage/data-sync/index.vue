<!--
 * @Description: 数据同步
 * @Date: 2021-08-23
-->

<template>
  <div class="data-sync w100">
    <crud-basic
      class="user-container"
      ref="crud"
      title="数据同步列表"
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
              {{ taskStatusMapping[taskStatus || 0].label }}
            </span>
          </template>

          <!-- 执行状态 -->
          <template #execStatusColumn="{row: {execStatus}}">
            <span :style="{ color: execStatusMapping[execStatus || 0].color }">
              {{ execStatusMapping[execStatus || 0].label }}
            </span>
          </template>

          <!-- 操作按钮 -->
          <template #operationColumn="{row}">
            <div v-if="!row.status">
              <el-button type="text" @click="handleOperateClick(row, 'View')">
                查看
              </el-button>
              <el-button
                type="text"
                @click="handleOperateClick(row, 'Disabled')"
              >
                停用
              </el-button>
            </div>

            <div v-else>
              <el-button type="text" @click="handleOperateClick(row, 'Delete')">
                删除
              </el-button>
              <el-button
                type="text"
                @click="handleOperateClick(row, 'Enabled')"
              >
                启用
              </el-button>
              <el-button type="text" @click="handleOperateClick(row, 'Edit')">
                编辑
              </el-button>
            </div>
          </template>
        </j-table>
      </template>
    </crud-basic>

    <Message ref="operateMessage" />
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

export default {
  mixins: [crud, operate, workspace],
  components: { Message, Detail },

  data() {
    return {
      isSyncClick: false,
      sliderVal: 100,

      // 表格与表单参数
      formOption,
      mixinSearchFormConfig: {
        models: {
          workspaceId: '',
          taskName: '',
          taskStatus: '',
          execStatus: ''
        }
      },
      tableConfiguration: tableConfiguration(this),
      fetchConfig: { retrieve: { url: '/datasync/syncTasks', method: 'post' } },

      // 任务状态值映射
      taskStatusMapping: {
        0: { label: '启用', color: '#52c41a' },
        1: { label: '草稿', color: '#999' },
        2: { label: '停用', color: '#ff4d4f' }
      },

      // 执行状态值映射
      execStatusMapping: {
        0: { label: '成功', color: '#52c41a' },
        1: { label: '失败', color: '#ff4d4f' },
        2: { label: '执行中', color: '#faad14' }
      }
    }
  },

  created() {
    this.mixinRetrieveTableData()
  },

  methods: {
    handleAddSyncTask() {
      this.$refs.addStepFirst.open()
    },

    // 删除
    handleDeleteClick(row) {
      console.log(row, 'row')
      const options = {
        title: '删除同步任务',
        beforeOperateMsg: '删除同步任务',
        afterOperateMsg: '后，同步任务不会在任务列表中展示，确认删除吗？',
        name: 'XXX同步任务名称'
      }
      this.$refs.operateMessage.open(options)
    },

    // 查看操作
    handleDetailClick(row, opType) {
      console.log('row', row)
      this.$refs.dataDetail.open({ row, opType })
    },

    // 启用
    handleEnabledClick(row) {
      console.log(row)
      this.$message.success({
        type: 'success',
        offset: 200,
        center: true,
        duration: 1500,
        message: '启用成功！'
      })
      // 调用接口
    },

    // 停用
    handleDisabledClick(row) {
      console.log(row, 'raw')
      const options = {
        title: '停用数据同步任务',
        beforeOperateMsg: '停用同步任务',
        afterOperateMsg:
          '后，不再从源表写入数据到目标表，即目标表将不再更新，确认停用吗？',
        name: '同步任务名称'
      }
      this.$refs.operateMessage.open(options)
    },

    // 同步
    handleSyncClick(row) {
      console.log(row)
      this.isSyncClick = true
      // 调用接口
      setTimeout(() => {
        this.isSyncClick = false
      }, 3000)
    },

    handleOperateClick(row, opType) {
      console.log(row, 'row', opType)
      this.$refs.dataSourceDialog.open(opType, 'xxxx工作空间')
    }
  }
}
</script>
