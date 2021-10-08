<!--
 * @Author: lizheng
 * @Description: 数据开发
 * @Date: 2021-09-28
-->
<template>
  <div class="data-develop w100">
    <aside class="data-develop-aside">
      <el-select
        v-model="value1"
        size="mini"
        class="top-select"
        placeholder="请选择"
      >
        <el-option
          v-for="item in [{ label: 'sss', value: 'ssss' }]"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>

      <div class="search-select">
        <el-select
          class="text-select"
          filterable
          clearable
          remote
          placeholder="请输入文件夹或工作流名称"
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
        <span class="btn">
          <i class="icon el-icon-circle-plus-outline"></i>
          新增业务流程
        </span>
        <span class="btn">
          <i class="icon el-icon-circle-plus-outline"></i>
          新增工作流
        </span>
      </div>

      <el-tree class="tree" :data="data"></el-tree>
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
          label: '一级 1',
          children: [
            {
              label: '二级 1-1',
              children: [
                {
                  label: '三级 1-1-1'
                }
              ]
            }
          ]
        },
        {
          label: '一级 2',
          children: [
            {
              label: '二级 2-1',
              children: [
                {
                  label: '三级 2-1-1'
                }
              ]
            },
            {
              label: '二级 2-2',
              children: [
                {
                  label: '三级 2-2-1'
                }
              ]
            }
          ]
        },
        {
          label: '一级 3',
          children: [
            {
              label: '二级 3-1',
              children: [
                {
                  label: '三级 3-1-1'
                }
              ]
            },
            {
              label: '二级 3-2',
              children: [
                {
                  label: '三级 3-2-1'
                }
              ]
            }
          ]
        }
      ]
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
    @include flex(column, flex-start);
    width: 230px;
    height: 100%;
    border-right: 1px solid #d9d9d9;
    overflow: hidden;

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
      @include flex(row, space-between);
      width: 100%;
      height: 20px;
      line-height: 20px;
      margin: 12px 0;
      padding: 0 10px;
      .btn {
        font-size: 14px;
        font-family: PingFangSC, PingFangSC-Regular;
        font-weight: 400;
        text-align: left;
        color: #262626;
        line-height: 20px;
        cursor: pointer;
        .icon {
          font-size: 16px;
        }

        &:hover {
          color: #1890ff;
        }
      }
    }

    .tree {
      width: 100%;
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
