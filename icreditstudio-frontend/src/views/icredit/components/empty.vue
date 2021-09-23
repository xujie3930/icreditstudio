<!--
 * @Author: lizheng
 * @Description: 空数据
 * @Date: 2021-09-22
-->
<template>
  <div class="no-date">
    <j-svg
      name="no-data"
      class="jsvg"
      style="width:100%;height:100%;margin: 20px 0"
    />
    <div>暂无数据</div>
  </div>
</template>

<script>
export default {}
</script>

<style lang="scss">
::v-deep {
  .no-data {
    @include flex;
    flex-direction: column;

    .jsvg {
      width: 141px;
      height: 100px;
      margin: 20px 0;
    }
  }
}
</style>
