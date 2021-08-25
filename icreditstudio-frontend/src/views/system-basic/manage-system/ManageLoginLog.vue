<template>
  <div class="w100 h100 log">
    <crud-basic
      ref="crud"
      title="登录日志"
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
    ></crud-basic>
  </div>
</template>

<script>
import crud from '@/mixins/crud'
import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-login-log'
import formOption from '@/views/system-basic/configuration/form/manage/manage-login-log'
import dayjs from 'dayjs'
import { deepClone, handleTrim } from 'utils/util'

export default {
  name: 'ManageLoginLog',

  mixins: [crud],

  data() {
    return {
      formOption,
      tableConfiguration,

      // 搜索表单参数
      mixinSearchFormConfig: {
        models: {
          userAccount: '',
          userName: '',
          loginTime: []
        }
      },

      // 接口请求参数
      fetchConfig: {
        retrieve: {
          url: '/system/log/loginlog/pageList',
          method: 'post'
        }
      }
    }
  },

  created() {
    this.mixinRetrieveTableData()
  },

  methods: {
    // 时间格式化
    handleTimeFormat(date, formatStr = 'YYYY-MM-DD HH:mm:ss') {
      return date ? dayjs(Number(date)).format(formatStr) : ''
    },

    // 表格请求接口参数拦截
    interceptorsRequestRetrieve(params) {
      const { loginTime, ...restParams } = params
      const startTime = loginTime && loginTime.length ? loginTime[0] : ''
      const endTime =
        loginTime && loginTime.length
          ? loginTime[1] + 24 * 60 * 60 * 1000 - 1
          : ''
      const newParams = { startTime, endTime, ...restParams }
      return handleTrim(newParams)
    },

    // 处理表格接口返回的参数
    interceptorsResponseTableData(data) {
      return deepClone(data).map(({ loginTime, logoutTime, ...item }) => ({
        loginTime: this.handleTimeFormat(loginTime),
        logoutTime: this.handleTimeFormat(logoutTime),
        ...item
      }))
    }
  }
}
</script>

<style lang="scss">
.log {
  .el-range__icon,
  .el-range-separator,
  .el-range__close-icon {
    line-height: 24px !important;
  }
}
</style>
