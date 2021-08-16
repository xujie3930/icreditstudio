<template>
  <div class="approval-manage-scope">
    <div class="h100" v-if="isApproval">
      <layout-main-basic-container
        title="审批列表"
      >
        <template #header>
          <j-form
            ref="searchForm"
            :form-items="mixinSearchFormItems"
            :form-func="mixinSearchFormFunc"
            :form-config="mixinSearchFormConfig"
            @mixinSearch="mixinHandleSearch"
            @mixinReset="mixinHandleReset"
          ></j-form>
        </template>
        <template #content>
          <div class="search-tab">
            <el-tabs v-model="activeName" type="card" @tab-click="toggleTypes">
              <el-tab-pane
                v-for="tab in tabList"
                :key="tab.tab"
                :label="tab.name"
                :name="tab.tabName"
              >
              </el-tab-pane>
            </el-tabs>
          </div>
          <j-table
            v-loading="mixinTableLoading"
            :table-configuration="tableConfiguration"
            :table-pagination="mixinTablePagination"
            :table-data="mixinTableData"
            @handleSizeChange="mixinHandleSizeChange"
            @handleCurrentChange="mixinHandleCurrentChange"
          ></j-table>
        </template>
      </layout-main-basic-container>
    </div>
    <div v-else>
      <div
        v-loading.fullscreen.lock="loading"
        class="dialog-container"
        :class="{'cus-dialog-container':widgetForm.config.isBackEdge}"
      >
        <div class="form-container" :class="{'container-max':activeName !== 'draft'}">
          <j-form-preview
            v-if="!previewVisible"
            ref="generateForm"
            class="cus-dialog preview-padding"
            insite="true"
            :form-title="formtitle"
            :data="widgetForm"
            :value="widgetModels"
            :remote="remoteFuncs"
          />
          <div class="button">
            <el-button
              v-if="operationInfo.pass"
              size="small"
              type="primary"
              @click="handleDialo('pass')"
            >
              通过
            </el-button>
            <el-button
              v-if="operationInfo.againStart"
              size="small"
              type="primary"
              @click="handleStart"
            >
              重新提交
            </el-button>
            <el-button
              v-if="operationInfo.noPass"
              size="small"
              @click="handleDialo('noPass')"
            >
              不通过
            </el-button>
            <el-button
              v-if="operationInfo.startProcinst"
              size="small"
              type="primary"
              :loading="startLoading"
              @click="handleStart"
            >
              提交
            </el-button>
            <el-button
              v-if="operationInfo.save"
              :loading="saveLoading"
              size="small"
              @click="save"
            >
              保存
            </el-button>
            <el-button
              v-if="operationInfo.recall"
              size="small"
              type="primary"
              @click="recall"
            >
              撤回
            </el-button>
            <el-button
              v-if="operationInfo.returnBack"
              size="small"
              @click="handleDialo('returnBack')"
            >
              退回
            </el-button>
<!--            <el-button-->
<!--              v-if="operationInfo.manual"-->
<!--              size="small"-->
<!--              @click="manual"-->
<!--            >-->
<!--              抄送-->
<!--            </el-button>-->
            <el-button
              v-if="operationInfo.assign"
              size="small"
              @click="assign"
            >
              指派
            </el-button>
            <el-button
              v-if="operationInfo.unassign"
              size="small"
              @click="unassign"
            >
              撤销指派
            </el-button>
            <el-button
              size="small"
              @click="preview"
            >
              导出预览
            </el-button>
            <el-button
              size="small"
              @click="init"
            >
              返回
            </el-button>
          </div>
        </div>
        <div class="common-dialog">
          <el-dialog
            v-if="previewVisible"
            title="打印预览"
            :visible.sync="previewVisible"
            width="1000px"
            :close-on-click-modal="false"
          >
            <j-form-preview
              ref="printPreview"
              style="width:100%;"
              insite="true"
              class="cus-dialog preview-padding print-preview"
              :form-title="formtitle"
              :data="widgetForm"
              :value="detailsModels"
              :remote="remoteFuncs"
              @on-change="handleDetailsChange"
            />
            <span slot="footer" class="dialog-footer">
              <el-button size="small" type="primary" @click="toImage">确 定</el-button>
              <el-button size="small" @click="previewVisible = false">取 消</el-button>
            </span>
          </el-dialog>
        </div>
        <steps
          v-if="activeName !== 'draft'"
          ref="steps"
          style="margin-left:20px"
          @setAgainStart="setAgainStart"
          @searchDetails="searchDetails"
        />
      </div>
    </div>
    <el-dialog
      title="预览"
      :visible.sync="formPreview"
      :class="widgetForm.config.isBackEdge && 'cus-dialog-container'"
      width="1000px"
      :close-on-click-modal="false"
    >
      <j-form-preview
        ref="generateForm"
        class="cus-dialog"
        insite="true"
        :form-title="formtitle"
        :data="widgetForm"
        :value="widgetModels"
        :remote="remoteFuncs"
        @on-change="handleDataChange"
      />
    </el-dialog>
    <dialog-modal ref="dialogModal" :active-name="activeName" @carbon="setCarbon" @init="init"
                  @dialogAgainStart="dialogAgainStart"/>
    <projectCredit ref="projectCredit"/>
    <div class="common-dialog">
      <el-dialog
        v-if="dialogVisible"
        title="提示"
        :visible.sync="dialogVisible"
        width="30%"
        :close-on-click-modal="false"
      >
        <div :class="{'cus-dialog-container':dialoForm.config.isBackEdge}">
          <el-form
            v-if="type === 'returnBack'
              && formInfo.description
              && JSON.parse(formInfo.description).backInfo === 'choose'"
            ref="ruleForm"
            :model="ruleForm"
            :rules="rules"
            style="width:100%;margin: 0 auto;"
            label-width="100px"
          >
            <el-form-item label="退回节点:" prop="activityId">
              <el-select v-model="ruleForm.activityId" style="width:100%" placeholder="请选择">
                <el-option
                  v-for="item in optionsBack"
                  :key="item.value"
                  :label="item.label"
                  :value="item.nodeId"
                />
              </el-select>
            </el-form-item>
          </el-form>
          <j-form-preview
            v-if="hasJson"
            class="cus-dialog"
            style="width:100%"
            insite="true"
            :data="dialoForm"
            :value="dialoModels"
            :remote="remoteFuncs"
            @on-change="handleDialoChange"
          />
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" type="primary" @click="handleSaveInfo">确 定</el-button>
          <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        </span>
      </el-dialog>
      <el-dialog
        v-if="detailsVisible"
        title="提示"
        :visible.sync="detailsVisible"
        width="600px"
        :close-on-click-modal="false"
      >
        <div :class="{'cus-dialog-container':detailsForm.config.isBackEdge}">
          <j-form-preview
            insite="true"
            class="cus-dialog"
            :data="detailsForm"
            :value="detailsModels"
            :remote="remoteFuncs"
            @on-change="handleDetailsChange"
          />
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import crud from '@/mixins/crud'
import { deepClone, changState } from '@/utils/util'

