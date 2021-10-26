<!--
 * @Description: 质量检测-质检总览
 * @Date: 2021-10-25
-->

<template>
  <div class="quality w100">
    <div class="quality-header" v-loading="roughDataloading">
      <div class="title">
        <span class="left">数据质检总览</span>
      </div>
      <section class="content quality-header-content">
        <div
          class="tab-item"
          v-for="(item, idx) in scheduleSituation"
          :key="idx"
        >
          <div class="count">
            <CountAnimation class="num" :id="item.key" :count="item.value" />
            <!-- <span :class="['num']">{{ item.value }} </span> -->
            <span class="unit">{{ item.unit }}</span>
          </div>
          <div class="name">{{ item.name }}</div>
        </div>
      </section>
    </div>

    <div class="quality-chart">
      <div class="title">
        <span class="left">数据质量情况</span>
      </div>
      <div class="right-wrap" v-loading="countDataLoading">
        <div class="right-wrap-header"></div>
        <div id="lineChart" class="line-chart" style="height:250px"></div>
      </div>
    </div>

    <div class="quality-footer">
      <div class="title">
        <span class="left">数据质检明细</span>
      </div>
      <div class="content">
        <ScrollTable
          ref="table"
          :table-data="lfTableData"
          :table-configuration="lfTableConfiguration"
        ></ScrollTable>
      </div>
    </div>
  </div>
</template>

<script>
import ScrollTable from '@/components/scroll-table'
import CountAnimation from '@/views/icredit/components/count-animation'
import lfTableConfiguration from '@/views/icredit/configuration/table/govern-quality-overview'
import rgTableConfiguration from '@/views/icredit/configuration/table/data-schedule-runerror'
import { renderChart } from '@/utils/echarts'
import { optionsMapping } from './contant'
import API from '@/api/icredit'
import workspace from '@/mixins/workspace'
import dayjs from 'dayjs'

export default {
  mixins: [workspace],

  components: { ScrollTable, CountAnimation },

  data() {
    return {
      lfTableConfiguration,
      rgTableConfiguration,
      lfTableData: [],
      rgTableData: [],
      lfTableLoading: false,
      rgTableLoading: false,
      scheduleSituation: [
        { key: 't', value: 25, name: '总处理数据库', unit: '个' },
        { key: 'f', value: 550, name: '总处理表张数', unit: '张' },
        { key: 'n', value: 5434560, name: '总处理字段个数', unit: '个' },
        { key: 'q', value: 12220, name: '质检发现问题', unit: '个' },
        { key: 'w', value: 332340, name: '质检使用规则数', unit: '个' }
      ],
      date: [],
      scheduleType: '0',
      tabItems: [
        { label: '同步任务', name: '0' },
        { label: '开发任务', name: '1' },
        { label: '治理任务', name: '2' }
      ],
      yesterdayLeft: '',
      yesterdayRight: '',
      pickerOptions: {
        disabledDate: time => {
          const pickTimeStamp = time.getTime()
          const nowTimeStamp = new Date().getTime()
          const yearTimeStamp = new Date().setFullYear(
            new Date().getFullYear() - 1
          )
          return pickTimeStamp > nowTimeStamp || pickTimeStamp < yearTimeStamp
        }
      },

      roughDataloading: false,
      runtimeDataLoading: false,
      countDataLoading: false,
      runDayDataLoading: false,
      errMonthDataLoading: false
    }
  },

  mounted() {
    // this.initPage()
  },

  methods: {
    initPage() {
      // const curTime = new Date().getTime()
      // const oneDayTime = 3600 * 1000 * 24
      // this.date = [curTime - oneDayTime * 7, curTime - oneDayTime]
      // this.getHomeRoughData()
      // this.getHomeRuntimeData()
      // this.getHomeCountData()
      // this.getHomeRunDayData()
      // this.getHomeErrMonthData()
    },

    handleChangTabClick(name) {
      this.scheduleType = name
      this.getHomeCountData()
    },

    handleChangeDate(date) {
      const [sTime, eTime] = date || []
      if (eTime - sTime > 3600 * 1000 * 24 * 30) {
        this.$message.error(
          '起始时间与结束时间的跨度不能大于一个月，请重新选择！'
        )
        this.date = []
      } else this.getHomeCountData()
    },

    // 获取近72小时内的调度情况数据
    getHomeRoughData() {
      const { workspaceId, scheduleSituation } = this
      this.roughDataloading = true
      API.dataScheduleHomeRough({ workspaceId })
        .then(({ success, data }) => {
          if (success) {
            this.scheduleSituation = scheduleSituation.map(
              ({ key, value, ...rest }) => {
                return {
                  key,
                  value: data[key],
                  ...rest
                }
              }
            )
          }
        })
        .finally(() => {
          this.roughDataloading = false
        })
    },

    // 获取当天运行情况数据
    getHomeRuntimeData(id = 'pieChart') {
      const { workspaceId } = this
      const chartInstance = renderChart(id, optionsMapping[id])

      this.runtimeDataLoading = true
      API.dataScheduleHomeRuntime({ workspaceId })
        .then(({ success, data }) => {
          if (success) {
            this.yesterdayLeft = dayjs(
              new Date().getTime() - 24 * 60 * 60 * 1000
            ).format('YYYY-MM-DD')
            chartInstance.setOption({
              series: [
                {
                  data: data.map(({ taskDesc, count }) => ({
                    value: count,
                    name: `${taskDesc}   ${count}`
                  }))
                }
              ]
            })
          }
        })
        .finally(() => {
          this.runtimeDataLoading = false
        })
    },

    // 调度任务数量情况
    getHomeCountData(id = 'lineChart') {
      const { workspaceId, date, scheduleType } = this
      const [schedulerStartTime, schedulerEndTime] = date || []
      const params = {
        workspaceId,
        scheduleType,
        schedulerStartTime,
        schedulerEndTime
      }

      const chartInstance = renderChart(id, optionsMapping[id])
      this.countDataLoading = true
      API.dataScheduleHomeCount(params)
        .then(({ success, data }) => {
          if (success && data?.length) {
            this.yesterdayRight = dayjs(
              new Date().getTime() - 24 * 60 * 60 * 1000
            ).format('YYYY-MM-DD')
            const name = dayjs(data[data.length - 1].date).format('YYYY')
            chartInstance.setOption({
              xAxis: {
                name,
                data: data.map(({ date: d }) => dayjs(d).format('YYYY.MM.DD'))
              },
              series: [{ data: data.map(({ value }) => value) }]
            })
          }
        })
        .finally(() => {
          this.countDataLoading = false
        })
    },

    // 获取近一天运行时长排行数据
    getHomeRunDayData() {
      const { workspaceId } = this
      this.lfTableLoading = true
      this.lfTableData = []
      API.dataScheduleHomeRunDay({ workspaceId })
        .then(({ success, data }) => {
          if (success) {
            this.lfTableData = data
          }
        })
        .finally(() => {
          this.lfTableLoading = false
        })
    },

    // 获取近一月运行出错排行数据
    getHomeErrMonthData() {
      const { workspaceId } = this
      this.rgTableLoading = true
      this.rgTableData = []
      API.dataScheduleHomeErrMonth({ workspaceId })
        .then(({ success, data }) => {
          if (success) {
            this.rgTableData = data
          }
        })
        .finally(() => {
          this.rgTableLoading = false
        })
    },

    mixinChangeWorkspaceId() {
      console.log('this.wokspaceId=', this.workspaceId)
      this.initPage()
    }
  }
}
</script>

