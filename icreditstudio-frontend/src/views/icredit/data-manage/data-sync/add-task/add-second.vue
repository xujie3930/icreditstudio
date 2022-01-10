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
              @clear="handleClearTableName"
              @change="handleChangeTableName"
            >
              <el-option
                v-for="(item, idx) in tableNameOptions"
                :key="`${item.tableName}-${idx}`"
                :label="item.tableName"
                :value="item.tableName"
              >
              </el-option>
            </el-select>
            <i class="search el-icon-search" @click="handleChangeTableName"></i>
          </div>
          <div class="content-aside-tree">
            <el-button-group class="btn-group">
              <el-radio-group
                v-model="secondTaskForm.sourceType"
                @change="changeSourceType"
              >
                <el-radio-button
                  v-for="(item, idx) in radioBtnOption"
                  :key="`${item.label}-${idx}`"
                  :label="item.label"
                  :class="item.className"
                >
                  {{ item.name }}
                </el-radio-button>
              </el-radio-group>
            </el-button-group>

            <!-- 左侧数据库树列表 -->
            <el-tree
              highlight-current
              check-on-click-node
              class="tree"
              ref="tree"
              node-key="idx"
              empty-text="暂无数据"
              :data="treeData"
              :props="{ label: 'name', children: 'content' }"
              :default-expanded-keys="defalutExpandKey"
              v-loading="treeLoading"
            >
              <div
                :id="node.id"
                :draggable="node.level > 1 && opType !== 'edit'"
                class="custom-tree-node"
                slot-scope="{ node, data }"
                @dragstart="e => handleDragClick(e, data, node)"
              >
                <JSvg class="jsvg-icon" :name="data.icon"></JSvg>
                <el-tooltip
                  v-if="data.name.length > 21 && node.level > 1"
                  effect="dark"
                  placement="top-start"
                  :content="data.name"
                >
                  <span class="table-label">{{ data.name }}</span>
                </el-tooltip>
                <span v-else>{{ data.name }}</span>
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
              v-model="secondTaskForm.sql"
              :autosize="{ minRows: 7 }"
              @change="handleSqlChange"
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
            <span v-if="!selectedTable.length" class="sql-tip">
              请从左侧数据源中拖动要关联的表到该区域中
            </span>
            <el-row
              v-else
              class="row"
              type="flex"
              align="middle"
              justify="center"
            >
              <template v-for="(item, idx) in selectedTable">
                <div
                  :key="idx"
                  :class="['col', `col-${idx + 1}`]"
                  v-if="item.type === 'tag'"
                >
                  <el-tag
                    :closable="opType !== 'edit'"
                    id="tagItem"
                    :class="[
                      'table-item',
                      `table-item-${idx}`,
                      item.isChecked ? 'table-item-checked' : ''
                    ]"
                    @mouseenter.native="item.isShowDot = true"
                    @mouseleave.native="item.isShowDot = false"
                    @close="handleDeleteTagClick(idx, item.name)"
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
                    :name="item.iconName"
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
                  :disabled="opType === 'edit'"
                  :loading="searchStockLoading"
                  :remote-method="getFluzzyStockName"
                  @change="changeStockName"
                  @clear="handleClear('stockNameOptions')"
                >
                  <el-option
                    v-for="(item, idx) in stockNameOptions"
                    :key="`${item.name}-${idx}`"
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
                  :disabled="opType === 'edit'"
                  v-model.trim="secondTaskForm.wideTableName"
                  @blur="handleVerifyWidthTableName"
                >
                  <el-button
                    size="mini"
                    :class="[
                      'append-btn',
                      verifyTableDisabled && opType !== 'edit'
                        ? ''
                        : 'append-btn-disabled'
                    ]"
                    slot="append"
                    :disabled="opType === 'edit'"
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
                  clearable
                  size="mini"
                  :disabled="opType === 'edit'"
                  v-model="
                    secondTaskForm.syncCondition[
                      opType === 'edit'
                        ? 'incrementalFieldLabel'
                        : ' incrementalField'
                    ]
                  "
                  placeholder="请选择增量字段"
                  @change="handleIncrementFieldChange"
                >
                  <el-option
                    v-for="(item, idx) in increFieldsOptions"
                    :key="`${item.value}-${idx}`"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>

                <el-checkbox
                  size="mini"
                  style="margin-left:16px"
                  :disabled="opType === 'edit'"
                  v-if="secondTaskForm.syncCondition.incrementalField"
                  v-model="secondTaskForm.syncCondition.inc"
                >
                  增量存储
                </el-checkbox>
              </div>
              <div
                class="label-wrap"
                v-if="secondTaskForm.syncCondition.incrementalField"
              >
                <div class="label">时间过滤条件: T +</div>
                <el-input-number
                  size="mini"
                  style="width: 80px"
                  controls-position="right"
                  :disabled="opType === 'edit'"
                  :min="1"
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
                  @change="value => handleCascaderChange(row, value)"
                ></el-cascader>
              </template>

              <!-- 字段中文名称 -->
              <template #fieldChineseNameColumn="{row}">
                <el-input
                  clearable
                  show-word-limit
                  placeholder="请输入中文名称"
                  v-model.trim="row.fieldChineseName"
                ></el-input>
                <!-- @blur="handleChangeChineseName(row)" -->
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
                    :key="`${item.name}-${idx}`"
                    :label="item.name"
                    :value="item.key"
                  >
                  </el-option>
                </el-select>
              </template>

              <!-- 备注 -->
              <template #remarkColumn="{row}">
                <el-input
                  clearable
                  placeholder="请输入备注"
                  v-model.trim="row.remark"
                ></el-input>
              </template>

              <!-- 操作 -->
              <template #operationColumn="params">
                <el-button
                  size="mini"
                  type="text"
                  :disabled="opType === 'edit'"
                  @click="handleDateleRow(params)"
                  >删除</el-button
                >
              </template>
            </JTable>
          </div>
        </section>
      </div>

      <footer class="footer-btn-wrap">
        <el-button class="btn" @click="handlePreviousClick">
          上一步
        </el-button>
        <el-button
          class="btn"
          type="primary"
          :disabled="!secondTaskForm.sql"
          :loading="saveSettingLoading"
          @click="handleStepClick"
        >
          下一步
        </el-button>
      </footer>
    </div>

    <!-- 设置关联关系 -->
    <Affiliations ref="linkDialog" @on-confirm="handleVisualConfirm" />

    <!-- 编辑表结构 -->
    <EditTable
      ref="editTable"
      @on-confirm="data => (secondTaskForm.fieldInfos = data)"
    />

    <!-- 数据库同名选择对应IP下的table -->
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
        <el-radio-group class="group" v-model="checkList">
          <el-radio
            class="box"
            v-for="(item, idx) in sameNameDataBase"
            :key="`${item.host}-${idx}`"
            :label="item.datasourceId"
          >
            {{ item.databaseName }}({{ item.host }})
          </el-radio>
        </el-radio-group>
      </div>
    </Dialog>
  </div>
