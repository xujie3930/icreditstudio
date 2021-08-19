<!--
 * @Description: 数据源管理
 * @Date: 2021-08-17
-->

<template>
  <div class="workspace-setting">
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
    </crud-basic>

    <Dialog ref="dataSourceDialog" />
    <AddDataSourceStepFirst ref="addStepFirst" />
  </div>
</template>

<script>
import crud from '@/mixins/crud'
import tableConfiguration from '@/views/icredit/configuration/table/workspace-datasource'
import formOption from '@/views/icredit/configuration/form/workspace-datasource'
import Dialog from './dialog'
import AddDataSourceStepFirst from './add-step-first'

export default {
  mixins: [crud],
  components: { Dialog, AddDataSourceStepFirst },

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
          url: '/user/user/pageList',
          method: 'post'
        }
      }
    }
  },

  created() {
    this.mixinRetrieveTableData()
  },

  methods: {
    handleAddDataSource() {
      this.$refs.addStepFirst.open()
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
          this.$refs.dataSourceDialog.open(opType, 'xxxx工作空间')
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
  background: red;

  ::v-deep {
    .iframe-label .iframe-form-label[title='数据源自定义名称'] {
      width: 150px;
    }
  }
}
</style>
