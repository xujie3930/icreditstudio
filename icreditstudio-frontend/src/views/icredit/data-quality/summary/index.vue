<!--
 * @Description: 概览-治理首页
 * @Date: 2021-10-22
-->
<template>
  <div class="govern-page w100 h100">
    <header class="govern-page-header"><Card /><Card /><Card /><Card /></header>

    <section class="govern-page-section">
      <div class="chart-left" v-loading="runtimeDataLoading">
        <div class="title">
          <span class="left">数据质检任务</span>
        </div>
        <div id="pieChart" style="height:300px"></div>
      </div>

      <div class="chart-right">
        <div class="title">
          <span class="left">质检问题分类</span>
        </div>
        <div class="right-wrap" v-loading="countDataLoading">
          <j-table
            ref="leftTable"
            v-loading="lfTableLoading"
            :table-configuration="lfTableConfiguration"
            :table-data="lfTableData"
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
          v-loading="lfTableLoading"
          :table-configuration="lfTableConfiguration"
          :table-data="lfTableData"
        ></j-table>
      </div>
    </footer>
  </div>
</template>

<script>
import Card from '@/views/icredit/components/card'
import lfTableConfiguration from '@/views/icredit/configuration/table/data-schedule-runtime'

export default {
  components: { Card },

  data() {
    return {
      lfTableConfiguration,
      lfTableData: [],
      lfTableLoading: false
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
      flex: 1;
      height: 334px;
      background-color: #fff;

      .right-wrap {
        margin: 16px;
      }
    }

    .chart-left {
      width: 556px;
      margin-right: 16px;
    }
  }

  &-footer {
    @include card-header;
    padding-top: 0;
    background-color: #fff;
  }
}
</style>
