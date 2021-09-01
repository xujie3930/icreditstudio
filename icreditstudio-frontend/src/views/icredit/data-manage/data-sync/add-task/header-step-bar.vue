<!--
 * @Author: lizheng
 * @Description: 新增同步任务 - 步骤条
 * @Date: 2021-08-31
-->

<template>
  <div class="task-step">
    <!-- <el-steps process-status="finish" space="192" simple :active="curStep">
      <el-step
        :title="item.title"
        :key="item.title"
        v-for="(item, index) in renderStepsConfig"
      >
        <div slot="icon" class="el-step__icon">
          <div class="el-step__icon-inner">{{ index + 1 }}</div>
        </div>
      </el-step>
    </el-steps> -->

    <div class="step" :key="index" v-for="(item, index) in renderStepsConfig">
      <!-- <div class="step-main"> -->
      <div
        :class="['step-icon', curStep === index + 1 ? 'step-icon__active' : '']"
      >
        <div class="step-icon-inner">{{ index + 1 }}</div>
      </div>
      <div
        :class="[
          'step-title',
          index + 1 === curStep ? 'step-title__active' : ''
        ]"
      >
        {{ item.title }}
      </div>
      <!-- </div> -->
      <!-- <div class="step-line">
        <i
          class="el-step__line-inner"
          style="transition-delay: 0ms; border-width: 0px; width: 0%;"
        ></i>
      </div> -->
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      renderStepsConfig: [
        { title: '同步任务定义' },
        { title: '同步任务构建' },
        { title: '同步任务调度' }
      ]
    }
  },

  props: {
    curStep: {
      type: Number,
      default: 3
    }
  }
}
</script>

<style lang="scss" scoped>
.task-step {
  @include flex;
  width: 1100px;
  margin-left: 15%;

  .step {
    @include flex(row, flex-start);
    position: relative;
    flex: 1;
    margin-right: 16px;

    &-main {
      @include flex;
    }

    &-icon {
      @include flex;
      width: 32px;
      height: 32px;
      border-radius: 50%;
      border: 1px solid rgba(0, 0, 0, 0.15);

      &__active {
        background: #1890ff;
        border-color: #1890ff;

        .step-icon-inner {
          color: #fff;
        }
      }

      &-inner {
        font-size: 16px;
        font-family: HelveticaNeue;
        text-align: center;
        color: rgba(0, 0, 0, 0.25);
      }
    }

    &-title {
      font-size: 16px;
      font-family: PingFangSC, PingFangSC-Regular;
      font-weight: 400;
      text-align: left;
      color: rgba(0, 0, 0, 0.45);
      margin: 0 8px;
    }

    &-title__active {
      font-weight: 500;
      color: rgba(0, 0, 0, 0.85);
    }

    &::after {
      content: '';
      position: absolute;
      width: 192px;
      height: 1px;
      background: #e9e9e9;
      right: 0;
      transform: translateY(-50%);
    }

    &:last-child {
      &::after {
        display: none;
      }
    }
  }
}
</style>
