<!--
 * @Author: lizheng
 * @Description: detail
 * @Date: 2021-10-08
-->

<template>
  <div>
    <el-tabs class="model-tab" v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="节点属性" name="first">
        <div class="tabs-detail">
          <template v-for="item in baseDetailConfig">
            <div
              v-if="item.level.includes(currentTab.level)"
              class="tabs-detail-item"
              :key="item.label"
            >
              <div class="label">{{ item.label }}</div>
              <div v-if="item.key === 'remark'" class="text">
                <el-input class="input" v-model="item.value"></el-input>
              </div>
              <div v-else class="text">{{ item.value }}</div>
            </div>
          </template>
        </div>
      </el-tab-pane>

      <el-tab-pane label="子节点信息" name="second">
        <div class="table-wrap">
          <div class="table-header space" v-if="currentTab.level === 3">
            <div>
              <span class="label">名称</span>
              <el-input
                clearable
                style="width:300px"
                placeholder="请输入名称"
                v-model="name"
              ></el-input>
              <el-button class="btn" size="mini" type="primary">搜索</el-button>
            </div>

            <div>
              <el-button
                class="btn"
                size="mini"
                type="primary"
                @click="handleAddNextLevel"
                >新增下级</el-button
              >
              <el-button
                class="btn"
                size="mini"
                type="primary"
                :disabled="!selectionRows.length"
                @click="handleDeletLevel({ opType: 'batchDelete' })"
                >批量删除</el-button
              >
            </div>
          </div>

          <div class="table-header" v-else>
            <span class="label">分类名称</span>
            <el-input
              clearable
              style="width:300px"
              placeholder="请输入分类名称"
              v-model="name"
            ></el-input>
            <el-button class="btn" size="mini" type="primary">搜索</el-button>
          </div>

          <JTable
            v-if="currentTab.level === 3"
            ref="ctable"
            v-loading="tableLoading"
            :table-data="3"
            :table-configuration="chilrenTableConfig"
            @selection-change="handleSelectChange"
          >
          </JTable>

          <JTable
            v-else
            ref="table"
            v-loading="tableLoading"
            :table-data="tableData"
            :table-configuration="parentTableConfig"
          >
          </JTable>
        </div>
      </el-tab-pane>
    </el-tabs>

    <Add ref="addLevel" />
  </div>
</template>

<script>
import Add from './add'
import tableConfig from '@/views/icredit/configuration/table/workspace-system-dictionary'
import childrenTableConfig from '@/views/icredit/configuration/table/workspace-system-node'

export default {
  components: { Add },

  data() {
    return {
      selectionRows: [],

      name: '',
      tableData: [{ name: 'monkeyCode' }, { name: 'masterLee ' }],
      activeName: 'first',
      tableLoading: false,
      parentTableConfig: tableConfig,
      chilrenTableConfig: childrenTableConfig(this),

      // 表信息
      baseDetailConfig: [
        { level: [0, 1, 2, 3], label: '分类名称', key: '', value: '是' },
        { level: [0, 1, 2, 3], label: '分类编号', key: '', value: '12KB' },
        {
          level: [0, 1],
          label: '子一级节点个数',
          key: '',
          value: '12个'
        },
        {
          level: [0, 1],
          label: '子二级节点个数',
          key: '',
          value: '12个'
        },
        {
          level: [0],
          label: '子三级节点个数',
          key: '',
          value: '12个'
        },
        { level: [2], label: '子节点个数', key: '', value: '12个' },
        {
          level: [0, 1, 2, 3],
          label: '叶子节点个数',
          key: '',
          value: '2021'
        },
        { level: [0, 1, 2, 3], label: '创建人', key: '', value: 'admin' },
        {
          level: [0, 1, 2, 3],
          label: '创建时间',
          key: '',
          value: '2021-06-30'
        },
        {
          level: [0, 1, 2, 3],
          label: '更新时间',
          key: '',
          value: '2021-07-18  24:00:05'
        },
        {
          level: [0, 1, 2, 3],
          label: '备注',
          key: 'remark',
          value: '用于datax测试'
        }
      ]
    }
  },

  props: {
    currentTab: {
      type: Object,
      default: () => ({ level: 0 })
    }
  },

  methods: {
    handleClick() {},

    handleAddNextLevel() {
      this.$refs.addLevel.open({ opType: 'add' })
    },

    handleDeletLevel({ row, opType }) {
      console.log(row, opType, 'opopop')
      const message = '请确认是否删除该项数据？'
      this.$confirm(message, '删除提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
        .catch(() => {})
    },

    handleSelectChange(rows) {
      this.selectionRows = rows.map(({ id }) => id)
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

  .table-header {
    @include flex(row, flex-start);
    margin-bottom: 16px;

    .label {
      margin-right: 10px;
    }

    .btn {
      margin-left: 10px;
    }
  }

  .space {
    @include flex(row, space-between);
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

      .input {
        /deep/ .el-input__inner {
          border: none;
        }
      }
    }
  }
}
</style>
