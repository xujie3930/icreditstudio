<!--
 * @Author: lizheng
 * @Description: 数据开发
 * @Date: 2021-09-28
-->
<template>
  <div class="data-develop w100">
    <aside class="data-develop-aside">
      <el-select size="mini" class="top-select" placeholder="请选择">
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
      <el-tabs closable type="card">
        <el-tab-pane label="用户管理">用户管理</el-tab-pane>
        <el-tab-pane label="配置管理">配置管理</el-tab-pane>
        <el-tab-pane label="角色管理">角色管理</el-tab-pane>
        <el-tab-pane label="定时任务补偿">定时任务补偿</el-tab-pane>
      </el-tabs>

      <el-dropdown class="tabs-options">
        <div class="more">
          <i class="el-icon-caret-bottom"></i>
        </div>
        <el-dropdown-menu slot="dropdown" class="tabs-more">
          <el-dropdown-item>
            关闭其他
          </el-dropdown-item>
          <el-dropdown-item>
            关闭左侧
          </el-dropdown-item>
          <el-dropdown-item>
            关闭右侧
          </el-dropdown-item>
          <el-dropdown-item>
            关闭全部
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: '',
      value: '',
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

    .tabs-options {
      position: absolute;
      top: 0;
      right: 0;

      .more {
        text-align: center;
        width: 35px;
        height: 30px;
        line-height: 30px;
        background: rgba(255, 255, 255, 0.65);
        border: 1px solid rgba(0, 0, 0, 0.15);
        cursor: pointer;
      }
    }
  }
}
</style>
