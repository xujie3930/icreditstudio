<template>
  <!-- 外嵌链接的处理方式有误 待修改方案 -->
  <iframe class="iframe" :src="`http://${$route.params.src}`" frameborder="0"></iframe>
</template>
<script>
export default {
  name: 'LayoutIframe'
}
</script>
<style lang="scss" scoped>
.iframe {
  width: 100%;
  height: 100%;
}
</style>
