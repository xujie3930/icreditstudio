<!--
 * @Description: 综合分析
 * @Date: 2021-10-25
-->
<template>
  <div class="analysis-page w100 h100">
    <header class="analysis-page-header">
      <h4 class="title">数据质量综合情况分析</h4>
      <div>
        <el-button type="primary">导出为PDF</el-button>
        <el-button type="primary">导出为EXCEL</el-button>
      </div>
    </header>

    <div class="analysis-page-title">
      <h1 class="title">数据质量综合情况分析</h1>
      <h3 class="date">统计时间：2021.9.29</h3>
    </div>

    <Collapse title="各规则类型问题数占比">
      <div class="collapse-content">
        <div class="collapse-content-item">
          <h3 class="title">规则类型问题数占比</h3>
          <div class="chart"></div>
        </div>
        <div class="collapse-content-item">
          <h3 class="title">规则类型问题数排行</h3>
          <j-table
            ref="viewLogTable"
            style="width: 80%"
            v-loading="tableLoading"
            :table-data="tableData"
            :table-configuration="tableConfiguration"
          >
          </j-table>
        </div>
      </div>
    </Collapse>
  </div>
</template>

<script>
import Collapse from '@/views/icredit/components/collapse'
import tableConfiguration from '@/views/icredit/configuration/table/govern-analysis-question'

export default {
  components: { Collapse },

  data() {
    return {
      tableLoading: false,
      tableData: [],
      tableConfiguration
    }
  }
}
</script>

<style lang="scss" scoped>
.analysis-page {
  width: 100%;
  background-color: #fff;

  &-header {
    @include flex(row, space-between);
    height: 60px;
    margin: 0 16px;
    border-bottom: 1px dashed #d9d9d9;

    .title {
      height: 14px;
      font-size: 14px;
      font-family: SourceHanSansCN, SourceHanSansCN-Regular;
      font-weight: 400;
      color: #000;
    }
  }

  &-title {
    @include flex(column);
    height: 120px;

    .title {
      font-size: 22px;
      font-family: SourceHanSansCN, SourceHanSansCN-Medium;
      font-weight: 500;
      text-align: left;
      color: #000;
    }

    .date {
      font-size: 14px;
      font-family: PingFangSC, PingFangSC-Regular;
      font-weight: 400;
      text-align: left;
      color: #262626;
      margin-top: 15px;
    }
  }

  .collapse-content {
    @include flex(row, flex-start);
    margin-top: 26px;

    &-item {
      width: 50%;
      .title {
        margin-bottom: 20px;
      }
    }
  }
}
</style>
