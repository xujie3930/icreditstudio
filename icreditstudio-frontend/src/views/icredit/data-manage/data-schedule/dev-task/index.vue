<!--
 * @Description: 手动任务调度-手动任务
 * @Date: 2021-09-23
-->
<template>
  <ScheduleList
    ref="scheduleList"
    :form-option="formOption"
    :fetch-configuration="fetchConfiguration"
    :table-configuration="tableConfiguration"
  />
</template>

<script>
import ScheduleList from '../schedule-list'
import formOption from '@/views/icredit/configuration/form/schedule-manual-task'
import tableConfiguration from '@/views/icredit/configuration/table/schedule-manual-task'

export default {
  components: { ScheduleList },

  data() {
    return {
      formOption,
      tableConfiguration: tableConfiguration(this),
      fetchConfiguration: {
        retrieve: {
          url: '/dolphinscheduler/dispatch/page',
          method: 'post'
        }
      }
    }
  },

  // created() {
  //   this.$nextTick(() => {
  //     this.$refs.scheduleList.mixinRetrieveTableData()
  //   })
  // },

  methods: {}
}
</script>
