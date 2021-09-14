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
              node-key="idx"
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
                @dragstart="e => handleDragClick(e, data, node)"
              >
                <JSvg class="jsvg-icon" :name="data.icon"></JSvg>
                <span>{{ data.name }}</span>
              </div>
            </el-tree>
          </div>
        </aside>

        <section class="content-section" v-loading="detailLoading">
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
              v-model="secondTaskForm.sqlInfo.sql"
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
            <el-row class="row" type="flex" align="middle" justify="center">
              <template v-for="(item, idx) in selectedTable">
                <div
                  :key="idx"
                  :class="['col', `col-${idx + 1}`]"
                  v-if="item.type === 'tag'"
                >
                  <el-tag
                    closable
                    id="tagItem"
                    :class="[
                      'table-item',
                      `table-item-${idx}`,
                      item.isChecked ? 'table-item-checked' : ''
                    ]"
                    @mouseenter.native="item.isShowDot = true"
                    @mouseleave.native="item.isShowDot = false"
                    @close="handleDeleteTagClick(idx)"
                  >
                    <el-tooltip
                      effect="dark"
                      placement="top-start"
                      :content="item.name"
                    >
                      <span class="col">{{ item.name }}</span>
                    </el-tooltip>
                    <span v-if="item.isShowDot" class="dot dot-left"></span>
                    <span v-if="item.isShowDot" class="dot dot-right"></span>
                  </el-tag>
                </div>

                <div
                  v-else-if="item.isShow && selectedTable.length - 1 !== idx"
                  class="col relation-line"
                  :key="idx"
                >
                  <div class="line"></div>
                  <JSvg
                    name="left-link"
                    class="icon"
                    @click.native="handleLinkIconClick({ idx, ...item })"
                  />
                  <div class="line"></div>
                </div>
              </template>
            </el-row>
          </div>

          <div class="content-section-table">
            <div class="filter">
              <div class="label-wrap">
                <div class="label">宽表信息</div>
                <el-select
                  style="min-width:150px"
                  class="text-select"
                  v-model.trim="secondTaskForm.targetSource"
                  filterable
                  clearable
                  remote
                  size="mini"
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
                  size="mini"
                  style="margin-left:10px;"
                  placeholder="请输入宽表名称"
                  v-model.trim="secondTaskForm.wideTableName"
                >
                  <el-button
                    size="mini"
                    :disabled="verifyTableDisabled"
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
                <div class="label">增量字段</div>
                <el-select
                  size="mini"
                  v-model="secondTaskForm.syncCondition.incrementalField"
                  placeholder="请选择增量字段"
                >
                  <el-option
                    v-for="item in increFieldsOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>

                <!-- 增量类型 -->
                <el-select
                  style="margin-left:10px"
                  size="mini"
                  v-model="secondTaskForm.syncCondition.partition"
                  placeholder="请选择增量类型"
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
              <div class="label-wrap">
                <div class="label">时间过滤条件: T +</div>
                <el-input-number
                  size="mini"
                  style="width: 80px"
                  controls-position="right"
                  :min="0"
                  v-model="secondTaskForm.syncCondition.n"
                />
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
          :disabled="!secondTaskForm.sqlInfo.sql"
          @click="handleSaveSetting"
          >保存设置</el-button
        >
        <el-button
          class="btn"
          type="primary"
          :disabled="!secondTaskForm.sqlInfo.sql"
          @click="handleStepClick"
        >
          下一步
        </el-button>
      </footer>
    </div>

    <Affiliations ref="linkDialog" @on-confirm="handleVisualConfirm" />

    <Dialog
      width="600px"
      title="提示"
      ref="baseDialog"
      @on-confirm="handleSelectBatabase"
    >
      <div class="same-base-tip">
        <h4 class="title">
          该SQL表达式中所选择的库存在同名情况，请在下列重新选择正确的库:
        </h4>
        <el-checkbox-group class="group" v-model="checkList">
          <el-checkbox
            class="box"
            v-for="(item, idx) in sameNameDataBase"
            :key="idx"
            :label="item.datasourceId"
          >
            {{ item.databaseName }}({{ item.host }})
          </el-checkbox>
        </el-checkbox-group>
      </div>
    </Dialog>
  </div>
