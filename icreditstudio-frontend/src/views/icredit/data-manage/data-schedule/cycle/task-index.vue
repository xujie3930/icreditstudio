<!--
 * @Author: lizheng
 * @Description: 任务
 * @Date: 2021-09-23
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
import formOption from '@/views/icredit/configuration/form/schedule-cycle-task'
import tableConfiguration from '@/views/icredit/configuration/table/schedule-cycle-task'

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
