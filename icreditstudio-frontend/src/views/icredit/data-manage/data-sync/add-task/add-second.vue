<!--
 * @Author: lizheng
 * @Description: 新增同步任务 - 同步任务构建
 * @Date: 2021-09-02
-->
<template>
  <div class="add-task-page">
    <Back path="/data-manage/data-sync" />
    <div class="add-task">
      <HeaderStepBar :cur-step="2" />
      <div class="add-task-content">
        <aside class="content-aside">
          <div class="content-aside-header">
            <el-select
              class="text-select"
              v-model="searchTableName"
              filterable
              clearable
              remote
              placeholder="请输入表名称"
              size="mini"
              :loading="searchLoading"
              :remote-method="getFluzzyTableName"
              @clear="tableNameOptions = []"
            >
              <el-option
                v-for="item in tableNameOptions"
                :key="item.tableName"
                :label="item.tableName"
                :value="item.tableName"
              >
              </el-option>
            </el-select>
            <i class="search el-icon-search"></i>
          </div>
          <div class="content-aside-tree">
            <el-button-group class="btn-group">
              <el-radio-group
                v-model="secondTaskForm.sourceType"
                @change="changeSourceType"
              >
                <el-radio-button
                  :class="item.className"
                  :label="item.label"
                  :key="item.label"
                  v-for="item in radioBtnOption"
                >
                  {{ item.name }}
                </el-radio-button>
              </el-radio-group>
            </el-button-group>

            <el-tree
              class="tree"
              :data="treeData"
              node-key="name"
              default-expand-all
              highlight-current
              check-on-click-node
              empty-text="暂无数据"
              :props="{ label: 'name', children: 'content' }"
              v-loading="treeLoading"
            >
              <div
                :id="node.id"
                :draggable="node.level > 1"
                class="custom-tree-node"
                slot-scope="{ node, data }"
                @dragstart="e => handleDropClick(e, data, node)"
              >
                <JSvg class="jsvg-icon" :name="data.icon"></JSvg>
                <span>{{ data.name }}</span>
              </div>
            </el-tree>
          </div>
        </aside>

        <section class="content-section">
          <!-- sql语句 -->
          <div
            v-if="secondTaskForm.createMode === 1"
            class="content-section-header"
          >
            <el-input
              class="sql-textarea"
              type="textarea"
              placeholder="请在此输入hive语法的SQL语句"
              show-word-limit
              v-model="secondTaskForm.sql"
              :autosize="{ minRows: 7 }"
            >
            </el-input>
          </div>

          <!-- 可视化 -->
          <div
            v-else
            ref="dropArea"
            id="dropArea"
            class="content-section-header"
            @drop="handleTagWrapDrop"
            @dragover="handlePreventDefault"
          >
            <VueDraggable tag="span">
              <el-tag
                closable
                draggable
                id="tagItem"
                class="table-item"
                @mouseenter.native="isShowDot = true"
                @mouseleave.native="isShowDot = false"
                @click.native="handleLinkDialogOpen"
              >
                <!-- @mousedown.native="handleTagMouseDown" -->
                <!-- @dragstart="handleTagItemDrag" -->
                <span class="text">huhuhuhuhu</span>
                <span v-if="isShowDot" class="dot dot-top"></span>
                <span v-if="isShowDot" class="dot dot-left"></span>
                <span v-if="isShowDot" class="dot dot-right"></span>
                <span v-if="isShowDot" class="dot dot-bottom"></span>
              </el-tag>
            </VueDraggable>
          </div>

          <div class="content-section-table">
            <div class="filter">
              <div class="label-wrap">
                <div class="label">宽表信息</div>
                <el-select
                  style="min-width:250px"
                  class="text-select"
                  v-model.trim="secondTaskForm.targetSource"
                  filterable
                  clearable
                  remote
                  placeholder="请输入库名称"
                  :loading="searchStockLoading"
                  :remote-method="getFluzzyStockName"
                  @change="changeStockName"
                  @clear="handleClear('stockNameOptions')"
                >
                  <el-option
                    v-for="(item, idx) in stockNameOptions"
                    :key="idx"
                    :label="item.name"
                    :value="item.name"
                  >
                  </el-option>
                </el-select>
                <el-input
                  clearable
                  style="margin-left:10px"
                  placeholder="请输入宽表名称"
                  v-model.trim="secondTaskForm.wideTableName"
                >
                  <el-button
                    :disabled="!secondTaskForm.sql"
                    :class="['append-btn', isCanJumpNext ? '' : 'is-disabled']"
                    slot="append"
                    :loading="widthTableLoading"
                    @click="handleIdentifyTable"
                  >
                    {{ secondTaskForm.createMode ? '执行SQL' : '识别宽表' }}
                  </el-button>
                </el-input>
              </div>
              <div class="label-wrap">
                <div class="label">分区字段</div>
                <el-select
                  v-model="secondTaskForm.partition"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in zoningOptions"
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
              :table-data="secondTaskForm.fieldInfos"
            >
              <!-- 字段类型 -->
              <template #fieldTypeColumn="{row}">
                <el-cascader
                  v-model="row.fieldType"
                  :options="row.fieldTypeOptions"
                  :show-all-levels="false"
                  @change="handleCascaderChange"
                ></el-cascader>
              </template>

              <!-- 字段中文名称 -->
              <template #fieldChineseNameColumn="{row}">
                <el-input
                  clearable
                  placeholder="请输入中文名称"
                  v-model.trim="row.fieldChineseName"
                  @change="handleChangeChineseName"
                ></el-input>
              </template>

              <!-- 关联字典表 -->
              <template #associateDictColumn="{row}">
                <el-select
                  remote
                  filterable
                  clearable
                  placeholder="请输入字典名称"
                  class="text-select"
                  v-model.trim="row.associateDict"
                  :loading="row.dictLoading"
                  :remote-method="name => getFluzzyDictionary(name, row)"
                  @clear="row.dictionaryOptions = []"
                >
                  <el-option
                    v-for="(item, idx) in row.dictionaryOptions"
                    :key="idx"
                    :label="item.name"
                    :value="item.key"
                  >
                  </el-option>
                </el-select>
              </template>
            </JTable>
          </div>
        </section>
      </div>

      <footer class="footer-btn-wrap">
        <el-button class="btn" @click="$router.push('/data-manage/add-task')">
          上一步
        </el-button>
        <el-button
          class="btn"
          :disabled="!secondTaskForm.sql"
          @click="handleSaveSetting"
          >保存设置</el-button
        >
        <el-button
          class="btn"
          type="primary"
          :disabled="!secondTaskForm.sql"
          @click="handleStepClick"
        >
          下一步
        </el-button>
      </footer>
    </div>

    <Affiliations ref="linkDialog" />
  </div>
