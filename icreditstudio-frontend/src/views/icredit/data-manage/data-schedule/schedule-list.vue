<!--
 * @Author: lizheng
 * @Description: 调度列表
 * @Date: 2021-09-24
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
    <ViewLog ref="viewLog" />
  </div>
</template>

<script>
import ViewLog from './view'
import crud from '@/mixins/crud'

export default {
  name: 'schedulePageList',

  mixins: [crud],

  components: { ViewLog },

  data() {
    return {
      mixinTableData: [{ userName: 'ss' }]
    }
  },

  props: {
    formOption: {
      type: Array,
      default: () => []
    },

    tableConfiguration: {
      type: Object,
      default: () => {}
    },

    fetchConfiguration: {
      type: Object,
      default: () => {}
    }
  },

  watch: {
    fetchConfiguration: {
      deep: true,
      handler(nVal) {
        this.fetchConfig = nVal
      }
    }
  },

  methods: {}
}
</script>
