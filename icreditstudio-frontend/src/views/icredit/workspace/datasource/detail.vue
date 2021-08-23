<!--
 * @Description: 详情
 * @Date: 2021-08-20
-->

<template>
  <BaseDialog
    ref="baseDialog"
    title="数据源查看"
    width="600px"
    :hide-footer="true"
    @onClose="handleClose"
    @onConfirm="handleConfirm"
  >
    <el-form
      v-if="opType === 'view'"
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      :class="[opType === 'view' ? 'form-detail' : '']"
    >
      <template>
        <el-form-item label="数据源名称" prop="name">
          <span class="label-text"> 输入自定义数据源名称</span>
        </el-form-item>

        <el-form-item label="数据库名" prop="name">
          <span class="label-text"> 请输入数据库名</span>
        </el-form-item>

        <el-form-item label="IP" prop="name">
          <span class="label-text"> IPIPIP </span>
        </el-form-item>
        <el-form-item label="端口" prop="name">
          <span class="label-text"> 请输入端口</span>
        </el-form-item>

        <el-form-item label="启用" prop="resource">
          <span class="label-text"> 是 </span>
        </el-form-item>

        <el-form-item label="数据源描述" prop="desc">
          <span class="label-text"> 请输入数据源描述</span>
        </el-form-item>
      </template>
    </el-form>

    <el-form
      v-else
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      :class="[opType === 'view' ? 'form-detail' : '']"
    >
      <template>
        <el-form-item label="数据源名称" prop="name">
          <el-input
            show-word-limit
            :maxlength="15"
            v-model="ruleForm.name"
            placeholder="请输入自定义数据源名称"
          ></el-input>
        </el-form-item>

        <el-form-item label="数据库名" prop="name">
          <el-input
            v-model="ruleForm.name"
            placeholder="请输入数据库名"
          ></el-input>
        </el-form-item>

        <el-row>
          <el-col :span="12">
            <el-form-item label="IP" prop="name">
              <el-input v-model="ruleForm.name" placeholder="请输入IP">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="端口" prop="name">
              <el-input
                v-model="ruleForm.name"
                placeholder="请输入端口"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="启用" prop="resource">
          <el-radio-group v-model="ruleForm.resource">
            <el-radio label="是"></el-radio>
            <el-radio label="否"></el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="数据源描述" prop="desc">
          <el-input
            show-word-limit
            :maxlength="250"
            type="textarea"
            v-model="ruleForm.desc"
            placeholder="请输入数据源描述"
          ></el-input>
        </el-form-item>
      </template>
    </el-form>
  </BaseDialog>
</template>

<script>
import BaseDialog from '../components/dialog'

export default {
  components: { BaseDialog },
  data() {
    return {
      opType: 'view',
      dialogVisible: false,
      ruleForm: {
        name: 'weweewewewe',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入自定义数据源名称', trigger: 'blur' }
        ],
        region: [
          { required: true, message: '请选择活动区域', trigger: 'change' }
        ],
        resource: [
          { required: true, message: '请选择活动资源', trigger: 'change' }
        ]
      }
    }
  },

  props: {
    title: String
  },

  methods: {
    open({ row, opType }) {
      console.log(row, 'row')
      this.opType = opType
      this.$refs.baseDialog.open()
    },

    handleClose() {
      this.dialogVisible = false
    },

    handleConfirm() {
      this.handleClose()
      this.$emit('on-confirm')
    }
  }
}
</script>

<style lang="scss" scoped>
.form-detail {
  .label-text {
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #262626;
  }
}
</style>
