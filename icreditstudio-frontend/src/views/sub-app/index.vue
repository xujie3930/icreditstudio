<!--
 * @Author: lizheng
 * @Description: ddd
 * @Date: 2022-01-16
-->
<template>
  <main
    id="subapp-container"
    v-loading="loading"
    style="width:100%;height:calc(100vh - 120px)"
  ></main>
</template>

<script>
import bootstrap from '@/plugins/qiankun/index'

export default {
  data() {
    return { loading: false }
  },
  mounted() {
    this.loading = true
    // 启动微应用框架qiankun
    bootstrap()

    setTimeout(() => {
      this.loading = false
    }, 500)
  },

  updated() {
    bootstrap()
  }
}
</script>

<style></style>
