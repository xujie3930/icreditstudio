<!--
 * @Author: lizheng
 * @Description: detail
 * @Date: 2021-10-08
-->

<template>
  <el-tabs class="model-tab" v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="库信息" name="first">
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
    </el-tab-pane>
    <el-tab-pane label="表信息" name="second">
      <div class="table-wrap">
        <JTable
          ref="table"
          v-loading="tableLoading"
          :table-configuration="tableConfiguration"
          :table-data="tableData"
        >
        </JTable>
      </div>
    </el-tab-pane>
    <el-tab-pane label="ER图" name="third">
      <div class="er-wrap">ER图</div>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import tableConfiguration from '@/views/icredit/configuration/table/workspace-data-model'

export default {
  data() {
    return {
      tableConfiguration,
      tableLoading: false,
      tableData: [],
      activeName: 'first',
      detailConfig: [
        { label: '数据源类型', key: '', value: 'mysql' },
        { label: '数据源自定义名称', key: '', value: 'datax_web' },
        {
          label: '数据源连接信息',
          key: '',
          value: 'jdbc:mysql://192.168.0.175 :3306/test_datax'
        },
        { label: '默认字符集', key: '', value: 'utf8' },
        { label: '是否启用', key: '', value: '是' },
        { label: '数据库大小', key: '', value: '12KB' },
        { label: '创建时间', key: '', value: '2021-06-30' },
        { label: '最近一次同步时间', key: '', value: '2021-07-08  23:00:05' },
        { label: '最近一次同步状态', key: '', value: '成功' },
        { label: '创建人', key: '', value: 'admin' },
        { label: '描述信息', key: '', value: '用于datax测试' }
      ]
    }
  },

  methods: {
    handleClick() {}
  }
}
</script>

<style lang="scss" scoped>
.model-tab {
  .table-wrap,
  .er-wrap {
    margin: 16px;
    height: 30vh;
  }

  /deep/ .el-tabs__nav {
    margin-left: 30px;
  }
}

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
