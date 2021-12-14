<!--
 * @Description: 字典表
 * @Date: 2021-09-27
-->
<template>
  <div class="w100 h100">
    <crud-basic
      ref="crud"
      title="字典表列表"
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
      @handleAddDict="handleAddDict"
      @handleImportDict="handleImportDict"
    >
    </crud-basic>
    <Message ref="message" @on-confirm="messageOperateCallback" />
    <AddDialog ref="addDialog" @on-confirm="dialogOperateCallback" />
  </div>
</template>

<script>
import tableConfiguration from '@/views/icredit/configuration/table/data-manage-dictionary'
import formOption from '@/views/icredit/configuration/form/data-manage-dictionary'
import Message from '@/views/icredit/components/message'
import AddDialog from './add'
import crud from '@/mixins/crud'
import operate from '@/mixins/operate'

export default {
  name: 'dictionaryTable',
  mixins: [crud, operate],
  components: { Message, AddDialog },

  data() {
    return {
      formOption,
      mixinSearchFormConfig: {
        models: { dictName: '' }
      },
      tableConfiguration: tableConfiguration(this),
      fetchConfig: {
        retrieve: { url: '/datasync/dict/pageList', method: 'post' }
      }
    }
  },

  created() {
    this.mixinRetrieveTableData()
  },

  methods: {
    handleImportDict(options) {
      this.$refs.addDialog.open(options)
    },

    handleAddDict(options) {
      this.$refs.addDialog.open(options)
    },

    handleViewClick() {
      this.$refs.addDialog.open()
    },

    // 打开删除提示弹窗
    handleDeleteDict({ row }) {
      const { englishName } = row
      const options = {
        row,
        name: englishName,
        opType: 'Delete',
        title: `删除字典表${englishName}`,
        beforeOperateMsg: '删除后，',
        afterOperateMsg:
          '将不再在列表中呈现，字段不能再关联该字典表，确认删除吗？'
      }
      this.$refs.message.open(options)
    },

    // 确认提示弹窗确认回调
    messageOperateCallback(opType, { id }) {
      // 字典表删除
      this[`handle${opType}Click`]('dictionaryDelete', { id }, 'message')
    },

    // 新增或编辑字典表弹窗确认回调
    dialogOperateCallback(success) {
      if (success) {
        this.$refs.addDialog.handleClose()
        this.mixinRetrieveTableData()
      }
    }
  }
}
</script>
