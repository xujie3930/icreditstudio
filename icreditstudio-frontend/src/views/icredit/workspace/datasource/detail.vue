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
      :class="[opType === 'View' ? 'form-detail' : '']"
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
              <span class="label-text">{{ detailData.password }}</span>
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

    <!-- 新增或编辑 -->
    <!-- <el-form
      v-else
      :model="detialForm"
      :rules="rules"
      ref="detialForm "
      label-width="100px"
      :class="[opType === 'View' ? 'form-detail' : '']"
    >
      <template>
        <el-form-item label="数据源名称" prop="name">
          <el-input
            show-word-limit
            :maxlength="15"
            v-model="detialForm.name"
            placeholder="请输入自定义数据源名称"
          ></el-input>
        </el-form-item>

        <el-form-item label="数据库名" prop="name">
          <el-input
            v-model="detialForm.name"
            placeholder="请输入数据库名"
          ></el-input>
        </el-form-item>

        <el-row>
          <el-col :span="12">
            <el-form-item label="IP" prop="name">
              <el-input v-model="detialForm.name" placeholder="请输入IP">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="端口" prop="name">
              <el-input
                v-model="detialForm.name"
                placeholder="请输入端口"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="启用" prop="resource">
          <el-radio-group v-model="detialForm.resource">
            <el-radio label="是"></el-radio>
            <el-radio label="否"></el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="数据源描述" prop="desc">
          <el-input
            show-word-limit
            :maxlength="250"
            type="textarea"
            v-model="detialForm.desc"
            placeholder="请输入数据源描述"
          ></el-input>
        </el-form-item>
      </template>
    </el-form> -->
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
      detailData: {},
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
.form-detail {
  .label-text {
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #262626;
  }
}
</style>
