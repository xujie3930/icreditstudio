<!--
 * @Author: lizheng
 * @Description: detail
 * @Date: 2021-10-08
-->

<template>
  <div class="tabs-detail">
    <div
      class="tabs-detail-item"
      v-for="item in detailConfig"
      :key="item.label"
    >
      <div class="label">{{ item.label }}</div>
      <div class="text">{{ item.value }}</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      detailConfig: [
        { label: '业务流程名称', key: '', value: 'fxt_test' },
        { label: '文件夹分类个数', key: '', value: '2' },
        { label: '工作流个数', key: '', value: '3' },
        { label: '正在运行工作流数', key: '', value: '0' },
        { label: '所属工作空间', key: '', value: '默认工作空间' },
        { label: '创建人', key: '', value: 'admin' },
        { label: '创建时间', key: '', value: '2021-06-30' },
        { label: '更新时间', key: '', value: '2021-10-30' },
        { label: '描述', key: '', value: '用于水务测试' }
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
.tabs-detail {
  margin: 16px 18px;
  border: 1px solid #d9d9d9;

  &-item {
    @include flex(row, flex-start);
    font-size: 14px;
    font-family: PingFangSC, PingFangSC-Medium;
    color: #262626;
    border-bottom: 1px solid #d9d9d9;

    &:last-child {
      border: none;
    }

    .label {
      width: 150px;
      padding: 0 10px;
      line-height: 34px;
      height: 34px;
      background: #fafafa;
      border-right: 1px solid #d9d9d9;

      font-weight: 500;
    }

    .text {
      flex: 1;
      padding: 0 15px;
      font-weight: 400;
    }
  }
}
</style>
