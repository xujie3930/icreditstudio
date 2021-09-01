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
          <el-input v-model="addTaskForm.taskName"></el-input>
        </el-form-item>

        <el-form-item label="任务启用" prop="enable">
          <el-switch
            v-model="addTaskForm.enable"
            :inactive-value="0"
            :active-value="1"
          ></el-switch>
        </el-form-item>

        <el-form-item label="创建方式" prop="createMode">
          <el-select v-model="addTaskForm.createMode" placeholder="请选择">
            <el-option
              v-for="item in createModeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="工作空间描述" prop="descriptor">
          <el-input
            clearable
            show-word-limit
            :maxlength="250"
            :rows="4"
            type="textarea"
            v-model="addTaskForm.taskDescribe"
            placeholder="请输入任务描述"
          ></el-input>
        </el-form-item>
      </el-form>

      <footer class="footer-btn-wrap">
        <el-button class="btn" @click="saveSetting('addTaskForm')">
          保存设置
        </el-button>
        <el-button class="btn" type="primary" @click="nextStep('addTaskForm')"
          >下一步</el-button
        >
      </footer>
    </div>
  </div>
</template>

<script>
import Back from '@/views/icredit/components/back'
import HeaderStepBar from './header-step-bar'

export default {
  components: { Back, HeaderStepBar },

  data() {
    return {
      createModeOptions: [
        { label: '可视化', value: 0 },
        { label: 'sql', value: 1 }
      ],
      addTaskForm: {},
      addTaskFormRules: {
        taskName: [
          { required: true, message: '必填项不能为空', trigger: 'blur' }
        ],
        enable: [
          { required: true, message: '必填项不能为空', trigger: 'blur' }
        ],
        createMode: [
          { required: true, message: '必填项不能为空', trigger: 'blur' }
        ]
      }
    }
  },

  methods: {
    saveSetting() {},
    nextStep() {}
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

    .btn {
      width: 150px;
      height: 40px;
      border-radius: 4px;
    }
  }
}
</style>
