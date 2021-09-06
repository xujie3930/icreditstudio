<!--
 * @Author: lizheng
 * @Description: 新增同步任务 - 同步任务构建
 * @Date: 2021-09-02
-->
<template>
  <div class="add-task-page">
    <div class="add-task">
      <HeaderStepBar :cur-step="2" />
      <div class="add-task-content">
        <aside class="content-aside">
          <div class="content-aside-header">
            <span class="text">数据源</span>
            <i class="search el-icon-search"></i>
          </div>
          <div class="content-aside-tree">
            <el-button-group class="btn-group">
              <el-radio-group v-model="sourceType">
                <el-radio-button class="btn btn-left" :label="0">
                  外接数据库
                </el-radio-button>
                <el-radio-button class="btn btn-center" :label="1">
                  本地文件
                </el-radio-button>
                <el-radio-button class="btn btn-right" :label="2">
                  区块链数据
                </el-radio-button>
              </el-radio-group>
            </el-button-group>
            <el-tree
              class="tree"
              :data="treeData"
              node-key="id"
              default-expand-all
              highlight-current
              check-on-click-node
              empty-text="暂无数据"
            >
              <div class="custom-tree-node" slot-scope="{ node, data }">
                <JSvg class="jsvg-icon" :name="data.icon"></JSvg>
                <span>{{ node.label }}</span>
              </div>
            </el-tree>
          </div>
        </aside>
        <section class="content-section">
          <div class="content-section-header">
            <!-- sql语句 -->
            <el-input
              class="sql-textarea"
              type="textarea"
              placeholder="请在此输入hive语法的SQL语句"
              show-word-limit
              v-if="createMode === 1"
              v-model="textarea"
              :autosize="{ minRows: 7 }"
            >
            </el-input>
          </div>
          <div class="content-section-table">
            <div class="filter">
              <div class="label-wrap">
                <div class="label">宽表名称</div>
                <el-input placeholder="请输入内容" v-model="input2">
                  <el-button class="append-btn" type="primary" slot="append"
                    >识别宽表</el-button
                  >
                </el-input>
              </div>
              <div class="label-wrap">
                <div class="label">分区字段</div>
                <el-select v-model="value" placeholder="请选择">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </div>
            </div>
            <JTable
              class="table"
              ref="table"
              v-loading="tableLoading"
              :table-configuration="tableConfiguration"
              :table-data="tableData"
            ></JTable>
          </div>
        </section>
      </div>
      <footer class="footer-btn-wrap">
        <el-button class="btn" @click="$router.push('/data-manage/add-task')">
          上一步
        </el-button>
        <el-button class="btn">保存设置</el-button>
        <el-button class="btn" type="primary" @click="handleStepClick">
          下一步
        </el-button>
      </footer>
    </div>
  </div>
</template>

<script>
import HeaderStepBar from './header-step-bar'
import crud from '@/mixins/crud'
import tableConfiguration from '@/views/icredit/configuration/table/data-sync-add'

