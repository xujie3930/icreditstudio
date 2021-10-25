<!--
 * @Description: 概览-治理首页
 * @Date: 2021-10-22
-->
<template>
  <div class="govern-page w100 h100">
    <header class="govern-page-header">
      <Card :card-config="taskCardConfig" />
      <Card :card-config="taskCardConfig" />
      <Card :card-config="taskCardConfig" />
      <Card :card-config="taskCardConfig" />
    </header>

    <section class="govern-page-section">
      <div class="chart-left">
        <div class="title">
          <span class="left">数据质检任务</span>
        </div>
        <div id="pieChart" style="height:300px"></div>
        <div class="radio-group">
          <el-button-group>
            <el-button
              v-for="btn in btnGroupConfig"
              :key="btn.model"
              :type="radio === btn.model ? 'primary' : 'default'"
              @click="handleChangeTab(btn.model)"
              >{{ btn.label }}</el-button
            >
          </el-button-group>
        </div>
      </div>

      <div class="chart-right">
        <div class="title">
          <span class="left">质检问题分类</span>
          <span class="right">统计时间：2021.8.29-2021.9.29</span>
        </div>
        <div class="right-wrap">
          <j-table
            ref="leftTable"
            v-loading="rhTableLoading"
            :table-configuration="rhTableConfiguration"
            :table-data="rhTableData"
          ></j-table>
        </div>
      </div>
    </section>

    <footer class="govern-page-footer">
      <div class="title">
        <span class="left">数据准确性分析</span>
      </div>
      <div class="content">
        <j-table
          ref="leftTable"
          v-loading="btTableLoading"
          :table-configuration="btTableConfiguration"
          :table-data="btTableData"
        ></j-table>
      </div>
    </footer>
  </div>
</template>

<script>
import Card from '@/views/icredit/components/card'
import btTableConfiguration from '@/views/icredit/configuration/table/data-govern-accuracy'
import rhTableConfiguration from '@/views/icredit/configuration/table/data-govern-category'
// import { deepClone } from '@/utils/util'
import { renderChart } from '@/utils/echarts'
import { optionsMapping } from './contant'

export default {
  components: { Card },

  data() {
    return {
      rhTableConfiguration,
      btTableConfiguration,
      btTableData: [],
      rhTableData: [],
      btTableLoading: false,
      rhTableLoading: false,
      radio: 'yesterday',
      btnGroupConfig: [
        { label: '昨天', model: 'yesterday' },
        { label: '本周', model: 'week' },
        { label: '本月', model: 'month' }
      ],

      taskCardConfig: {
        title: '总质检任务数',
        tooltip: '总质检任务数是平台上在质检检测中的质检任务的个数',
        count: 126560,
        unit: '个',
        leftIcon: 'icon-decline',
        rightIcon: 'icon-incline',
        leftValue: '12%',
        rightValue: '11%',
        leftLabel: '周同比',
        rightLabel: '日环比'
      }
    }
  },

  mounted() {
    this.initPage()
  },

  methods: {
    initPage(id = 'pieChart') {
      renderChart(id, optionsMapping[id])
    },

    handleChangeTab(model) {
      this.radio = model
    }
  }
}
</script>

<style lang="scss" scoped>
.govern-page {
  width: 100%;

  &-header {
    @include flex;
  }

  &-section {
    @include flex(row, space-between);
    margin: 16px 0;

    .chart-left,
    .chart-right {
      @include card-header;
      height: 334px;
      background-color: #fff;

      .right-wrap {
        margin: 16px;
      }
    }

    .chart-left {
      position: relative;
      width: 556px;
      margin-right: 16px;
      margin-bottom: 0;

      .radio-group {
        position: absolute;
        top: 30px;
        right: 40px;
      }
    }

    .chart-right {
      width: calc(100% - 572px);
      margin-bottom: 0;
    }
  }

  &-footer {
    @include card-header;
    background-color: #fff;
  }
}
</style>
