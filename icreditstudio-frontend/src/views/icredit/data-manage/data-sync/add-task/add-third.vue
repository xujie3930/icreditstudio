<!--
 * @Author: lizheng
 * @Description: 新增同步任务 - 同步任务调度
 * @Date: 2021-09-02
-->
<template>
  <div class="add-task-page">
    <div class="add-task">
      <HeaderStepBar :cur-step="3" />
      <div class="add-task-content"></div>
      <footer class="footer-btn-wrap">
        <el-button class="btn" @click="$router.push('/data-manage/add-build')">
          上一步
        </el-button>
        <el-button class="btn" type="primary" @click="handlePublish">
          发布
        </el-button>
      </footer>
    </div>
  </div>
</template>

<script>
import HeaderStepBar from './header-step-bar'

export default {
  components: { HeaderStepBar },

  methods: {
    handlePublish() {}
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/public/data-manage';

.add-task {
  margin-top: 34px;
}

.add-task-content {
  width: 100%;
  margin-top: 34px;
  border-top: 1px solid #e9e9e9;
}
</style>
