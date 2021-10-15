<!--
 * @Description: 系统字典
 * @Date: 2021-10-12
-->

<template>
  <div class="data-develop w100">
    <aside class="data-develop-aside">
      <div class="title">字典</div>
      <div class="search-select" style="margin-bottom: 10px">
        <el-select
          class="text-select"
          filterable
          clearable
          remote
          placeholder="请输入分类名称"
          size="mini"
          v-model="value"
          :loading="searchLoading"
          @change="handleSelectChange"
        >
          <el-option
            v-for="item in selectOptions"
            :key="item.tableName"
            :label="item.tableName"
            :value="item.id"
          >
          </el-option>
        </el-select>
        <i class="search el-icon-search"></i>
      </div>

      <el-tree
        class="tree"
        ref="tree"
        node-key="id"
        :data="treeData"
        :expand-on-click-node="false"
        :default-expanded-keys="defalutExpandKey"
        @node-click="handleNodeClick"
      >
        <div slot-scope="{ node, data }" :id="node.id" class="custom-tree-node">
          <div class="left">
            <JSvg class="jsvg-icon" :name="data.icon"></JSvg>
            <span>{{ data.label }}</span>
          </div>
        </div>
      </el-tree>
    </aside>

    <section class="data-develop-section">
      <Tabs
        :cur-tab-name="curTabName"
        :tabs-config="tabsConfig"
        @change="changeTabCallback"
        @delete="deleteTabCallback"
      >
        <TabPanel :current-tab="currentTab" slot="panel" />
      </Tabs>
    </section>

    <Message ref="operateMessage" @on-confirm="messageOperateCallback" />
  </div>
</template>

<script>
import Tabs from '@/views/icredit/components/tabs'
import TabPanel from './panel'
import Message from '@/views/icredit/components/message'

export default {
  components: { Tabs, TabPanel, Message },

  data() {
    return {
      name: '',
      value: '',
      value1: '',
      selectOptions: [{ tableName: 'datax_web', id: 1 }],
      searchLoading: false,
      curTabName: null,
      currentTab: {},
      curNodeKey: null,
      defalutExpandKey: null,
      treeData: [
        {
          label: '数据标准',
          icon: 'database',
          level: 0,
          id: 1,
          disabled: false,
          category: 'database',
          children: [
            {
              label: '标准类型',
              icon: 'table',
              level: 1,
              id: 21,
              disabled: false,
              category: 'table',
              children: [
                {
                  label: '数据元标准',
                  icon: 'table',
                  level: 2,
                  id: 22,
                  disabled: false,
                  category: 'table',
                  children: [
                    {
                      label: '表示词',
                      icon: 'table',
                      level: 3,
                      id: 23,
                      disabled: false,
                      category: 'table'
                    }
                  ]
                }
              ]
            },
            {
              label: 'h_data_metadata_code',
              icon: 'table',
              level: 2,
              id: 3,
              disabled: true,
              category: 'table'
            }
          ]
        },
        {
          label: 'datax_web2',
          icon: 'database',
          level: 0,
          id: 6,
          disabled: true,
          category: 'database',
          children: [
            {
              label: 'h_app_sysytem',
              icon: 'table',
              level: 1,
              id: 7,
              category: 'table'
            },
            {
              label: 'h_data_metadata_code',
              icon: 'table',
              level: 2,
              id: 8,
              category: 'table'
            }
          ]
        }
      ],
      tabsConfig: []
    }
  },

  watch: {
    curTabName(nVal) {
      this.$refs.tree.setCurrentKey(nVal)
    }
  },

  mounted() {
    this.initPage()
  },

  methods: {
    initPage() {
      const { label, id, ...rest } = this.treeData[0]
      this.tabsConfig.push({ name: label, id, ...rest })
      this.defalutExpandKey = [id]
      this.$refs.tree.setCurrentKey(id)
      this.curTabName = id
      this.currentTab = this.treeData[0]
    },

    // 点击选中当前节点
    handleNodeClick(curData, curNode) {
      console.log('curData, curNode=', curData, curNode)
      const { label, id, ...rest } = curData
      const idx = this.tabsConfig.findIndex(item => item.id === id)
      this.curTabName = id
      this.currentTab = curData
      // tab选项已经存在当前节点
      if (idx > -1) {
        console.log(id)
      } else {
        this.tabsConfig.push({ name: label, id, ...rest })
      }
    },

    handleSelectChange(id) {
      if (!id) return
      this.curTabName = id
      const idx = this.tabsConfig.findIndex(item => item.id === id)
      this.currentTab = this.tabsConfig[idx]
    },

    // 删除
    handleDeleteBtnClick() {
      const options = {
        opType: 'Delete',
        title: '删除表xxxx',
        beforeOperateMsg:
          '表删除后将不在列表中展示，且不可在启用，危险操作请谨慎处理，确认一定要删除吗？'
      }
      this.$refs.operateMessage.open(options)
    },

    messageOperateCallback() {
      this.$refs.operateMessage.close()
    },

    // 切换Tab
    changeTabCallback(curTab) {
      console.log(curTab, 'curTab')
      const { id } = curTab
      this.curTabName = id
      this.currentTab = curTab
      // this.$refs.tree.setCurrentKey(id)
    },

    // 删除Tab
    deleteTabCallback(tab, index) {
      console.log('tab', tab, index)
      const { id } = tab
      this.tabsConfig.splice(index, 1)
      // 删的是当前激活状态的tab
      if (this.curTabName === id) {
        const nTabIdx = index ? index - 1 : 0
        this.currentTab = this.tabsConfig[nTabIdx]
        this.curTabName = this.tabsConfig[nTabIdx].id
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

      .is-disabled {
        color: #c0c4cc;
        cursor: not-allowed;
        background-image: none;
        background-color: #fff;
        border-color: #ebeef5;
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
