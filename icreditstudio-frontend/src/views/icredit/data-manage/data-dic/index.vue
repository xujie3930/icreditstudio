<!--
 * @Description: 字典表
 * @Date: 2021-09-27
-->
<template>
  <div class="w100 h100">
    <crud-basic
      ref="crud"
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
    >
    </crud-basic>
  </div>
</template>

<script>
import tableConfiguration from '@/views/icredit/configuration/table/data-manage-dictionary'
import formOption from '@/views/icredit/configuration/form/data-manage-dictionary'

import crud from '@/mixins/crud'

export default {
  name: 'schedulePageList',

  mixins: [crud],

  data() {
    return {
      formOption,
      mixinSearchFormConfig: {
        models: { name: '' }
      },
      tableConfiguration: tableConfiguration(this)
    }
  },

  methods: {}
}
</script>
