<!--
 * @Author: lizheng
 * @Description: 治理调度
 * @Date: 2021-10-13
-->
<template>
  <ScheduleList
    ref="scheduleList"
    :form-option="formOption"
    :table-configuration="tableConfiguration"
  />
</template>

<script>
import ScheduleList from '../schedule-list'
import formOption from '@/views/icredit/configuration/form/schedule-sync-task'
import tableConfiguration from '@/views/icredit/configuration/table/schedule-sync-task'

export default {
  components: { ScheduleList },

  data() {
    return {
      formOption,
      tableConfiguration: tableConfiguration(this)
    }
  },

  methods: {
    handleViewLog(row) {
      console.log('row', row, this.$refs)
      this.$refs.scheduleList.$refs.viewLog.$refs.baseDialog.open()
    },

    handleOpenDagPage(row) {
      console.log('row', row)
      this.$router.push('/data-manage/data-schedule/dag')
    }
  }
}
</script>
