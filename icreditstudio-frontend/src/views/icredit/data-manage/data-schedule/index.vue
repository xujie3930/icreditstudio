<!--
 * @Author: lizheng
 * @Description: 调度中心
 * @Date: 2021-09-23
-->
<template>
  <div class="schedule w100">
    <div class="schedule-header">
      <div class="title">
        <span class="left">近72小时内的调度情况</span>
      </div>
      <section class="content schedule-header-content">
        <div
          class="tab-item"
          v-for="(item, idx) in scheduleSituation"
          :key="item"
        >
          <div class="count">
            <span :class="['num', idx === 1 ? 'err' : '']"
              >{{ item.value }}
            </span>
            <span class="unit">{{ item.unit }}</span>
          </div>
          <div class="name">{{ item.name }}</div>
        </div>
      </section>
    </div>

    <div class="schedule-chart">
      <div class="schedule-chart-left">
        <div class="title">
          <span class="left">近一天运行时长排行</span>
          <span class="right">上次更新： 2021-07-06</span>
        </div>
      </div>
      <div class="schedule-chart-right">
        <div class="title">
          <span class="left">近一天运行时长排行</span>
          <span class="right">上次更新： 2021-07-06</span>
        </div>
      </div>
    </div>

    <div class="schedule-footer">
      <div class="schedule-footer-left">
        <div class="title">
          <span class="left">近一天运行时长排行</span>
          <span class="right">上次更新： 2021-07-06</span>
        </div>
        <div class="content">
          <j-table
            ref="leftTable"
            v-loading="lfTableLoading"
            :table-configuration="lfTableConfiguration"
            :table-data="lfTableData"
          ></j-table>
        </div>
      </div>

      <div class="schedule-footer-right">
        <div class="title">
          <span class="left">近一月运行出错排行</span>
          <span class="right">上次更新： 2021-07-06</span>
        </div>
        <div class="content">
          <j-table
            ref="rightTable"
            v-loading="rgTableLoading"
            :table-configuration="rgTableConfiguration"
            :table-data="rgTableData"
          ></j-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import lfTableConfiguration from '@/views/icredit/configuration/table/data-schedule-runtime'
import rgTableConfiguration from '@/views/icredit/configuration/table/data-schedule-runerror'

export default {
  data() {
    return {
      lfTableConfiguration,
      rgTableConfiguration,
      lfTableData: [],
      rgTableData: [],
      lfTableLoading: false,
      rgTableLoading: false,
      scheduleSituation: [
        { key: '', value: 25, name: '总调度任务数', unit: '个' },
        { key: '', value: 5, name: '执行失败实例', unit: '个' },
        { key: '', value: 15000, name: '新增数据量条数', unit: '万条' },
        { key: '', value: 0, name: '新增总数据量', unit: 'KB' },
        { key: '', value: 8, name: '实时任务记录速度', unit: 'RPS ' }
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
@mixin header {
  position: relative;
  background: #fff;
  padding: 14px 0;
  margin-bottom: 12px;

  .title {
    @include flex(row, space-between, center);
    font-size: 14px;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #262626;
    line-height: 20px;
    margin: 0 16px;

    .right {
      font-size: 13px;
      color: #999;
    }
  }

  .content {
    margin: 18px 16px;
  }

  &::before {
    content: '';
    position: absolute;
    top: 15px;
    left: 0;
    width: 4px;
    height: 18px;
    background: #1890ff;
    border-radius: 0px 2px 2px 0px;
  }
}

.schedule {
  &-header {
    @include header;
    width: 100%;
    height: 143px;

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
          width: 36px;
          height: 42px;
          font-size: 30px;
          font-family: PingFangSC, PingFangSC-Medium;
          font-weight: 500;
          text-align: left;
          color: #262626;
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
    @include flex(row, space-between);

    &-left,
    &-right {
      @include header;
    }

    &-left {
      width: 462px;
      margin-right: 12px;
      overflow: hidden;
    }

    &-right {
      width: calc(100% - 473px);
    }
  }

  &-footer {
    @include flex(row, space-between);

    &-left,
    &-right {
      width: 50%;
      @include header;
    }

    &-left {
      margin-right: 12px;
      overflow: hidden;
    }

    &-left {
      overflow: hidden;
    }
  }
}
</style>
