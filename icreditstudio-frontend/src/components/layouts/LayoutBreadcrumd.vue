<!--
 * @Description: 头部导航栏面包屑
 * @Date: 2021-08-09
-->
<template>
  <div class="iframe-header-breadcrumb">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>活动管理</el-breadcrumb-item>
      <el-breadcrumb-item>活动列表</el-breadcrumb-item>
      <el-breadcrumb-item>活动详情</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
export default {}
</script>

<style lang="scss" scoped>
.iframe-header-breadcrumb {
  margin: 16px 12px;
  font-size: 14px;
  font-family: PingFangSC, PingFangSC-Regular;
  font-weight: 400;
  text-align: left;
  color: #8c8c8c;
  line-height: 20px;
}
</style>
