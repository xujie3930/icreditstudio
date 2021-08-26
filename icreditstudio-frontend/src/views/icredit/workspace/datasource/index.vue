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
    </crud-basic>

    <div class="source-slider" v-if="isSyncClick">
      <div class="bar"></div>
      <div class="text">同步成功，新增10张表</div>
    </div>

    <Dialog ref="dataSourceDialog" />
    <Detail ref="dataSourceDetail" :footer="true" />
    <AddDataSourceStepFirst ref="addStepFirst" />
  </div>
</template>

<script>
import crud from '@/mixins/crud'
import tableConfiguration from '@/views/icredit/configuration/table/workspace-datasource'
import formOption from '@/views/icredit/configuration/form/workspace-datasource'
import Dialog from './tip-dialog'
import Detail from './detail'
import AddDataSourceStepFirst from './add-step-first'

export default {
  mixins: [crud],
  components: { Dialog, Detail, AddDataSourceStepFirst },

  data() {
    return {
      isSyncClick: false,
      sliderVal: 100,
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
    handleAddDataSource() {
      this.$refs.addStepFirst.open()
    },

    handleDeleteClick(row) {
      console.log(row, 'row')
    },

    // 查看操作
    handleDetailClick(row, opType) {
      console.log('row', row)
      this.$refs.dataSourceDetail.open({ row, opType })
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

    .text {
      display: inline-block;
      margin-left: 10px;
      color: #52c41a;
    }
  }

  ::v-deep {
    .iframe-label .iframe-form-label[title='数据源自定义名称'] {
      width: 150px;
    }
  }
}
</style>