</template>

<script>
import HeaderStepBar from './header-step-bar'
import Affiliations from './affiliations'
import dayjs from 'dayjs'
import crud from '@/mixins/crud'
import tableConfiguration from '@/views/icredit/configuration/table/data-sync-add'
import API from '@/api/icredit'
import { debounce, cloneDeep } from 'lodash'
import { mapState } from 'vuex'
import {
  treeIconMapping,
  radioBtnOption,
  fieldTypeOptions,
  iconMapping
} from '../contant'
import { randomNum, deepClone } from '@/utils/util'
import { validStrZh, validStrSpecial } from '@/utils/validate'
import Dialog from '@/views/icredit/components/dialog'
import EditTable from './edit-table'

const viewDefaultData = {
  associatedType: undefined,
  conditions: [{ left: '', associate: '', right: '' }]
}

export default {
  components: { HeaderStepBar, Affiliations, EditTable, Dialog },
  mixins: [crud],

  data() {
    this.getFluzzyTableName = debounce(this.getFluzzyTableName, 500)
    this.getFluzzyStockName = debounce(this.getFluzzyStockName, 500)
    this.getFluzzyDictionary = debounce(this.getFluzzyDictionary, 500)

    return {
      step: '',
      opType: 'add',
      oldSql: '',
      datasourceId: null,
      isCanSaveSetting: false,
      isShowDot: false,

      // 加载的状态
      detailLoading: false,
      treeLoading: false,
      searchLoading: false,
      searchStockLoading: false,
      tableLoading: false,
      widthTableLoading: false,
      saveSettingLoading: false,

      fieldTypeOptions,
      radioBtnOption,
      tableConfiguration: tableConfiguration(this),
      originTreeData: [],
      treeData: [],
      defalutExpandKey: [],
      zoningOptions: [],
      increFieldsOptions: [],
      tableNameOptions: [],
      stockNameOptions: [],
      sameNameDataBase: [],
      searchTableName: '',
      checkList: [],
      oldFieldInfos: [],
      curNodeKey: undefined,

      // 可视化-已拖拽的表
      selectedTable: [],

      // 表单参数
      secondTaskForm: {
        syncCondition: {
          incrementalField: '',
          incrementalFieldLabel: '',
          inc: undefined,
          n: 1
        },
        sql: '',
        targetSource: '', // 目标库
        wideTableName: '', // 宽表名称
        fieldInfos: [], // 表信息
        sourceType: 0, // 资源类型
        callStep: 2, // 调用步骤
        createMode: null, // 创建方式

        // 可视化表单参数
        dialect: null,
        datasourceId: null,
        sourceTables: [],
        view: []
      }
    }
  },

  props: {
    options: {
      type: Object,
      default: () => ({})
    },

    form: {
      type: Object,
      default: () => ({})
    }
  },

  computed: {
    ...mapState('user', ['workspaceId']),

    // 识别宽表按钮禁用状态
    verifyTableDisabled() {
      const { sql } = this.secondTaskForm
      return Boolean(sql) || this.selectedTable.length
    }
  },

  watch: {
    form: {
      immediate: true,
      deep: true,
      handler(nVal) {
        if (nVal) {
          const { secondTaskForm: secondForm } = this
          this.secondTaskForm = { ...cloneDeep(secondForm), ...cloneDeep(nVal) }
        }
      }
    }
  },

  created() {
    this.initPage()
    this.getDatasourceCatalog()
  },

  methods: {
    initPage() {
      const { opType } = this.$route.query
      this.opType = opType ?? 'add'
      // taskId存在表明是该条数据已经保存过
      this.secondTaskForm.taskId && this.getDetailData()
    },

    // 重置参数
    initParams() {
      this.secondTaskForm.view = []
      this.secondTaskForm.fieldInfos = []
      this.secondTaskForm.sql = ''
      this.secondTaskForm.wideTableName = ''
      this.secondTaskForm.targetSource = ''
      this.secondTaskForm.syncCondition = {
        incrementalField: '',
        partition: '',
        n: undefined
      }
    },

    // 左侧数搜索-清空
    handleClearTableName() {
      const { idx } = this.originTreeData[0]
      this.tableNameOptions = []
      this.defalutExpandKey = [idx]
      this.treeData = this.originTreeData
      this.$nextTick(() => this.$refs.tree.setCurrentKey(idx))
    },

    // 左侧数搜索-更改
    handleChangeTableName() {
      const name = this.searchTableName
      if (this.searchTableName) {
        const filterTreeData = []
        this.originTreeData.forEach(({ content: children, ...restItem }) => {
          const childArr = children.filter(item => item.name.includes(name))
          if (childArr.length) {
            filterTreeData.push({
              content: childArr,
              ...restItem
            })
          }
        })

        const { idx } = filterTreeData[0].content[0]
        this.treeData = filterTreeData
        this.defalutExpandKey = [idx]
        this.$nextTick(() => this.$refs.tree.setCurrentKey(idx))
      }
    },

    handleIncrementFieldChange(value) {
      this.secondTaskForm.syncCondition.inc = !!value ?? false
    },

    // 删除表格的某一行
    handleDateleRow(options) {
      this.$confirm('请确认是否删除该表字段?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.secondTaskForm.fieldInfos.splice(options.index, 1)
        })
        .catch(() => {})
    },

    // sql语句更改重置数据
    handleSqlChange() {
      if (this.secondTaskForm.createMode) {
        this.secondTaskForm.targetSource = ''
        this.secondTaskForm.wideTableName = ''
        this.secondTaskForm.fieldInfos = []
        this.handleClear('stockNameOptions')
      }
    },

    // 可视化-表拖拽
    handleDragClick(evt, data, node) {
      const {
        id: tableId,
        parent: { label: database }
      } = node
      const { host = '' } = node.parent.data
      const ip = host.split(':')[0] || ''
      const { sourceType } = this.secondTaskForm
      evt.dataTransfer.setData(
        'application/json',
        JSON.stringify({ tableId, database, ip, sourceType, ...data })
      )
    },

    // 可视化-释放被动的表
    handleTagWrapDrop(evt) {
      evt.preventDefault()
      const domData = evt.dataTransfer.getData('application/json')
      const dataSource = JSON.parse(domData)
      const addTableObj = [
        { type: 'tag', isChecked: false, isShowDot: false, ...dataSource },
        { type: 'line', iconName: 'left-link', isShow: false }
      ]

      // 最多只能关联四张表
      const tagArr = this.selectedTable.filter(({ type }) => type === 'tag')
      if (tagArr.length > 3) {
        this.$message.error('目前最多只支持4张表进行关联，请重新操作！')
        return
      }

      // 不能重复拖动同一张表
      const isExistIdx = this.selectedTable.findIndex(
        ({ tableId }) => dataSource.tableId === tableId
      )
      if (isExistIdx > -1) {
        this.$message.error('拖动的表已存在， 请重新选择一张表！')
        return
      }

      // 数据源类型不同的表不能拖
      const isDiffTypeIdx = this.selectedTable.findIndex(
        ({ sourceType }) => dataSource.sourceType !== sourceType
      )
      if (isDiffTypeIdx < -1) {
        this.$message.error('数据源类型不同的表不能拖动，请重新选择一张表！')
        return
      }

      // IP地址不相同的表不能拖
      const isSameIpIdx = this.selectedTable.findIndex(
        ({ ip }) => dataSource.ip === ip
      )
      if (isSameIpIdx < -1) {
        this.$message.error('IP地址不同的表不能拖动， 请重新选择一张表！')
        return
      }

      // 不同类型的数据库不能拖该库下的表
      const filterDiffType = () => {
        const { dialect } = this.selectedTable[0]
        if (dataSource.dialect !== dialect) {
          this.$message.error('数据库类型不同表不能拖动， 请重新选择一张表！')
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
      this.$ss.set('selectedTable', this.selectedTable)
    },

    // 点击图标设置关联字段回调
    handleVisualConfirm(options) {
      // 保存或更新关联关系
      const { idx, associatedType } = options
      const { length } = this.secondTaskForm.view
      const curIndex = (idx - 1) / 2
      if (!length) {
        this.secondTaskForm.view = Array(curIndex + 1).fill(
          deepClone(viewDefaultData)
        )
      }
      this.secondTaskForm.view.splice(curIndex, 1, options)

      // 显示已设置关联关系的表的状态
      this.selectedTable[idx - 1].isChecked = true
      this.selectedTable[idx + 1].isChecked = true
      this.selectedTable[idx].iconName = iconMapping[associatedType].iconActive
    },

    handlePreventDefault(evt) {
      evt.preventDefault()
    },

    // 可视化-删除已选择的的表
    handleDeleteTagClick(idx, name) {
      !this.secondTaskForm.fieldInfos.length
        ? this.handleDeleteTable(idx)
        : this.handleDeleteTableConfirm(idx, name)
    },

    handleDeleteTableConfirm(idx, name) {
      this.$confirm(
        `当前表中字段已被宽表识别，删除后宽表中的信息将会丢失，确认要删除${name}`,
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
        .then(() => this.handleDeleteTable(idx))
        .catch(() => {})
    },

    // 删除已拖动选择的表
    handleDeleteTable(idx) {
      this.secondTaskForm.fieldInfos = []
      this.secondTaskForm.syncCondition.incrementalField = ''
      this.secondTaskForm.syncCondition.partition = ''
      this.increFieldsOptions = []
      this.zoningOptions = []

      // 因为最多只有四张表所以通过表的index来删除selectedTable里面相关连的线
      switch (idx) {
        case 0:
          this.selectedTable.splice(0, 1)
          this.selectedTable.splice(0, 1)
          this.secondTaskForm.view.splice(0, 1)
          break
        case 2:
          this.selectedTable.splice(2, 1)
          this.selectedTable.splice(2, 1)
          this.secondTaskForm.view.splice(1, 1)
          this.secondTaskForm.view.splice(0, 1)
          this.selectedTable[0].isChecked = false
          break
        case 4:
          this.selectedTable.splice(4, 1)
          this.selectedTable.splice(4, 1)
          this.secondTaskForm.view.splice(1, 1)
          this.secondTaskForm.view.length === 2 &&
            this.secondTaskForm.view.splice(1, 1)
          break
        case 6:
          this.selectedTable.splice(6, 1)
          this.selectedTable.splice(6, 1)
          this.secondTaskForm.view.splice(2, 1)
          break

        default:
          break
      }
    },

    // 可视化-点击关联图标打开关联弹窗
    handleLinkIconClick(options) {
      const { idx } = options
      // 目前只能新增四张表， 通过ICON的index找前后关联的两张表index
      const { dialect, datasourceId, name, database } = this.selectedTable[
        idx - 1
      ]
      const {
        datasourceId: sid,
        name: rName,
        database: rDatabase
      } = this.selectedTable[idx + 1]

      const leftTable = { datasourceId, name, database }
      const rightTable = { datasourceId: sid, name: rName, database: rDatabase }
      const { view } = this.secondTaskForm
      const vidx = (idx - 1) / 2
      const { associatedType, conditions } =
        view[vidx] || deepClone(viewDefaultData)

      this.$refs.linkDialog.open({
        idx,
        dialect,
        leftTable,
        rightTable,
        associatedType,
        conditions,
        opType: this.opType,
        title: `${this.opType === 'add' ? '新增' : '查看'}关联关系`
      })
    },

    // 中文名称
    handleChangeChineseName(row) {
      if (row.fieldChineseName) {
        const valid = validStrZh(name)
        this.isCanSaveSetting = valid
        valid && this.$message.error('该字段为中文名称输入，请检查后重新输入！')
      }
    },

    // 上一步
    handlePreviousClick() {
      const { taskName: oldName } = this.secondTaskForm
      this.$emit('change', 1, null, { oldName })
    },

    // 保存设置
    handleSaveSetting(step = 0) {
      const params = this.handleTaskFormParams()
      this.saveSettingLoading = true
      API.dataSyncAdd(params)
        .then(({ success, data }) => {
          if (success && data) {
            this.secondTaskForm.taskId = data.taskId
            this.$emit('change', step, cloneDeep(this.secondTaskForm))
            this.$notify.success({
              title: '操作结果',
              duration: 1500,
              message: '保存成功'
            })
          }
        })
        .finally(() => {
          this.saveSettingLoading = false
        })
    },

    // 下一步
    handleStepClick() {
      if (this.handleVerifyTip()) return
      this.handleSaveSetting(3)
    },

    // 验证宽表信息以及宽表名称是否已填
    handleVerifyTip() {
      const {
        targetSource,
        wideTableName,
        // createMode,
        sourceTables,
        view
      } = this.secondTaskForm

      if (!targetSource) {
        this.$message.error('请先选择宽表信息！')
        return true
      } else if (!wideTableName) {
        this.$message.error('请先填写宽表名称！')
        return true
        // } else if (!['edit', 'previousStep'].includes(this.opType)) {
        //   this.$message.error(
        //     `请先进行${createMode ? '执行SQL' : '识别宽表'}操作！`
        //   )
        //   return true
      } else if (sourceTables.length > 1 && !view.length) {
        this.$message.error('表关联关系不能为空，请关联后再进行操作！')
        return true
      } else {
        return false
      }
    },

    // 验证宽表信息
    handleVerifyWidthTableName() {
      const { wideTableName } = this.secondTaskForm
      if (!wideTableName) return
      const valid = validStrZh(wideTableName)
      const validSp = validStrSpecial(wideTableName.replaceAll('_', ''))
      if (!valid) {
        this.$message.error('宽表名称不能输入中文！')
        this.secondTaskForm.wideTableName = ''
      } else if (validSp) {
        this.$message.error('宽表名称只能输入英文字母、下划线和数字！')
        this.secondTaskForm.wideTableName = ''
      }
    },

    // 表单参数缓存以及过滤处理
    handleTaskFormParams() {
      const { workspaceId } = this
      // 可视化方式参数处理
      !this.secondTaskForm.createMode && this.handleVisualizationParams()
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
      return secondForm
    },

    // 处理可视化表单参数
    handleVisualizationParams() {
      const { dialect: dia, view } = this.secondTaskForm
      const { dialect, datasourceId: sourceId } = this.selectedTable[0] || {}

      this.secondTaskForm.dialect = dialect ?? dia
      this.secondTaskForm.datasourceId = sourceId ?? this.datasourceId
      this.secondTaskForm.view = deepClone(view).map(({ idx, ...item }) => item)

      this.secondTaskForm.sourceTables = deepClone(this.selectedTable)
        .filter(({ type }) => type === 'tag')
        .map(({ datasourceId, database, name }) => {
          return {
            database,
            datasourceId: datasourceId ?? this.datasourceId,
            tableName: name
          }
        })
    },

    // 清空下拉框Options
    handleClear(name) {
      this[name] = []
    },

    handleDictClear(row) {
      // eslint-disable-next-line no-param-reassign
      row.dictionaryOptions = []
    },

    // 字段类型级联值发生改变
    handleCascaderChange(row, value) {
      const [idx] = value
      const { fieldType } = this.oldFieldInfos[row.sort - 1]
      if (idx !== fieldType[0]) {
        this.$message.error('字段类型与源表值类型不匹配，请重新选择！')
        // eslint-disable-next-line no-param-reassign
        row.fieldType = []
      }
    },

    // 数据库同名选择弹窗回调
    handleSelectBatabase() {
      const [{ datasourceId, dialect }] = deepClone(
        this.sameNameDataBase
      ).filter(item => this.checkList.includes(item.datasourceId))
      this.datasourceId = datasourceId
      this.secondTaskForm.dialect = dialect
      this.handleIdentifyTable(false)
    },

    // 识别宽表
    async handleIdentifyTable(isChooseIp = true) {
      if (this.verifyLinkTip()) return
      this.handleVisualizationParams()

      // SQL模式下-数据库同名的情况选择相应的库
      const { createMode } = this.secondTaskForm
      if (createMode && isChooseIp) {
        const isShow = await this.getSelectHostModel()
        if (!isShow) return
      }

      const datasourceId =
        this.selectedTable[0]?.datasourceId ?? this.datasourceId
      const {
        sourceType,
        sql,
        sourceTables,
        dialect,
        view
      } = this.secondTaskForm

      // sql模式参数
      const sqlParams = {
        sql,
        dialect,
        createMode,
        sourceType,
        datasourceId
      }

      // 可视化模式参数
      const visualParams = {
        ...sqlParams,
        view: sourceTables.length === 1 ? [] : view,
        sourceTables: deepClone(sourceTables).map(
          ({ database, tableName }) => ({
            database,
            datasourceId,
            tableName
          })
        )
      }

      this.widthTableLoading = true
      this.tableLoading = true
      this.$refs.baseDialog.close()
      API.dataSyncGenerateTable(createMode ? sqlParams : visualParams)
        .then(({ success, data }) => {
          if (success && data) {
            const { sql: sq, partitions, fields, incrementalFields } = data
            this.secondTaskForm.sql = sq
            this.zoningOptions = partitions || []
            this.increFieldsOptions = incrementalFields || []
            this.secondTaskForm.fieldInfos = this.hadleFieldInfos(fields || [])
            this.oldFieldInfos = this.hadleFieldInfos(fields || [])
          }
        })
        .finally(() => {
          this.widthTableLoading = false
          this.tableLoading = false
        })
    },

    // 已经拖动的表是否完全设置了关联
    verifyLinkTip() {
      const unlinkTable = this.selectedTable.filter(
        ({ type, isChecked }) => type === 'tag' && !isChecked
      )
      if (unlinkTable.length && this.selectedTable.length > 2) {
        this.$message.error(
          '识别失败，当前存在未进行关联的表，请关联后再进行识别！'
        )
        return true
      }
      if (!this.verifyTableDisabled) {
        this.$message.error(
          `${
            this.secondTaskForm.createMode
              ? '请先填写SQL语句表达式！'
              : '表关联区域至少存在一张表！'
          }`
        )
        return true
      }
      return false
    },

    // 表格信息过滤
    hadleFieldInfos(fields = []) {
      return deepClone(fields)?.map(item => {
        return {
          fieldTypeOptions: this.fieldTypeOptions,
          dictLoading: false,
          dictionaryOptions: [],
          ...item
        }
      })
    },

    // 切换数据源类型
    changeSourceType() {
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

    // 编辑情况下进行关联表的渲染
    handleRenderLinkTable(graphicData) {
      // 根据接口的view字段数据整合成selectedTable字段相应的数据结构
      const { length } = graphicData || []
      const {
        associatedType,
        datasourceId,
        leftSource,
        leftSourceDatabase,
        rightSource,
        rightSourceDatabase
      } = graphicData[length - 1]

      this.selectedTable[0] = {
        type: 'tag',
        isChecked: true,
        isShowDot: false,
        datasourceId, // 待后端返回
        name: leftSource,
        database: leftSourceDatabase
      }

      if (rightSource && rightSourceDatabase) {
        this.selectedTable[1] = {
          type: 'line',
          iconName: iconMapping[associatedType]?.iconActive,
          isShow: true
        }
        this.selectedTable[2] = {
          type: 'tag',
          isChecked: true,
          isShowDot: false,
          datasourceId: '',
          name: rightSource,
          database: rightSourceDatabase
        }

        switch (length) {
          case 1:
            this.selectedTable[3] = {
              type: 'line',
              iconName: iconMapping[associatedType]?.iconActive,
              isShow: true
            }
            break

          case 2:
            // 第一张表
            this.selectedTable[0] = {
              type: 'tag',
              isChecked: true,
              isShowDot: false,
              datasourceId: '',
              name: graphicData[0].leftSource,
              database: graphicData[0].leftSourceDatabase
            }

            this.selectedTable[1] = {
              type: 'line',
              iconName: iconMapping[graphicData[0].associatedType]?.iconActive,
              isShow: true
            }

            // 第二张表
            this.selectedTable[2] = {
              type: 'tag',
              isChecked: true,
              isShowDot: false,
              datasourceId: '',
              name: graphicData[0].rightSource,
              database: graphicData[0].rightSourceDatabase
            }

            this.selectedTable[3] = {
              type: 'line',
              iconName: iconMapping[graphicData[1].associatedType]?.iconActive,
              isShow: true
            }

            // 第三张表
            this.selectedTable[4] = {
              type: 'tag',
              isChecked: true,
              isShowDot: false,
              datasourceId: '',
              name: graphicData[1].rightSource,
              database: graphicData[1].rightSourceDatabase
            }

            this.selectedTable[5] = {
              type: 'line',
              iconName: iconMapping[graphicData[1].associatedType]?.iconActive,
              isShow: true
            }
            break

          case 3:
            // 第一张表
            this.selectedTable[0] = {
              type: 'tag',
              isChecked: true,
              isShowDot: false,
              datasourceId: '',
              name: graphicData[0].leftSource,
              database: graphicData[0].leftSourceDatabase
            }

            this.selectedTable[1] = {
              type: 'line',
              iconName: iconMapping[graphicData[0].associatedType]?.iconActive,
              isShow: true
            }

            // 第二张表
            this.selectedTable[2] = {
              type: 'tag',
              isChecked: true,
              isShowDot: false,
              datasourceId: '',
              name: graphicData[1].leftSource,
              database: graphicData[1].leftSourceDatabase
            }

            this.selectedTable[3] = {
              type: 'line',
              iconName: iconMapping[graphicData[1].associatedType]?.iconActive,
              isShow: true
            }

            // 第三张表
            this.selectedTable[4] = {
              type: 'tag',
              isChecked: true,
              isShowDot: false,
              datasourceId: '',
              name: graphicData[2].leftSource,
              database: graphicData[2].leftSourceDatabase
            }

            this.selectedTable[5] = {
              type: 'line',
              iconName: iconMapping[graphicData[2].associatedType]?.iconActive,
              isShow: true
            }

            // 第四张表
            this.selectedTable[6] = {
              type: 'tag',
              isChecked: true,
              isShowDot: false,
              datasourceId: '',
              name: graphicData[2].rightSource,
              database: graphicData[2].rightSourceDatabase
            }

            this.selectedTable[7] = {
              type: 'line',
              iconName: iconMapping[graphicData[2].associatedType]?.iconActive,
              isShow: true
            }
            break

          default:
            break
        }
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
            this.originTreeData = this.treeData
          }
        })
        .finally(() => {
          this.treeLoading = false
        })
    },

    // 数据源表模糊搜索
    getFluzzyTableName(tableName) {
      const { workspaceId } = this
      const { sourceType } = this.secondTaskForm
      this.searchLoading = true
      API.dataSyncFluzzySearch({ tableName, sourceType, workspaceId })
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
      this.searchStockLoading = true
      API.dataSyncTargetSource({ name, workspaceId: this.workspaceId })
        .then(({ success, data }) => {
          if (success && data) {
            this.stockNameOptions = data
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
      API.dataSyncDictionary({ name, workspaceId: this.workspaceId })
        .then(({ success, data }) => {
          if (success && data) {
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
      if (this.opType === 'edit') {
        this.$message.warning({
          duration: 4000,
          message: '编辑模式下， 可视化区域的表以及关联关系不可编辑！'
        })
      }
      this.detailLoading = true
      API.dataSyncBuildDetial({ taskId: this.secondTaskForm.taskId })
        .then(({ success, data }) => {
          if (success && data) {
            this.datasourceId = data.datasourceId
            for (const [key, value] of Object.entries(data)) {
              switch (key) {
                case 'fieldInfos':
                  this.secondTaskForm[key] = this.hadleFieldInfos(value)
                  this.oldFieldInfos = this.hadleFieldInfos(value)
                  break
                case 'view':
                  this.secondTaskForm[key] = value
                  !this.secondTaskForm.createMode &&
                    this.handleRenderLinkTable(value)
                  break
                default:
                  this.secondTaskForm[key] = value
                  break
              }
            }
          }
        })
        .catch(() => this.initParams())
        .finally(() => {
          this.detailLoading = false
        })
    },

    // 是否弹窗选择不同IP的表去识别宽表
    getSelectHostModel() {
      const { sql, sourceType } = this.secondTaskForm
      this.widthTableLoading = true
      return API.dataSyncVerifyHost({
        sql,
        sourceType,
        workspaceId: this.workspaceId
      })
        .then(({ success, data }) => {
          if (success && data) {
            const { showWindow, sameNameDataBase } = data
            const [{ datasourceId, dialect }] = sameNameDataBase

            this.widthTableLoading = false
            this.datasourceId = datasourceId
            this.secondTaskForm.dialect = dialect
            this.sameNameDataBase = sameNameDataBase ?? []
            showWindow && this.$refs.baseDialog.open()
            return !showWindow
          }
        })
        .catch(() => {
          this.widthTableLoading = false
          return false
        })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/public/data-manage';

.add-task {
  margin-top: 30px;
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
        max-height: calc(100vh - 424px);
        overflow-y: auto;

        .custom-tree-node {
          @include flex;
          cursor: pointer;

          .jsvg-icon {
            width: 14px;
            height: 14px;
            margin: 0 5px;
          }

          .table-label {
            max-width: 184px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
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

      .sql-tip {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        font-size: 16px;
        color: #dcdfe6;
      }

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

        ::v-deep {
          .el-input__inner {
            border: none;
            background-color: transparent;
          }
        }
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

        .append-btn-disabled {
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
