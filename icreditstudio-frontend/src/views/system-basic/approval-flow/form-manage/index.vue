<template>
  <div class="h100">
    <crud-basic
      ref="crud"
      title="流程列表"
      :form-items-search="mixinSearchFormItems"
      :form-func-search="mixinSearchFormFunc"
      :form-config-search="mixinSearchFormConfig"
      :form-items-dialog="mixinDialogFormItems"
      :form-func-dialog="mixinDialogFormFunc"
      :form-config-dialog="mixinDialogFormConfig"
      :dialog-type="mixinDialogType"
      :dialog-visible.sync="mixinDialog"
      :custom-btn-config="tableConfiguration.customBtnConfig"
      :handleAdd="mixinHandleAdd"
      :handleSearch="mixinHandleSearch"
      :handleReset="mixinHandleReset"
      :handleUpdate="mixinHandleCreateOrUpdate"
      :handleCancel="mixinHandleCancel"
      @addGroup="handleGroupOperate"
      @deleteGroup="handleFlowGroupDelete"
    >
      <template #content>
        <j-table
          v-loading="mixinTableLoading"
          :table-configuration="tableConfiguration"
          :table-pagination="mixinTablePagination"
          :table-data="mixinTableData"
          @handleSizeChange="mixinHandleSizeChange"
          @handleCurrentChange="mixinHandleCurrentChange"
        >
          <!-- 流程 -->
          <template #flowColumn="{ row }">
            <el-button
              v-if="row.pid"
              type="text"
              @click.stop="$router.push('/approvalFlow/flow/1')"
            >
              流程配置
            </el-button>
            <el-button
              v-else
              type="text"
              @click.stop="$router.push('/approvalFlow/flow/1')"
            >
              新增流程
            </el-button>
          </template>

          <!-- 操作按钮自定义渲染 -->
          <template #operationColumn="{ row }">
            <el-row v-if="row.pid" type="flex" justify="space-around">
              <el-col :span="8">
                <el-button type="text" @click="handleExpandConfigure">
                  扩展配置
                </el-button>
              </el-col>
              <el-col :span="8">
                <el-button type="text" @click="handleFlowInitiate">
                  流程发起
                </el-button>
              </el-col>
              <el-col :span="8">
                <el-dropdown trigger="click" placement="bottom-start">
                  <el-button type="text">
                    更多<i class="el-icon-arrow-down el-icon--right"></i>
                  </el-button>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>
                      <el-button type="text" @click="handleFlowsDelete">
                        删除
                      </el-button>
                    </el-dropdown-item>
                    <el-dropdown-item>
                      <el-button type="text" @click="handleFlowPreview">
                        预览
                      </el-button>
                    </el-dropdown-item>
                    <el-dropdown-item>
                      <el-button type="text" @click="handleFlowHistoryVersion">
                        历史版本
                      </el-button>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </el-col>
            </el-row>

            <el-row v-else>
              <el-button type="text" @click="handleGroupOperate('edit')">
                修改
              </el-button>
              <el-button type="text" @click="handleFlowGroupDelete">
                删除
              </el-button>
            </el-row>
          </template>
        </j-table>
      </template>
    </crud-basic>

    <!-- 预览 -->
    <el-dialog
      title="预览"
      :visible.sync="isFlowPreview"
      :close-on-click-modal="false"
    >
      <img src="@/assets/bg.jpg" alt="" width="100%" />
    </el-dialog>

    <!-- 扩展配置 -->
    <el-dialog
      width="60vw"
      title="扩展配置"
      v-if="isEditNameVisible"
      :visible.sync="isEditNameVisible"
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        label-suffix=":"
        :model="flowGroupNameForm"
        label-width="100px"
      >
        <el-row type="flex" justify="space-between">
          <el-col :span="11">
            <el-form-item
              label="流程定义ID"
              prop="id"
              :rules="[
                { required: true, message: '必填项不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="flowGroupNameForm.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="流程名称"
              prop="name"
              :rules="{
                required: true,
                message: '必填项不能为空',
                trigger: 'blur'
              }"
            >
              <el-input v-model="flowGroupNameForm.name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row type="flex" justify="space-between">
          <el-col :span="24">
            <el-form-item label="流程描述">
              <el-input
                clearable
                type="textarea"
                show-word-limit
                :maxlength="100"
                v-model="flowGroupNameForm.name"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row type="flex" justify="space-between">
          <el-col :span="11">
            <el-form-item label="扩展字段1">
              <el-input v-model="flowGroupNameForm.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="扩展字段2">
              <el-input v-model="flowGroupNameForm.name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row type="flex" justify="space-between">
          <el-col :span="11">
            <el-form-item label="扩展字段3">
              <el-input v-model="flowGroupNameForm.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="扩展字段4">
              <el-input v-model="flowGroupNameForm.name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row type="flex" justify="space-between">
          <el-col :span="11">
            <el-form-item label="扩展字段5">
              <el-input v-model="flowGroupNameForm.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="扩展字段6">
              <el-input v-model="flowGroupNameForm.name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item style="text-align:right">
          <el-button type="primary" @click="handleGroupNameSave">
            保存
          </el-button>
          <el-button @click="isEditNameVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 分组名称-新增或修改 -->
    <el-dialog
      :title="flowGroup.title"
      :visible.sync="flowGroup.visible"
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="flowGroup.form" label-width="80px">
        <el-form-item label="分组名称">
          <el-input
            clearable
            show-word-limit
            :maxlength="50"
            v-model="flowGroup.form.name"
            placeholder="请输入分组名称"
          ></el-input>
        </el-form-item>
        <el-form-item style="text-align:right">
          <el-button type="primary" @click="handleGroupNameSave">
            保存
          </el-button>
          <el-button @click="handleCloseDialog">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 历史版本 -->
    <el-dialog
      width="80vw"
      title="流程版本列表"
      :visible.sync="tableDialogVisible"
      :close-on-click-modal="false"
    >
      <div>
        <j-table
          ref="table"
          v-loading="tabTableLoading"
          :table-data="tabTableData"
          :table-configuration="tableConfig"
          :table-pagination="tabTablePagination"
          @handleSizeChange="handleSizeChange"
          @handleCurrentChange="handleCurrentChange"
        />
      </div>

      <div style="text-align: right; margin-top: 20px">
        <el-button @click="handleCloseDialog">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import crud from '@/mixins/crud'
