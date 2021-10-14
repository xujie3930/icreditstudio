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
    <Message ref="message" @on-confirm="handleConfirm" />
  </div>
</template>

<script>
import ViewLog from './view'
import Message from '@/views/icredit/components/message'

import crud from '@/mixins/crud'

export default {
  name: 'schedulePageList',

  mixins: [crud],

  components: { ViewLog, Message },

  data() {
    return {
      mixinTableData: [
        { syncTaskName: 'ss', taskStatus: 0 },
        { syncTaskName: 'sss', taskStatus: 1 }
      ]
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

  methods: {
    handleConfirm(option) {
      this.$emit('confirm', option)
      this.$refs.message.close()
    }
  }
}
</script>
