<!--
 * @Author: lizheng
 * @Description: 图
 * @Date: 2021-09-06
-->
<template>
  <div class="figure">
    <div class="figure-wrap">
      <div class="figure-box">
        <span class="name"> h_data_source</span>
        <span class="type">mysql</span>
      </div>
      <FigureLine icon-name="all-link" />
      <div class="figure-box">
        <span class="name"> h_data_source</span>
        <span class="type">mysql</span>
      </div>
    </div>

    <div class="center-line">
      <FigureLine class="left" icon-name="left-link" />
      <FigureLine class="right" icon-name="cover-link" />
    </div>

    <div class="figure-wrap">
      <div class="figure-box">
        <span class="name"> h_data_source</span>
        <span class="type">mysql</span>
      </div>

      <div class="figure-lines"></div>
      <div class="figure-box">
        <span class="name"> h_data_source</span>
        <span class="type">mysql</span>
      </div>
    </div>
  </div>
</template>

<script>
import FigureLine from './line'

export default {
  components: {
    FigureLine
  },

  data() {
    return {}
  },

  props: {
    dataSource: {
      type: Array,
      default: () => []
    }
  }
}
</script>

<style lang="scss" scoped>
.figure-wrap {
  @include flex;
  margin: 30px 20px;
  margin-bottom: 0;

  .figure-box {
    @include flex;
    position: relative;
    width: 150px;
    height: 34px;
    background: #f0f0f0;
    border: 1px solid #1890ff;
    border-radius: 8px;
    font-size: 14px;
    font-family: PingFangSC, PingFangSC-Medium;
    font-weight: 500;
    text-align: center;
    color: #262626;
    // z-index: 999;

    .type {
      position: absolute;
      top: -24px;
      left: 0;
    }
  }

  .figure-lines {
    position: relative;
    width: calc(100% - 300px);
  }
}

.center-line {
  position: relative;
  width: 100%;
  height: 60px;
  @include flex(row, flex-start);

  .left {
    position: absolute;
    width: 92px;
    top: 45px;
    left: 50px;
    transform: rotate(90deg);
  }

  .right {
    position: absolute;
    width: 92px;
    top: 45px;
    right: 50px;
    transform: rotate(90deg);
  }
}
</style>