import { deepClone } from '@/utils/util'
import { xmlConfigDefault } from '@/views/system-basic/approval-flow/form-manage/config/xml.default'

import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-approval-form'
import tableConfig from '@/views/system-basic/configuration/table/manage/flow-history-version'
import formOption from '@/views/system-basic/configuration/form/manage/manage-approval-form'

import {
  queryFormGroupModelInfo,
  queryApprovalOrgTree,
  queryApprovalRoles,
  queryApprovalUsers,
  queryProcdef,
  deleteProcdef,
  editFormState,
  copyForm
} from '@/api/system-basic/approval-flow'

import FormItem from '@/views/system-basic/approval-flow/form-manage/config/form-item'

const newFormItem = new FormItem()

export default {
  name: 'ManageFrom',
  mixins: [crud],
  data() {
    return {
      formOption,
      mixinSearchFormConfig: {
        models: {
          name: '',
          modelType: 2,
          type: '0'
        }
      },
      mixinDialogFormConfig: {
        models: {},
        rule: {}
      },
      tableConfiguration: tableConfiguration(this),
      fetchConfig: {
        retrieve: {
          url: '/system/process/form/group/query',
          method: 'post'
        },
        create: {
          url: '/system/process/form/group/add',
          method: 'post'
        },
        update: {
          url: '/system/re/res/user/edit',
          method: 'post'
        },
        delete: {
          url: '/system/form/info/delete',
          method: 'post'
        },
        multipleDelete: {
          url: '/system/code/code/delete',
          method: 'post'
        }
      },

      formPreview: false,
      widgetForm: {
        list: [],
        config: {
          labelWidth: 100,
          labelPosition: 'right',
          size: 'small',
          isBackEdge: false
        }
      },
      widgetModels: {},
      remoteFuncs: {},
      name: '',

      isFlowPreview: false,
      isEditNameVisible: false,
      flowGroupNameForm: {},
      flowGroup: {
        visible: false,
        title: '新增分组名称',
        form: {}
      },

      tableConfig: tableConfig(this),
      tabTableData: [
        { name: 'v1.0.0', name1: 'wsdd' },
        { name: 'v1.0.0', name1: 'wsdd' },
        { name: 'v1.0.0', name1: 'wsdd' },
        { name: 'v1.0.0', name1: 'wsdd' },
        { name: 'v1.0.0', name1: 'wsdd' },
        { name: 'v1.0.0', name1: 'wsdd' },
        { name: 'v1.0.0', name1: 'wsdd' },
        { name: 'v1.0.0', name1: 'wsdd' },
        { name: 'v1.0.0', name1: 'wsdd' },
        { name: 'v1.0.0', name1: 'wsdd' },
        { name: 'v1.0.0', name1: 'wsdd' },
        { name: 'v1.0.0', name1: 'wsdd' },
        { name: 'v1.0.0', name1: 'wsdd' },
        { name: 'v1.0.0', name1: 'wsdd' }
      ],
      tabTableLoading: false,
      tableDialogVisible: false,
      tabTablePagination: {
        currentPage: 1,
        pageSize: 10,
        total: 0,
        pagerCount: 5,
        handleSizeChange: this.handleSizeChange,
        handleCurrentChange: this.handleCurrentChange
      }
    }
  },

  created() {
    this.mixinSearchFormItems = deepClone(this.formOption).filter(
      e => e.isSearch
    )
    this.mixinRetrieveTableData()
  },

  methods: {
    ...mapActions('approval-form', [
      'setFormConfig',
      'setFormInfo',
      'setModelData',
      'setApprovalInfo',
      'setXmlConfig'
    ]),
    interceptorsResponseTableData(data) {
      const _data = data
      _data.forEach(item => {
        Object.assign(item, {
          blackList: [
            'flowAdd',
            'flowPreview',
            'initiation',
            'copy',
            'expand',
            'more'
          ]
        })
        item.formInfoResults.forEach(e => {
          const _btnBlackList = ['create']
          Object.assign(e, { pid: item.id, blackList: _btnBlackList })
        })
      })
      return _data
    },
    interceptorsRequestCreate(data) {
      return Object.assign({}, data, { show: '0', type: '0' })
    },
    handleFormPre(key) {
      return queryFormGroupModelInfo({ key })
        .then(res => {
          return res.success ? res.data : {}
        })
        .catch(err => {
          console.log(err)
        })
    },
    async handleFlowPrev(data) {
      this.getModelData() // 流程配置树选择
      let _data = await this.handleFormPre(data.key)
      _data = Object.assign(data, _data)
      const formJson = JSON.parse(_data.modelEditorJson)
      const formInfo = []
      const formConfig = {
        authorityItem: [],
        conditionItem: []
      }
      const approvalInfo = {
        tenantId: _data.pid, // 父级 分组id
        formKey: _data.key,
        procdefKey: _data.procdefKey,
        form: '/approvalFlow'
      }
      formJson.list.forEach(item => {
        if (item.options.required) {
          formConfig.authorityItem.push({ name: item.name, authority: '' })
          formConfig.conditionItem.push(newFormItem.getConditionItem(item))
        }
        if (item.type === 'grid') {
          formInfo.push(...item.columns.map(e => e.list).flat(1))
        } else if (item.type !== 'childForm') {
          formInfo.push(item)
        }
      })
      await this.setFormConfig(formConfig)
      await this.setFormInfo({ ...formJson, list: formInfo })
      await this.setApprovalInfo(approvalInfo)
      return _data
    },
    async handleFlowGo(data) {
      const _data = await this.handleFlowPrev(data)
      // this.getModelData()
      // const _data = await this.handleFormPre()
      // Object.assign(_data, data)
      // const formJson = JSON.parse(_data.modelEditorJson)
      // const formInfo = []
      // const formConfig = {
      //   authorityItem: [],
      //   conditionItem: []
      // }
      // const approvalInfo = {
      //   tenantId: _data.pid, // 父级 分组id
      //   formKey: _data.key,
      //   procdefKey: _data.procdefKey,
      //   form: '/approvalFlow'
      // }
      // formJson.list.forEach(item => {
      //   if (item.options.required) {
      //     formConfig.authorityItem.push({ name: item.name, authority: '' })
      //     formConfig.conditionItem.push(newFormItem.getConditionItem(item))
      //   }
      //   if (item.type === 'grid') {
      //     formInfo.push(...item.columns.map(e => e.list).flat(1))
      //   } else if (item.type !== 'childForm') {
      //     formInfo.push(item)
      //   }
      // })
      // await this.setFormConfig(formConfig)
      // await this.setFormInfo({ ...formJson, list: formInfo })
      // await this.setApprovalInfo(approvalInfo)
      // queryProcdef({ procdefKey: _data.procdefKey })
      queryProcdef({ procdefKey: _data.procdefKey }).catch(err => {
        if (typeof err === 'string') {
          this.setXmlConfig(err)
          this.$router.push(`/approvalFlow/flow/${_data.key}`)
        }
      })
    },
    async handleFlowAdd(data) {
      const _data = await this.handleFlowPrev(data)
      await this.setXmlConfig(xmlConfigDefault)
      await this.$router.push(`/approvalFlow/flow/${_data.key}`)
    },
    handleFlowDelete({ procdefKey, key }) {
      this.$confirm('是否移除该流程', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      })
        .then(() => {
          deleteProcdef({ procdefKeys: [procdefKey], formKeys: [key] }).then(
            () => {
              this.mixinHandleReset()
              this.$notify.success('移除成功')
            }
          )
        })
        .catch(err => {
          console.log(err)
        })
    },
    getModelData() {
      // queryApprovalOrgTree()
      //   .then(res => {
      //     this.setModelData(res.data)
      //   })
      Promise.all([
        queryApprovalUsers(),
        queryApprovalRoles(),
        queryApprovalOrgTree()
      ])
        .then(res => {
          const _modelData = [
            {
              label: '组织',
              type: 'org',
              data: res[2].data
            },
            {
              label: '角色',
              type: 'role',
              data: res[1].data
            },
            {
              label: '人员',
              type: 'user',
              data: res[0].data
            }
          ]
          this.setModelData(_modelData)
        })
        .catch(err => {
          console.log(err)
        })
    },
    /**
     * 表单操作
     * @param row 行数据
     * @param type {'add' | 'edit'}
     * @return {Promise<void>}
     */
    async handleFormOperate(row, type) {
      const _isAdd = type === 'add'
      const _data = _isAdd ? {} : await this.handleFormPre(row.key)
      const data = Object.assign({}, row, _data)
      // const params = {
      //   pageNum: 1,
      //   pageSize: 10,
      //   conditions: ''
      // }
      try {
        // TODO
        // const externalItems = await queryInterfaceList(params)
        //   .then(res => (res.data.datas || [])
        //     .map(e => Object.assign(e, {
        //       label: e.interfaceName,
        //       options: JSON.parse(e.returnParam)
        //     })))
        const externalItems = []
        const formResults = this.mixinTableData.map(r => {
          return {
            id: r.id,
            name: r.name,
            formInfoResults: r.formInfoResults
              .filter(e => e.state === '0' && e.key !== data.key)
              .map(({ id, name, procdefKey, key }) => ({
                id,
                name,
                procdefKey,
                key,
                requestUrl: 'form/group/get/modelInfo'
              }))
          }
        })
        // TODO
        // const source = await queryDataSourceList(params).then(res => res.data.datas || [])
        // const rule = await queryRuleList(params).then(res => res.data.datas || [])
        const source = []
        const rule = []
        let formInfo = {
          form: '/approvalFlow/form',
          formResults,
          externalItems,
          source,
          rule,
          autoSave: type
        }
        const _concatInfoByType = _isAdd ? { tenantId: row.id } : { ...data }
        formInfo = Object.assign(_concatInfoByType, formInfo)
        this.setFormInfo(formInfo).then(() => {
          this.$router.push('/approvalFlow/formGenerator')
        })
      } catch (e) {
        console.log(e)
      }
    },
    handleFormPreview({ row }) {
      queryFormGroupModelInfo({
        key: row.key
      }).then(res => {
        const _result = res.data
        this.formPreview = true
        this.widgetForm = JSON.parse(_result.modelEditorJson)
        this.name = _result.name
      })
    },
    handleFormCopy({ row }) {
      copyForm({
        name: `${row.name}-复制`,
        key: row.key,
        modelType: 2
      }).then(() => {
        this.mixinHandleReset()
        this.$notify.success('复制成功')
      })
    },
    handleFromStatusChange({ row }) {
      editFormState({
        type: row.state,
        key: row.key,
        modelType: 2
      }).then(() => {
        this.mixinHandleReset()
        this.$notify.success(
          `${{ 0: '停用', 1: '启用', 2: '发布' }[row.state]}}成功`
        )
      })
    },
    handleDataChange(field, value, data) {
      console.log(field, value, data)
    },

    // 流程分组名称修改
    handleFlowGroupEdit() {
      this.isEditNameVisible = true
    },

    // 流程分组-删除
    handleFlowGroupDelete(row) {
      if (row) {
        return this.$message({
          type: 'warning',
          message: '该分组下有正在运行的流程，不能删除！',
          center: true
        })
      }

      this.$confirm(
        '倘若删除该分组则该分组下的所有流程都将删除，是否确认删除？',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
        .then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },

    // 流程-删除
    handleFlowsDelete(row) {
      if (row) {
        return this.$message({
          type: 'warning',
          message: '该流程为正在运行的流程，不能删除！',
          center: true
        })
      }

      this.$confirm(
        '删除该流程后所有历史版本都将删除，是否确认删除？',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
        .then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },

    // 流程分组名称修改-保存
    handleGroupNameSave() {
      this.handleCloseDialog()
    },

    // 操作按钮 - 扩展配置
    handleExpandConfigure() {
      console.log('ssss')
      this.isEditNameVisible = true
    },

    // 操作按钮 - 流程发起
    handleFlowInitiate() {
      this.$confirm('是否确认发起流程？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$notify.success({ message: '流程发起成功!', duration: 2500 })
        })
        .catch(() => {
          this.$notify.info({ message: '流程发起已取消！', duration: 2500 })
        })
    },

    // 操作按钮 - 预览
    handleFlowPreview() {
      this.isFlowPreview = true
    },

    // 操作按钮 - 历史版本
    handleFlowHistoryVersion() {
      this.tableDialogVisible = true
    },

    // 流程分组新增或修
    handleGroupOperate(opType = 'add', row) {
      console.log(row)
      const opTypeMapping = {
        add: { title: '新增分组名称' },
        edit: { title: '编辑分组名称' }
      }

      this.flowGroup.visible = true
      this.flowGroup.title = opTypeMapping[opType].title
    },

    // 关闭弹窗
    handleCloseDialog() {
      this.isEditNameVisible = false
      this.flowGroup.visible = false
      this.tableDialogVisible = false
    },

    handleSizeChange(size) {
      this.tabTablePagination.pageSize = size
    },

    handleCurrentChange(pageIndex) {
      this.tabTablePagination.currentPage = pageIndex
    }
  }
}
</script>
