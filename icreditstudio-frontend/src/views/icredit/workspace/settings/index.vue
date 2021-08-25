<!--
 * @Description: 空间设置
 * @Date: 2021-08-17
-->

<template>
  <div class="workspace-setting">
    <crud-basic
      class="user-container"
      ref="crud"
      title="工作空间列表"
      :form-items-search="mixinSearchFormItems"
      :form-func-search="mixinSearchFormFunc"
      :form-config-search="mixinSearchFormConfig"
      :form-items-dialog="mixinDialogFormItems"
      :form-func-dialog="mixinDialogFormFunc"
      :form-config-dialog="mixinDialogFormConfig"
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
          <template #statusColumn="{row}">
            <span :style="{ color: row.status ? '#52c41a' : '#ff4d4f' }">
              {{ row.status ? '启用' : '停用' }}
            </span>
          </template>
        </j-table>
      </template>
    </crud-basic>

    <Dialog ref="settingDialog" />
  </div>
</template>

<script>
import crud from '@/mixins/crud'
import tableConfiguration from '@/views/icredit/configuration/table/workspace-setting'
import formOption from '@/views/icredit/configuration/form/workspace-setting'
import Dialog from './dialog'

export default {
  mixins: [crud],

  components: { Dialog },

  data() {
    return {
      formOption,
      mixinSearchFormConfig: {
        models: {
          userName: '',
          accountIdentifier: '',
          telPhone: '',
          orgList: []
        },
        retrieveModels: {
          userId: ''
        }
      },
      mixinDialogFormConfig: {
        models: {
          userName: '',
          userCode: '',
          userBirth: '',
          sortNumber: '',
          telPhone: '',
          accountIdentifier: '',
          userGender: '',
          deleteFlag: 'N',
          orgList: [],
          userRemark: ''
        },
        rule: {
          userName: [
            { required: true, message: '用户姓名不能为空', trigger: 'blur' }
          ],
          accountIdentifier: [
            { required: true, message: '账号不能为空', trigger: 'blur' }
          ],
          telPhone: [
            { pattern: /^1[0-9]{10}$/, message: '请输入正确的手机号码' }
          ],
          orgList: [
            {
              required: true,
              message: '部门不能为空',
              trigger: ['change', 'blur']
            }
          ]
        }
      },
      tableConfiguration: tableConfiguration(this),
      fetchConfig: {
        retrieve: {
          url: '/workspace/pageList',
          method: 'post'
        }
      }
    }
  },

  created() {
    this.mixinRetrieveTableData()
  },

  methods: {
    handleAddWorkspace() {
      this.$router.push('/workspace/detail')
    },

    handleDeleteClick(row) {
      console.log(row, 'row')
    },

    handleOperateClick(row, opType) {
      console.log(row, 'row', opType)
      switch (opType) {
        case 'view':
          this.$router.push('/workspace/detail')
          break
        default:
          this.$refs.settingDialog.open(opType, 'xxxx工作空间')
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