</template>

<script>
// import VueDraggable from 'vuedraggable'
import Back from '@/views/icredit/components/back'
import HeaderStepBar from './header-step-bar'
import Affiliations from './affiliations'
import dayjs from 'dayjs'
import crud from '@/mixins/crud'
import tableConfiguration from '@/views/icredit/configuration/table/data-sync-add'
import API from '@/api/icredit'
import { debounce } from 'lodash'
import { mapState } from 'vuex'
import { treeIconMapping, radioBtnOption, fieldTypeOptions } from '../contant'
import { randomNum, deepClone } from '@/utils/util'
import { validStrZh } from '@/utils/validate'
import Dialog from '@/views/icredit/components/dialog'

export default {
  components: { Back, HeaderStepBar, Affiliations, Dialog },
  mixins: [crud],

  data() {
    this.getFluzzyTableName = debounce(this.getFluzzyTableName, 500)
    this.getFluzzyStockName = debounce(this.getFluzzyStockName, 500)
    this.getFluzzyDictionary = debounce(this.getFluzzyDictionary, 500)

    return {
      isCanJumpNext: false,
      isCanSaveSetting: false,
      isShowDot: false,

      // 加载的状态
      detailLoading: false,
      treeLoading: false,
      searchLoading: false,
      searchStockLoading: false,
      tableLoading: false,
      widthTableLoading: false,

      fieldTypeOptions,
      radioBtnOption,
      tableConfiguration,
      treeData: [],
      zoningOptions: [],
      increFieldsOptions: [],
      tableNameOptions: [],
      stockNameOptions: [],
      sameNameDataBase: [
        {
          datasourceId: 1909,
          databaseName: '数据库-100',
          host: '192.168.0.90'
        },
        {
          datasourceId: 1999,
          databaseName: '数据库-1023ssd',
          host: '192.168.0.93'
        }
      ],
      searchTableName: '',
      checkList: [],

      // 可视化-已拖拽的表
      selectedTable: [],

      // 表单参数
      secondTaskForm: {
        syncCondition: { incrementalField: '', partition: '', n: undefined },
        sqlInfo: { sql: '', databaseHost: [] },
        targetSource: '', // 目标库
        wideTableName: '', // 宽表名称
        // partition: '', // 分区字段
        fieldInfos: [], // 表信息
        sourceType: 0, // 资源类型
        callStep: 2, // 调用步骤
        createMode: null, // 创建方式

        // 可视化表单参数
        dialect: null,
        sourceTables: [],
        view: []
      }
    }
  },

  computed: {
    ...mapState('user', ['workspaceId']),
    verifyTableDisabled() {
      return false
    }
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
      // taskId存在表明是编辑的情况
      this.secondTaskForm.taskId && this.getDetailData()
    },

    // 可视化-表拖拽
    handleDragClick(evt, data, node) {
      const { id, parent } = node
      evt.dataTransfer.setData(
        'application/json',
        JSON.stringify({ tableId: id, database: parent.label, ...data })
      )
    },

    // 可视化-释放被动的表
    handleTagWrapDrop(evt) {
      evt.preventDefault()
      const domData = evt.dataTransfer.getData('application/json')
      const dataSource = JSON.parse(domData)
      console.log('ssss', dataSource)
      const addTableObj = [
        { type: 'tag', isChecked: false, isShowDot: false, ...dataSource },
        { type: 'line', iconName: 'left-link', isShow: false }
      ]

      const isExistIdx = this.selectedTable.findIndex(
        ({ tableId }) => dataSource.tableId === tableId
      )

      // 不能重复拖动同一张表
      if (isExistIdx > -1) {
        this.$message.error('拖动的表已存在， 请重新选择一张表！')
        return
      }

      // 不同类型的数据库不能拖该库下的表
      const filterDiffType = () => {
        const { dialect } = this.selectedTable[0]
        if (dataSource.dialect !== dialect) {
          this.$message.error('数据库类型不统一， 请重新选择一张表！')
          return true
        }
        return false
      }

      if (!this.selectedTable.length) {
        this.selectedTable.push(...addTableObj)
      } else {
        if (filterDiffType()) return
        this.selectedTable = deepClone([
          ...this.selectedTable,
          ...addTableObj
        ]).map(({ isShow, ...rest }) => {
          return {
            isShow: true,
            ...rest
          }
        })
      }
    },

    // 点击图标设置关联字段
    handleVisualConfirm(options) {
      const { idx } = options
      const updateViewData = () => {
        const index = this.secondTaskForm.view.findIndex(
          item => item.idx === idx
        )
        this.secondTaskForm.view.splice(index, 1, options)
      }

      !this.secondTaskForm.view.length
        ? this.secondTaskForm.view.push(options)
        : updateViewData()

      console.log(this.secondTaskForm, 'this.secondTaskForm')
    },

    handlePreventDefault(evt) {
      evt.preventDefault()
    },

    // 可视化-删除已选择的的表
    handleDeleteTagClick(idx) {
      switch (idx) {
        case 0:
          this.selectedTable = []
          break

        default:
          break
      }
      // this.selectedTable = deepClone(
      //   this.selectedTable.filter(({ type, isShow }) => {
      //     return type === 'tag' || isShow
      //   })
      // ).filter((item, index) => {
      //   return index !== idx && index !== idx + 1 && index && idx - 1
      // })
    },

    // 可视化-点击关联图标打开关联弹窗
    handleLinkIconClick(options) {
      const { idx } = options
      // 目前只能新增四张表， 通过ICON的index找前后关联的两张表index
      const lfTbIdx = idx - 1
      const rhTbIdx = idx + 1

      console.log(idx, rhTbIdx, this.selectedTable)

      const { dialect, datasourceId, name, database } = this.selectedTable[
        lfTbIdx
      ]
      const {
        datasourceId: sid,
        name: rName,
        database: rDatabase
      } = this.selectedTable[rhTbIdx]

      const leftTable = { datasourceId, name, database }
      const rightTable = { datasourceId: sid, name: rName, database: rDatabase }

      this.$refs.linkDialog.open({
        idx,
        title: '新增关联关系',
        dialect,
        leftTable,
        rightTable
      })
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
      const firstFrom = JSON.parse(sessionStorage.getItem('taskForm') || '{}')
      console.log('firstFrom', firstFrom)
      // 可视化方式参数处理
      !firstFrom.createMode && this.handleVisualizationParams()
      const { fieldInfos, ...restForm } = this.secondTaskForm
      const newFieldInfos = deepClone(fieldInfos).map(
        ({
          dictLoading,
          dictionaryOptions,
          fieldTypeOptions: fOption,
          fieldType,
          ...rest
        }) => {
          return {
            fieldType: fieldType[1],
            ...rest
          }
        }
      )
      const secondForm = { fieldInfos: newFieldInfos, workspaceId, ...restForm }

      const params = { ...secondForm, ...firstFrom }
      sessionStorage.setItem('taskForm', JSON.stringify(params))
      return params
    },

    // 处理可视化表单参数
    handleVisualizationParams() {
      this.secondTaskForm.dialect = this.selectedTable[0]?.dialect

      this.secondTaskForm.view = deepClone(this.secondTaskForm.view).map(
        ({ idx, ...item }) => item
      )

      this.secondTaskForm.sourceTables = deepClone(this.selectedTable)
        .filter(({ type }) => type === 'tag')
        .map(({ datasourceId, database, name }) => {
          return {
            datasourceId,
            database,
            tableName: name
          }
        })
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

    // 数据库同名选择弹窗回调
    handleSelectBatabase() {
      this.secondTaskForm.sqlInfo.databaseHost = deepClone(
        this.sameNameDataBase
      ).filter(({ datasourceId }) => this.checkList.includes(datasourceId))
      this.handleIdentifyTable()
    },

    // 识别宽表
    handleIdentifyTable() {
      this.handleVisualizationParams()
      const {
        createMode,
        sqlInfo,
        sourceTables,
        dialect,
        view
      } = this.secondTaskForm
      const sqlParams = {
        workspaceId: this.workspaceId,
        createMode,
        sqlInfo
      }

      const visualParams = {
        datasourceId: this.selectedTable[0]?.datasourceId,
        createMode,
        sourceTables: deepClone(
          sourceTables
        ).map(({ database, tableName }) => ({ database, tableName })),
        view,
        dialect
      }
      console.log('createMode==', createMode)
      this.widthTableLoading = false
      this.tableLoading = true
      API.dataSyncGenerateTable(createMode ? sqlParams : visualParams)
        .then(({ success, data }) => {
          if (success && data) {
            console.log(data)
            const { sql, partitions, fields, incrementalFields } = data
            this.zoningOptions = partitions
            this.increFieldsOptions = incrementalFields
            this.secondTaskForm.sqlInfo.sql = sql
            this.secondTaskForm.fieldInfos = this.hadleFieldInfos(fields)

            // 数据库同名的情况选择相应的库
            if (createMode && data.sameNameDataBase) {
              this.sameNameDataBase = data?.sameNameDataBase || []
              data.sameNameDataBase.length && this.$refs.baseDialog.open()
            } else {
              this.$refs.baseDialog.close()
            }
          }
        })
        .catch(() => {
          this.$refs.baseDialog.close()
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
      const { sourceType } = this.secondTaskForm
      const icon = (idx, name) => {
        return sourceType === 1
          ? treeIconMapping[sourceType][idx][name][idx]
          : treeIconMapping[sourceType][idx]
      }

      const params = {
        workspaceId: this.workspaceId,
        tableName: '',
        sourceType
      }
      this.treeLoading = true
      API.dataSyncCatalog(params)
        .then(({ success, data }) => {
          if (success && data) {
            this.treeData = data.map(item => {
              const { content = [], ...rest } = item
              const newContent = content?.map(list => {
                return {
                  icon: icon(1, list.name),
                  idx: new Date().getTime() * Math.random() * 10,
                  ...list
                }
              })
              return {
                icon: icon(0, item.name),
                idx: new Date().getTime() * Math.random() * 10,
                content: newContent,
                ...rest
              }
            })
          }
        })
        .finally(() => {
          this.treeLoading = false
        })
    },

    // 数据源表模糊搜索
    getFluzzyTableName(tableName) {
      const { sourceType } = this.secondTaskForm
      this.searchLoading = true
      API.dataSyncFluzzySearch({ tableName, sourceType })
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
    },

    // 编辑情况下获取详情
    getDetailData() {
      this.detailLoading = true
      API.dataSyncBuildDetial({ taskId: this.secondTaskForm.taskId })
        .then(({ success, data }) => {
          if (success && data) {
            for (const [key, value] of Object.entries(data)) {
              console.log(key, value, typeof value)
              this.addTaskForm[key] = value
            }
          }
        })
        .finally(() => {
          this.detailLoading = false
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
          cursor: pointer;

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

      .row {
        width: 100%;
        height: 100%;
        overflow: hidden;

        .col {
          width: 150px;
          height: 34px;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }

        .col-1 {
          text-align: right;
        }

        .relation-line {
          @include flex;
          width: 150;

          .line {
            width: 60px;
            height: 2px;
            background-color: #1890ff;
          }

          .icon {
            width: 30px;
            height: 20px;
            cursor: pointer;
          }
        }
      }

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
        cursor: pointer;

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

      .table-item-checked {
        color: #1890ff;
        background: #fff;
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

.same-base-tip {
  .title {
    margin-bottom: 10px;
    font-size: 16px;
  }

  .group {
    @include flex(column, center, flex-start);
    padding-left: 20px;

    .box {
      min-width: 80%;
      margin: 5px 0;
      padding: 10px;
    }

    .is-checked {
      border: 1px solid #1890ff;
      border-radius: 4px;
    }
  }
}
</style>