<style lang="scss" scoped>
@mixin header {
  position: relative;
  background: #fff;
  padding: 14px 0;
  margin-bottom: 16px;

  .title {
    @include flex(row, space-between, center);
    font-size: 14px;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #262626;
    margin: 0 16px;
    height: 32px;
    line-height: 32px;

    .right {
      font-size: 13px;
      color: #999;
    }

    .tab {
      .tab-item {
        position: relative;
        width: 56px;
        height: 20px;
        font-size: 14px;
        font-family: PingFangSC, PingFangSC-Regular;
        font-weight: 400;
        text-align: left;
        color: #333;
        line-height: 20px;
        margin-left: 14px;
        cursor: pointer;

        &:hover {
          color: #1890ff;
        }
      }

      .tab-item-active {
        &::before {
          position: absolute;
          content: '';
          bottom: -5px;
          left: 13px;
          width: 30px;
          height: 2px;
          background: #1890ff;
          border-radius: 3px;
        }
      }
    }
  }

  .content {
    margin: 18px 16px;
  }

  &::before {
    content: '';
    position: absolute;
    top: 21px;
    left: 0;
    width: 4px;
    height: 18px;
    background: #1890ff;
    border-radius: 0px 2px 2px 0px;
  }
}

.quality {
  &-header {
    @include header;
    width: 100%;
    height: 150px;

    &-content {
      @include flex;
      .tab-item {
        @include flex(column);
        flex: 1;
        position: relative;

        .count {
          margin-bottom: 5px;
        }

        .num {
          display: inline-block;
          font-size: 30px;
          font-family: PingFangSC, PingFangSC-Medium;
          font-weight: 500;
          text-align: left;
          color: #262626;
          margin-right: 5px;
          line-height: 42px;
        }

        .err {
          color: #ff4d4f;
        }

        .unit {
          width: 14px;
          height: 20px;
          font-size: 14px;
          font-family: PingFangSC, PingFangSC-Regular;
          font-weight: 400;
          text-align: left;
          color: #262626;
          line-height: 20px;
        }

        &::after {
          position: absolute;
          right: 0;
          top: 18%;
          content: '';
          width: 1px;
          height: 46px;
          border: 1px solid #d8d8d8;
        }

        &:last-child {
          &::after {
            content: '';
            display: none;
          }
        }
      }
    }
  }

  &-chart {
    @include header;
    height: 340px;
  }

  &-footer {
    @include header;
  }
}
</style>
