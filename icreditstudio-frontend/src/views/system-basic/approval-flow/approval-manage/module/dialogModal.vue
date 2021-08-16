<template>
  <div v-loading.fullscreen.lock="loading">
    <div class="common-dialog">
      <el-dialog
        v-if="tferDialogVisible"
        :title="type === 'manual' ? '选择抄送人' : '选择指派人'"
        :visible.sync="tferDialogVisible"
        width="500px"
        height="187px"
        :close-on-click-modal="false"
      >
        <el-form ref="tferFormRef" :model="tferForm" :rules="tferRules" label-width="100px">
          <el-form-item label="请选择：" prop="tranferuserIds">
            <el-select
              v-model="tferForm.tranferuserIds"
              filterable
              remote
              clearable
              size="small"
              style="width: 100%;"
              placeholder="请输入姓名、账号或手机号"
              :remote-method="remoteMethod"
              :multiple="type === 'manual'"
              @change="remoteselect"
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
        <j-form-preview
          v-if="hasJson"
          ref="preview"
          class="cus-dialog"
          style="width:100%"
          insite="true"
          :data="dialoForm"
          :value="dialoModels"
          :remote="remoteFuncs"
          @on-change="handleDialoChange"
        />
        <span slot="footer" class="dialog-footer">
          <el-button
            type="primary"
            class-name="dialog-footer-commit"
            @click="distributionOrder"
          >确定</el-button>
          <el-button
            class-name="dialog-footer-cannel"
            @click="tferDialogVisible = false"
          >取消</el-button>
        </span>
      </el-dialog>
      <el-dialog
        v-loading="backLoading"
        title="提示"
        :visible.sync="backVisible"
        width="30%"
        :close-on-click-modal="false"
      >
        <el-form ref="recallForm" :model="recallForm" label-width="100px">
          <el-form-item label="撤回原因：" prop="message">
            <el-input
              v-model="recallForm.message"
              type="textarea"
              :rows="2"
              placeholder="请输入内容"
            />
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" type="primary" @click="saveRecall">确 定</el-button>
          <el-button size="small" @click="backVisible = false">取 消</el-button>
        </span>
      </el-dialog>
      <el-dialog v-if="outerVisible" title="提交审核" :visible.sync="outerVisible" width="500px" :close-on-click-modal="false">
        <el-form ref="form" :model="form" label-width="100px">
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
          <el-form-item v-if="operationInfo.manual" label="抄送人(可空)" prop="copyInfos">
            <el-select
              v-model="form.copyIds"
              filterable
              remote
              clearable
              size="small"
              style="width: 100%;"
              placeholder="请输入姓名、账号或手机号"
              :remote-method="remoteMethodAppoint"
              multiple
              @focus="handleFocus"
            >
              <el-option
                v-for="item in optionsAppoint"
                :key="item.value"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveAppoint">确定</el-button>
          <el-button @click="outerVisible = false">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import {
  proctaskRecall,
  procdefQueryDetail,
  proctaskManualCC,
  userFilterMe,
  proctaskAssignStartUsers,
  proctaskAssign
} from '@/api/system-basic/approval-list'

import {
  initiateApprovalSubmit,
  initiateApprovalTaskReplay
} from '@/api/system-basic/approval-flow'

import { debounce } from '../utils/index.js'

