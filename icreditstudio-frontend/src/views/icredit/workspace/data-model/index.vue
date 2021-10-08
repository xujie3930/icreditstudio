<!--
 * @Author: lizheng
 * @Description: 数仓建模
 * @Date: 2021-10-08
-->

<template>
  <div class="data-develop w100">
    <aside class="data-develop-aside">
      <div class="title">数仓</div>
      <div class="search-select">
        <el-select
          class="text-select"
          filterable
          clearable
          remote
          placeholder="请输入关键字"
          size="mini"
          :loading="searchLoading"
          v-model="value"
        >
          <el-option
            v-for="item in [{ tableName: 'sss' }]"
            :key="item.tableName"
            :label="item.tableName"
            :value="item.tableName"
          >
          </el-option>
        </el-select>
        <i class="search el-icon-search"></i>
      </div>

      <div class="btn-wrap">
        <i class="icon el-icon-circle-plus-outline"></i>
        <span class="text">新建表</span>
      </div>

      <el-tree class="tree" :data="data">
        <div
          :id="node.id"
          :draggable="node.level > 1"
          class="custom-tree-node"
          slot-scope="{ node, data }"
        >
          <div class="left">
            <span v-if="data.type === '3'" class="circle"></span>
            <JSvg class="jsvg-icon" :name="data.icon"></JSvg>
            <span>{{ data.label }}</span>
          </div>
          <div class="right">
            <el-dropdown @command="handleCommand">
              <span class="el-dropdown-link">
                <i class="el-icon-more icon"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="doc">新增文件夹</el-dropdown-item>
                <el-dropdown-item command="flow">新增工作流</el-dropdown-item>
                <el-dropdown-item command="edit">编辑</el-dropdown-item>
                <el-dropdown-item command="delete">删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
      </el-tree>
    </aside>

    <section class="data-develop-section">
      <Tabs>
        <TabDetail slot="panel" />
      </Tabs>
    </section>
  </div>
</template>

<script>
import Tabs from '@/views/icredit/components/tabs'
import TabDetail from './detail'

export default {
  components: { Tabs, TabDetail },

  data() {
    return {
      name: '',
      value: '',
      value1: '',
      searchLoading: false,
      data: [
        {
          label: '水务基础数据梳理',
          icon: 'dev-business',
          type: '0',
          id: 1,
          children: [
            {
              label: '分类1',
              icon: 'dev-doc',
              type: '1',
              id: 2,
              children: [
                {
                  label: '分类1-1',
                  icon: 'dev-flow',
                  type: '3',
                  id: 3
                }
              ]
            }
          ]
        }
      ]
    }
  },

  methods: {
    handleCommand(command) {
      console.log(command)
      switch (command) {
        case 'doc':
          this.$refs.addDoc.$refs.addDocDialog.open()
          break
        case 'edit':
          this.$refs.editFlow.$refs.editDialog.open()
          break
        case 'delete':
          this.$refs.deleteFlow.open({ title: '' })
          break

        default:
          break
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.data-develop {
  @include flex(row, flex-start, flex-start);
  height: calc(100vh - 126px);
  overflow-y: auto;
  background-color: #fff;

  &-aside {
    @include flex(column, flex-start, unset);
    width: 230px;
    height: 100%;
    border-right: 1px solid #d9d9d9;
    overflow: hidden;

    .title {
      text-align: left;
      width: 28px;
      height: 20px;
      font-size: 14px;
      font-family: PingFangSC, PingFangSC-Medium;
      font-weight: 500;
      text-align: left;
      color: #262626;
      line-height: 20px;
      margin: 16px 10px;
    }

    .top-select {
      width: 100%;
      margin-bottom: 16px;

      ::v-deep {
        .el-input__inner {
          background-color: #f0f5ff;
          padding: 0 10px;
          font-size: 12px;
          font-family: PingFangSC, PingFangSC-Regular;
          font-weight: 400;
          text-align: left;
          color: #1890ff;
          line-height: 20px;
        }

        .el-select__caret {
          color: #1890ff;
        }
      }
    }

    .search-select {
      @include flex(row, space-between);
      position: relative;
      width: 210px;
      height: 32px;
      padding: 0 5px;
      border: 1px solid rgba(0, 0, 0, 0.15);
      background: rgba(255, 255, 255, 0.04);
      border-radius: 4px;
      margin-left: 10px;

      .search {
        color: #1890ff;
        font-size: 15px;
        cursor: pointer;
      }

      .text-select {
        width: 180px;
      }
    }

    .btn-wrap {
      @include flex(row, flex-start);
      width: 100%;
      height: 20px;
      line-height: 20px;
      margin: 12px 0;
      padding: 0 10px;
      cursor: pointer;
      color: #262626;

      .icon {
        font-size: 16px;
        margin-right: 6px;
        color: #999;
      }

      .text {
        font-size: 14px;
        font-family: PingFangSC, PingFangSC-Regular;
        font-weight: 400;
        line-height: 20px;
      }

      &:hover {
        color: #1890ff;
        .icon {
          color: #1890ff;
        }
      }
    }

    .tree {
      width: 100%;

      .custom-tree-node {
        @include flex(row, space-between);
        flex: 1;
        cursor: pointer;
        padding-right: 8px;

        .left {
          @include flex;

          .jsvg-icon {
            width: 14px;
            height: 14px;
            margin: 0 5px;
          }

          .circle {
            width: 6px;
            height: 6px;
            background: #52c41a;
            border-radius: 50%;
            margin-right: 5px;
          }
        }

        .right {
          display: none;

          .icon {
            transform: rotate(90deg);
          }
        }

        &:hover > .right {
          display: block;
          &:hover {
            cursor: pointer;
          }
        }
      }

      ::v-deep {
        .el-tree-node.is-current > .el-tree-node__content {
          color: #1890ff;
          .right {
            display: block;
          }
        }
      }
    }

    ::v-deep {
      .el-input--mini .el-input__inner {
        border: none;
        padding: 0 5px;
      }
    }
  }

  &-section {
    position: relative;
    flex: 1;
  }
}
</style>
