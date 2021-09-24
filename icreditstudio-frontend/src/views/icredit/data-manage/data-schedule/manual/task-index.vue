<!--
 * @Description: 手动任务调度-手动任务
 * @Date: 2021-09-23
-->
<template>
  <ScheduleList
    :form-option="formOption"
    :table-configuration="tableConfiguration"
  />
</template>

<script>
import ScheduleList from '@/views/icredit/components/schedule-list'
import formOption from '@/views/icredit/configuration/form/schedule-manual-task'
import tableConfiguration from '@/views/icredit/configuration/table/schedule-manual-task'

export default {
  components: { ScheduleList },

  data() {
    return {
      formOption,
      tableConfiguration: tableConfiguration(this)
    }
  }
}
</script>
