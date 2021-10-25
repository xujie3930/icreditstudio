<!--
 * @Author: lizheng
 * @Description: 卡片
 * @Date: 2021-10-22
-->
<template>
  <el-card class="icredit-card">
    <header class="icredit-card-header">
      <span>{{cardConfig.title}}</span>
      <el-tooltip
        effect="dark"
        :content="cardConfig.tooltip"
        placement="left"
      >
        <div></div><j-svg class="j-svg" name="icon-close"></j-svg></div>
      </el-tooltip>
    </header>

    <section class="icredit-card-section">
      <span class="count">{{cardConfig.count}}</span>
      <span class="unit">{{cardConfig.unit}}</span>
    </section>

    <footer class="icredit-card-footer">
      <slot v-if="footer" name="footer"/>
      <template>
        <div class="footer-item">
          <span>{{cardConfig.leftLabel}}</span>
          <j-svg class="j-svg" :name="cardConfig.leftIcon"></j-svg>
          <span>{{cardConfig.leftValue}}</span>
        </div>
        <div class="footer-item">
          <span>{{cardConfig.rightLabel}}</span>
          <j-svg class="j-svg" :name="cardConfig.rightIcon"></j-svg>
          <span>{{cardConfig.rightValue}}</span>
        </div>
      </template>
    </footer>
  </el-card>
</template>

<script>
export default {
  data() {},
  props: {
    footer: {
      type: Boolean,
      default: false
    },

    cardConfig: {
      type: Array,
      default: () => ({})
    }
  }
}
</script>

<style lang="scss" scoped>
.icredit-card {
  flex: 1;
  min-width: 300px;
  height: 166px;
  margin-right: 16px;
  background-color: #fff;

  &-header {
    @include flex(row, space-between);
    font-size: 14px;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: rgba(0, 0, 0, 0.45);

    .j-svg {
      width: 16px;
      height: 16px;
      cursor: pointer;
    }
  }

  &-section {
    margin: 16px 0;

    .count {
      height: 38px;
      font-size: 30px;
      font-family: HelveticaNeue, HelveticaNeue-Regular;
      font-weight: 400;
      text-align: left;
      color: rgba(0,0,0,0.85);
      line-height: 38px;
    }

    .unit {
      height: 20px;
      font-size: 14px;
      font-family: PingFangSC, PingFangSC-Regular;
      font-weight: 400;
      text-align: left;
      color: #333333;
      line-height: 20px;
      margin-left: 10px;
    }
  }

  &-footer {
    @include flex(row, space-between);
    width: 100%;
    font-size: 14px;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    color: rgba(0, 0, 0, 0.65);

    .footer-item {
      @include flex;

      .j-svg {
        margin: 0 4px;
      }
    }
  }

  &:last-child {
    margin-right: 0;
  }

  ::v-deep {
    .el-card__body {
      padding: 24px;
    }
  }
}
</style>
