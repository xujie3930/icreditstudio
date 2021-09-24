<!--
 * @Author: lizheng
 * @Description: 新增同步任务 - 同步任务调度
 * @Date: 2021-09-02
-->
<template>
  <div class="add-task-page">
    <Back @on-jump="handleBackClick" />
    <div class="add-task">
      <HeaderStepBar :cur-step="3" />

      <el-form
        class="add-task-content"
        ref="taskForm"
        v-loading="detailLoading"
        :model="taskForm"
        :rules="taskRules"
      >
        <el-form-item style="width:100%">
          <div class="content-item">
            <h3 class="title">通道控制</h3>
          </div>
        </el-form-item>

        <el-form-item
          label-width="35%"
          label="任务期望最大并发数"
          prop="maxThread"
        >
          <el-select
            style="width: 500px"
            v-model="taskForm.maxThread"
            placeholder="请选择最大并发数"
          >
            <el-option label="2" :value="2"></el-option>
            <el-option label="3" :value="3"></el-option>
            <el-option label="4" :value="4"></el-option>
            <el-option label="5" :value="5"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label-width="35%" label="同步速率" prop="syncRate">
          <el-radio-group v-model="taskForm.syncRate">
            <el-radio :label="0">不限流</el-radio>
            <el-radio :label="1">限流</el-radio>
          </el-radio-group>
        </el-form-item>

        <transition name="fade">
          <el-form-item
            label-width="35%"
            v-if="taskForm.syncRate"
            label="限流速率"
            prop="limitRate"
          >
            <el-input
              clearable
              style="width: 500px"
              placeholder="请输入限流的条数"
              v-model.trim="taskForm.limitRate"
            >
              <span slot="suffix" class="suffix-label">条/s</span>
            </el-input>
          </el-form-item>
        </transition>

        <el-form-item style="width:100%">
          <div class="content-item">
            <h3 class="title">调度设置</h3>
          </div>
        </el-form-item>

        <el-form-item label-width="35%" label="调度类型" prop="scheduleType">
          <el-radio-group v-model="taskForm.scheduleType">
            <el-radio :label="1">周期执行</el-radio>
            <el-radio :label="0">手动执行</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item
          v-if="taskForm.scheduleType"
          label-width="35%"
          label="Cron"
          prop="cron"
        >
          <el-input
            style="width: 500px"
            placeholder="请输入内容"
            v-model="taskForm.cron"
          >
            <div slot="append" class="cron-suffix" @click="handleOpenCron">
              <i class="el-icon-open "></i>
            </div>
          </el-input>
        </el-form-item>
      </el-form>

      <footer class="footer-btn-wrap">
        <el-button
          class="btn"
          @click="$router.push('/data-manage/add-build?opType=edit')"
        >
          上一步
        </el-button>
        <el-button
          class="btn"
          :loading="settingBtnLoading"
          @click="handleSaveSetting(3, 'settingBtnLoading')"
        >
          保存设置
        </el-button>
        <el-button
          class="btn"
          type="primary"
          :loading="publishLoading"
          @click="handleSaveSetting(4, 'publishLoading')"
        >
          发布
        </el-button>
      </footer>
    </div>

    <Cron ref="cron" v-model="taskForm.cron" />
  </div>
</template>

<script>
import HeaderStepBar from './header-step-bar'
import Back from '@/views/icredit/components/back'
import Cron from '@/components/cron'
import API from '@/api/icredit'
import { mapState } from 'vuex'
import { deepClone } from '@/utils/util'

export default {
  components: { HeaderStepBar, Back, Cron },

  data() {
    const verifyLimitRate = (rule, value, cb) => {
      const num = parseInt(value, 10) || 0
      this.taskForm.limitRate = num
      cb()
    }

    return {
      detailLoading: false,
      settingBtnLoading: false,
      publishLoading: false,

      taskForm: {
        maxThread: 2,
        limitRate: '',
        syncRate: 1,
        scheduleType: 1,
        cron: ''
      },

      taskRules: {
        maxThread: [
          { required: true, message: '必填项不能为空', trigger: 'change' }
        ],
        syncRate: [
          { required: true, message: '必填项不能为空', trigger: 'change' }
        ],
        scheduleType: [
          { required: true, message: '必填项不能为空', trigger: 'change' }
        ],
        period: [
          { required: true, message: '必填项不能为空', trigger: 'change' }
        ],
        limitRate: [{ validator: verifyLimitRate, trigger: 'blur' }],
        cron: [
          {
            required: true,
            message: '必填项不能为空',
            trigger: ['blur', 'change']
          }
        ]
      }
    }
  },

  computed: {
    ...mapState('user', ['workspaceId'])
  },

  created() {
    this.initPage()
  },

  methods: {
    initPage() {
      const beforeStepForm = this.$ls.get('taskForm') || {}
      this.taskForm = deepClone({ ...this.taskForm, ...beforeStepForm })
      // 编辑
      this.taskForm.taskId && this.getDetailData()
    },

    // 打开选择CRON表达式的弹窗
    handleOpenCron() {
      this.$refs.cron.open()
    },

    // 保存设置或发布
    handleSaveSetting(callStep, loading) {
      const params = {
        workspaceId: this.workspaceId,
        ...this.taskForm
      }
      params.callStep = callStep
      this.$refs.taskForm.validate(valid => {
        if (valid) {
          this[loading] = true
          API.dataSyncAdd(params)
            .then(({ success, data }) => {
              if (success && data) {
                this.taskForm.taskId = data.taskId
                this.$notify.success({
                  title: '操作结果',
                  message: callStep === 3 ? '保存设置成功！' : '发布成功！'
                })
                if (callStep === 4) {
                  this.$router.push('/data-manage/data-sync')
                  this.$ls.remove('taskForm')
                }
              }
            })
            .finally(() => {
              this[loading] = false
            })
        }
      })
    },

    // 返回提示
    handleBackClick() {
      this.$ls.remove('taskForm')
      this.$router.push('/data-manage/data-sync')
    },

    // 编辑情况下获取详情
    getDetailData() {
      this.detailLoading = true
      API.dataSyncDispatchDetial({ taskId: this.taskForm.taskId })
        .then(({ success, data }) => {
          if (success && data) {
            for (const [key, value] of Object.entries(data)) {
              console.log(key, value, typeof value)
              this.taskForm[key] = value
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
}

.add-task-content {
  width: 100%;
  margin-top: 34px;

  .content-item {
    @include flex;
    width: 100%;
    border-bottom: 1px solid #f0f0f0;
  }

  .title {
    position: relative;
    font-size: 16px;
    font-family: PingFangSC, PingFangSC-Medium;
    font-weight: 500;
    text-align: left;
    color: #000;
    padding-bottom: 8px;

    &::after {
      content: '';
      position: absolute;
      left: 0;
      bottom: -2px;
      width: 70px;
      height: 4px;
      background: #1890ff;
      border-radius: 2px;
    }
  }

  .suffix-label {
    margin-top: 2px;
  }

  .cron-suffix {
    cursor: pointer;
    padding: 0;
  }

  .fade-enter-active,
  .fade-leave-active {
    transition: opacity 0.3s ease;
  }

  .fade-enter,
  .fade-leave-to {
    opacity: 0;
  }
}
</style>
