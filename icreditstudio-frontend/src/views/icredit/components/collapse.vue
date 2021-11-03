<!--
 * @Author: lizheng
 * @Description: 折叠面板
 * @Date: 2021-10-28
-->
<template>
  <div class="icredit-collapse">
    <header class="icredit-collapse-header">
      <div class="title-wrap">
        <span class="title">{{ title }}</span>
        <j-svg v-if="tooltip" class="j-svg" name="chain"></j-svg>
      </div>
      <i
        :class="[
          'el-icon-caret-bottom',
          'icon',
          isCollapse ? 'is-collapse' : ''
        ]"
        @click="handleCollapse"
      ></i>
    </header>

    <!-- <JCollapseTransition> -->
    <div v-show="!isCollapse" class="icredit-collapse-section">
      <slot></slot>
    </div>
    <!-- </JCollapseTransition> -->
  </div>
</template>

<script>
// import JCollapseTransition from '@/utils/collapse-transition'

export default {
  // components: { JCollapseTransition },

  data() {
    return {
      isCollapse: false
    }
  },

  props: {
    title: {
      type: String,
      default: 'title'
    },

    tooltip: {
      type: Boolean,
      default: false
    }
  },

  methods: {
    handleCollapse() {
      this.isCollapse = !this.isCollapse
      this.$emit('collapse', this.isCollapse)
    }
  }
}
</script>

<style lang="scss" scoped>
.icredit-collapse {
  background-color: #fff;

  &-header {
    @include flex(row, space-between);
    position: relative;
    margin: 0 16px;
    height: 50px;

    .title-wrap {
      @include flex;

      .title {
        font-size: 16px;
        font-family: PingFangSC, PingFangSC-Medium;
        font-weight: 500;
        color: #262626;
        margin: 0 5px 0 10px;
      }

      .j-svg {
        width: 12px;
        height: 16px;
      }
    }

    .icon {
      cursor: pointer;
      font-size: 16px;
      transition: transform 0.3s ease;
      transform: rotate(0);
    }

    .is-collapse {
      transition: transform 0.3s ease;
      transform: rotate(-180deg);
    }

    &::before {
      content: '';
      position: absolute;
      top: 17px;
      left: 0;
      width: 4px;
      height: 18px;
      background-color: #1890ff;
      border-radius: 0px 2px 2px 0px;
    }
  }

  &-section {
    margin: 0 16px;
    padding-bottom: 20px;
    border-bottom: 1px dashed #d9d9d9;
  }
}
</style>
