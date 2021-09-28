<!--
 * @Author: lizheng
 * @Description:  查看
 * @Date: 2021-09-28
-->
<template>
  <BaseDialog
    ref="baseDialog"
    width="800px"
    :title="title"
    @onClose="handleClose"
    @onConfirm="handleConfirm"
  >
    <el-form
      :model="dictForm"
      :rules="dictRules"
      ref="dictForm"
      label-width="150px"
      class="dict-form"
    >
      <el-form-item label="字典表英文名称" prop="name">
        <el-input
          v-model="dictForm.name"
          maxlength="50"
          placeholder="请输入英文名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="字典表中文名称" prop="name">
        <el-input
          v-model="dictForm.name"
          maxlength="50"
          placeholder="请输入中文名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="字典表描述" prop="name">
        <el-input
          type="textarea"
          maxlength="250"
          placeholder="请输入字典表描述"
          v-model="dictForm.name"
        ></el-input>
      </el-form-item>
      <el-form-item v-if="opType !== 'import'" label="字典表内容" prop="table">
        <j-table
          ref="table"
          v-loading="tableLoading"
          :table-configuration="tableConfiguration"
          :table-data="tableData"
        >
          <template #operationColumn="{row, column, index}">
            <el-button
              size="mini"
              plain
              type="primary"
              icon="el-icon-plus"
              circle
              @click="handleAddRow(row, column, index)"
            ></el-button>
            <el-button
              size="mini"
              plain
              type="error"
              icon="el-icon-minus"
              circle
              @click="handleMinusRow(row, column, index)"
            ></el-button>
          </template>
        </j-table>
      </el-form-item>
      <el-form-item v-else label="上传文件">
        <div class="dict-btn">
          <el-button icon="el-icon-download" type="text">
            下载模板
          </el-button>
        </div>
        <el-upload class="dict-upload" drag multiple>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        </el-upload>
      </el-form-item>
    </el-form>
  </BaseDialog>
</template>

<script>
import BaseDialog from '@/views/icredit/components/dialog'
import tableConfiguration from '@/views/icredit/configuration/table/data-dictionary-add'

export default {
  components: { BaseDialog },

  data() {
    return {
      title: '',
      opType: '',
      dictForm: {},
      dictRules: {
        name: [{ required: true, message: '必填项不能为空', trigger: 'blur' }]
      },
      tableLoading: false,
      tableConfiguration,
      tableData: [{ key: '', value: '' }]
    }
  },

  methods: {
    open(options) {
      const { row, title, opType = 'add' } = options
      this.opType = opType
      this.title = title
      console.log('deddeded', row, options)

      this.$nextTick(() => this.$refs.baseDialog.open())
    },

    // 新增一行
    handleAddRow(row) {
      console.log(row)
      this.tableData.push({ key: '', value: '', remark: '' })
    },

    // 删减一行
    handleMinusRow(row, column, index) {
      console.log(row, column, index, 'row')
      index && this.tableData.splice(index, 1)
    },

    handleClose() {
      this.$refs.baseDialog.close()
    },

    handleConfirm() {
      this.handleClose()
      this.$emit('on-confirm')
    }
  }
}
</script>

<style lang="scss" scoped>
.dict-form {
  .dict-btn {
    text-align: right;
  }

  .dict-upload {
    width: 100%;
    border-radius: 4px;
    border: 1px dashed rgba(0, 0, 0, 0.15);

    ::v-deep {
      .el-upload-dragger {
        @include flex(column);
        height: 130px;
        .el-icon-upload {
          margin: 0;
          margin-bottom: 20px;
        }
      }
    }
  }
}
</style>
