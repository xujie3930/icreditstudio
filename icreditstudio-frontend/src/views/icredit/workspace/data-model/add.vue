<!--
 * @Author: lizheng
 * @Description: 新增或编辑表
 * @Date: 2021-10-09
-->
<template>
  <div class="add-task-page add-page">
    <div class="add-task" v-loading="detailLoading">
      <HeaderStepBar
        :cur-step="currentStep"
        :render-steps-config="renderStepsConfig"
      />

      <!-- 第一步 -->
      <template v-if="currentStep === 1">
        <p class="tip">
          注：新增表时需要先根据工作空间-数据源中已经连接的数据源创建数据库信息，即将新建的表放在某个数据库下。
        </p>
        <el-form
          class="add-task-form"
          :model="taskForm"
          :rules="addTaskFormRules"
          ref="taskForm"
          label-width="140px"
        >
          <el-form-item label="数据库创建方式" prop="createMode">
            <el-radio-group v-model="taskForm.createMode">
              <el-radio :label="1">已有数据库</el-radio>
              <el-radio :label="0">新建数据库</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="数据库类型" prop="dataType">
            <el-select
              disabled
              v-model="taskForm.dataType"
              placeholder="请选择数据库类型"
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

          <el-form-item
            v-if="taskForm.createMode"
            label="选择数据库名称"
            prop="taskName"
          >
            <el-select
              v-model="taskForm.taskName"
              placeholder="请选择数据库名称"
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

          <el-form-item v-else label="创建数据库名" prop="taskName">
            <el-input
              v-model.trim="taskForm.taskName"
              placeholder="请输入创建的数据库名"
              clearable
              :maxlength="50"
              show-word-limit
            ></el-input>
          </el-form-item>

          <el-form-item label="是否启用" prop="enable">
            <el-radio-group v-model="taskForm.enable">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </template>

      <!-- 第二步 -->
      <template v-else-if="currentStep === 2">
        <el-form
          class="add-task-form"
          :model="taskForm"
          :rules="addTaskFormRules"
          ref="taskForm"
          label-width="140px"
        >
          <el-form-item label="表英文名" prop="taskName">
            <el-input
              v-model.trim="taskForm.taskName"
              placeholder="请输入表名，字母开头，支持字母、数字和下划线"
              clearable
              :maxlength="50"
              show-word-limit
            ></el-input>
          </el-form-item>

          <el-form-item label="表中文名" prop="taskName">
            <el-input
              v-model.trim="taskForm.taskName"
              placeholder="请输入表中文名"
              clearable
              :maxlength="50"
              show-word-limit
            ></el-input>
          </el-form-item>

          <el-form-item label="是否启用" prop="enable">
            <el-radio-group v-model="taskForm.enable">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="生命周期" prop="dataType">
            <el-select
              disabled
              v-model="taskForm.dataType"
              placeholder="请选择数据库类型"
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

          <el-form-item label="使用方式" prop="taskName">
            <el-select
              v-model="taskForm.taskName"
              placeholder="请选择数据库名称"
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

          <el-form-item label="数据源描述" prop="taskDescribe">
            <el-input
              clearable
              show-word-limit
              :maxlength="250"
              :rows="4"
              type="textarea"
              v-model.trim="taskForm.taskDescribe"
              placeholder="请输入数据源描述"
            ></el-input>
          </el-form-item>
        </el-form>
      </template>

      <!-- 第三步 -->
      <template v-else>
        <div class="table-wrap">
          <el-button class="btn" type="primary">新增字段</el-button>
          <el-button class="btn">删除字段</el-button>
          <JTable
            ref="table"
            v-loading="tableLoading"
            :table-configuration="tableConfiguration"
            :table-data="tableData"
          >
          </JTable>
        </div>
      </template>

      <footer class="footer-btn-wrap">
        <el-button
          v-if="currentStep !== 1"
          class="btn"
          @click="handleJump('taskForm', 'previous')"
        >
          上一步
        </el-button>
        <el-button
          v-if="currentStep !== 3"
          class="btn"
          type="primary"
          @click="handleJump('taskForm', 'next')"
        >
          下一步
        </el-button>
        <el-button v-if="currentStep === 3" class="btn" type="primary">
          提交
        </el-button>
      </footer>
    </div>
  </div>
</template>

<script>
import HeaderStepBar from '@/views/icredit/components/steps'
import API from '@/api/icredit'
import { mapState } from 'vuex'
import { verifySpecialStr } from '@/utils/validate'
import tableConfiguration from '@/views/icredit/configuration/table/workspace-model-add'

export default {
  components: { HeaderStepBar },

  data() {
    return {
      tableConfiguration,
      tableLoading: false,
      tableData: [],
      currentStep: 1,
      renderStepsConfig: [
        {
          title: '数据库属性',
          description: '这是一段很长很长很长的描述性文字'
        },
        {
          title: '新增表属性',
          description: '这是一段很长很长很长的描述性文字'
        },
        { title: '表字段信息', description: '这是一段很长很长很长的描述性文字' }
      ],
      step: '',
      opType: '',
      detailLoading: false,
      saveSettingLoading: false,
      createModeOptions: [{ label: 'hive', value: 'hive' }],
      taskForm: {
        taskId: undefined,
        taskName: '',
        enable: 1,
        createMode: 1,
        dataType: 'hive',
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
        dataType: [
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
      this.opType === 'add' && !this.step && this.$ls.remove('taskForm')
      this.taskForm = this.$ls.get('taskForm') || this.taskForm
      // 编辑的情况下 taskId 有值
      const { taskId, taskName } = this.taskForm
      this.taskForm.taskId = taskId || this.$route.query?.taskId
      this.taskForm.taskId
        ? this.getDetailData()
        : this.autoGenerateTaskName(taskName)
    },

    handleBackClick() {
      // 返回提示
      this.$ls.remove('taskForm')
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
    handleJump(name, opType) {
      this.$refs[name].validate(valid => {
        if (valid) {
          if (opType === 'next') {
            this.currentStep += 1
          } else if (opType === 'previous') {
            this.currentStep -= 1
          }
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

  .tip {
    margin-top: 30px;
    font-size: 14px;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #999;
    line-height: 22px;
  }

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

.add-page {
  .table-wrap {
    width: 100%;
  }
}
</style>
