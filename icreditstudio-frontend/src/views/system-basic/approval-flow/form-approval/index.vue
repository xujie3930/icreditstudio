<template>
  <div v-loading.fullscreen.lock="loading" class="dialog-container"
       :class="{'cus-dialog-container':widgetForm.config.isBackEdge}">
    <div class="form-container" :class="{'container-max':formInfo.hasSteps === true}">
      <j-form-preview
        ref="generateForm"
        :form-title="formInfo.name"
        class="cus-dialog"
        insite="true"
        :data="widgetForm"
        :value="widgetModels"
        :remote="remoteFuncs"
        @on-change="handleDataChange"
      />
      <div class="button">
        <el-button
          v-if="formInfo.hasSubmit !== false"
          size="small"
          type="primary"
          :loading="startLoading"
          @click="handleSubmit"
        >
          提交
        </el-button>
        <el-button
          v-if="formInfo.hasSave !== false"
          :loading="saveLoading"
          size="small"
          @click="handleSaveDraft"
        >
          保存
        </el-button>
        <el-button
          size="small"
          @click="goBack"
        >
          返回
        </el-button>
      </div>
    </div>
    <div class="common-dialog">
      <el-dialog
        v-if="detailsVisible"
        title="提示"
        :visible.sync="detailsVisible"
        width="600px"
        :close-on-click-modal="false"
      >
        <j-form-preview
          class="cus-dialog"
          insite="true"
          :data="detailsForm"
          :value="detailsModels"
          :remote="remoteFuncs"
          @on-change="handleDetailsChange"
        />
      </el-dialog>
      <el-dialog
        v-loading="treeLoading"
        title="提交审核"
        :visible.sync="outerVisible"
        :close-on-click-modal="false"
        width="500px">
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
          <el-form-item v-if="operationInfo.appoint" label="审批人">
            <el-select
              v-model="form.approveIds"
              filterable
              remote
              clearable
              size="small"
              style="width: 100%;"
              placeholder="未指定审批人，自动跳转下一个节点"
              :remote-method="remoteMethodAssign"
              multiple
              @focus="handleFocus"
            >
              <el-option
                v-for="item in optionsAssign"
                :key="item.value"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item v-if="operationInfo.manual" label="抄送人(可空)" prop="copyIds">
            <el-select
              v-model="form.copyIds"
              filterable
              remote
              clearable
              size="small"
              style="width: 100%;"
              placeholder="请输入工号、姓名、手机号、部门"
              :remote-method="remoteMethod"
              multiple
              @focus="handleFocus"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" :loading="saveTreeLoading" @click="saveAppoint">确定</el-button>
          <el-button @click="outerVisible = false">取 消</el-button>
        </div>
      </el-dialog>
    </div>
<!--    <steps-->
<!--      v-if="formInfo.hasSteps === true"-->
<!--      ref="steps"-->
<!--      style="margin-left:20px"-->
<!--      @searchDetails="searchDetails"-->
<!--    />-->
  </div>
</template>

<script>
// import { mapGetters, mapActions } from 'vuex'
import { debounce } from '@/utils/util'
import {
  queryProcdefSponsorTaskDetail,
  initiateApprovalSaveDraft,
  // initiateApprovalCheckFormRule,
  initiateApprovalSubmit
} from '@/api/system-basic/approval-flow'
// import steps from '@/components/Steps/index'

