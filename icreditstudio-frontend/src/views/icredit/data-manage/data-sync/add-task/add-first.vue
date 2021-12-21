<!--
 * @Author: lizheng
 * @Description: 新增同步任务 - 同步任务定义
 * @Date: 2021-08-31
-->
<template>
  <div class="add-task-page">
    <div class="add-task" v-loading="detailLoading">
      <HeaderStepBar />

      <el-form
        class="add-task-form"
        :model="taskForm"
        :rules="addTaskFormRules"
        ref="taskForm"
        label-width="100px"
      >
        <el-form-item label="任务名" prop="taskName">
          <el-input
            v-model.trim="taskForm.taskName"
            placeholder="请输入任务名"
            clearable
            :maxlength="50"
            show-word-limit
          >
            <i
              v-if="veifyNameLoading"
              slot="suffix"
              class="el-icon-loading"
            ></i>
          </el-input>
        </el-form-item>

        <el-form-item label="任务启用" prop="enable">
          <el-radio-group v-model="taskForm.enable">
            <el-radio :label="0">是</el-radio>
            <el-radio :label="1">否</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="创建方式" prop="createMode">
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

        <el-form-item label="任务描述" prop="taskDescribe">
          <el-input
            clearable
            show-word-limit
            :maxlength="250"
            :rows="4"
            type="textarea"
            v-model.trim="taskForm.taskDescribe"
            placeholder="请输入任务描述"
          ></el-input>
        </el-form-item>
      </el-form>

      <footer class="footer-btn-wrap">
        <el-button class="btn" type="primary" @click="nextStep('taskForm')">
          下一步
        </el-button>
      </footer>
    </div>
  </div>
</template>

<script>
import HeaderStepBar from './header-step-bar'
import API from '@/api/icredit'
import { mapState } from 'vuex'
import { validStrSpecial } from '@/utils/validate'
import { debounce } from 'lodash'

export default {
  components: { HeaderStepBar },

  data() {
    this.verifySyncTaskName = debounce(this.verifySyncTaskName, 500)
    return {
      step: '',
      opType: '',
      oldName: '',
      detailLoading: false,
      veifyNameLoading: false,
      timerId: null,
      saveSettingLoading: false,
      createModeOptions: [
        { label: '可视化', value: 0 },
        { label: 'SQL', value: 1 }
      ],
      taskForm: {
        taskId: undefined,
        taskName: '',
        enable: 0,
        createMode: 0,
        taskDescribe: ''
      },
      addTaskFormRules: {
        taskName: [
          { required: true, message: '任务名不能为空', trigger: 'blur' },
          { validator: this.verifyTaskname, trigger: ['blur', 'change'] }
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

  props: {
    options: {
      type: Object,
      default: () => ({})
    }
  },

  computed: {
    ...mapState('user', ['workspaceId'])
  },

  watch: {
    options: {
      immediate: true,
      deep: true,
      handler(nVal) {
        if (nVal && nVal.oldName) {
          this.oldName = nVal.oldName ?? ''
        }
      }
    }
  },

  mounted() {
    this.initPage()
  },

  methods: {
    initPage() {
      // 编辑的情况下 taskId 有值
      const { taskId, taskName } = this.taskForm
      this.taskForm.taskId = taskId || this.$route.query?.taskId
      this.taskForm.taskId
        ? this.getDetailData()
        : this.autoGenerateTaskName(taskName)
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
          this.oldName = this.taskForm.taskName
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
    saveSetting() {
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
            this.$emit('change', 0, this.taskForm)
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
    nextStep(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.saveSetting()
          // const { createMode } = this.taskForm
          // this.$ss.set('taskForm', this.taskForm)
          this.$emit('change', 2, { ...this.taskForm })
          // this.$router.push({
          //   path: '/data-manage/add-build',
          //   query: { createMode, opType: this.opType, step: 'first' }
          // })
        }
      })
    },

    // 任务名称校验
    verifyTaskname(rule, value, cb) {
      const nVal = value.replaceAll('→', '')
      if (validStrSpecial(nVal)) {
        cb(new Error('该名称中包含不规范字符，请重新输入'))
      } else {
        this.verifySyncTaskName(rule, value, cb)
      }
    },

    // 验证是否已经存在同步任务名称
    verifySyncTaskName(rule, value, cb) {
      this.timerId = null
      if (
        (this.taskForm.taskId || this.opType === 'edit') &&
        this.oldName === value
      ) {
        cb()
      } else {
        this.veifyNameLoading = true
        API.dataSyncVerifyName({
          workspaceId: this.workspaceId,
          taskName: value
        })
          .then(({ success, data }) => {
            success && data?.isRepeat
              ? cb(new Error('该名称已存在，请重新输入'))
              : cb()
          })
          .finally(() => {
            this.timerId = setTimeout(() => {
              this.veifyNameLoading = false
            }, 300)
          })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.add-task-page {
  position: relative;
  width: 100%;
  min-height: calc(100vh - 126px);
  background-color: #fff;
}

.add-task {
  @include flex(column, flex-start);
  width: 100%;
  padding-top: 50px;

  &-form {
    width: 600px;
    margin: 0 20px;
    margin-top: 50px;
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
    border-top: 1px solid #e9e9e9;
    z-index: 9;

    .btn {
      width: 150px;
      height: 40px;
      border-radius: 4px;
    }
  }
}
</style>
