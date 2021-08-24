<!--
 * @Description: 数据同步
 * @Date: 2021-08-23
-->

<template>
  <div class="data-sync">
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
    </crud-basic>

    <Message ref="operateMessage" />
  </div>
</template>

<script>
import crud from '@/mixins/crud'
import tableConfiguration from '@/views/icredit/configuration/table/data-manage-sync'
import formOption from '@/views/icredit/configuration/form/data-manage-sync'
import Message from '@/views/icredit/components/message'

export default {
  mixins: [crud],
  components: { Message },

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
