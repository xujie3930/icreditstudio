<!--
 * @Author: lizheng
 * @Description: 新增同步任务 - 步骤条
 * @Date: 2021-08-31
-->

<template>
  <el-steps :active="1" finish-status="success" simple>
    <el-step title="同步任务定义"> </el-step>
    <el-step title="同步任务构建"> </el-step>
    <el-step title="同步任务调度"></el-step>
  </el-steps>
</template>

<script>
export default {
  props: {
    curStep: {
      type: Number,
      default: 0
    }
  }
}
</script>

<style></style>
