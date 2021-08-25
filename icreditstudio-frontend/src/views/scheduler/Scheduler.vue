<template>
  <crud-basic
    ref="crud"
    title="任务列表"
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
  >
  </crud-basic>
</template>
<script>
import crud from '@/mixins/crud'
import { deepClone } from '@/utils/util'

import tableConfiguration from '@/views/scheduler/configuration/table/scheduler-table'
import formOption from '@/views/scheduler/configuration/form/scheduler-form'

export default {
  name: 'Scheduler',
  mixins: [crud],
  data() {
    return {
      formOption,
      tableConfiguration: tableConfiguration(this),
      mixinSearchFormConfig: {
        models: {
          jobName: ' ',
          jobGroup: ' ',
          jobClassName: ' ',
          triggerName: ' ',
          triggerGroup: ' ',
          cronExpression: ' ',
          startTime: ' ',
          endTime: ' ',
          nextFireTime: ' ',
          prevFireTime: ' '
        }
      },
      mixinDialogFormConfig: {
        models: {
          jobName: ' ',
          jobGroup: ' ',
          jobClassName: ' ',
          triggerName: ' ',
          triggerGroup: ' ',
          cronExpression: ' ',
          startTime: ' ',
          endTime: ' ',
          nextFireTime: ' ',
          prevFireTime: ' '
        }
      },
      fetchConfig: {
        retrieve: {
          url: '/system/job/queryjob',
          method: 'get'
        },
        create: {
          url: '/system/job/addjob',
          method: 'post'
        },
        update: {
          url: '/system/job/reschedulejob',
          method: 'post'
        },
        delete: {
          url: '/system/job/deletejob',
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
  }
}
</script>
