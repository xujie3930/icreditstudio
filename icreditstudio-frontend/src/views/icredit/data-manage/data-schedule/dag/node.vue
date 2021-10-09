<!--
 * @Author: lizheng
 * @Description: node
 * @Date: 2021-09-27
-->
<template>
  <div class="x6-flow-node" :style="style" @click="handleNodeclick">
    <JSvg :name="nodeInfo.iconName" class="jsvg" />
    <div class="text">{{ nodeInfo.nodeName }}</div>
    <Detail v-if="isOpenDetail" @open="openCallBack" />
  </div>
</template>

<script>
import Detail from './detail'

const nodeStyleColor = {
  success: { bgColor: 'rgba(82,196,26,0.08)', lineColor: '#52c41a' },
  error: { bgColor: 'rgba(255,77,79,0.08)', lineColor: '#ff4d4f' },
  default: { bgColor: '#f4f4f4', lineColor: '#d6e4ff' }
}

export default {
  name: 'Node',
  inject: ['getGraph', 'getNode'],
  components: { Detail },

  props: {
    nodeInfo: {
      type: Object,
      default: () => ({})
    },

    isOpenDetail: {
      type: Boolean,
      default: false
    }
  },

  computed: {
    style() {
      const { status = 'default' } = this.nodeInfo
      const { bgColor, lineColor } = nodeStyleColor[status]

      return {
        border: '1px solid transparent',
        borderRadius: '4px',
        borderColor: status === 'default' ? 'transparent' : lineColor,
        backgroundColor: bgColor
      }
    }
  },

  methods: {
    openCallBack() {
      console.log('MMM==', this)
    },

    handleNodeclick() {
      console.log('nodeinfo=', this.nodeInfo)
      this.$emit('open', !this.nodeInfo.isOpenDetail)
    }
  }
}
</script>

<style lang="scss" scoped>
.x6-flow-node {
  @include flex(row, flex-start);
  position: relative;
  width: 100%;
  height: 100%;
  background-color: #fff;

  .jsvg {
    width: 20px;
    height: 20px;
    margin-left: 12px;
  }

  .text {
    width: calc(100% - 40px);
    height: 20px;
    font-size: 14px;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: center;
    color: #262626;
    line-height: 20px;
  }
}
</style>
