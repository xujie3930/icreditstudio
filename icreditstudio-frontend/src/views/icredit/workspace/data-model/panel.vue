<!--
 * @Author: lizheng
 * @Description: detail
 * @Date: 2021-10-08
-->

<template>
  <div>
    <el-tabs class="model-tab" v-model="activeName" @tab-click="handleClick">
      <el-tab-pane :label="isTable ? '表属性' : '库属性'" name="first">
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

      <template v-if="isTable">
        <el-tab-pane label="采样数据(100)" name="third">
          <div class="table-wrap">
            <JTable
              ref="table"
              v-loading="tableLoading"
              :table-configuration="tbSamplesConfig"
              :table-data="tbSampleData"
            >
            </JTable>
          </div>
        </el-tab-pane>

        <el-tab-pane label="关系表" name="fourth">
          <div>
            <div class="btn-wrap">
              <el-button type="primary" @click="handleAddRelation"
                >新增关联关系</el-button
              >
            </div>
            <div class="table-wrap">
              <JTable
                ref="table"
                :table-data="tbData"
                :table-configuration="tbConfig"
                v-loading="tableLoading"
              >
              </JTable>
            </div>
          </div>
        </el-tab-pane>
      </template>

      <el-tab-pane v-else label="ER图" name="firth">
        <div class="er-wrap">ER图</div>
      </el-tab-pane>
    </el-tabs>

    <Relations ref="relation" />
  </div>
</template>

<script>
import Relations from './relations'
import tbConfig from '@/views/icredit/configuration/table/workspace-model-relation'
import tbSamplesConfig from '@/views/icredit/configuration/table/workspace-model-samples'
import tableConfiguration from '@/views/icredit/configuration/table/workspace-data-model'

export default {
  components: { Relations },

  data() {
    return {
      tbSamplesConfig,
      tbSampleData: [{ id: new Date().getTime(), name: 'jingxuan' }],
      tableConfiguration,
      tableData: [],
      tbConfig: tbConfig(this),
      tbData: [{ type: '左关联' }],
      activeName: 'first',
      tableLoading: false,

      // 库信息
      databaseDetailConfig: [
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
      ],

      // 表信息
      tableDetailConfig: [
        { label: '表英文名称', key: '', value: 'tableA' },
        { label: '表中文名称', key: '', value: '表A' },
        { label: '是否启用', key: '', value: '是' },
        { label: '生命周期', key: '', value: 'utf8' },
        { label: '使用方式', key: '', value: '是' },
        { label: '大小', key: '', value: '12KB' },
        { label: '最近一次更新时间', key: '', value: '2021-07-08  23:00:05' },
        { label: '创建时间', key: '', value: '2021-06-30' },
        { label: '创建人', key: '', value: 'admin' },
        { label: '标签', key: '', value: '成功' },
        { label: '备注', key: '', value: '用于datax测试' }
      ]
    }
  },

  props: {
    currentTab: {
      type: Object,
      default: () => ({})
    }
  },

  computed: {
    isTable() {
      return this.currentTab.icon === 'table'
    },

    detailConfig() {
      const { isTable, tableDetailConfig, databaseDetailConfig } = this
      return isTable ? tableDetailConfig : databaseDetailConfig
    }
  },

  watch: {
    isTable() {
      this.activeName = 'first'
    }
  },

  methods: {
    handleClick() {},

    handleAddRelation() {
      this.$refs.relation.open()
    }
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

  .btn-wrap {
    text-align: right;
    margin-top: 16px;
    margin-right: 16px;
  }

  /deep/ .el-tabs__nav {
    margin-left: 30px;
  }

  /deep/ .el-tabs__header {
    margin: 0;
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
