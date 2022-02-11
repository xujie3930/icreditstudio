<!--
 * @Author: lizheng
 * @Description: ddd
 * @Date: 2022-01-16
-->
<template>
  <main
    id="subapp-container"
    v-loading="loading"
    v-if="loadSubAppState"
    style="width:100%;height:calc(100vh - 120px)"
  ></main>

  <main v-else class="error-load">loading Sub Application Failure</main>
</template>

<script>
import bootstrap from '@/plugins/qiankun/index'
import { mapGetters } from 'vuex'

export default {
  data() {
    return { loading: false }
  },

  computed: {
    ...mapGetters({ loadSubAppState: 'common/loadSubAppState' })
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
    this.$nextTick(() => bootstrap())
  }
}
</script>

<style lang="scss" scoped>
.error-load {
  @include flex;
  background-color: #fff;
  width: 100%;
  height: calc(100vh - 126px);
  color: red;
  font-size: 20px;
}
</style>
