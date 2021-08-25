<template>
  <div class="log h100 w100">
    <crud-basic
      ref="crud"
      title="审计日志"
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
      :dialog-title="mixinDialogTitle"
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
    ></crud-basic>
  </div>
</template>
<script>
import dayjs from 'dayjs'
import crud from '@/mixins/crud'
import { deepClone, handleTrim } from 'utils/util'
import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-audit-log'
import formOption from '@/views/system-basic/configuration/form/manage/manage-audit-log'

const OPRATE_TYPE_ENUMS = {
  C: '新增',
  R: '删除',
  U: '更新',
  D: '查询'
}
const OPRAT_ERESULT_ENUMS = {
  S: '成功',
  F: '失败'
}
export default {
  name: 'ManageAuditLog',

  mixins: [crud],

  data() {
    return {
      formOption,
      tableConfiguration: tableConfiguration(this),

      mixinSearchFormConfig: {
        models: {
          userName: '',
          oprateInfo: '',
          operateTime: []
        }
      },

      mixinDialogFormConfig: {
        models: {
          userName: '',
          oprateType: '',
          oprateInfo: '',
          createTime: '',
          operateTime: '',
          oprateResult: ''
        }
      },

      fetchConfig: {
        retrieve: {
          url: 'log/auditlog/pageList',
          method: 'post'
        }
      }
    }
  },

  created() {
    this.mixinSearchFormItems = deepClone(this.formOption).filter(
      e => e.isSearch
    )
    this.mixinRetrieveTableData()
  },

  methods: {
    // 请求拦截-参数处理
    interceptorsRequestRetrieve(params) {
      const { operateTime, ...restParams } = params
      const startTime = operateTime && operateTime.length ? operateTime[0] : ''
      const endTime =
        operateTime && operateTime.length
          ? operateTime[1] + 24 * 60 * 60 * 1000 - 1
          : ''
      const newParams = { startTime, endTime, ...restParams }
      return handleTrim(newParams)
    },

    // 详情弹窗-渲染表单参数处理
    interceptorsBeforeView(params) {
      return params.map(({ label, type, ...item }) => {
        if (label === '操作内容') return { type: 'textarea', label, ...item }
        return { label, type, ...item }
      })
    },

    // 数据拦截-修改接口返回的表格数据
    interceptorsResponseTableData(data) {
      return deepClone(data).map(
        ({ oprateTime, oprateType, oprateResult, ...item }) => ({
          oprateType: OPRATE_TYPE_ENUMS[oprateType],
          oprateResult: OPRAT_ERESULT_ENUMS[oprateResult],
          oprateTime: oprateTime
            ? dayjs(Number(oprateTime)).format('YYYY-MM-DD HH:mm:ss')
            : '',
          ...item
        })
      )
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
