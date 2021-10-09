<!--
 * @Description: 头部导航栏面包屑
 * @Date: 2021-08-09
-->
<template>
  <div class="iframe-header-breadcrumb">
    <Back
      v-if="canBackPages.includes($route.path)"
      @on-jump="handleBackClick"
    />

    <el-breadcrumb
      v-else-if="curBreadcrumb.length"
      separator-class="el-icon-arrow-right"
    >
      <el-breadcrumb-item :key="index" v-for="(item, index) in curBreadcrumb">
        {{ item.label || item.name }}
      </el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
import Back from '@/components/back'

export default {
  components: { Back },

  data() {
    return {
      // 需要返回的页面
      canBackPages: [
        // '/workspace/detail',
        '/data-manage/add-task',
        '/data-manage/add-build',
        '/data-manage/add-transfer',
        '/data-manage/data-schedule/dag'
      ]
    }
  },

  props: {
    curBreadcrumb: {
      type: Array,
      default: () => []
    }
  },

  methods: {
    // 返回
    handleBackClick() {
      const { path } = this.$route
      if (path === '/data-manage/data-schedule/dag') {
        this.$router.replace('/data-shcedule/cycle-task')
      } else {
        this.$ls.remove('taskForm')
        this.$ls.remove('selectedTable')
        this.$router.push('/data-manage/data-sync')
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.iframe-header-breadcrumb {
  margin: 13px 12px;
  font-size: 14px;
  font-family: PingFangSC, PingFangSC-Regular;
  font-weight: 400;
  text-align: left;
  color: #8c8c8c;
  height: 20px;
  line-height: 20px;
}
</style>
