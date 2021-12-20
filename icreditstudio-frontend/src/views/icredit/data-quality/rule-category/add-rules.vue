<!--
 * @Description: 质量规则-新增/编辑自定义规则
 * @Date: 2021-10-26
-->

<template>
  <div class="add-rules-page">
    <div class="add-rules" v-loading="detailLoading">
      <el-form
        class="add-rules-form icredit-form"
        :model="taskForm"
        :rules="addTaskFormRules"
        ref="taskForm"
        label-width="100px"
      >
        <el-row>
          <el-col :span="8">
            <el-form-item label="规则名称" prop="taskName">
              <el-input
                v-model.trim="taskForm.taskName"
                placeholder="请输入规则名称"
                clearable
                :maxlength="50"
                show-word-limit
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="规则强度" prop="createMode">
              <el-select
                v-model="taskForm.createMode"
                placeholder="请选择创建方式"
                style="width:100%"
              >
                <el-option
                  v-for="item in createModeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="规则对象" prop="createMode">
              <el-select
                v-model="taskForm.createMode"
                placeholder="请选择创建方式"
                style="width:100%"
              >
                <el-option
                  v-for="item in createModeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="规则类型">
              <span>自定义规则</span>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="规则内容" prop="taskDescribe">
          <el-input
            clearable
            show-word-limit
            :maxlength="250"
            :rows="4"
            type="textarea"
            v-model.trim="taskForm.taskDescribe"
            placeholder="请在此输入规则的校验内容或者SQL自定义函数"
          ></el-input>
        </el-form-item>

        <el-form-item prop="taskDescribe">
          <el-button type="primary">规则规范性校验</el-button>
        </el-form-item>

        <el-form-item label="规则描述" prop="taskDescribe">
          <el-input
            clearable
            show-word-limit
            :maxlength="250"
            :rows="4"
            type="textarea"
            v-model.trim="taskForm.taskDescribe"
            placeholder="请在此输入规则的描述信息"
          ></el-input>
        </el-form-item>
      </el-form>

      <footer class="footer-btn-wrap">
        <el-button
          class="btn"
          :loading="saveSettingLoading"
          @click="handleCancelClick"
        >
          取消
        </el-button>
        <el-button class="btn" type="primary">
          提交
        </el-button>
      </footer>
    </div>
  </div>
</template>

<script>
import API from '@/api/icredit'
import { mapState } from 'vuex'
import { verifySpecialStr } from '@/utils/validate'

export default {
  data() {
    return {
      step: '',
      opType: '',
      detailLoading: false,
      saveSettingLoading: false,
      createModeOptions: [
        { label: '可视化', value: 0 },
        { label: 'SQL', value: 1 }
      ],
      taskForm: {
        taskId: undefined,
        taskName: '',
        enable: 1,
        createMode: 0,
        taskDescribe: ''
      },
      addTaskFormRules: {
        taskName: [
          { required: true, message: '任务名不能为空', trigger: 'blur' },
          { validator: this.verifyTaskname, trigger: 'blur' }
        ],
        enable: [
          { required: true, message: '任务启用不能为空', trigger: 'blur' }
        ],
        createMode: [
          { required: true, message: '创建方式不能为空', trigger: 'change' }
        ]
      }
    }
  },

  computed: {
    ...mapState('user', ['workspaceId'])
  },

  mounted() {
    this.initPage()
  },

  methods: {
    initPage() {
      this.opType = this.$route.query?.opType || 'add'
      this.step = this.$route.query?.opType || ''
      this.opType === 'add' && !this.step && this.$ss.remove('taskForm')
      this.taskForm = this.$ss.get('taskForm') || this.taskForm
      // 编辑的情况下 taskId 有值
      const { taskId, taskName } = this.taskForm
      this.taskForm.taskId = taskId || this.$route.query?.taskId
      this.taskForm.taskId
        ? this.getDetailData()
        : this.autoGenerateTaskName(taskName)
    },

    handleCancelClick() {
      this.$router.push('/data-quality/rule-category/quality-rules')
    },

    handleBackClick() {
      // 返回提示
      this.$ss.remove('taskForm')
      this.$router.push('/data-manage/data-sync')
    },

    // 编辑情况下获取详情
    getDetailData() {
      this.detailLoading = true
      API.dataSyncDefineDetial({ taskId: this.taskForm.taskId })
        .then(({ success, data }) => {
          if (success && data) {
            for (const [key, value] of Object.entries(data)) {
              this.taskForm[key] = value
            }
          }
        })
        .finally(() => {
          this.detailLoading = false
        })
    },

    // 自动生成任务名规则
    autoGenerateTaskName(name) {
      if (name) return false
      const prefixStrArr = ['mysql', 'oracle', 'postSql', 'excel']
      const suffixStrArr = ['hive', 'hdfs']
      const preNum = Math.floor(Math.random() * 10)
      const sufNum = Math.floor(Math.random() * 10)
      const preIdx = preNum > 2 ? 3 : preNum
      const sufIdx = sufNum > 0 ? 1 : sufNum
      this.taskForm.taskName = `${prefixStrArr[preIdx]}→${suffixStrArr[sufIdx]}`
    },

    // 保存设置
    saveSetting(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          const params = {
            workspaceId: this.workspaceId,
            callStep: 1,
            ...this.taskForm
          }
          this.saveSettingLoading = true
          API.dataSyncAdd(params)
            .then(({ success, data }) => {
              if (success && data) {
                this.taskForm.taskId = data.taskId
                this.$notify.success({ title: '操作结果', message: '保存成功' })
              }
            })
            .finally(() => {
              this.saveSettingLoading = false
            })
        }
      })
    },

    // 下一步
    nextStep(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          const { createMode } = this.taskForm
          this.$ss.set('taskForm', this.taskForm)
          this.$router.push({
            path: '/data-manage/add-build',
            query: { createMode, opType: this.opType, step: 'first' }
          })
        }
      })
    },

    // 任务名称校验
    verifyTaskname(rule, value, cb) {
      const nVal = value.replaceAll('→', '')
      verifySpecialStr(rule, nVal, cb)
    }
  }
}
</script>

<style lang="scss" scoped>
.add-rules-page {
  position: relative;
  width: 100%;
  min-height: calc(100vh - 126px);
  background-color: #fff;
}

.add-rules {
  @include flex(column, flex-start, flex-start);
  width: 100%;
  padding-top: 50px;

  &-form {
    width: 100%;
    padding-right: 31px;

    /deep/ .el-form-item {
      margin-bottom: 20px;

      .el-form-item__label {
        font-size: 14px;
        font-family: PingFangSC, PingFangSC-Regular;
        font-weight: 400;
        text-align: right;
        color: #262626;
      }
    }
  }

  .footer-btn-wrap {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    margin-top: 20px;
    height: 100px;
    line-height: 100px;
    text-align: center;
    color: #262626;
    border-radius: 4px;
    z-index: 9;

    .btn {
      width: 150px;
      height: 40px;
      border-radius: 4px;
    }
  }
}
</style>
