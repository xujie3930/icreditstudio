<!--
 * @Description: 返回上一级页面
 * @Date: 2021-09-01
-->
<template>
  <div title="返回" class="back-icon" @click="handleClick">
    <j-svg name="back" class="j-svg" />
    <span class="text">返回</span>
  </div>
</template>

<script>
export default {
  props: {
    path: {
      type: String
    }
  },

  methods: {
    handleClick() {
      this.path && this.$router.replace(this.path)
      this.$emit('on-jump')
    }
  }
}
</script>

<style lang="scss" scoped>
.back-icon {
  @include flex(row, flex-start);

  .j-svg {
    width: 15px;
    height: 15px;
  }

  .text {
    font-size: 14px;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #1890ff;
    line-height: 20px;
    margin-left: 5px;
  }

  &:hover {
    cursor: pointer;
  }
}
</style>
