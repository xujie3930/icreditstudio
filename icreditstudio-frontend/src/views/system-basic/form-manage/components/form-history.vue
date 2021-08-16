<!--
 * @Author: lizheng
 * @Description: 表单历史版本列表弹窗
 * @Date: 2021-07-16
-->
<template>
  <cus-dialog
    form
    ref="formHistory"
    width="80vw"
    title="版本列表"
    :visible="visible"
    @on-close="close"
  >
    <crud-basic
      ref="crud"
      :hide-menu="true"
      :hide-header="true"
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
      :handleImport="mixinHandleImport"
      :handleExport="mixinHandleExport"
      :handleSearch="mixinHandleSearch"
      :handleReset="mixinHandleReset"
      :handleUpdate="mixinHandleCreateOrUpdate"
      :handleCancel="mixinHandleCancel"
      :handleSelectChange="mixinHandleSelectChange"
      @handleMultipleDelete="mixinHandleMultipleDelete"
    />

    <footer style="text-align:right; margin-top: 20px">
      <el-button type="small" @click="close">
        返回
      </el-button>
    </footer>

    <form-preview ref="formHistoryPreview" dialog-title="历史表单预览" />
  </cus-dialog>
</template>

<script>
import crud from '@/mixins/crud'
import CusDialog from './cus-dialog'
import FormPreview from './form-preview'
import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-form-history'
import { formHistoryDelete } from '@/api/system-basic/form-manage'

export default {
  mixins: [crud],
  components: { CusDialog, FormPreview },

  data() {
    return {
      visible: false,
      tableConfiguration: tableConfiguration(this),
      fetchConfig: {
        retrieve: {
          url: '/form/hi/definition/hiPage',
          method: 'post'
        }
      },
      formParams: { formId: '' }
    }
  },

  methods: {
    open(row) {
      this.visible = true
      this.formParams.formId = row.id
      this.mixinRetrieveTableData()
    },

    close() {
      this.visible = false
      this.mixinTablePagination.currentPage = 1
      this.mixinTablePagination.pageSize = 10
    },

    // 预览
    handleFormPreview({ row }) {
      this.$refs.formHistoryPreview.open(row, 'formHistoryPreview')
    },

    // 删除
    handleFormDelete({ row }) {
      const { id, formVersion } = row
      // 发布状态下
      const delteMsg =
        row.formStatus === 'P'
          ? '该表单处于发布中，请确认是否删除？'
          : '请确认是否删除该版本？'
      this.$confirm(delteMsg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          formHistoryDelete({ id, formVersion }).then(({ success }) => {
            if (success) {
              this.$notify.success({ message: '删除成功！', duration: 2500 })
              this.mixinRetrieveTableData()
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },

    // 导出
    handleFormExport({ row }) {
      console.log(row)
      this.$refs.formHistoryPreview.open(row, 'formHistoryPreview', true)
    },

    interceptorsRequestRetrieve(params) {
      return { ...params, ...this.formParams }
    }
  }
}
</script>
