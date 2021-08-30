<!--
 * @Author: lizheng
 * @Description: 新增或编辑工作空间
 * @Date: 2021-08-17
-->
<template>
  <div class="workspace-setting">
    <div
      title="返回"
      class="back-icon"
      @click="$router.replace('/workspace/space-setting')"
    >
      <j-svg name="back" />
    </div>
    <el-form
      v-loading="detailLoading"
      :model="detailForm"
      :rules="detailRules"
      ref="detailForm"
      label-width="125px"
      :disabled="opType === 'view'"
    >
      <el-form-item class="info-title">
        <div class="text" slot="label">基础信息</div>
      </el-form-item>

      <el-row>
        <el-col :span="8">
          <el-form-item label="工作空间名称" prop="name">
            <el-input
              clearable
              show-word-limit
              :maxlength="15"
              v-model="detailForm.name"
              type="text"
              placeholder="请输入工作空间名称"
              size="small"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="状态" prop="status">
            <el-switch
              v-model="detailForm.status"
              :active-value="0"
              :inactive-value="1"
            ></el-switch>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="20">
          <el-form-item label="工作空间描述" prop="descriptor">
            <el-input
              clearable
              show-word-limit
              :maxlength="250"
              :rows="4"
              type="textarea"
              v-model="detailForm.descriptor"
              placeholder="请输入工作空间描述"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <div class="form-divider" />

      <el-form-item class="info-title">
        <div class="text" slot="label">用户角色信息</div>
      </el-form-item>

      <el-row>
        <el-col :span="8">
          <el-form-item label="负责人" prop="director">
            <el-select
              style="width: 100%"
              filterable
              clearable
              size="small"
              placeholder="请选择"
              v-model="detailForm.director"
            >
              <el-option
                v-for="item in userOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="20">
          <el-form-item label="成员信息" prop="desc">
            <j-table
              ref="table"
              v-loading="tableLoading"
              :table-data="tableData"
              :table-configuration="tableConfiguration"
            ></j-table>
          </el-form-item>
        </el-col>
      </el-row>

      <div class="form-divider" />
    </el-form>

    <div v-if="opType !== 'view'" class="footer-btn">
      <el-button
        class="btn"
        type="primary"
        :loading="btnLoading"
        @click="handleConfirm"
      >
        确定
      </el-button>
    </div>
  </div>
</template>

<script>
import tableConfiguration from '@/views/icredit/configuration/table/workspace-setting-detail'
import crud from '@/mixins/crud'
import operate from '@/mixins/operate'

import API from '@/api/icredit'

export default {
  mixins: [crud, operate],
  data() {
    return {
      id: null,
      tableLoading: false,
      tableConfiguration,
      tableData: [],
      opType: '',
      btnLoading: false,
      detailForm: {
        status: 0,
        director: []
      },
      detailRules: {
        status: [
          { required: true, message: '必填项不问为空', trigger: 'change' }
        ],
        director: [
          { required: true, message: '必填项不能为空', trigger: 'change' }
        ]
      },
      userOptions: [
        { value: 'admin', label: 'admin' },
        { value: 'zhangsan', label: '张三' },
        { value: 'monkeyCode', label: 'monkeyCode' },
        { value: 'lisi', label: '里斯' }
      ]
    }
  },

  mounted() {
    this.$refs.detailForm.resetFields()
    this.initPage()
  },

  methods: {
    initPage() {
      const { query } = this.$route
      this.opType = query?.opType || ''
      this.id = query?.id || null
      this.id && this.handleEditClick('workspaceDetail', this.id)
    },

    // 编辑操作数据回显
    mixinDetailInfo(data) {
      this.detailForm = data
    },

    // 新增
    handleConfirm() {
      this.$refs.detailForm.validate(valid => {
        if (valid) {
          this.btnLoading = true
          API[`workspace${this.id ? 'Update' : 'Add'}`](this.detailForm)
            .then(({ success }) => {
              if (success) {
                this.$notify.success({
                  title: '操作提示',
                  message: `工作空间${this.id ? '编辑' : '新增'}成功！`
                })
                this.$router.push('/workspace/space-setting')
              }
            })
            .finally(() => {
              this.btnLoading = false
            })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.workspace-setting {
  position: relative;
  background: #fff;
  width: 100%;
  height: calc(100vh - 126px);
  overflow: hidden;

  .back-icon {
    margin: 20px 0 0 20px;
    &:hover {
      cursor: pointer;
    }
  }

  .info-title {
    position: relative;
    margin-top: 24px;
    font-size: 18px;
    font-family: PingFangSC, PingFangSC-Medium;
    font-weight: 600;
    text-align: left;
    color: #262626;
    line-height: 25px;

    .text {
      text-align: left;
      margin-left: 16px;
    }

    &::before {
      content: '';
      position: absolute;
      left: 0;
      bottom: 12px;
      width: 4px;
      height: 18px;
      opacity: 1;
      background: #1890ff;
      border-radius: 0px 2px 2px 0px;
    }
  }

  .status-text {
    @include flex;
    display: inline-flex;
    color: #1890ff;
    margin-left: 12px;
  }

  .form-divider {
    margin: 0 16px;
    border-bottom: 1px dashed #d9d9d9;
  }

  .footer-btn {
    position: absolute;
    bottom: 20px;
    left: 45%;
    text-align: center;
    margin-top: 20px;

    .btn {
      width: 150px;
      height: 40px;
      background: #1890ff;
      border-radius: 4px;
    }
  }
}
</style>
