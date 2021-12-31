<!--
 * @Description: 详情
 * @Date: 2021-08-20
-->

<template>
  <BaseDialog
    ref="baseDialog"
    class="datasource-dialog"
    width="850px"
    :title="title"
    :hide-footer="true"
    @onClose="handleClose"
    @onConfirm="handleConfirm"
  >
    <div class="detail-form">
      <div class="banner-title">
        <span class="text">数据源基本信息</span>
      </div>
      <!-- 查看详情 -->
      <el-form
        :model="detailData"
        :rules="rules"
        ref="detailData"
        label-width="100px"
        :class="['icredit-form', opType === 'View' ? 'form-detail' : '']"
      >
        <el-row type="flex" justify="center">
          <el-col :span="10">
            <el-form-item label="数据源名称" :rules="[{ required: true }]">
              <span class="label-text"> {{ detailData.name }}</span>
            </el-form-item></el-col
          >
          <el-col :span="10">
            <el-form-item label="数据库名" :rules="[{ required: true }]">
              <span class="label-text"> {{ detailData.databaseName }}</span>
            </el-form-item></el-col
          >
        </el-row>

        <el-row type="flex" justify="center">
          <el-col :span="10">
            <el-form-item label="IP" :rules="[{ required: true }]">
              <span class="label-text">
                {{ detailData.ip }}
              </span>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="端口" :rules="[{ required: true }]">
              <span class="label-text">
                {{ detailData.port }}
              </span>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row type="flex" justify="center">
          <el-col :span="10">
            <el-form-item label="用户" :rules="[{ required: true }]">
              <span class="label-text">{{ detailData.username }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="密码" :rules="[{ required: true }]">
              <span class="label-text">{{ hidePassword }}</span>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row type="flex" justify="center">
          <el-col :span="10">
            <el-form-item label="启用" :rules="[{ required: true }]">
              <span class="label-text">
                {{ detailData.status ? '否' : '是' }}
              </span>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="数据源描述">
              <el-input
                v-if="detailData.descriptor"
                class="label-text"
                :readonly="true"
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 4 }"
                v-model="detailData.descriptor"
              ></el-input>
              <span class="label-text" v-else>
                {{ detailData.descriptor }}</span
              >
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <div class="detail-table">
      <div class="banner-title banner-select">
        <div class="text">数据源表结构信息</div>
        <div class="right">
          <span class="right-label">选择表</span>
          <el-select
            v-model="tableName"
            filterable
            clearable
            placeholder="请输入或选择表名"
            @change="handleTableSelectChange"
          >
            <el-option
              v-for="(item, idx) in tableOptions"
              :key="idx"
              :label="item.value"
              :value="item.value"
            >
            </el-option>
          </el-select>

          <div class="right-text">
            <template v-if="tableIndex == null || tableIndex === undefined">
              <span>全部表，</span>
            </template>
            <template v-else>
              第<span class="right-text-num">{{ tableIndex }}</span
              >张表，
            </template>
            <template
              >共<span class="right-text-num">{{ tableCount }}</span
              >张表
            </template>
          </div>
        </div>
      </div>
      <j-table
        ref="editTable"
        v-loading="tableLoading"
        :table-data="tableData"
        :table-configuration="tableConfiguration"
      >
      </j-table>
    </div>
  </BaseDialog>
</template>

<script>
import API from '@/api/icredit'
import { uriSplit, deepClone } from '@/utils/util'
import BaseDialog from '@/views/icredit/components/dialog'
import tableConfiguration from '@/views/icredit/configuration/table/workspace-datasource-detail'

export default {
  components: { BaseDialog },
  data() {
    return {
      timerId: null,
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
      },
      tableLoading: false,
      sourceTableData: [],
      tableData: [],
      tableCount: 0,
      tableIndex: null,
      tableName: undefined,
      tableOptions: [],
      tableConfiguration
    }
  },

  computed: {
    hidePassword() {
      const { showPassword, password: pwd } = this.detailData
      const getHideIcon = num =>
        Array(num)
          .fill('*')
          .join('')

      return showPassword ? pwd : getHideIcon(pwd ? String(pwd).length : 0)
    }
  },

  methods: {
    open({ opType, data = {} }) {
      this.title = `数据源${opType === 'View' ? '查看' : '编辑'}`
      this.opType = opType
      this.tableOptions = []
      this.detailData = uriSplit(data.uri, data)
      this.handleReset()
      this.getTableDetailData(data.id)
      this.$refs.baseDialog.open()
    },

    // 获取表结构信息详情
    getTableDetailData(id) {
      this.tableLoading = true
      API.datasourceTableDetail(id)
        .then(({ success, data }) => {
          if (success && data) {
            const { tableCount, tableOptions, columnList } = data
            this.tableCount = tableCount
            this.tableOptions = tableOptions
              ? tableOptions.map(value => ({ value }))
              : []
            this.tableData = columnList ?? []
            this.sourceTableData = columnList ?? []
          }
        })
        .finally(() => {
          this.tableLoading = false
        })
    },

    handleTableSelectChange() {
      this.tableLoading = true
      clearTimeout(this.timerId)
      if (this.tableName) {
        this.tableIndex = this.tableOptions.findIndex(
          item => item.value === this.tableName
        )
        this.tableIndex++
        this.tableData = deepClone(this.sourceTableData).filter(
          item => item.tableName === this.tableName
        )
      } else {
        this.tableIndex = null
        this.tableData = this.sourceTableData
      }

      this.timerId = setTimeout(() => {
        this.tableLoading = false
      }, 300)
    },

    handleReset() {
      this.tableName = ''
      this.tableIndex = null
      this.tableData = []
      this.sourceTableData = []
    },

    handleClose() {
      this.dialogVisible = false
      this.handleReset()
    },

    handleConfirm() {
      this.handleClose()
      this.$emit('on-confirm')
    }
  }
}
</script>

<style lang="scss" scoped>
.datasource-dialog {
  ::v-deep {
    .el-dialog__body {
      padding: 20px;
    }
  }
}
.icredit-form {
  @include icredit-form;

  ::v-deep {
    .el-form-item--small.el-form-item {
      margin-bottom: 0;
    }
  }
}

.form-detail {
  .label-text {
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    // color: #262626;
  }
}

.detail-form,
.detail-table {
  margin-top: 30px;

  .banner-title {
    position: relative;
    margin: 20px 0;

    .text {
      height: 20px;
      font-size: 14px;
      font-family: PingFangSC, PingFangSC-Regular;
      font-weight: 400;
      text-align: left;
      color: #262626;
      line-height: 20px;
      margin-left: 10px;
    }

    &::before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      width: 4px;
      height: 18px;
      background: #1890ff;
      border-radius: 0px 2px 2px 0px;
    }
  }

  .banner-select {
    @include flex(row, space-between);
    font-size: 14px;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: right;
    color: #262626;

    .right {
      @include flex;

      &-label {
        margin-right: 12px;
      }

      &-text {
        margin-left: 16px;

        &-num {
          color: #1890ff;
        }
      }
    }

    &::before {
      top: 7px;
    }
  }
}
</style>
