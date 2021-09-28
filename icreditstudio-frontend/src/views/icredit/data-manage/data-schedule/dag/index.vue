<!--
 * @Description: dag图
 * @Date: 2021-09-24
-->
<template>
  <div class="dag w100 h100">
    <div ref="content" id="container" class="dag-content">
      <!-- <el-button @click="isShowFooter = !isShowFooter">show</el-button> -->
    </div>

    <div
      ref="footer"
      :class="['dag-footer', isTabsCollapse ? 'dag-footer-collapse' : '']"
      v-if="isShowFooter"
    >
      <el-tabs class="tabs" v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="属性" name="first">sedwdw</el-tab-pane>
        <el-tab-pane label="操作日志" name="second">dsdssdsds</el-tab-pane>
        <el-tab-pane label="代码" name="third">sddssdsd</el-tab-pane>
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
import { x6Json, customNodeStyle, renderGraph } from './x6'

export default {
  data() {
    return {
      isTabsCollapse: false,
      isShowFooter: false,
      activeName: 'second'
    }
  },

  mounted() {
    this.renderFlowGraph()
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
    },

    // 渲染流程图
    renderFlowGraph() {
      const { nodes, edges } = x6Json
      console.log(edges)
      const graph = renderGraph()
      nodes.forEach(item => graph.addNode(customNodeStyle(item)))
      edges.forEach(item => graph.addEdge(item))
      graph.centerContent()
    }
  }
}
</script>

<style lang="scss" scoped>
.dag {
  @include flex(column, space-between);
  height: calc(100vh - 124px);
  background-color: #fff;

  &-content {
    position: relative;
  }

  &-footer {
    position: relative;
    height: 240px;
    width: 100%;
    overflow: auto;

    .tabs {
      ::v-deep {
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

  .el-tabs__header {
    padding-left: 33px;
    background-color: #f4f4f4;
  }

  &-footer-collapse {
    height: 40px;
    overflow: hidden;

    .icon-wrap {
      background-color: #fff;
    }
  }

  ::v-deep {
    .dag-footer .el-tabs__header {
      padding-left: 33px;
      background-color: #f4f4f4;
    }

    .dag-footer-collapse .el-tabs__header {
      padding-left: 33px;
      background-color: #fff;
    }
  }
}
</style>
