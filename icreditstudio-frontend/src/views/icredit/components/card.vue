<!--
 * @Author: lizheng
 * @Description: 卡片
 * @Date: 2021-10-22
-->
<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>卡片名称</span>
      <el-button style="float: right; padding: 3px 0" type="text"
        >操作按钮</el-button
      >
    </div>
    <div v-for="o in 4" :key="o" class="text item">
      {{ '列表内容 ' + o }}
    </div>
  </el-card>
</template>

<script>
export default {}
</script>

<style lang="scss" scoped>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: '';
}
.clearfix:after {
  clear: both;
}

.box-card {
  flex: 1;
  min-width: 300px;
  height: 166px;
  margin-right: 16px;
  background-color: #fff;

  &:last-child {
    margin-right: 0;
  }
}
</style>
