<!--
 * @Description: dag图
 * @Date: 2021-09-24
-->
<template>
  <div class="dag w100 h100">
    <div ref="content" class="dag-content">
      <el-button @click="isShowFooter = true">show</el-button>
    </div>
    <div ref="footer" class="dag-footer" v-if="isShowFooter">
      <el-tabs class="tabs" v-model="activeName" @tab-click="handleClick">
        <el-tab-pane
          label="属性"
          name="first"
          :class="[isTabsCollapse ? 'hide-pane' : '']"
          >sedwdw</el-tab-pane
        >
        <el-tab-pane
          label="操作日志"
          name="second"
          :class="[isTabsCollapse ? 'hide-pane' : '']"
          >dsdssdsds</el-tab-pane
        >
        <el-tab-pane
          label="代码"
          name="third"
          :class="[isTabsCollapse ? 'hide-pane' : '']"
          >sddssdsd</el-tab-pane
        >
      </el-tabs>
      <div class="icon-wrap">
        <i
          @click="handleArrowClick"
          :class="[
            'icon',
            'el-icon-d-arrow-left',
            isTabsCollapse ? 'arrow-top' : 'arrow-down'
          ]"
        ></i>
        <i class="icon el-icon-close" @click="handleCloseClick"></i>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isTabsCollapse: false,
      isShowFooter: false,
      activeName: 'second'
    }
  },

  methods: {
    handleClick(tab, event) {
      console.log(tab, event)
    },

    handleCloseClick() {
      this.isShowFooter = false
    },

    handleArrowClick() {
      this.isTabsCollapse = !this.isTabsCollapse
    }
  }
}
</script>

<style lang="scss" scoped>
.dag {
  height: calc(100vh - 124px);
  background-color: #fff;

  &-content {
    height: calc(100% - 240px);
  }

  &-footer {
    position: relative;
    height: 240px;
    width: 100%;
    overflow: auto;
    // padding: 0 16px;

    .tabs {
      ::v-deep {
        .el-tabs__header {
          padding-left: 33px;
          background-color: #f4f4f4;
        }

        .el-tabs__nav-wrap::after {
          display: none;
        }
      }
    }

    .icon-wrap {
      @include flex(row, space-evenly);
      position: absolute;
      top: 0;
      right: 0;
      width: 100px;
      height: 40px;
      background-color: #f5f5f5;

      .icon {
        font-size: 16px;
        cursor: pointer;
      }

      .arrow-down {
        transform: rotate(-90deg);
      }

      .arrow-top {
        transform: rotate(90deg);
      }
    }
  }
}
</style>