export default {
  name: 'FormApproval',
  components: {
    // steps
  },
  data() {
    return {
      widgetForm: {
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
      options: [],
      startLoading: false,
      saveLoading: false,
      widgetModels: {},
      isBackEdge: false,
      saveTreeLoading: false,
      detailsVisible: false,
      remoteFuncs: {
        func_test(resolve) {
          setTimeout(() => {
            const options = [
              { id: '1', name: '1111' },
              { id: '2', name: '2222' },
              { id: '3', name: '3333' }
            ]

            resolve(options)
          }, 2000)
        },
        funcGetToken(resolve) {
          console.log(resolve)
        },
        upload_callback(response, file, fileList) {
          console.log(response, file, fileList)
        }
      },
      outerVisible: false,
      innerVisible: false,
      operationInfo: {
        appoint: false, // 是否有指定审批人
        manual: false
      },
      loading: false,
      treeLoading: false,
      roleTree: [],
      roleList: [],
      form: {
        approveInfos: [],
        approveIds: [],
        copyInfos: [],
        copyIds: []
      },
      rules: {
        approveInfos: [
          { required: true, message: '审批人不能为空', trigger: 'change' }
        ]
      },
      filterTree: '',
      type: '',
      optionsAssign: []
    }
  },
  computed: {
    formInfo() {
      return JSON.parse(this.$ls.get('approvalInitiate-formInfo'))
    }
    // ...mapGetters({
    //   formInfo: 'approval-form/formInfo'
    // })
  },
  watch: {
    filterTree(val) {
      this.$refs.roleTree.filter(val)
    }
  },
  created() {
    this.init()
    this.$once('hook:destroyed', () => {
      this.$ls.remove('approvalInitiate-formInfo')
    })
  },
  methods: {
    // ...mapActions('approval-form', ['setFormInfo']),
    init() {
      if (this.formInfo.modelEditorJson) {
        /* eslint-disable */
        this.widgetForm = JSON.parse(this.formInfo.modelEditorJson)
        this.isBackEdge = this.widgetForm.config.isBackEdge
        this.widgetForm.list.forEach(item => {
          if (item.type === 'imgupload') {
            // 旧表单实现批量上传图片
            item.options.multiple = true
          }
          if (item.type === 'childForm') {
            item.tableColumns.forEach(i => {
              if (i.type === 'imgupload') {
                // 旧表单实现批量上传图片
                i.options.multiple = true
              }
            })
          }
          if (item.type === 'grid') {
            item.columns.forEach(i => {
              i.list.forEach(j => {
                if (j.type === 'imgupload') {
                  // 旧表单实现批量上传图片
                  j.options.multiple = true
                }
              })
            })
          }
          if (!this.formInfo.processVariables) return
          item.options.defaultValue = this.formInfo.processVariables[item.model]
          // if (item.type === 'childForm') {
          //   item.tableColumns.forEach(i => {
          //     i.options.defaultValue = this.formInfo.processVariables[item.model].map(j => j[i.model])
          //   })
          // }
          if (item.type === 'grid') {
            item.columns.forEach(i => {
              i.list.forEach(j => {
                j.options.defaultValue = this.formInfo.processVariables[j.model]
              })
            })
          }
          // formType 表单状态 readonly 只读 isHidden 隐藏
          if (this.formInfo.formType) {
            const {formType} = this.formInfo
            item.options[formType] = true
            if (item.type === 'childForm') {
              item.tableColumns.forEach(i => {
                i.options[formType] = true
              })
            }
            if (item.type === 'grid') {
              item.columns.forEach(i => {
                i.list.forEach(j => {
                  j.options[formType] = true
                })
              })
            }
          }
        })
        /* eslint-enable */
      }
      // formType只有在立项台账，授信台账有 customerType客户管理
      if (!this.formInfo.formType && !this.formInfo.customerType) {
        this.getTaskDetail()
      }
    },
    getTaskDetail() {
      queryProcdefSponsorTaskDetail({
        procdefKey: this.formInfo.procdefKey
      })
        .then(res => {
          const document = JSON.parse(res.data.document)
          if (document) {
            this.operationInfo.appoint = document.isAppoint
            this.operationInfo.manual = document.isManual
            if (document.termList && document.termList.length) {
              this.changeTermState(document)
            }
          }
        })
        .finally(() => {
          this.loading = false
        })
    },
    // 保存草稿
    async handleSaveDraft() {
      this.saveLoading = true
      const data = await this.$refs.generateForm.getData()
      initiateApprovalSaveDraft({
        processInstanceId: this.formInfo.key,
        values: data,
        recordId: this.formInfo.recordId,
        formJson: this.formInfo.modelEditorJson
      })
        .then(() => {
          this.$notify.success('保存申请成功')
        })
        .finally(() => {
          this.saveLoading = false
        })
    },
    handleSubmit() {
      this.$refs.generateForm.$refs.formPreview.validate(valid => {
        if (valid) {
          if (this.operationInfo.appoint || this.operationInfo.manual) {
            this.outerVisible = true
          } else {
            this.startProcessInstance()
          }
        } else {
          this.startLoading = false
          this.loading = false
          this.$notify.error('请检查完参数再提交')
        }
      })
    },
    async startProcessInstance() {
      this.startLoading = true
      this.loading = true
      const data = await this.$refs.generateForm.getData()
      // initiateApprovalCheckFormRule({
      //   formKey: this.formInfo.key,
      //   values: data,
      //   modelType: 2
      // })
      //   .then(() => {
      initiateApprovalSubmit({
        formKey: this.formInfo.key,
        values: data,
        recordId: this.formInfo.recordId,
        formJson: this.formInfo.modelEditorJson
      })
        .then(() => {
          this.startLoading = false
          this.loading = false
          this.$notify.success('提交申请成功')
          this.goBack()
        })
        .finally(() => {
          this.startLoading = false
          this.loading = false
        })
        // })
        // .catch(err => {
        //   console.log(err)
        //   this.startLoading = false
        //   this.loading = false
        // })
    },
    handleDataChange(field, value, data) {
      console.log(field, value, data)
    },
    /* eslint-disable */
    searchDetails(row) {
      const fullMessage = JSON.parse(row.fullMessage)
      this.detailsForm = fullMessage
      this.detailsForm.list.forEach(item => {
        item.options.disabled = true
        if (item.type === 'childForm') {
          item.tableColumns.forEach(i => {
            i.options.disabled = true
          })
        }
        if (item.type === 'grid') {
          item.columns.forEach(i => {
            i.list.forEach(j => {
              j.options.disabled = true
            })
          })
        }
      })
      this.detailsVisible = true
    },
    // 模糊查询用户
    remoteMethod: debounce(function (query) {
      this.options = []
      if (query !== '') {
        const params = {
          param: query
        }
        this.$api.approval.userFilterMe(params).then(res => {
          if (res.success) {
            res.data.forEach(item => {
              item.value =
                `${item.platformUserId
                }\xa0\xa0\xa0${
                  item.name}`
            })
            this.options = res.data
          }
        })
      } else {
        this.options = []
      }
    }),
    remoteMethodAssign: debounce(function (query) {
      this.optionsAssign = []
      if (query !== '') {
        const params = {
          param: query,
          startUserId: this.userData.id
        }
        this.$api.approval.proctaskAssignStartUsers(params).then(res => {
          if (res.success) {
            res.data.forEach(item => {
              item.value =
                `${item.platformUserId
                }\xa0\xa0\xa0${
                  item.name}`
            })
            this.optionsAssign = res.data
          }
        })
      } else {
        this.optionsAssign = []
      }
    }),
    handleFocus() {
      this.options = []
    },
    promiseApprove() {
      this.saveTreeLoading = true
      this.loading = false
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
              const params = {
                formKey: this.formInfo.key,
                values: {
                  ...data
                },
                recordId: this.formInfo.recordId,
                formJson: this.formInfo.modelEditorJson
              }

              if (this.form.approveIds.length) {
                params.values.nextApprovalUserIds = this.form.approveIds
              }
              this.$api.approval
                .procinstStart(params)
                .then(res => {
                  this.saveTreeLoading = false
                  this.loading = false
                  if (res.success) {
                    if (this.form.copyIds.length && this.operationInfo.manual) {
                      this.promiseCopy(res.data)
                    } else {
                      this.$notify.success('提交审核成功')
                      this.outerVisible = false
                      this.goBack()
                    }
                  } else {
                    this.$notify.error(res.returnMsg)
                  }
                })
            } else {
              this.$notify.error(res.returnMsg)
            }
          })
          .catch(err => {
            console.log(err)
          })
      })
    },
    promiseCopy(data) {
      const params = {
        processDefinitionKey: data.processDefinitionKey,
        processInstanceId: data.processInstanceId,
        userIds: this.form.copyIds,
        type: 0,
        processActiveId: ''
      }
      this.$api.approval.proctaskManual(params).then(res => {
        this.saveTreeLoading = false
        this.loading = false
        if (res.success) {
          this.$notify.success('提交审核成功')
          this.outerVisible = false
          this.goBack()
        } else {
          this.$notify.error(res.returnMsg)
        }
      })
    },
    saveAppoint() {
      this.promiseApprove()
    },
    setOptions(item, label, value = true) {
      item.options[label] = value
      if (item.type === 'childForm') {
        item.tableColumns.forEach(i => {
          i.options[label] = value
        })
      }
      if (item.type === 'grid') {
        item.columns.forEach(i => {
          i.list.forEach(j => {
            j.options[label] = value
          })
        })
      }
    },
    changeTermState(target) {
      const termList = target.termList || []
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
      })
    },
    handleDetailsChange(field, value, data) {
      //   console.log(field, value, data)
    },
    goBack() {
      const path = this.formInfo.form || '/ApprovalManagement/FormManage'
      const key = this.formInfo.tabs ? `?tabs=${this.formInfo.tabs}` : ''
      this.$router.push(path + key)
    }
  }
}
</script>
<style lang="scss" scoped>
.button {
  display: flex;
  justify-content: center;
  margin-top: 20px;

  .button-container {
    margin-right: 10px
  }
}

