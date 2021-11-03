<!--
 * @Description: 详情
 * @Date: 2021-08-20
-->

<template>
  <BaseDialog
    ref="baseDialog"
    width="600px"
    :title="title"
    :hide-footer="true"
    @onClose="handleClose"
    @onConfirm="handleConfirm"
  >
    <!-- 查看详情 -->
    <el-form
      :model="detailData"
      :rules="rules"
      ref="detailData"
      label-width="100px"
      :class="['icredit-form', opType === 'View' ? 'form-detail' : '']"
    >
      <template>
        <el-form-item label="数据源名称" :rules="[{ required: true }]">
          <span class="label-text"> {{ detailData.name }}</span>
        </el-form-item>

        <el-form-item label="数据库名" :rules="[{ required: true }]">
          <span class="label-text"> {{ detailData.databaseName }}</span>
        </el-form-item>

        <el-row>
          <el-col :span="12">
            <el-form-item label="IP" :rules="[{ required: true }]">
              <span class="label-text">
                {{ detailData.ip }}
              </span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="端口" :rules="[{ required: true }]">
              <span class="label-text">
                {{ detailData.port }}
              </span>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="用户" :rules="[{ required: true }]">
              <span class="label-text">{{ detailData.username }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="密码" :rules="[{ required: true }]">
              <span class="label-text">{{ hidePassword }}</span>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="启用" :rules="[{ required: true }]">
          <span class="label-text">
            {{ detailData.status ? '否' : '是' }}
          </span>
        </el-form-item>

        <el-form-item label="数据源描述">
          <span class="label-text"> {{ detailData.descriptor }}</span>
        </el-form-item>
      </template>
    </el-form>
  </BaseDialog>
</template>

<script>
import { uriSplit } from '@/utils/util'
import BaseDialog from '@/views/icredit/components/dialog'

export default {
  components: { BaseDialog },
  data() {
    return {
      title: '',
      opType: 'View',
      dialogVisible: false,
      detailData: { showPassword: 0 },
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

  computed: {
    hidePassword() {
      const { showPassword, password } = this.detailData
      const getHideIcon = num =>
        Array(num)
          .fill('*')
          .join('')

      return showPassword ? password : getHideIcon(String(password).length)
    }
  },

  methods: {
    open({ opType, data = {} }) {
      this.title = `数据源${opType === 'View' ? '查看' : '编辑'}`
      this.opType = opType
      this.detailData = uriSplit(data.uri, data)
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
.icredit-form {
  @include icredit-form;
}

.form-detail {
  .label-text {
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    // color: #262626;
  }
}
</style>
