<!--
 * @Author: lizheng
 * @Description: 返回上一级页面
 * @Date: 2021-09-01
-->
<template>
  <div title="返回" class="back-icon" @click="handleClick">
    <j-svg name="back" />
  </div>
</template>

<script>
export default {
  props: {
    path: {
      type: String,
      default: '/'
    }
  },

  methods: {
    handleClick() {
      this.$router.replace(this.path)
      this.$emit('on-jump')
    }
  }
}
</script>

<style lang="scss" scoped>
.back-icon {
  margin: 20px 0 0 20px;
  &:hover {
    cursor: pointer;
  }
}
</style>