import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-approval-list'
import formOption from '@/views/system-basic/configuration/form/manage/manage-approval-list'

import projectCredit from './components/ProjectCredit'
import { mapGetters, mapActions } from 'vuex'
import steps from './components/Steps/index'
import dialogModal from './module/dialogModal'

import {
  proctaskNoPass,
  proctaskfNodes,
  proctaskGoBack,
  // procinstAddInfo,
  proctaskPrevious,
  proctaskStartNode,
  proctaskRecall,
  taskStartCheck,
  taskVariables,
  assignCheck,
  proctaskAssignCancel,
  queryFromGroup,
  proctaskPass,
  procdefQueryDetail
} from '@/api/system-basic/approval-list'
import {
  initiateApprovalSaveDraft
} from '@/api/system-basic/approval-flow'

export default {
  name: 'ManageFrom',
  mixins: [crud],
  components: {
    steps,
    dialogModal,
    projectCredit
  },
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
      tabList: [{
        name: '待处理',
        tabName: 'approval',
        url: 'user/task/info/page/todo',
        type: 'post',
        tableColumn: [
          {
            type: 'text',
            label: '审批标题',
            prop: 'processInstanceName',
            align: 'left'
          },
          {
            type: 'text',
            label: '审批摘要',
            prop: 'approvalSummary',
            align: 'left'
          },
          {
            type: 'text',
            label: '发起时间',
            prop: 'startTime',
            align: 'left'
          },
          {
            type: 'text',
            label: '状态',
            prop: 'stateStr',
            align: 'center'
          },
          {
            type: 'operation',
            label: '操作',
            prop: 'operation',
            operationList: [
              {
                func: this.approvalPreview,
                label: '查看',
                key: 'preview'
              }
            ],
            fixed: 'right'
          }
        ]
      }, {
        name: '已处理',
        url: 'user/task/info/page/done',
        type: 'post',
        tabName: 'notApproval',
        tableColumn: [
          {
            type: 'text',
            label: '审批标题',
            prop: 'processInstanceName',
            align: 'left'
          },
          {
            type: 'text',
            label: '审批摘要',
            prop: 'approvalSummary',
            align: 'left'
          },
          {
            type: 'text',
            label: '发起与完成',
            prop: 'startTime',
            align: 'left'
          },
          {
            type: 'text',
            label: '状态',
            prop: 'stateStr',
            align: 'left'
          },
          {
            type: 'operation',
            label: '操作',
            prop: 'operation',
            operationList: [
              {
                func: this.approvalPreview,
                label: '查看',
                key: 'preview'
              }
            ],
            fixed: 'right'
          }
        ]
      }, {
        name: '我发起的',
        url: 'process/instance/info/page/myself',
        type: 'post',
        tabName: 'sponsor',
        tableColumn: [
          {
            type: 'text',
            label: '审批标题',
            prop: 'name',
            align: 'left'
          },
          {
            type: 'text',
            label: '审批摘要',
            prop: 'approvalSummary',
            align: 'left'
          },
          {
            type: 'text',
            label: '发起与完成',
            prop: 'startTime',
            align: 'left'
          },
          {
            type: 'text',
            label: '状态',
            prop: 'stateStr',
            align: 'left'
          },
          {
            type: 'operation',
            label: '操作',
            prop: 'operation',
            operationList: [
              {
                func: this.approvalPreview,
                label: '查看',
                key: 'preview'
              }
            ],
            fixed: 'right'
          }
        ]
      }, {
        name: '抄送',
        url: 'process/instance/info/page/carbon/record',
        type: 'post',
        tabName: 'copy',
        tableColumn: [
          {
            type: 'text',
            label: '审批标题',
            prop: 'name',
            align: 'left'
          },
          {
            type: 'text',
            label: '审批摘要',
            prop: 'approvalSummary',
            align: 'left'
          },
          {
            type: 'text',
            label: '发起与完成',
            prop: 'createTime',
            align: 'left'
          },
          {
            type: 'text',
            label: '状态',
            prop: 'stateStr',
            align: 'left'
          },
          {
            type: 'operation',
            label: '操作',
            prop: 'operation',
            operationList: [
              {
                func: this.approvalPreview,
                label: '查看',
                key: 'preview'
              }
            ],
            fixed: 'right'
          }
        ]
      }, {
        name: '草稿箱',
        url: 'process/definition/info/page/myself/draft',
        type: 'get',
        tabName: 'draft',
        tableColumn: [
          {
            type: 'text',
            label: '审批标题',
            prop: 'name',
            align: 'left'
          },
          {
            type: 'text',
            label: '更新时间',
            prop: 'procdefInfoUpdateTime',
            align: 'left'
          },
          {
            type: 'operation',
            label: '操作',
            prop: 'operation',
            operationList: [
              {
                func: this.approvalPreview,
                label: '查看',
                key: 'preview'
              }
            ],
            fixed: 'right'
          }
        ]
      }],
      tableConfiguration: tableConfiguration(this),
      fetchConfig: {
        retrieve: {
          url: 'user/task/info/page/todo',
          method: 'post'
        },
        create: {
          url: '/re/res/user/add',
          method: 'post'
        },
        update: {
          url: '/re/res/user/edit',
          method: 'post'
        },
        delete: {
          url: '/re/res/user/delete',
          method: 'post'
        }
      },
      formPreview: false,
      widgetModels: {},
      formtitle: '',
      activeName: 'approval',
      isApproval: true,
      widgetForm: {
        list: [],
        config: {
          labelWidth: 100,
          labelPosition: 'right',
          size: 'small',
          isBackEdge: false
        }
      },
      dialoForm: {
        list: [],
        config: {
          labelWidth: 100,
          labelPosition: 'right',
          size: 'small',
          isBackEdge: false
        }
      },
      detailsForm: {
        list: [],
        config: {
          labelWidth: 100,
          labelPosition: 'right',
          size: 'small',
          isBackEdge: false
        }
      },
      ruleForm: {
        activityId: '',
        message: ''
      },
      rules: {
        activityId: [
          { required: true, message: '请选择退回节点', trigger: 'change' }
        ]
      },
      isBackEdge: false,
      optionsApproval: [],
      startLoading: false,
      dialogVisible: false,
      detailsVisible: false,
      loading: false,
      hasJson: true,
      optionsBack: [],
      dialoModels: {},
      detailsModels: {},
      previewVisible: false,
      operationInfo: {
        pass: false, // 是否有通过按钮
        noPass: false, // 是否有不通过按钮
        returnBack: false, // 是否有退回按钮
        againStart: false, // 是否有重新发起按钮
        startProcinst: false, // 是否有提交按钮
        save: false, // 是否有保存按钮
        recall: false, // 是否有撤回按钮
        manual: false, // 是否有抄送按钮
        assign: false, // 是否有指派按钮
        unassign: false// 是否有撤销指派按钮
      },
      type: '',
      saveLoading: false,
      remoteFuncs: {
        func_test(resolve) {
          setTimeout(() => {
            const options = [
              {
                id: '1',
                name: '1111'
              },
              {
                id: '2',
                name: '2222'
              },
              {
                id: '3',
                name: '3333'
              }
            ]
            resolve(options)
          }, 2000)
        },
        upload_callback(response, file, fileList) {
          console.log('callback', response, file, fileList)
        }
      }
    }
  },
  computed: {
    ...mapGetters({
      formInfo: 'approval-form/formInfo'
    })
  },
  created() {
    this.mixinSearchFormItems = deepClone(this.formOption)
      .filter(e => e.isSearch)
    this.queryFromGroup1()
    this.mixinRetrieveTableData()
  },
  mounted() {
    if (this.$route.query.formType === 'dashboard') {
      this.activeName = 'approval'
      this.changePage()
    } else {
      this.activeName = this.$route.query.tabs || 'approval'
    }
  },
  methods: {
    ...mapActions('approval-form', ['setFormInfo']),
    interceptorsResponseTableData(data) {
      this.tableConfiguration.rowKey = this.activeName === 'draft' ? 'recordId' : 'id'
      data.forEach(el => Object.assign(el, { stateStr: changState(el.state) }))
      return data
    },
    approvalPreview(e) {
      // procdefDraft({
      //   recordId: e.row.recordId
      // }).then(res => {
      //   console.log(res)
      //   res.data.list
      // })
      const obj = {
        ...e.row,
        taskId: e.row.id,
        id: e.row.processInstanceId || e.row.id,
        processInstanceId: e.row.id,
        modelEditorJson: e.row.processVariables.formJson,
        processDefinitionKey: e.row.processDefinitionKey,
        state: e.row.state,
        key: e.row.processVariables.formKey
      }
      this.setFormInfo(obj)
      this.changePage()
    },
    toggleTypes() {
      const tab = this.tabList.find(el => el.tabName === this.activeName)
      this.fetchConfig.retrieve.url = tab?.url
      this.fetchConfig.retrieve.method = tab?.type
      this.tableConfiguration.group = tab.tableColumn
      this.mixinRetrieveTableData()
    },
    queryFromGroup1() {
      queryFromGroup().then(res => {
        this.mixinSearchFormItems
          .find(el => el.ruleProp === 'formGroupId')
          .options = res.data.map(item => {
            return {
              name: item.name,
              value: item.id
            }
          })
      })
    },
    interceptorsRequestRetrieve() {
      return {
        formGroupId: this.mixinSearchFormConfig.models.formGroupId || undefined
      }
    },
    changePage() {
      try {
        if (this.activeName !== 'draft' && this.formInfo.name.indexOf('【') > -1) {
          const reg = /(?<=[【[]).*(?=[】\]])/
          this.formtitle = this.formInfo.name.match(reg)[0]
        } else {
          this.formtitle = this.formInfo.name
        }
        const description = JSON.parse(this.activeName !== 'draft' ? this.formInfo.description : this.formInfo.modelEditorJson)
        const back = this.activeName === 'approval' && this.formInfo.description
        this.reset()
        /**
         * 我发起的--退回状态单独判断是否有重新提交按钮
         */
        if (this.activeName === 'sponsor' && this.formInfo.state === 'GOBACK') {
          this.loading = true
        }
        // 是否有提交按钮
        this.operationInfo.startProcinst = this.activeName === 'draft'// 草稿箱
        // 是否有保存按钮
        this.operationInfo.save = this.activeName === 'draft'// 草稿箱
        if (description) {
          // 是否有指派按钮
          this.operationInfo.assign = description.isAssign && this.activeName === 'approval' && !this.formInfo.assignFlag
          // 是否有通过按钮
          this.operationInfo.pass = description.isApprove && this.activeName === 'approval'
          // 是否有不通过按钮
          this.operationInfo.noPass = description.isBack && this.activeName === 'approval'
          // 是否有退回按钮
          this.operationInfo.returnBack = description.isNoConfirm && back
        }

        // 判断撤销指派
        if (this.activeName === 'notApproval') {
          this.setUnassign()
        } else {
          // 判断撤回
          this.setRecall()
        }
        // 判断抄送
        this.setCarbon()
        this.setFormJson()
        this.isApproval = false
      } catch (e) {
        console.log(e)
      }
    },
    preview() {
      this.$refs.generateForm.getData().then(res => {
        this.widgetForm.list.forEach(item => {
          const tl = item
          tl.options.defaultValue = res[item.model]
          if (item.type === 'grid') {
            item.columns.forEach(i => {
              i.list.forEach(j => {
                const t = j
                t.options.defaultValue = res[j.model]
              })
            })
          }
        })
        this.previewVisible = true
      })
    },
    reset() {
      // 是否有通过按钮
      this.operationInfo.pass = false
      // 是否有不通过按钮
      this.operationInfo.noPass = false
      // 是否有退回按钮
      this.operationInfo.returnBack = false
      // 是否有重新发起按钮
      this.operationInfo.againStart = false
      // 是否有提交按钮
      this.operationInfo.startProcinst = false
      // 是否有保存按钮
      this.operationInfo.save = false
      // 是否有撤回按钮
      this.operationInfo.recall = false
      // 是否有抄送按钮
      this.operationInfo.manual = false
      // 是否有指派按钮
      this.operationInfo.assign = false
      // 是否有撤销指派按钮
      this.operationInfo.unassign = false
    },
    handleTabClick(val) {
      this.$refs[val.name].filterPro.searchInput = ''
      this.$refs[val.name].getList()
      if (this.$route.query.tabs) {
        this.$router.push({ query: {} })
      }
    },
    passProcinst() {
      // this.loading = true
      this.$refs.generateForm.getData().then(data => {
        const params = {
          taskId: this.formInfo.taskId,
          message: JSON.stringify(this.dialoForm),
          values: data
        }
        proctaskPass(params).then(res => {
          this.loading = false
          if (res.success) {
            this.$notify.success('提交申请成功')
            this.dialogVisible = false
            this.init()
          } else {
            this.$notify.error(res.returnMsg)
          }
        }).catch(() => {
          this.loading = false
        })
      })
    },
    handleDialo(val) {
      this.type = val
      const { description } = this.formInfo
      const descriptionJson = JSON.parse(description)
      if (val === 'pass') {
        this.dialoForm = description
          && descriptionJson.approveForm
          && JSON.parse(descriptionJson.approveForm)
      } else if (val === 'noPass') {
        this.dialoForm = description
          && descriptionJson.backForm
          && JSON.parse(descriptionJson.backForm)
      } else if (val === 'returnBack') {
        if (descriptionJson.backInfo === 'choose') {
          // 退回时选择节点
          this.returnBack()
        }
        this.dialoForm = description
          && descriptionJson.notCofirmForm
          && JSON.parse(descriptionJson.notCofirmForm)
      }
      if (this.dialoForm.list && this.dialoForm.list.length) {
        this.dialogVisible = true
      } else if (val === 'pass') {
        this.passProcinst()
      } else if (val === 'noPass') {
        this.noPass()
      } else if (val === 'returnBack') {
        if (descriptionJson.backInfo === 'prev') {
          // 退回到上一个节点
          this.previousNode()
        } else if (descriptionJson.backInfo === 'appoint') {
          // 退回到发起节点
          this.initiatingNode()
        } else if (descriptionJson.backInfo === 'choose') {
          // 退回时选择节点
          this.dialogVisible = true
          this.hasJson = false
        }
        //   this.saveReturn()
      }
    },
    manual() {
      this.$refs.dialogModal.setManual()
    },
    handleSaveInfo() {
      const { description } = this.formInfo
      const descriptionJson = JSON.parse(description)
      if (this.type === 'pass') {
        this.passProcinst()
      } else if (this.type === 'noPass') {
        this.noPass()
      } else if (this.type === 'returnBack') {
        if (descriptionJson.backInfo === 'prev') {
          // 退回到上一个节点
          this.previousNode()
        } else if (descriptionJson.backInfo === 'appoint') {
          // 退回到发起节点
          this.initiatingNode()
        } else if (descriptionJson.backInfo === 'choose') {
          // 退回时选择节点
          this.saveReturn()
        }
      } else if (this.type === 'recall') {
        this.recallOperate(JSON.stringify(this.dialoForm))
      } else if (this.type === 'unassign') {
        this.saveUnassign()
      }
    },
    /**
     * @date 2021/01/20 20:23
     * @author dongchao
     * @desc 保存
     * @params id
     * @return null
     */
    save() {
      this.loading = true
      this.$refs.generateForm.getData().then(data => {
        const params = {
          processInstanceId: this.processInstanceId || this.formInfo.key,
          values: data,
          recordId: this.formInfo.recordId
        }
        initiateApprovalSaveDraft(params)
          .then(res => {
            this.loading = false
            if (res.success) {
              this.$notify.success('保存申请成功')
              this.init()
            } else {
              this.$notify.error(res.returnMsg)
            }
          })
      })
    },
    assign() {
      this.$refs.dialogModal.type = 'assign'
      this.$refs.dialogModal.setDialoForm()
    },
    handleStart() {
      this.$refs.generateForm.$refs.formPreview.validate(valid => {
        if (valid) {
          this.$refs.dialogModal.getTaskDetail()
        } else {
          this.startLoading = false
          this.$notify.error('请检查完参数再提交')
        }
      })
    },
    dialogAgainStart(hasDialog) {
      console.log('dialogAgainStart')
      if (hasDialog) {
        this.$confirm('是否重新提交流程?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.handleLaunch()
        }).catch(() => {

        })
      } else {
        this.handleLaunch()
      }
    },
    againStart() {
      this.$confirm('是否重新提交流程?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.activeName === 'sponsor') {
          if (this.formInfo.state === 'NOPASS') {
            this.startProcinst() // 不通过是调发起接口
          } else {
            this.sponsorAgain() // 其他状态调重新发起接口
          }
        } else {
          this.procinstAgain()
        }
      }).catch(() => {

      })
    },
    handleLaunch() {
      this.$refs.generateForm.getData()
        .then(data => {
          console.log('this.activeName', this.activeName)
          if (this.activeName === 'sponsor') {
            if (this.formInfo.state === 'NOPASS') {
              this.$refs.dialogModal.startProcinst(data) // 不通过是调发起接口
            } else {
              this.$refs.dialogModal.promiseApprove(data)// 其他状态调重新发起接口
            }
          } else if (this.activeName === 'draft') {
            this.$refs.dialogModal.startProcinst(data) // 草稿箱也是调发起接口
          }
        })
    },
    sponsorAgain() {
      this.loading = true
      this.$refs.generateForm.getData().then(data => {
        const params = {
          processInstanceId: this.formInfo.processInstanceId,
          message: '',
          values: data
        }
        this.$api.form.taskReplay(params).then(res => {
          this.loading = false
          if (res.success) {
            this.$notify.success('提交申请成功')
            this.dialogVisible = false
            this.init()
          } else {
            this.$notify.error(res.returnMsg)
          }
        })
      })
    },
    procinstAgain() {
      this.loading = true
      this.$refs.generateForm.getData().then(data => {
        const params = {
          taskId: this.formInfo.taskId,
          message: '',
          values: data
        }
        this.$api.form.taskComplate(params).then(res => {
          this.loading = false
          if (res.success) {
            this.$notify.success('提交申请成功')
            this.dialogVisible = false
            this.init()
          } else {
            this.$notify.error(res.returnMsg)
          }
        })
      })
    },
    startProcinst() {
      this.$refs.generateForm.$refs.formPreview.validate(vaild => {
        if (vaild) {
          this.startLoading = true
          this.loading = true
          this.$refs.generateForm.getData().then(data => {
            const params = {
              formKey: this.formInfo.key,
              values: data,
              modelType: 2
            }
            this.$api.approval
              .ruleFormInfo(params)
              .then(res => {
                if (res.success) {
                  const params2 = {
                    formKey: this.formInfo.key,
                    values: data,
                    recordId: this.formInfo.recordId
                  }
                  this.$api.approval
                    .procinstStart(params2)
                    .then(resl => {
                      this.startLoading = false
                      this.loading = false
                      if (resl.success) {
                        this.$notify.success('提交申请成功')
                        this.init()
                      } else {
                        this.$notify.error(resl.returnMsg)
                      }
                    })
                } else {
                  this.$notify.error(res.returnMsg)
                  this.startLoading = false
                  this.loading = false
                }
              })
              .catch(err => {
                this.startLoading = false
                this.loading = false
                console.log(err)
              })
          })
        } else {
          this.startLoading = false
          this.loading = false
          this.$notify.error('请检查完参数再提交')
        }
      })
    },
    returnBack() {
      proctaskfNodes({
        taskId: this.formInfo.taskId
      })
        .then(res => {
          this.optionsBack = res.data.map(i => {
            const actRoleInfos = i.actRoleInfos.map(j => j.name).join('、')
            const value = i.initiator ? i.userName : actRoleInfos
            const label = i.nodeName ? `${value}（${i.nodeName}）` : value
            this.$set(i, 'label', label)
            return i
          })
        })
    },
    previousNode() {
      this.loading = true
      const params = {
        taskId: this.formInfo.taskId,
        message: JSON.stringify(this.dialoForm),
        processDefinitionKey: this.formInfo.processDefinitionKey,
        processInstanceId: this.formInfo.processInstanceId
      }
      proctaskPrevious(params)
        .then(res => {
          this.loading = false
          if (res.success) {
            this.$notify.success('退回成功')
            this.dialogVisible = false
            this.init()
          } else {
            this.$notify.error(res.returnMsg)
          }
        })
    },
    initiatingNode() {
      this.loading = true
      const params = {
        taskId: this.formInfo.taskId,
        message: JSON.stringify(this.dialoForm),
        processDefinitionKey: this.formInfo.processDefinitionKey,
        processInstanceId: this.formInfo.processInstanceId
      }
      proctaskStartNode(params)
        .then(() => {
          this.loading = false
          this.$notify.success('退回成功')
          this.dialogVisible = false
          this.init()
        })
    },
    saveReturn() {
      this.loading = true
      if (!this.ruleForm.activityId) {
        this.$notify.error('请选择退回节点')
        this.loading = false
        return
      }
      proctaskGoBack({
        taskId: this.formInfo.taskId,
        message: JSON.stringify(this.dialoForm),
        activityId: this.ruleForm.activityId,
        processDefinitionKey: this.formInfo.processDefinitionKey,
        processInstanceStartUserId: this.formInfo.processInstanceStartUserId,
        processInstanceId: this.formInfo.processInstanceId,
        processVariables: this.formInfo.processVariables
      })
        .then(() => {
          this.$notify.success('退回成功')
          this.dialogVisible = false
          this.ruleForm = {
            activityId: '',
            message: ''
          }
          this.hasJson = true
          this.init()
          this.loading = false
        })
    },
    searchDetails(row) {
      if (this.activeName === 'draft') return
      const fullMessage = JSON.parse(row.fullMessage)
      this.detailsForm = fullMessage
      this.detailsForm.list.forEach(item => {
        const li = item
        li.options.disabled = true
        if (item.type === 'childForm') {
          item.tableColumns.forEach(i => {
            const l = i
            l.options.disabled = true
          })
        }
        if (item.type === 'grid') {
          item.columns.forEach(i => {
            i.list.forEach(j => {
              const l = j
              l.options.disabled = true
            })
          })
        }
      })
      this.detailsVisible = true
    },
    creditExtension(data) {
      this.$refs.projectCredit.getCreditItems({ data, type: 'credit' })
    },
    handleDialoChange(field, value) {
      this.dialoForm.list.forEach(i => {
        const l = i
        if (i.model === field) {
          l.options.defaultValue = value
        }
      })
    },
    handleDetailsChange(field, value, data) {
      console.log(field, value, data)
    },
    handleDataChange(field, value, data) {
      console.log(field, value, data)
    },
    recall() {
      //   this.$refs.dialogModal.backVisible = true
      const params = {
        processInstanceId: this.formInfo.processInstanceId
      }
      taskVariables(params).then(res => {
        const form = JSON.parse(res.data.recallForm)
        if (form.list.length) {
          this.dialoForm = form
          this.type = 'recall'
          this.dialogVisible = true
        } else {
          this.$confirm('是否撤回表单?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.recallOperate()
          }).catch(() => {

          })
        }
      })
    },
    recallOperate(message = '') {
      this.loading = true
      const params = {
        message,
        processInstanceId: this.formInfo.processInstanceId
      }
      proctaskRecall(params)
        .then(() => {
          this.$notify.success('撤回成功')
          this.dialogVisible = false
          this.init()
        })
        .finally(() => {
          this.loading = false
        })
    },
    noPass() {
      this.loading = true
      proctaskNoPass({
        taskId: this.formInfo.taskId,
        message: JSON.stringify(this.dialoForm)
      })
        .then(() => {
          this.$notify.success('提交成功')
          this.dialogVisible = false
          this.init()
        }).catch(err => {
          console.log(err)
        })
        .finally(() => {
          this.loading = false
        })
    },
    init() {
      this.isApproval = true
      this.mixinRetrieveTableData()
    },
    unassign() {
      this.type = 'unassign'
      this.dialogVisible = true
      this.dialoForm = {
        list: [
          {
            type: 'textarea',
            icon: 'icon-textarea',
            options: {
              width: '100%',
              defaultValue: '',
              required: false,
              disabled: false,
              regexp: 'partten',
              pattern: '',
              errorMsg: '',
              placeholder: '',
              hiddenLabel: false,
              readonly: false,
              isPartten: true,
              remoteFunc: 'func_1597391110000_80095'
            },
            name: '撤销指派原因',
            key: '1597391110000_80095',
            model: 'textarea_1597391110000_80095',
            rules: []
          }
        ],
        config: {
          labelWidth: 100,
          labelPosition: 'right',
          size: 'small',
          isBackEdge: false
        }
      }
    },
    saveUnassign() {
      this.loading = true
      proctaskAssignCancel({
        processInstanceId: this.formInfo.processInstanceId,
        message: JSON.stringify(this.dialoForm)
      })
        .then(() => {
          this.$notify.success('提交成功')
          this.dialogVisible = false
          this.init()
        })
        .finally(() => {
          this.loading = false
        })
    },
    setCarbon() {
      if (this.activeName === 'sponsor' || this.activeName === 'notApproval' || this.activeName === 'copy') {
        // const params = {
        //   processDefinitionKey: this.formInfo.processDefinitionKey,
        //   processInstanceId: this.formInfo.processInstanceId,
        //   startUserId: this.formInfo.startUserId || '',
        //   type: this.activeName === 'sponsor' ? 0 : 1,
        //   processActiveId: this.formInfo.taskDefinitionKey
        // }
        // proctaskCarbon(params).then(res => {
        //   if (res.success) {
        //     // 是否有抄送按钮
        this.operationInfo.manual = true// res.data
        //   } else {
        //     this.$notify.error(res.returnMsg)
        //   }
        // })
      }
      if (this.activeName === 'approval') {
        const description = JSON.parse(this.formInfo.description)
        if (!description) return
        this.operationInfo.manual = !!description.isManual // 转成布尔值
      }
    },
    setAgainStart(res) {
      /**
       * 我发起的--退回状态单独判断是否有重新提交按钮
       */
      if (this.activeName === 'sponsor' && (this.formInfo.state === 'GOBACK' || this.formInfo.state === 'RECALL' || this.formInfo.state === 'NOPASS')) {
        const data = res[res.length - 1]
        if ((data.activityType === 'userTask' && data.initiator) || this.formInfo.state === 'NOPASS') {
          this.operationInfo.againStart = true
          this.procdefQueryDetail()
          //   this.setFormJson()
        }
      }
      this.loading = false
    },
    procdefQueryDetail() {
      const params = {
        procdefKey: this.formInfo[this.activeName === 'draft' ? 'procdefKey' : 'processDefinitionKey']
      }
      procdefQueryDetail(params)
        .then(res => {
          const document = JSON.parse(res.data.document)
          if (document) {
            if (document.termList && document.termList.length) {
              this.changeTermState(document)
            }
          }
        })
    },
    changeTermState(target) {
      const termList = target.termList || []
      const termModelList = termList.map(i => i.model)
      this.widgetForm.list.forEach(item => {
        termList.forEach(i => {
          if (item.type === 'childForm') {
            item.tableColumns.forEach(j => {
              if (i.authority === 'show' && j.model === i.model) {
                this.setOptions(j, 'isHidden')
              }
              if (i.authority === 'read' && j.model === i.model) {
                this.setOptions(j, 'disabled')
              }
              if (i.authority === 'edit' && j.model === i.model) {
                this.setOptions(j, 'disabled', false)
              }
              // 因为现在子表单中的组件不支持节点配置，所以都是编辑状态
              if (termModelList.indexOf(j.model) === -1) {
                this.setOptions(j, 'disabled', false)
              }
            })
          } else if (item.type === 'grid') {
            item.columns.forEach(j => {
              j.list.forEach(v => {
                if (i.authority === 'show' && v.model === i.model) {
                  this.setOptions(v, 'isHidden')
                }
                if (i.authority === 'read' && v.model === i.model) {
                  this.setOptions(v, 'disabled')
                }
                if (i.authority === 'edit' && v.model === i.model) {
                  this.setOptions(v, 'disabled', false)
                }
                if (termModelList.indexOf(v.model) === -1) {
                  this.setOptions(v, 'disabled', false)
                }
              })
            })
          } else {
            if (i.authority === 'show' && item.model === i.model) {
              this.setOptions(item, 'isHidden')
            }
            if (i.authority === 'read' && item.model === i.model) {
              this.setOptions(item, 'disabled')
            }
            if (i.authority === 'edit' && item.model === i.model) {
              this.setOptions(item, 'disabled', false)
            }
            if (termModelList.indexOf(item.model) === -1) {
              this.setOptions(item, 'disabled', false)
            }
          }
        })
      })
    },
    setRecall() {
      // 是否有撤回按钮
      const recall = this.activeName === 'notApproval' && this.formInfo.state === 'TOAUDIT' && !this.formInfo.endTime
      // 我发起的 - 撤回或者待审核单独判断
      const condition = this.activeName === 'sponsor' && this.formInfo.state === 'TOAUDIT'
      if (recall || condition) {
        const params = {
          processDefinitionKey: this.formInfo.processDefinitionKey,
          processInstanceId: this.formInfo.processInstanceId
        }
        taskStartCheck(params)
          .then(res => {
            this.operationInfo.recall = res.data.resubmit
          })
      }
    },
    setUnassign() {
      const params = {
        processInstanceId: this.formInfo.processInstanceId
      }
      assignCheck(params).then(res => {
        this.operationInfo.unassign = res.data.allowCancelAssign
        // 撤销指派和撤回不共存，这边判断撤销指派false才去查是否有撤回权限
        if (!this.operationInfo.unassign) {
          this.setRecall()
        }
      })
    },
    toImage() {
      this.loading = true
      this.$nextTick(() => {
        this.$refs.printPreview.print(50)
        this.loading = false
        this.previewVisible = false
      })
    },
    setOptions(item, label, value = true) {
      const it = item
      it.options[label] = value
      if (item.type === 'childForm') {
        item.tableColumns.forEach(i => {
          const inter = i
          inter.options[label] = value
        })
      }
      if (item.type === 'grid') {
        item.columns.forEach(i => {
          i.list.forEach(j => {
            const inter = j
            inter.options[label] = value
          })
        })
      }
    },
    setFormJson() {
      if (this.formInfo.modelEditorJson) {
        this.widgetForm = JSON.parse(this.formInfo.modelEditorJson)
        this.isBackEdge = this.widgetForm.config.isBackEdge
        this.widgetForm.list.forEach(item => {
          const ts = item
          ts.options.defaultValue = this.formInfo.processVariables[item.model]
          if (item.type === 'imgupload') {
            // 旧表单实现批量上传图片
            ts.options.multiple = true
          }
          if (item.type === 'grid') {
            item.columns.forEach(i => {
              i.list.forEach(j => {
                if (j.type === 'imgupload') {
                  // 旧表单实现批量上传图片
                  // eslint-disable-next-line no-param-reassign
                  j.options.multiple = true
                }
                // eslint-disable-next-line no-param-reassign
                j.options.defaultValue = this.formInfo.processVariables[j.model]
              })
            })
          }
          if (item.type === 'childForm') {
            item.tableColumns.forEach(i => {
              const tr = i
              if (i.type === 'imgupload') {
                // 旧表单实现批量上传图片
                tr.options.multiple = true
              }
            })
          }
          if (this.activeName !== 'draft') {
            const termList =
              (this.formInfo.description && JSON.parse(this.formInfo.description)
                && JSON.parse(this.formInfo.description).termList) || []
            // 发起人条件 当我发起的是处于撤回或者退回状态是可编辑的
            const sponsorCondition = this.activeName === 'sponsor' && (this.formInfo.state !== 'GOBACK' && this.formInfo.state !== 'RECALL')
            // 流程配置节点 过滤没有的表单key 如果是审批节点默认是只读 发起节点默认是可编辑
            const approvalCondition = termList.map(i => i.model).indexOf(item.model) === -1 && this.activeName === 'approval'
            if (this.activeName === 'copy' || this.activeName === 'notApproval' || sponsorCondition || approvalCondition) {
              this.setOptions(item, 'disabled')
            }
            if ((this.activeName === 'approval') && termList && termList.length) {
              termList.forEach(i => {
                if (item.type === 'childForm') {
                  item.tableColumns.forEach(j => {
                    if (i.authority === 'show' && j.model === i.model) {
                      this.setOptions(j, 'isHidden')
                    }
                    if (i.authority === 'read' && j.model === i.model) {
                      this.setOptions(j, 'disabled')
                    }
                    if (i.authority === 'edit' && j.model === i.model) {
                      this.setOptions(j, 'disabled', false)
                    }
                  })
                } else if (item.type === 'grid') {
                  item.columns.forEach(j => {
                    j.list.forEach(v => {
                      if (i.authority === 'show' && v.model === i.model) {
                        this.setOptions(v, 'isHidden')
                      }
                      if (i.authority === 'read' && v.model === i.model) {
                        this.setOptions(v, 'disabled')
                      }
                      if (i.authority === 'edit' && v.model === i.model) {
                        this.setOptions(v, 'disabled', false)
                      }
                    })
                  })
                } else {
                  if (i.authority === 'show' && item.model === i.model) {
                    this.setOptions(item, 'isHidden')
                  }
                  if (i.authority === 'read' && item.model === i.model) {
                    this.setOptions(item, 'disabled')
                  }
                  if (i.authority === 'edit' && item.model === i.model) {
                    this.setOptions(item, 'disabled', false)
                  }
                }
              })
            }
          }
        })
        if (this.activeName === 'draft') {
          this.procdefQueryDetail()
        }
      }
    }

  }
}
</script>
<style lang="scss" scoped>
.approval-manage-scope{
  .search-tab {
    margin: 0;

    .el-tabs__content {
      padding: 0;
    }
  }

  ::v-deep .tab-header {
    display: flex;
    margin-bottom: 10px;
    justify-content: flex-end;
  }

  .button {
    display: flex;
    justify-content: center;
    margin-top: 10px;
    margin-bottom: 10px;

    .button-container {
      margin-right: 10px
    }
  }

  ::v-deep .cus-dialog-container,
  .dialog-container {
    display: flex;
    height: calc(100vh - 80px);
    padding-bottom: 10px;
    background: #f1f1f1;

    .form-container {
      width: 100%;
      background-color: #fff;
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 50px 20px 0;
    }

    .container-max {
      max-width: calc(100% - 360px);
      overflow: auto;
    }

    .dialog-footer {
      display: flex;
      justify-content: center;
    }
  }

  .form-footer {
    v-deep .el-form-item__content {
      display: flex;
      justify-content: center;
      margin-left: 0 !important;
    }
  }

  .el-dialog {
    box-shadow: none;
  }

  @media screen and (max-width: 1500px) {
    .cus-dialog {
      width: 100%;
    }
  }

  @media screen and (min-width: 1500px) {
    .cus-dialog {
      width: 80%;
    }
  }

  .preview-title {
    display: flex;
    justify-content: center;
    font-size: 24px;
    font-weight: 600;
    color: #666;
    margin-bottom: 35px;
  }
}
</style>
