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
    :close-on-click-modal="false"
    @on-close="handleClose"
    @on-confirm="handleConfirm"
  >
    <el-form
      :model="dictForm"
      :rules="dictRules"
      ref="dictForm"
      label-width="150px"
      class="dict-form"
    >
      <el-form-item label="字典表英文名称" prop="englishName">
        <el-input
          v-model.trim="dictForm.englishName"
          clearable
          show-word-limit
          :maxlength="50"
          placeholder="请输入字典表英文名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="字典表中文名称" prop="chineseName">
        <el-input
          v-model.trim="dictForm.chineseName"
          clearable
          show-word-limit
          :maxlength="50"
          placeholder="请输入字典表中文名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="字典表描述" prop="dictDesc">
        <el-input
          type="textarea"
          clearable
          maxlength="250"
          show-word-limit
          placeholder="请输入字典表描述"
          v-model="dictForm.dictDesc"
        ></el-input>
      </el-form-item>
      <el-form-item v-if="opType !== 'import'" label="字典表内容" prop="table">
        <j-table
          class="dictionary-table"
          ref="table"
          v-loading="tableLoading"
          :table-configuration="tableConfiguration"
          :table-data="tableData"
        >
          <template #operationColumn="{row, column, index}">
            <div class="btn-wrap">
              <el-button
                class="dictionary-table-btn"
                @click="handleAddRow(row, column, index)"
              >
                <i class="el-icon-plus icon"></i>
              </el-button>
              <el-button
                :disabled="tableData.length < 2"
                class="dictionary-table-btn"
                @click="handleMinusRow(row, column, index)"
              >
                <i class="el-icon-minus icon"></i
              ></el-button>
            </div>
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
import { mapGetters } from 'vuex'
import API from '@/api/icredit'
import BaseDialog from '@/views/icredit/components/dialog'
import tableConfiguration from '@/views/icredit/configuration/table/data-dictionary-add'
import {
  validStrSpecial,
  verifySpecialStr,
  validStrEn,
  strExcludeBlank
} from '@/utils/validate'

export default {
  components: { BaseDialog },

  data() {
    // 校验英文名称
    const verifyEnglishName = (rule, value, cb) => {
      if (validStrSpecial(value) && !value.includes('_')) {
        return cb(new Error('该名称中包含不规范字符，请重新输入'))
      } else if (!validStrEn(value) && !value.includes('_')) {
        return cb(new Error('只支持输入英名以及下划线'))
      } else {
        cb()
        this.dictForm.englishName = strExcludeBlank(value)
      }
    }

    return {
      title: '',
      opType: '',
      dictForm: { englishName: '', chineseName: '', remark: '' },
      dictRules: {
        englishName: [
          { required: true, message: '必填项不能为空', trigger: 'blur' },
          { validator: verifyEnglishName, trigger: 'blur' }
        ],
        chineseName: [
          { required: true, message: '必填项不能为空', trigger: 'blur' },
          { validator: verifySpecialStr, trigger: 'blur' }
        ]
      },
      tableLoading: false,
      tableConfiguration,
      tableData: [{ columnKey: '', columnValue: '', remark: '' }]
    }
  },

  computed: {
    ...mapGetters({ userInfo: 'user/userInfo' })
  },

  methods: {
    open(options) {
      const { row, title, opType = 'add' } = options
      this.opType = opType
      this.title = title

      console.log('deddeded', row, options)
      this.$nextTick(() => this.$refs.baseDialog.open())
    },

    reset() {
      this.$refs.dictForm.resetFields()
      this.tableData = []
      this.tableData.splice(0, 1, {
        columnKey: '',
        columnValue: '',
        remark: ''
      })
    },

    // 新增一行
    handleAddRow(row) {
      const { columnKey } = row
      this.tableData.push({ columnKey, columnValue: '', remark: '' })
    },

    // 删减一行
    handleMinusRow(row, column, index) {
      console.log(row, column, index, 'row')
      this.tableData.splice(index, 1)
    },

    handleClose() {
      this.reset()
      this.$refs.baseDialog.btnLoadingClose()
      this.$refs.baseDialog.dialogVisible = false
    },

    // 保存或编辑
    handleConfirm() {
      this.$refs.dictForm.validate(valid => {
        if (valid) {
          const { id, userName } = this.userInfo
          const params = {
            ...this.dictForm,
            dictColumns: this.tableData,
            createUserId: id,
            createUserName: userName
          }
          API.dictionarySave(params)
            .then(({ success, data }) => {
              if (success && data) {
                this.$notify.success({
                  title: '操作结果',
                  message: '字典表新增成功！',
                  duration: 1500
                })
                this.$emit('on-confirm', success)
              }
            })
            .finally(() => {
              this.$refs.baseDialog.btnLoadingClose()
            })
        } else {
          this.$refs.baseDialog.btnLoadingClose()
        }
      })
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
        border: none;
        .el-icon-upload {
          margin: 0;
          margin-bottom: 20px;
        }
      }
    }
  }

  .dictionary-table {
    .btn-wrap {
      @include flex;
    }

    &-btn {
      @include flex;
      width: 14px;
      height: 14px;
      padding: 0;
      border: 1px solid #999;

      .icon {
        font-size: 14px;
        transform: scale(0.7);
      }
    }

    ::v-deep {
      .el-input__inner {
        border: none;
        background-color: transparent;
      }
    }
  }
}
</style>
