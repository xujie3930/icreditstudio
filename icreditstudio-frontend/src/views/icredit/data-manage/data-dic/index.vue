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
    <Message ref="operateMessage" @on-confirm="messageOperateCallback" />
    <AddDialog ref="addDialog" />
  </div>
</template>

<script>
import tableConfiguration from '@/views/icredit/configuration/table/data-manage-dictionary'
import formOption from '@/views/icredit/configuration/form/data-manage-dictionary'

import Message from '@/views/icredit/components/message'
import AddDialog from './add'

import crud from '@/mixins/crud'

export default {
  name: 'schedulePageList',

  mixins: [crud],
  components: { Message, AddDialog },

  data() {
    return {
      formOption,
      mixinSearchFormConfig: {
        models: { name: '' }
      },
      mixinTableData: [{ enName: 'sdsdsds', zhName: '莫得感情的敲代码机器' }],
      tableConfiguration: tableConfiguration(this)
    }
  },

  methods: {
    handleImportDict() {},
    handleAddDict(options) {
      console.log(options, 'kokololo')
      this.$refs.addDialog.open(options)
    },

    handleViewClick() {
      console.log(this.$refs)
      this.$refs.addDialog.open()
    },

    // 删除
    mixinHandleDelete({ row }) {
      const { enName, zhName } = row
      const options = {
        row,
        name: zhName,
        opType: 'Delete',
        title: `删除字典表${enName}`,
        beforeOperateMsg: '删除后，',
        afterOperateMsg:
          '将不再在列表中呈现，字段不能再关联该字典表，确认删除吗？'
      }
      this.$refs.operateMessage.open(options)
    },

    messageOperateCallback() {}
  }
}
</script>
