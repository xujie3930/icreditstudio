<!--
 * @Author: lizheng
 * @Description: 页面底部 - 版权声明
 * @Date: 2021-06-18
-->
<template>
  <footer
    class="layout-footer label"
    v-if="systemSetting.copyRight || copyright"
  >
    Copyright
    <j-svg name="copyright" class="copy-icon" />
    <span class="text label">{{ systemSetting.copyRight || copyright }}</span>
  </footer>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  computed: {
    ...mapGetters({
      copyright: 'common/copyright',
      systemSetting: 'user/systemSetting'
    })
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/common/_mixin.scss';

.layout-footer {
  @include flex;
  width: 100%;
  height: 20px;
  margin-bottom: 16px;
  text-align: center;
  margin: 10px 0;

  .copy-icon {
    width: 18px;
    height: 18px;
    margin: 0 5px;
  }
}
</style>
