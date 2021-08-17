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
      @handleExportTemplate="handleExportTemplate"
    >
    </crud-basic>
  </div>
</template>

<script>
import crud from '@/mixins/crud'
import tableConfiguration from '@/views/icredit/configuration/table/workspace-setting'
import formOption from '@/views/icredit/configuration/form/workspace-setting'

export default {
  mixins: [crud],

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
      tableConfiguration: tableConfiguration(this)
    }
  }
}
</script>

<style lang="scss" scoped>
.workspace-setting {
  width: 100%;
  height: 100%;
  background: red;

  ::v-deep {
    .iframe-label .iframe-form-label {
      width: 104px;
    }
  }
}
</style>
