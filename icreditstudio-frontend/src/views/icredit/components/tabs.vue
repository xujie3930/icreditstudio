<!--
 * @Author: lizheng
 * @Description: tabs
 * @Date: 2021-10-08
-->

<template>
  <div class="icredit-tabs">
    <div class="icredit-tabs-header">
      <div class="icredit-tabs-bar">
        <div
          v-for="(item, idx) in tabsConfig"
          :key="idx"
          :class="[
            'icredit-tabs-header__nav',
            idx === curTabIdx ? 'icredit-tabs-header__nav-active' : ''
          ]"
          @click="handleTabsClick(idx)"
        >
          <span>{{ item.name }}</span>
          <i class="el-icon-close"></i>
        </div>
      </div>
      <el-dropdown trigger="click" class="icredit-tabs-header__options">
        <div class="more">
          <i class="el-icon-caret-bottom"></i>
        </div>
        <el-dropdown-menu slot="dropdown" class="tabs-more">
          <el-dropdown-item>
            关闭其他
          </el-dropdown-item>
          <el-dropdown-item>
            关闭左侧
          </el-dropdown-item>
          <el-dropdown-item>
            关闭右侧
          </el-dropdown-item>
          <el-dropdown-item>
            关闭全部
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <div class="icredit-tabs-pane">
      <slot name="panel" />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      curTabIdx: 1,
      tabsConfig: [
        { name: '平台门户' },
        { name: '工作流11' },
        { name: '文件夹名' },
        { name: 'BI数据报表' }
      ]
    }
  },

  methods: {
    handleTabsClick(idx) {
      this.curTabIdx = idx
    }
  }
}
</script>

<style lang="scss" scoped>
.icredit-tabs {
  width: 100%;

  &-header {
    position: relative;
    width: 100%;
    height: 30px;
    border: 1px solid rgba(0, 0, 0, 0.15);
    border-left: none;
    border-right: none;
    background-color: rgba(255, 255, 255, 0.65);
    font-size: 14px;
    color: #666;
    font-weight: 400;
    font-family: PingFangSC, PingFangSC-Regular;
    background-color: #f5f5f5;

    &__nav {
      @include flex(row, space-between);
      padding: 0 10px;
      width: 150px;
      height: 100%;
      cursor: pointer;
      border-right: 1px solid rgba(0, 0, 0, 0.15);

      &:hover {
        color: #1890ff;
      }
    }

    &__nav-active {
      color: #1890ff;
      background-color: #fff;
    }

    &__options {
      position: absolute;
      top: 0;
      right: 0;
      width: 35px;
      height: 28px;
      background-color: #f5f5f5;

      .more {
        width: 100%;
        height: 100%;
        line-height: 28px;
        text-align: center;
        background-color: #f5f5f5;
        cursor: pointer;
      }
    }
  }

  &-bar {
    @include flex(row, flex-start);
    width: calc(100% - 35px);
    height: 100%;
    overflow-x: hidden;
  }
}
</style>
