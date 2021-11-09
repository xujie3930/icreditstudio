<!--
 * @Description: 空间设置
 * @Date: 2021-08-17
-->

<template>
  <div class="workspace-setting h100">
    <crud-basic
      class="user-container"
      ref="crud"
      :form-items-search="mixinSearchFormItems"
      :form-func-search="mixinSearchFormFunc"
      :form-config-search="mixinSearchFormConfig"
      :form-items-dialog="mixinDialogFormItems"
      :form-func-dialog="mixinDialogFormFunc"
      :form-config-dialog="mixinDialogFormConfig"
      :table-configuration="tableConfiguration"
      :dialog-type="mixinDialogType"
      :dialog-visible.sync="mixinDialog"
      :handleAdd="mixinHandleAdd"
      :handleSearch="mixinHandleSearch"
      :handleReset="mixinHandleReset"
      :handleImport="mixinHandleImport"
      :handleExport="mixinHandleExport"
      :handleUpdate="mixinHandleCreateOrUpdate"
      :handleCancel="mixinHandleCancel"
      @handleAddWorkspace="handleAddWorkspace"
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
          <!-- 空间状态 -->
          <template #statusColumn="{row}">
            <span :style="{ color: !row.status ? '#52c41a' : '#ff4d4f' }">
              {{ !row.status ? '启用' : '停用' }}
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
              <el-button
                v-if="workspaceCreateAuth"
                type="text"
                @click="handleOperateClick(row, 'Delete')"
              >
                删除
              </el-button>
              <el-button
                type="text"
                @click="handleOperateClick(row, 'Enabled')"
              >
                启用
              </el-button>
              <el-button
                v-if="workspaceCreateAuth"
                type="text"
                @click="handleOperateClick(row, 'Edit')"
              >
                编辑
              </el-button>
            </div>
          </template>
        </j-table>
      </template>
    </crud-basic>

    <Dialog ref="tipDialog" @on-confirm="handleOperate" />
  </div>
</template>

<script>
import crud from '@/mixins/crud'
import workspace from '@/mixins/workspace'
import operate from '@/mixins/operate'
import tableConfiguration from '@/views/icredit/configuration/table/workspace-setting'
import formOption from '@/views/icredit/configuration/form/workspace-setting'
import Dialog from '@/views/icredit/components/message'
import { mapGetters } from 'vuex'

export default {
  mixins: [crud, operate, workspace],
  components: { Dialog },

  data() {
    return {
      formOption,
      mixinSearchFormConfig: {
        models: { name: '', updateTime: '', updateUser: '' }
      },
      fetchConfig: { retrieve: { url: '/workspace/pageList', method: 'post' } }
    }
  },

  computed: {
    ...mapGetters('user', ['userInfo', 'workspaceCreateAuth']),
    tableConfiguration() {
      return tableConfiguration(this)
    }
  },

  created() {
    this.mixinRetrieveTableData()
  },

  methods: {
    interceptorsRequestRetrieve(params) {
      const newParams = {
        userId: this.userInfo.id,
        spaceId: this.workspaceId,
        ...params
      }
      // this.workspaceId === 'all' && delete newParams.spaceId
      return newParams
    },

    handleAddWorkspace() {
      this.$router.push('/workspace/detail')
    },

    // 操作弹窗提示 - 确认操作
    handleOperate(opType, row) {
      const { id, status } = row
      const params =
        opType === 'Delete' ? { id } : { id, status: status ? 0 : 1 }
      const methodName =
        opType === 'Delete' ? 'workspaceDelete' : 'workspaceUpdate'
      this[`handle${opType}Click`](methodName, params, 'tipDialog')
    },

    handleOperateClick(row, opType) {
      let options
      const { id, status } = row
      const params = { id, status: status ? 0 : 1 }
      switch (opType) {
        case 'View':
          this.$router.push({
            path: '/workspace/detail',
            query: { opType: 'view', id }
          })
          break
        case 'Enabled':
          this.handleEnabledClick('workspaceUpdate', params, 'tipDialog')
          break
        case 'Edit':
          this.$router.push({ path: '/workspace/detail', query: { id } })
          break

        case 'Delete':
          options = {
            row,
            opType,
            title: '工作空间删除',
            afterOperateMsg: '吗？',
            beforeOperateMsg:
              '删除工作空间后，工作空间内的项目和工作流都将删除，请谨慎操作。确认要删除'
          }
          this.$refs.tipDialog.open(options)
          break

        case 'Disabled':
          options = {
            opType,
            row,
            title: '工作空间停用',
            afterOperateMsg: '吗？',
            beforeOperateMsg:
              '停用工作空间后，工作空间中的项目和工作流都不再进行调度，请谨慎操作。确认要停用'
          }
          this.$refs.tipDialog.open(options)
          break

        default:
          this.$refs.tipDialog.open(opType, row)
          break
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.workspace-setting {
  width: 100%;
  height: 100%;

  ::v-deep {
    .iframe-label .iframe-form-label {
      width: 104px;
    }
  }
}
</style>
