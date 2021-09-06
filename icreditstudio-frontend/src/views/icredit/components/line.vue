<!--
 * @Author: lizheng
 * @Description: 线
 * @Date: 2021-09-06
-->
<template>
  <div class="figure-lines">
    <JSvg :name="iconName" class="icon" />
  </div>
</template>

<script>
export default {
  props: {
    iconName: {
      type: String,
      default: 'left-link'
    }
  }
}
</script>

<style lang="scss" scoped>
.figure-lines {
  position: relative;
  width: calc(100% - 300px);
  border-top: 2px solid #1890ff;
  line-height: 34px;

  .icon {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -59%);
    background-color: #fff;
  }
}
</style>
