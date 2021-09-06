<!--
 * @Author: lizheng
 * @Description: 新增同步任务 - 同步任务定义
 * @Date: 2021-08-31
-->
<template>
  <div class="add-task-page">
    <Back path="/data-manage/data-sync" />
    <div class="add-task">
      <HeaderStepBar />

      <el-form
        class="add-task-form"
        :model="addTaskForm"
        :rules="addTaskFormRules"
        ref="addTaskForm"
        label-width="100px"
      >
        <el-form-item label="任务名" prop="taskName">
          <el-input
            v-model.trim="addTaskForm.taskName"
            placeholder="请选择任务名"
            clearable
            :maxlength="14"
            show-word-limit
          ></el-input>
        </el-form-item>

        <el-form-item label="任务启用" prop="enable">
          <el-radio-group v-model="addTaskForm.enable">
            <el-radio :label="0">是</el-radio>
            <el-radio :label="1">否</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="创建方式" prop="createMode">
          <el-select
            v-model="addTaskForm.createMode"
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

        <el-form-item label="工作空间描述" prop="taskDescribe">
          <el-input
            clearable
            show-word-limit
            :maxlength="250"
            :rows="4"
            type="textarea"
            v-model.trim="addTaskForm.taskDescribe"
            placeholder="请输入任务描述"
          ></el-input>
        </el-form-item>
      </el-form>

      <footer class="footer-btn-wrap">
        <el-button
          class="btn"
          :loading="saveSettingLoading"
          @click="saveSetting('addTaskForm')"
        >
          保存设置
        </el-button>
        <el-button class="btn" type="primary" @click="nextStep('addTaskForm')">
          下一步
        </el-button>
      </footer>
    </div>
  </div>
</template>

<script>
import Back from '@/views/icredit/components/back'
import HeaderStepBar from './header-step-bar'
import API from '@/api/icredit'
import { mapState } from 'vuex'

export default {
  components: { Back, HeaderStepBar },

  data() {
    return {
      saveSettingLoading: false,
      createModeOptions: [
        { label: '可视化', value: 0 },
        { label: 'SQL', value: 1 }
      ],
      addTaskForm: {
        taskName: '',
        enable: 0,
        createMode: 0,
        taskDescribe: ''
      },
      addTaskFormRules: {
        taskName: [
          { required: true, message: '任务名不能为空', trigger: 'blur' }
          // { validator: this.verifyTaskname, trigger: 'blur' }
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

  created() {
    this.autoGenerateTaskName()
  },

  methods: {
    // 自动生成任务名规则
    autoGenerateTaskName() {
      const prefixStrArr = ['mysql', 'oracle', 'postSql', 'excel']
      const suffixStrArr = ['hive', 'hdfs']
      const preNum = Math.floor(Math.random() * 10)
      const sufNum = Math.floor(Math.random() * 10)
      const preIdx = preNum > 2 ? 3 : preNum
      const sufIdx = sufNum > 0 ? 1 : sufNum
      this.addTaskForm.taskName = `${prefixStrArr[preIdx]}→${suffixStrArr[sufIdx]}`
    },

    // 保存设置
    saveSetting(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          console.log(name)
          const params = {
            workspaceId: this.workspaceId,
            ...this.addTaskForm
          }
          this.saveSettingLoading = true
          API.dataSyncAdd(params)
            .then(({ success }) => {
              if (success) {
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
          console.log(name, valid)
          this.$router.push({
            path: '/data-manage/add-build',
            query: { createMode: this.addTaskForm.createMode }
          })
        }
      })
    },

    // 任务名称校验
    verifyTaskname(rule, value, cb) {
      // 特殊符号
      const regStr = /[`~!@#$%^&*()_\-+=<>?:"{}|,./;'\\[\]·~！@#￥%……&*（）——\-+={}|《》？：“”【】、；‘’，。、]/gi
      // 表情包
      const emojiRegStr = /[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f\u2026\u2022\u20ac\r\n]/gi
      const isValid = regStr.test(value) || emojiRegStr.test(value)
      if (isValid) {
        cb(new Error('该任务名称中包含不规范字符，请重新输入'))
      } else cb()
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
