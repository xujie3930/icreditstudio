<!--
 * @Author: lizheng
 * @Description: 实例
 * @Date: 2021-09-23
-->

<template>
  <ScheduleList
    :form-option="formOption"
    :table-configuration="tableConfiguration"
  />
</template>

<script>
import ScheduleList from '../schedule-list'
import formOption from '@/views/icredit/configuration/form/schedule-manual-instance'
import tableConfiguration from '@/views/icredit/configuration/table/schedule-cycle-instance'

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
