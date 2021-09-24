<!--
 * @Author: lizheng
 * @Description: 任务
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
      console.log('row', row)
    },

    handleOpenDagPage(row) {
      console.log('row', row)
      this.$router.push('/data-manage/data-schedule/dag')
    }
  }
}
</script>