.cus-dialog-container, .dialog-container {
  display: flex;
  min-height: calc(100vh - 142px);
  background: #f1f1f1;

  .form-container {
    width: 100%;
    background-color: #fff;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 50px 20px 20px;
  }

  .container-max {
    max-width: calc(100% - 360px);
  }

  .dialog-footer {
    display: flex;
    justify-content: center;
  }
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
  margin-bottom: 20px;
}

.dialog-container ::v-deep {
  label {
    margin-top: 1px;
    margin-bottom: 0px;
  }

  .dialog-tag {
    padding: 10px 0 0 10px;
    border: 1px solid #cccccc;
    border-radius: 4px;
    margin-bottom: 15px;

    .el-tag {
      margin: 0 10px 10px 0;
    }
  }

  .el-tree {
    .el-tree-node {
      // height: 52px;
      // line-height: 52px;
      .el-tree-node__content {
        height: 32px;
        line-height: 32px;
        border-bottom: 1px solid #f0f0f0;

        &.is-current,
        &:hover {
          background-color: #F8F8F8;

          .el-dropdown-link {
            visibility: inherit;
          }
        }

        .el-checkbox {
          margin-bottom: 0;
        }
      }

      .el-tree-node.is-current:hover {
        background-color: #F8F8F8;
      }

      .el-tree-node:hover {
        //   background-color: #EFEFEF;
      }
    }
  }

  .dialog-tree {
    padding: 10px;
    border: 1px solid #cccccc;
    border-radius: 4px;
    height: 45vh !important;
    display: flex;
    flex-direction: column;

    .el-tree-node__content {
      border-bottom: none;
    }
  }

  .dialog-button {
    text-align: center;
    margin: 10px 0;
  }
}
</style>