</template>

<script>
import VueDraggable from 'vuedraggable'
import Back from '@/views/icredit/components/back'
import HeaderStepBar from './header-step-bar'
import Affiliations from './affiliations'
import dayjs from 'dayjs'
import crud from '@/mixins/crud'
import tableConfiguration from '@/views/icredit/configuration/table/data-sync-add'
import API from '@/api/icredit'
import { debounce } from 'lodash'
import { mapState } from 'vuex'
import { treeIconMapping } from '../contant'
import { randomNum, deepClone } from '@/utils/util'
import { validStrZh } from '@/utils/validate'

export default {
  components: { Back, HeaderStepBar, Affiliations, VueDraggable },
  mixins: [crud],

  data() {
    this.getFluzzyTableName = debounce(this.getFluzzyTableName, 500)
    this.getFluzzyStockName = debounce(this.getFluzzyStockName, 500)
    this.getFluzzyDictionary = debounce(this.getFluzzyDictionary, 500)

    // 字段类型
    this.fieldTypeOptions = [
      {
        value: 0,
        label: '数值类',
        children: [
          { label: 'TINYINT', value: 'TINYINT' },
          { label: 'SMALLINT', value: 'SMALLINT' },
          { label: 'INT', value: 'INT' },
          { label: 'BIGINT', value: 'BIGINT' },
          { label: 'FLOAT', value: 'FLOAT' },
          { label: 'DOUBLE', value: 'DOUBLE' },
          { label: 'DECIMAL', value: 'DECIMAL' }
        ]
      },
      {
        value: 1,
        label: '日期时间类',
        children: [
          { label: 'TIMESTAMP', value: 'TIMESTAMP' },
          { label: 'DATE', value: 'DATE' }
        ]
      },
      {
        value: 2,
        label: '字符串类',
        children: [
          { label: 'STRING', value: 'STRING' },
          { label: 'VARCHAR', value: 'VARCHAR' }
        ]
      }
    ]

    return {
      // 是否可以跳到下一步
      isCanJumpNext: false,
      isCanSaveSetting: false,
      isShowDot: false,
      searchTableName: '',
      searchStockName: '',
      tableNameOptions: [],
      stockNameOptions: [],
      treeLoading: false,
      searchLoading: false,
      searchStockLoading: false,
      tableLoading: false,
      widthTableLoading: false,
      type: 'sql',
      sql: '',
      sourceType: 0,
      tableConfiguration,
      zoningOptions: [],
      value: '',
      treeData: [],
      radioBtnOption: [
        { label: 0, className: 'btn btn-left', name: '外接数据库' },
        { label: 1, className: 'btn btn-center', name: '本地文件' },
        { label: 2, className: 'btn btn-right', name: '区块链数据' }
      ],

      // 表单参数
      secondTaskForm: {
        sql: '', // SQL命令
        targetSource: '', // 目标库
        wideTableName: '', // 宽表名称
        partition: '', // 分区字段
        fieldInfos: [], // 表信息
        sourceType: 0, // 资源类型
        callStep: 2, // 调用步骤
        createMode: 1 // 创建方式
      }
    }
  },

  computed: {
    ...mapState('user', ['workspaceId'])
  },

  created() {
    this.initPage()
    this.getDatasourceCatalog()
  },

  methods: {
    initPage() {
      const taskForm = JSON.parse(sessionStorage.getItem('taskForm') || '{}')
      this.secondTaskForm = { ...this.secondTaskForm, ...taskForm }
      this.secondTaskForm.fieldInfos = this.hadleFieldInfos(taskForm.fieldInfos)
    },

    handleDropClick(evt, data, node) {
      console.log('evt', evt, data, node)
      evt.dataTransfer.setData('text/json', data)
    },

    handleTagItemDrag(evt) {
      // eslint-disable-next-line no-param-reassign
      evt.currentTarget.style.border = 'dashed'
      evt.dataTransfer.setData('text/json', evt.target.id)
    },

    handleTagWrapDrop(evt) {
      evt.preventDefault()
      const data = evt.dataTransfer.getData('text/json')
      console.log('ddddd', data)
      // evt.target.appendChild(document.getElementById(data))
      // evt.dataTransfer.clearData()
      const tagItemDom = document.getElementById('tagItem')
      const tagItemNode = tagItemDom.cloneNode(true)
      document.getElementById('dropArea').appendChild(tagItemNode)
    },

    handleTagMouseDown(e) {
      const tag = document.getElementById('tag1')
      console.log()
      tag.style.left = `${e.clientX}px`
      tag.style.top = `${e.clientY}px`

      // tag.onmouseup = () => {
      //   document.onmousemove = null
      // }
    },

    handlePreventDefault(evt) {
      evt.preventDefault()
    },

    handleLinkDialogOpen() {
      this.$refs.linkDialog.open({ title: '新增关联关系' })
    },

    // 中文名称
    handleChangeChineseName(name) {
      console.log(name)
      const valid = validStrZh(name)
      this.isCanSaveSetting = valid
      this.$message.error('该字段为中文名称输入，请检查后重新输入！')
    },

    // 保存设置
    handleSaveSetting() {
      API.dataSyncAdd(this.handleTaskFormParams())
        .then(({ success, data }) => {
          if (success && data) {
            this.$notify.success({ title: '操作结果', message: '保存成功' })
          }
        })
        .finally(() => {
          this.saveSettingLoading = false
        })
    },

    // 下一步
    handleStepClick() {
      this.handleTaskFormParams()
      this.$router.push('/data-manage/add-transfer')
    },

    // 表单参数缓存以及过滤处理
    handleTaskFormParams() {
      const { workspaceId } = this
      const { fieldInfos, ...restForm } = this.secondTaskForm
      const newFieldInfos = deepClone(fieldInfos).map(
        ({
          dictLoading,
          dictionaryOptions,
          fieldTypeOptions,
          fieldType,
          ...rest
        }) => {
          return {
            fieldType: fieldType[1],
            ...rest
          }
        }
      )
      const firstFrom = JSON.parse(sessionStorage.getItem('taskForm') || '{}')
      const secondForm = { fieldInfos: newFieldInfos, workspaceId, ...restForm }
      const params = { ...firstFrom, ...secondForm }
      sessionStorage.setItem('taskForm', JSON.stringify(params))
      return params
    },

    // 清空下拉框Options
    handleClear(name) {
      this.isCanJumpNext = false
      this[name] = []
    },

    handleDictClear(row) {
      console.log(row, 'lp')
      // eslint-disable-next-line no-param-reassign
      row.dictionaryOptions = []
    },

    // 字段类型级联值发生改变
    handleCascaderChange(value) {
      console.log(value)
    },

    // 识别宽表
    handleIdentifyTable() {
      const sqlParams = {
        workspaceId: this.workspaceId,
        createMode: this.secondTaskForm.createMode,
        sql: this.secondTaskForm.sql
      }

      const params = {}
      this.widthTableLoading = false
      this.tableLoading = true
      API.dataSyncGenerateTable(
        this.secondTaskForm.createMode ? sqlParams : params
      )
        .then(({ success, data }) => {
          if (success && data) {
            console.log(data)
            const { partitions = [], fields = [] } = data
            this.zoningOptions = partitions
            this.secondTaskForm.sql = data.sql
            this.secondTaskForm.fieldInfos = this.hadleFieldInfos(fields)
          }
        })
        .finally(() => {
          this.widthTableLoading = false
          this.tableLoading = false
        })
    },

    // 表格信息过滤
    hadleFieldInfos(fields = []) {
      return deepClone(fields).map(item => {
        return {
          fieldTypeOptions: this.fieldTypeOptions,
          dictLoading: false,
          dictionaryOptions: [],
          ...item
        }
      })
    },

    // 切换数据源类型
    changeSourceType(value) {
      console.log(value, 'value')
      this.getDatasourceCatalog()
    },

    // 自动生成宽表名称
    changeStockName(name) {
      if (name && this.secondTaskForm.wideTableName === '') {
        this.secondTaskForm.wideTableName = `widthtable_${dayjs(
          new Date()
        ).format('YYYYMMDD')}_${randomNum(100000, 11000000)}`
      }
    },

    // 数据库表目录
    getDatasourceCatalog() {
      const icon = (idx, name) => {
        return this.sourceType === 1
          ? treeIconMapping[this.sourceType][idx][name][idx]
          : treeIconMapping[this.sourceType][idx]
      }

      const params = {
        workspaceId: this.workspaceId,
        sourceType: this.sourceType,
        tableName: ''
      }
      this.treeLoading = true
      API.dataSyncCatalog(params)
        .then(({ success, data }) => {
          if (success && data) {
            this.treeData = data.map(item => {
              const { content = [], ...rest } = item
              const newContent = content?.map(list => {
                return { icon: icon(1, list.name), ...list }
              })
              return { icon: icon(0, item.name), content: newContent, ...rest }
            })
          }
        })
        .finally(() => {
          this.treeLoading = false
        })
    },

    // 数据源表模糊搜索
    getFluzzyTableName(tableName) {
      this.searchLoading = true
      API.dataSyncFluzzySearch({ tableName, sourceType: this.sourceType })
        .then(({ success, data }) => {
          if (success && data) {
            this.tableNameOptions = data
          }
        })
        .finally(() => {
          this.searchLoading = false
        })
    },

    // 宽表信息下拉框
    getFluzzyStockName(name) {
      // if (!this.dialect) {
      //   this.$message.error('请先填写SQL表达式！')
      //   return
      // }
      this.searchStockLoading = true
      API.dataSyncTargetSource({ name, workspaceId: this.workspaceId })
        .then(({ success, data }) => {
          if (success && data) {
            this.stockNameOptions = data
            this.isCanJumpNext = true
          }
        })
        .finally(() => {
          this.searchStockLoading = false
        })
    },

    // 关联字典模糊查询
    getFluzzyDictionary(name, row) {
      /* eslint-disable no-param-reassign */
      row.dictLoading = true
      API.dataSyncFluzzyDictionary({ name })
        .then(({ success, data }) => {
          if (success && data) {
            console.log(data)
            // eslint-disable-next-line no-param-reassign
            row.dictionaryOptions = data
          }
        })
        .finally(() => {
          // eslint-disable-next-line no-param-reassign
          row.dictLoading = false
        })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/public/data-manage';

.add-task {
  margin-top: -7px;
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
      position: relative;
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

      .text-select {
        width: 100%;
        font-size: 14px;
        font-family: PingFangSC, PingFangSC-Regular;
        font-weight: 400;
        text-align: left;
        color: #262626;
        ::v-deep {
          .el-input__inner {
            border: none;
            background-color: transparent;
          }
        }
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

        ::v-deep {
          .el-tree-node.is-current > .el-tree-node__content {
            color: #1890ff;
          }
        }
      }
    }
  }

  .content-section {
    flex: 1;
    height: 100%;
    overflow-y: auto;
    padding-bottom: 20px;

    &-header {
      position: relative;
      width: 100%;
      height: 160px;
      overflow-y: auto;
      border-bottom: 1px solid #e9e9e9;

      .table-item {
        @include flex;
        display: inline-flex;
        position: relative;
        width: 150px;
        height: 34px;
        background: #f0f0f0;
        border: 1px solid #1890ff;
        border-radius: 8px;
        line-height: 34px;
        font-size: 14px;
        font-family: PingFangSC, PingFangSC-Medium;
        font-weight: 500;
        text-align: center;
        color: #262626;
        line-height: 34px;
        margin: 10px;
        cursor: move;

        .dot {
          position: absolute;
          width: 6px;
          height: 6px;
          border: 1px solid #1890ff;
          border-radius: 4px;
          background: #fff;
        }

        .dot-top {
          top: 0;
          left: 50%;
          transform: translate(-50%, -50%);
        }

        .dot-bottom {
          bottom: -7px;
          left: 50%;
          transform: translate(-50%, -50%);
        }

        .dot-left {
          left: 0;
          top: 50%;
          transform: translate(-50%, -50%);
        }

        .dot-right {
          right: -6px;
          top: 50%;
          transform: translate(-50%, -50%);
        }

        ::v-deep {
          .el-icon-close {
            font-size: 15px;
            margin-top: 2px;
          }
        }
      }

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
          width: 120px;
          text-align: right;
          margin-right: 12px;
        }

        .append-btn {
          color: #fff;
          border-color: #1890ff;
          background: #1890ff;
          border-radius: 0px 4px 4px 0px;
        }

        .is-disabled {
          color: #fff;
          background-color: #8cc8ff;
          border-color: #8cc8ff;
          border-radius: 0px 4px 4px 0px;
        }

        ::v-deep {
          .el-input-group__append {
            outline: none;
            border: none;
          }
        }
      }
    }
  }
}

.footer-btn-wrap {
  margin-top: 0;
}
</style>
