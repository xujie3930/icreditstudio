<!--
 * @Description: 节点详情
 * @Date: 2021-09-27
-->
<template>
  <div class="node-detail">
    <section class="node-detail-section">
      <div class="field-item" v-for="item in detailInfo" :key="item.label">
        <span class="label">{{ item.label }}:</span>
        <span class="text">{{ item.value }}</span>
      </div>
    </section>
    <footer class="node-detail-footer">
      <el-button
        v-if="false"
        size="mini"
        type="primary"
        class="btn"
        @click="handleOpenViewLog"
        >查看日志</el-button
      >
      <el-button size="mini" type="primary" @click="handleOpenDetailTab"
        >展开详情</el-button
      >
    </footer>
  </div>
</template>

<script>
export default {
  inject: ['getGraph', 'getNode'],
  data() {
    return {
      zIndex: 1,
      detailInfo: [
        { key: '', label: '节点id', value: '10001098' },
        { key: '', label: '名称', value: '数据开发名称' },
        { key: '', label: '调度周期', value: '每天0点0分' },
        { key: '', label: '创建人', value: 'admin' },
        { key: '', label: '描述信息', value: 'sdsdssdsds' }
      ]
    }
  },

  mounted() {
    const node = this.getNode()
    this.zIndex = node.zIndex
    node.zIndex = 100
  },

  beforeDestroy() {
    const node = this.getNode()
    node.zIndex = this.zIndex
  },

  methods: {
    handleOpenViewLog() {},
    handleOpenDetailTab() {
      this.$emit('open', 'nodeId')
    }
  }
}
</script>

<style lang="scss" scoped>
.node-detail {
  position: absolute;
  top: 0;
  right: -300px;
  width: 280px;
  height: 320px;
  border-radius: 4px;
  padding: 16px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  background-color: #fff;

  &-section {
    width: 100%;
    height: calc(100% - 44px);
    overflow-y: auto;

    .field-item {
      font-size: 14px;
      font-family: PingFangSC, PingFangSC-Regular;
      font-weight: 400;
      color: rgba(0, 0, 0, 0.65);
      margin-bottom: 15px;

      .label {
        margin-right: 10px;
      }
    }
  }

  &-footer {
    width: 100%;
    height: 44px;
    line-height: 44px;
    overflow: hidden;
    text-align: center;
    margin-top: 10px;

    .btn {
      margin-right: 24px;
    }
  }
}
</style>