export default {
  name: 'DialogModal',
  props: {
    activeName: {
      type: String,
      default: 'initiateApproval'
    }
  },
  data() {
    return {
      loading: false,
      tferForm: {
        value: [],
        tranferuserIds: []
      },
      options: [],
      optionsAppoint: [],
      optionsAssign: [],
      tferDialogVisible: false,
      backVisible: false,
      backLoading: false,
      recallForm: {
        message: ''
      },
      type: 'manual',
      hasJson: true,
      dialoForm: {
        list: [],
        config: {
          labelWidth: 100,
          labelPosition: 'right',
          size: 'small',
          isBackEdge: false
        }
      },
      tferRules: {
        tranferuserIds: [
          { required: true, message: '请选择用户', trigger: 'blur' }
        ]
      },
      dialoModels: {},
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
        upload_callback(response, file, fileList) {
          console.log('callback', response, file, fileList)
        }
      },
      treeLoading: false,
      roleTree: [],
      roleList: [],
      form: {
        approveInfos: [],
        approveIds: [],
        copyInfos: [],
        copyIds: []
      },
      filterTree: '',
      operationInfo: {
        appoint: false, // 是否有指定审批人
        manual: false
      },
      outerVisible: false,
      innerVisible: false
    }
  },
  computed: {
    ...mapGetters({
      formInfo: 'approval-form/formInfo',
      userData: 'user/userInfo'
    })
  },
  watch: {
    filterTree(val) {
      this.$refs.roleTree.filter(val)
    }
  },
  methods: {
    ...mapActions('approval-form', ['setFormInfo']),
    startProcinst(data) {
      this.loading = true
      // const params = {
      //   formKey: this.formInfo.key,
      //   values: data,
      //   modelType: 2
      // }
      // ruleFormInfo(params)
      //   .then(res => {
      //     if (res.success) {
      const params = {
        formKey: this.formInfo.key,
        values: {
          ...data
        },
        recordId: this.formInfo.recordId
      }
      if (this.form.approveIds.length) {
        params.values.nextApprovalUserIds = this.form.approveIds
      }
      initiateApprovalSubmit(params)
        .then(res => {
          const formInfo = {
            ...this.formInfo,
            ...res.data
          }
          this.setFormInfo(formInfo)
            .then(() => {
              if (this.form.copyIds.length && this.operationInfo.manual) {
                this.promiseCopy(data)
              } else {
                this.$notify.success('提交审核成功')
                this.outerVisible = false
                this.$emit('init')
              }
            })
        })
        .finally(() => {
          this.loading = false
        })
      //   } else {
      //     this.$notify.error(res.returnMsg)
      //     this.loading = false
      //   }
      // })
      // .catch(err => {
      //   this.loading = false
      //   console.log(err)
      // })
    },
    promiseApprove(data) {
      this.loading = true
      const params = {
        processInstanceId: this.formInfo.processInstanceId,
        message: '',
        values: {
          ...data
        }
      }
      if (this.form.approveIds.length) {
        params.values.nextApprovalUserIds = this.form.approveIds
      }
      initiateApprovalTaskReplay(params)
        .then(() => {
          if (this.form.copyIds.length && this.operationInfo.manual) {
            this.promiseCopy(data)
          } else {
            this.$notify.success('提交审核成功')
            this.outerVisible = false
            this.$emit('init')
          }
        })
        .catch(err => {
          console.log(err)
        })
        .finally(() => {
          this.loading = false
        })
    },
    // TODO
    /* eslint-disable */
    distributionOrder() {
      this.$refs.tferFormRef.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.type === 'manual') {
            const params = {
              processDefinitionKey: this.formInfo.processDefinitionKey,
              processInstanceId: this.formInfo.processInstanceId,
              userIds: this.tferForm.tranferuserIds,
              type: this.activeName === 'sponsor' ? 0 : 1,
              processActiveId: this.formInfo.taskDefinitionKey
            }
            proctaskManualCC(params).then(res => {
              this.loading = false
              if (res.success) {
                this.$notify.success('抄送成功')
                this.tferDialogVisible = false
                this.tferForm.value = []
                this.tferForm.tranferuserIds = []
                this.$emit('carbon')
              } else {
                this.$notify.error(res.returnMsg)
              }
            })
          } else {
            this.$refs.preview.getData()
              .then(res => {
                this.dialoForm.list.forEach(i => {
                  if (res[i.model] && i.type !== 'grid') {
                    i.options.defaultValue = res[i.model]
                  }
                  if (i.type === 'grid') {
                    i.columns.forEach(j => {
                      j.list.forEach(k => {
                        if (res[k.model]) {
                          k.options.defaultValue = res[k.model]
                        }
                      })
                    })
                  }
                })
                const params = {
                  taskId: this.formInfo.taskId,
                  userId: this.tferForm.tranferuserIds,
                  message: JSON.stringify(this.dialoForm)
                }
                proctaskAssign(params).then(res => {
                  this.loading = false
                  if (res.success) {
                    this.$notify.success('指派成功')
                    this.tferDialogVisible = false
                    this.tferForm.value = []
                    this.tferForm.tranferuserIds = []
                    this.$emit('init')
                  } else {
                    this.$notify.error(res.returnMsg)
                  }
                })
              })
          }
        } else {
          this.$notify.error('请检查参数')
        }
      })
    },
    handleDialoChange(field, value, data) {
      console.log(field, value, data)
    },
    saveRecall() {
      this.$confirm('是否撤回表单?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.backLoading = true
          this.loading = true
          const str = `{"list":[{"type":"textarea","icon":"icon-textarea","options":{"width":"100%","defaultValue":"${this.recallForm.message}","required":false,"disabled":false,"regexp":"partten","pattern":"","errorMsg":"","placeholder":"","hiddenLabel":false,"readonly":false,"isPartten":true,"remoteFunc":"func_1597391110000_80095"},"name":"撤回原因","key":"1597391110000_80095","model":"textarea_1597391110000_80095"}],"config":{"labelWidth":100,"labelPosition":"right","size":"small"}}`
          const params = {
            message: str,
            processInstanceId: this.formInfo.processInstanceId
          }
          proctaskRecall(params).then(res => {
            this.loading = false
            if (res.success) {
              this.$notify.success('撤回成功')
              this.backLoading = false
              this.backVisible = false
              this.$emit('init')
            } else {
              this.backLoading = false
              this.$notify.error(res.returnMsg)
            }
            this.recallForm.message = ''
          })
        })
        .catch(() => {
          this.backLoading = false
          this.loading = false
        })
    },
    setDialoForm() {
      this.hasJson = !!JSON.parse(JSON.parse(this.formInfo.description).assignForm).list.length
      this.dialoForm = JSON.parse(JSON.parse(this.formInfo.description).assignForm)
      this.remoteMethod('')
      this.tferDialogVisible = true
    },
    setManual() {
      this.hasJson = false
      this.tferDialogVisible = true
      this.type = 'manual'
      this.remoteMethod('')
    },
    remoteselect() {
      // this.tferForm.tranferuserIds = this.options .filter(i => this.tferForm.value.indexOf(i.value) !== -1) .map(j => j.id)
      this.$refs.tferFormRef.clearValidate('tranferuserIds')
    },
    handleFocus() {
      this.optionsAppoint = []
    },
    promiseCopy(data) {
      const params = {
        processDefinitionKey: this.formInfo.processDefinitionKey,
        processInstanceId: this.formInfo.processInstanceId,
        userIds: this.form.copyIds,
        type: 0,
        processActiveId: ''
      }
      proctaskManual(params).then(res => {
        if (res.success) {
          this.$notify.success('提交审核成功')
          this.outerVisible = false
          this.$emit('init')
        } else {
          this.$notify.error(res.returnMsg)
        }
      })
    },
    saveAppoint() {
      this.$emit('dialogAgainStart', false)
      //   this.promiseApprove()
    },
    getTaskDetail() {
      const params = {
        procdefKey: this.formInfo.processDefinitionKey || this.formInfo.procdefKey
      }
      procdefQueryDetail(params)
        .then(res => {
          const document = JSON.parse(res.data.document)
          if (document) {
            this.operationInfo.appoint = document.isAppoint
            this.operationInfo.manual = document.isManual
            if (this.operationInfo.appoint || this.operationInfo.manual) {
              this.outerVisible = true
            } else {
              this.$emit('dialogAgainStart', true)
            }
          }
        })
    },
    setOptions(item, label) {
      item.options[label] = true
      if (item.type === 'childForm') {
        item.tableColumns.forEach(i => {
          i.options[label] = true
        })
      }
      if (item.type === 'grid') {
        item.columns.forEach(i => {
          i.list.forEach(j => {
            j.options[label] = true
          })
        })
      }
    },
    remoteMethodAppoint: debounce(function(query) {
      this.optionsAppoint = []
      if (query !== '') {
        const params = {
          name: query
        }
        userFilterMe(params).then(res => {
          if (res.success) {
            res.data.forEach(item => {
              item.value = `${item.platformUserId}\xa0\xa0\xa0${item.name}`
            })
            this.optionsAppoint = res.data
          }
        })
      } else {
        this.optionsAppoint = []
      }
    }),
    remoteMethodAssign: debounce(function(query) {
      this.optionsAssign = []
      if (query !== '') {
        const params = {
          param: query,
          startUserId: this.userData.id
        }
        proctaskAssignStartUsers(params).then(res => {
          res.data.forEach(item => {
            item.value = `${item.platformUserId}\xa0\xa0\xa0${item.name}`
          })
        })
      } else {
        this.optionsAssign = []
      }
    }),
    // 模糊查询用户
    remoteMethod: debounce(function(query) {
      this.options = []
      if (query !== '') {
        // if (this.type === 'manual') {
        const params = {
          name: query
        }
        userFilterMe(params).then(res => {
          res.data.forEach(item => {
            item.value = `${item.id}\xa0\xa0\xa0${item.name}`
          })
          this.options = res.data
        })
        // } else {
        //   const params = {
        //     param: query,
        //     processInstanceId: this.formInfo.processInstanceId
        //   }
        //   proctaskAssignUsers(params).then(res => {
        //     if (res.success) {
        //       res.data.forEach(item => {
        //         item.value =
        //         `${item.platformUserId
        //         }\xa0\xa0\xa0${
        //         item.name}`
        //       })
        //       this.options = res.data
        //     }
        //   })
        // }
      } else {
        this.options = []
      }
    })
  }
}
</script>

<style lang="scss" scoped>
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
          background-color: #f8f8f8;

          .el-dropdown-link {
            visibility: inherit;
          }
        }

        .el-checkbox {
          margin-bottom: 0;
        }
      }

      .el-tree-node.is-current:hover {
        background-color: #f8f8f8;
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

.dialog-footer {
  display: flex;
  align-items: center;
  justify-content: center;
}

.list-container {
  display: flex;
  flex-wrap: wrap;
  margin: 0 auto;

  .list-item {
    width: 250px;
    height: 60px;
    border: 1px solid #ebebeb;
    border-radius: 4px;
    margin: 10px 20px 10px 0;
    display: flex;
    font-size: 18px;
    align-items: center;
    cursor: pointer;

    img {
      width: 43px;
      height: 43px;
      margin: 0 17px;
    }

    .title {
      width: 170px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      font-size: 18px;
    }
  }
}
</style>
