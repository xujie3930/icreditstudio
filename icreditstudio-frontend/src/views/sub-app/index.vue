<!--
 * @Author: lizheng
 * @Description: ddd
 * @Date: 2022-01-16
-->
<template>
  <main id="subapp-container"></main>
</template>

<script>
import bootstrap from '@/plugins/qiankun/index'

export default {
  mounted() {
    // 启动微应用框架qiankun
    bootstrap()
  },

  updated() {
    bootstrap()
  }
}
</script>

<style></style>