export default {
  components: { HeaderStepBar },
  mixins: [crud],

  data() {
    // 外界数据库
    const outTreeData = [
      {
        id: 1,
        label: '一级 1',
        icon: 'database',
        children: [
          {
            id: 4,
            label: '二级 1-1',
            icon: 'table'
          }
        ]
      },
      {
        id: 2,
        icon: 'database',
        label: '一级 2',
        children: []
      }
    ]

    // 本地文件
    const localeTreeData = [
      {
        id: 1,
        label: 'Excel',
        icon: 'excel-icon',
        children: [
          {
            id: 4,
            label: 'Excel文件名称',
            icon: 'excel-icon-2'
          },
          { id: new Date().getTime(), icon: 'excel-icon-2', label: 'WSED' }
        ]
      },
      {
        id: 6,
        icon: 'csv-icon',
        label: 'CSV',
        children: [
          { id: new Date().getTime(), icon: 'csv-icon-2', label: 'dedded' }
        ]
      },
      {
        id: 2,
        icon: 'txt-icon',
        label: 'TXT',
        children: [
          { id: new Date().getTime(), icon: 'txt-icon-2', label: 'sdssd' }
        ]
      }
    ]

    return {
      tableLoading: false,
      tableData: [],
      createMode: '',
      input2: '',
      type: 'sql',
      textarea: '',
      sourceType: 0,
      outTreeData,
      localeTreeData,
      tableConfiguration,
      options: [
        { value: '选项1', label: '黄金糕' },
        { value: '选项2', label: '双皮奶' }
      ],
      value: ''
    }
  },

  computed: {
    treeData() {
      const dataMapping = {
        0: this.outTreeData,
        1: this.localeTreeData,
        2: []
      }
      return dataMapping[this.sourceType]
    }
  },

  created() {
    const { createMode } = this.$route.query
    this.createMode = Number(createMode)
  },

  methods: {
    handleStepClick() {
      this.$router.push('/data-manage/add-transfer')
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/public/data-manage';

.add-task {
  margin-top: 34px;
  height: calc(100% - 134px);
  overflow: hidden;
}

.add-task-content {
  @include flex(row, flex-start);
  width: 100%;
  height: 100%;
  margin-top: 34px;
  border-top: 1px solid #e9e9e9;
  overflow: hidden;

  .content-aside {
    width: 261px;
    height: 100%;
    box-sizing: border-box;
    border-right: 1px solid #e9e9e9;
    overflow: hidden;

    &-header {
      @include flex(row, space-between);
      width: 100%;
      height: 32px;
      background: #fafafa;
      padding: 0 10px;
      border-bottom: 1px solid #f0f0f0;

      .search {
        color: #1890ff;
        font-size: 15px;
        cursor: pointer;
      }

      .text {
        font-size: 14px;
        font-family: PingFangSC, PingFangSC-Regular;
        font-weight: 400;
        text-align: left;
        color: #262626;
      }
    }

    &-tree {
      height: 100%;
      .btn-group {
        margin: 15px 10px;

        .btn {
          width: 80px;
          height: 32px;
          font-size: 13px;
          font-family: PingFangSC, PingFangSC-Regular;
          font-weight: 400;
          text-align: center;
          color: #fff;
          line-height: 18px;
          padding: 0;
          margin: 0;
          border: 1px solid #1890ff;
          box-sizing: border-box;

          border-radius: 0;
          ::v-deep {
            .el-radio-button__inner {
              width: 100%;
              padding: 9px 9px;
              border-color: #1890ff;
              color: #1890ff;
              border: none;
            }

            .el-radio-button__orig-radio:checked + .el-radio-button__inner {
              color: #fff;
            }
          }
        }

        .btn-left {
          border-radius: 4px 0 0 4px;
        }

        .btn-center {
          border-left: 0 solid transparent;
        }

        .btn-right {
          border-left: 0 solid transparent;
          border-radius: 0 4px 4px 0;
        }
      }

      .tree {
        max-height: calc(100% - 93px);
        overflow-y: auto;

        .custom-tree-node {
          @include flex;

          .jsvg-icon {
            width: 14px;
            height: 14px;
            margin: 0 5px;
          }
        }
      }
    }
  }

  .content-section {
    flex: 1;
    height: 100%;

    &-header {
      width: 100%;
      height: 160px;
      overflow-y: auto;
      border-bottom: 1px solid #e9e9e9;

      .sql-textarea {
        ::v-deep {
          .el-textarea__inner {
            border: none;
          }
        }
      }
    }

    &-table {
      width: 100%;
      .filter {
        @include flex(row, space-between);
        margin: 16px 0;
        padding: 0 10px;
      }

      .table {
        padding: 0 10px;
      }

      .label-wrap {
        @include flex(row, space-between);
        .label {
          width: 80px;
          text-align: right;
          margin-right: 12px;
        }

        .append-btn {
          color: #fff;
          border-radius: 0;
          border-color: #1890ff;
          background: #1890ff;
          border-radius: 0px 4px 4px 0px;
        }
      }
    }
  }
}

.footer-btn-wrap {
  margin-top: 0;
}
</style>
