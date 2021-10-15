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
          @change="handleSelectChange"
          v-model="value"
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

      <div class="btn-wrap" @click="handleAddTable">
        <i class="icon el-icon-circle-plus-outline"></i>
        <span class="text">新建表</span>
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
        <div
          slot-scope="{ node, data }"
          :id="node.id"
          :draggable="node.level > 1"
          :class="[
            'custom-tree-node',
            node.parent.disabled || data.disabled ? 'is-disabled' : ''
          ]"
        >
          <div class="left">
            <span v-if="data.type === '3'" class="circle"></span>
            <JSvg class="jsvg-icon" :name="data.icon"></JSvg>
            <span>{{ data.label }}</span>
          </div>
          <div class="right">
            <el-dropdown @command="command => handleCommand(command, node)">
              <span class="el-dropdown-link">
                <i class="el-icon-more icon"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <!-- 停用 -->
                <template v-if="node.parent.disabled || data.disabled">
                  <el-dropdown-item command="enabled">启用</el-dropdown-item>
                  <el-dropdown-item v-if="data.icon === 'table'" command="edit">
                    编辑
                  </el-dropdown-item>
                  <el-dropdown-item command="delete">删除</el-dropdown-item>
                </template>

                <!-- 启用 -->
                <template v-else>
                  <el-dropdown-item
                    command="add"
                    v-if="data.icon === 'database'"
                  >
                    新增表
                  </el-dropdown-item>
                  <el-dropdown-item command="disabled">停用</el-dropdown-item>
                </template>
              </el-dropdown-menu>
            </el-dropdown>
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
          label: 'datax_web',
          icon: 'database',
          type: 0,
          id: 1,
          disabled: false,
          category: 'database',
          children: [
            {
              label: 'h_app_sysytem',
              icon: 'table',
              type: 1,
              id: 2,
              disabled: false,
              category: 'table'
            },
            {
              label: 'h_data_metadata_code',
              icon: 'table',
              type: 2,
              id: 3,
              disabled: true,
              category: 'table'
            }
          ]
        },
        {
          label: 'datax_web2',
          icon: 'database',
          type: 0,
          id: 6,
          disabled: true,
          category: 'database',
          children: [
            {
              label: 'h_app_sysytem',
              icon: 'table',
              type: 1,
              id: 7,
              category: 'table'
            },
            {
              label: 'h_data_metadata_code',
              icon: 'table',
              type: 2,
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

    // 下拉菜单
    handleCommand(command, node) {
      console.log(node, 'node123')
      switch (command) {
        case 'doc':
          this.$refs.addDoc.$refs.addDocDialog.open()
          break
        case 'edit':
          this.$refs.editFlow.$refs.editDialog.open()
          break
        case 'delete':
          this.handleDeleteBtnClick()
          break
        case 'disabled':
          this.handleDisabledBtnClick()
          break

        default:
          break
      }
    },

    // 新增表
    handleAddTable() {
      this.$router.push('/workspace/data-model/add')
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

    // 停用
    handleDisabledBtnClick() {
      const options = {
        opType: 'Disabled',
        title: '停用表xxxx',
        beforeOperateMsg:
          '停用后，该表将不能再写入内容且不能提供数据服务（后期可点击启用进行恢复），确认要停用吗？'
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
