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
    handleViewLog({ row }) {
      console.log('row', row, this.$refs)
      this.$refs.scheduleList.$refs.viewLog.open(row)
    },

    handleReRuningTask({ row }) {
      console.log(row)
      this.$message.success({
        duration: 5000,
        center: true,
        offset: 200,
        message: '重跑任务已提交，稍后请在日志中查看执行结果!'
      })
    },

    handleStopTask({ row }) {
      console.log(row, 'row')
      const options = {
        row,
        name: row.syncTaskName,
        opType: 'Delete',
        title: '终止同步任务',
        afterTitleName: row.syncTaskName,
        beforeOperateMsg: '终止同步任务',
        afterOperateMsg:
          '后，当前同步任务将杀掉进程且宣告任务失败，确认要终止吗'
      }
      this.$refs.scheduleList.$refs.message.open(options)
    }
  }
}
</script>
