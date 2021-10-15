<!--
 * @Description: 数据源管理
 * @Date: 2021-08-17
-->

<template>
  <div class="datasource">
    <crud-basic
      class="user-container"
      ref="crud"
      title="数据源列表"
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
      @handleAddDataSource="handleAddDataSource"
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
          <!-- 最近一次同步状态 -->
          <template #lastSyncStatusColumn="{row}">
            <span
              :style="{
                color: !row.lastSyncStatus
                  ? '#52c41a'
                  : row.lastSyncStatus === 1
                  ? '#ff4d4f'
                  : '#606266'
              }"
            >
              {{
                !row.lastSyncStatus
                  ? '成功'
                  : row.lastSyncStatus === 1
                  ? '失败'
                  : '-'
              }}
            </span>
          </template>

          <!-- 操作按钮 -->
          <template #operationColumn="{row}">
            <div v-if="!row.status">
              <el-button type="text" @click="handleOperateClick(row, 'View')">
                查看
              </el-button>
              <el-button type="text" @click="handleSyncClick(row, 'Sync')">
                同步
              </el-button>
              <el-button type="text" @click="handleDisabledBtnClick(row)">
                停用
              </el-button>
            </div>

            <div v-else>
              <el-button type="text" @click="handleOperateClick(row, 'Edit')">
                编辑
              </el-button>
              <el-button
                type="text"
                @click="handleDeleteBtnClick(row, 'Delete')"
              >
                删除
              </el-button>
              <el-button
                type="text"
                @click="handleOperateClick(row, 'Enabled')"
              >
                启用
              </el-button>
            </div>
          </template>
        </j-table>
      </template>
    </crud-basic>

    <div class="source-slider" v-if="isSyncClick">
      <div :class="[isSyncStatus ? '' : 'red-bar', 'bar']"></div>
      <div :class="[isSyncStatus ? '' : 'red-text', 'text']">
        {{
          isSyncStatus
            ? `100% 同步成功，新增${syncDataCount}张表！`
            : '0% 同步失败，请重试！'
        }}
      </div>
    </div>

    <Message ref="operateMessage" @on-confirm="messageOperateCallback" />
    <Detail ref="dataSourceDetail" :footer="true" />
    <!-- 新增数据源 - 选择数据类型弹窗 -->
    <AddDataSourceStepFirst
      ref="addStepFirst"
      @on-confirm="addDatasourceCallback"
    />
    <!-- 新增或编辑数据源 - 表单类型弹窗 -->
    <AddDataSourceStepSecond
      ref="addStepSecond"
      @on-confirm="addDatasourceCallback"
    />
  </div>
</template>

<script>
import crud from '@/mixins/crud'
import operate from '@/mixins/operate'
import workspace from '@/mixins/workspace'
import tableConfiguration from '@/views/icredit/configuration/table/workspace-datasource'
import formOption from '@/views/icredit/configuration/form/workspace-datasource'
import Message from '@/views/icredit/components/message'
import Detail from './detail'
import AddDataSourceStepFirst from './add-step-first'
import AddDataSourceStepSecond from './add-step-second'
import API from '@/api/icredit'

export default {
  mixins: [crud, operate, workspace],
  components: {
    Message,
    Detail,
    AddDataSourceStepFirst,
    AddDataSourceStepSecond
  },

  data() {
    return {
      timerId: null,
      isSyncClick: false,
      isSyncStatus: true,
      syncDataCount: 0,

      btnViewLoading: false,
      btnEditLoading: false,

      formOption,
      mixinSearchFormConfig: {
        models: { name: '', type: '', status: '' }
      },
      tableConfiguration,
      fetchConfig: {
        retrieve: {
          url: '/datasource/pageList',
          method: 'post'
        }
      }
    }
  },

  created() {
    this.mixinRetrieveTableData()
  },

  methods: {
    interceptorsRequestRetrieve(params) {
      return {
        spaceId: this.workspaceId,
        ...params
      }
    },

    // 新增数据源
    handleAddDataSource() {
      this.$refs.addStepFirst.open()
    },

    // 停用
    handleDisabledBtnClick(row) {
      const options = {
        row,
        opType: 'Disabled',
        title: '数据源停用',
        beforeOperateMsg:
          '停用后在工作流设置中不能再使用该数据源，确认停用该数据源吗？'
        // afterOperateMsg: '）在调度，请先下线工作流后再停用。'
      }
      this.$refs.operateMessage.open(options)
    },

    // 删除
    handleDeleteBtnClick(row) {
      const options = {
        row,
        opType: 'Delete',
        title: '数据源删除',
        beforeOperateMsg:
          '数据源删除后将不在列表中展示，且不再参与工作流调度，确认删除吗？',
        afterOperateMsg: ''
      }
      this.$refs.operateMessage.open(options)
    },

    // 同步
    handleSyncClick(row) {
      this.timerId = null
      this.isSyncStatus = true
      this.syncDataCount = 0
      API.datasourceSync(row.id)
        .then(({ success, data }) => {
          if (success) {
            this.syncDataCount = data
            this.$notify.success({
              title: '操作提示',
              message: '数据源同步成功！'
            })
            this.mixinRetrieveTableData()
          }
        })
        .catch(() => {
          this.isSyncStatus = false
        })
        .finally(() => {
          this.isSyncClick = true
          this.timerId = setTimeout(() => {
            this.isSyncClick = false
          }, 2500)
        })
    },

    // 操作列
    handleOperateClick(row, opType) {
      const { id, status } = row
      const params = { id, status: status ? 0 : 1 }
      switch (opType) {
        case 'View':
          this.handleEditClick('datasourceDetail', id, opType)
          break
        case 'Enabled':
          this.handleEnabledClick('datasourceUpdate', params)
          break
        case 'Edit':
          this.handleEditClick('datasourceDetail', id, opType, 'addStepSecond')
          break
        default:
          this.$refs.operateMessage.open(opType, row)
          break
      }
    },

    // 弹窗提示回调函数
    messageOperateCallback(opType, row) {
      const { id, status } = row
      const params =
        opType === 'Delete' ? { id } : { id, status: status ? 0 : 1 }
      const methodName =
        opType === 'Delete' ? 'datasourceDelete' : 'datasourceUpdate'
      this[`handle${opType}Click`](methodName, params, 'operateMessage')
    },

    // 添加数据源的回调
    addDatasourceCallback(success) {
      success && this.mixinRetrieveTableData()
    },

    // 查看详情
    mixinDetailInfo(data, opType) {
      opType === 'View' && this.$refs.dataSourceDetail.open({ data, opType })
      opType === 'Edit' && this.$refs.addStepSecond.openEdit({ data, opType })
    }
  }
}
</script>

<style lang="scss" scoped>
.datasource {
  position: relative;
  width: 100%;
  height: 100%;

  .source-slider {
    display: inline-block;
    position: absolute;
    height: 30px;
    top: 100px;
    right: 150px;
    line-height: 30px;
    padding-top: 5px;

    .bar {
      display: inline-block;
      width: 320px;
      height: 6px;
      line-height: 6px;
      border-radius: 4px;
      background-color: #52c41a;
    }

    .red-bar {
      background-color: #ff4d4f;
    }

    .text {
      display: inline-block;
      margin-left: 10px;
      color: #52c41a;
    }

    .red-text {
      color: #ff4d4f;
    }
  }

  ::v-deep {
    .iframe-label .iframe-form-label[title='数据源自定义名称'] {
      width: 150px;
    }
  }
}
</style>
