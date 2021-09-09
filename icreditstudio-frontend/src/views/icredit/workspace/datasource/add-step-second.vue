<!--
 * @Author: lizheng
 * @Description: 新增数据源第二步
 * @Date: 2021-08-19
-->

<template>
  <BaseDialog
    footer
    ref="baseDialog"
    width="600px"
    top="20vh"
    :title="title"
    @onClose="handleClose"
    @onConfirm="handleConfirm"
  >
    <el-form
      :model="dataSourceForm"
      :rules="rules"
      ref="dataSourceForm"
      label-width="100px"
      v-loading="detailLoading"
    >
      <el-form-item label="数据源名称" prop="name">
        <el-input
          clearable
          show-word-limit
          :maxlength="15"
          v-model="dataSourceForm.name"
          placeholder="请输入自定义数据源名称"
          @blur="verifyDatasourceName"
        >
          <i v-if="veifyNameLoading" slot="suffix" class="el-icon-loading"></i>
        </el-input>
      </el-form-item>

      <el-form-item
        v-if="dataType === 'relational'"
        label="数据库名"
        prop="databaseName"
      >
        <el-input
          v-model="dataSourceForm.databaseName"
          placeholder="请输入数据库名"
        ></el-input>
      </el-form-item>

      <!-- 半结构化以及本地文件 -->
      <template v-if="['semiStructured', 'doc'].includes(dataType)">
        <el-form-item
          v-if="dataType === 'semiStructured'"
          label="数据源路径"
          prop="uri"
        >
          <el-input
            v-model="dataSourceForm.uri"
            placeholder="请输入数据源路径"
          ></el-input>
        </el-form-item>

        <el-form-item
          v-if="dataType === 'semiStructured'"
          label="文件格式"
          prop="resource"
        >
          <el-radio-group v-model="dataSourceForm.docType">
            <el-radio label="TXT">TXT</el-radio>
            <el-radio label="XLS">XLS</el-radio>
            <el-radio label="CSV">CSV</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="表头位置" prop="position">
          <el-select
            style="width: 100%"
            v-model="dataSourceForm.position"
            placeholder="请选择"
          >
            <el-option
              v-for="item in positionOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="分隔符" prop="separator">
          <el-input
            v-model="dataSourceForm.separator"
            placeholder="请输入分隔符"
          ></el-input>
        </el-form-item>
      </template>

      <el-row v-if="dataType !== 'doc'">
        <el-col :span="12">
          <el-form-item label="IP" prop="ip">
            <el-input
              v-model="dataSourceForm.ip"
              placeholder="请输入数据源连接IP"
            >
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="端口" prop="port">
            <el-input
              v-model="dataSourceForm.port"
              placeholder="请输入端口"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row v-if="dataType !== 'doc'">
        <el-col :span="12">
          <el-form-item label="用户名" prop="username">
            <el-input
              v-model="dataSourceForm.username"
              placeholder="请输入数据源连接用户名"
            >
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="密码" prop="password">
            <el-input
              show-password
              v-model="dataSourceForm.password"
              placeholder="请输入数据源连接密码"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="启用" prop="status">
        <el-radio-group v-model="dataSourceForm.status">
          <el-radio :label="0">是</el-radio>
          <el-radio :label="1">否</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="数据源描述" prop="descriptor">
        <el-input
          show-word-limit
          :maxlength="250"
          type="textarea"
          v-model="dataSourceForm.descriptor"
          placeholder="请输入数据源描述"
        ></el-input>
      </el-form-item>

      <el-form-item v-if="dataType === 'doc'" label="上传文件" prop="file">
        <el-upload
          drag
          class="upload-file"
          action="https://jsonplaceholder.typicode.com/posts/"
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        </el-upload>
      </el-form-item>
    </el-form>

    <div style="text-align:center" slot="customFooter">
      <el-button size="mini" v-if="opType === 'Add'" @click="handlePrevious">
        上一步
      </el-button>
      <el-button v-if="opType !== 'Add'" size="mini" @click="handleClose">
        取消
      </el-button>
      <el-button
        size="mini"
        type="primary"
        v-if="dataType !== 'doc'"
        :loading="testBtnLoading"
        @click="handleTestLink"
      >
        测试连接
      </el-button>
      <el-button
        size="mini"
        type="primary"
        :loading="btnLoading"
        @click="handleConfirm"
      >
        确 定
      </el-button>
    </div>
  </BaseDialog>
</template>

<script>
import { mapState } from 'vuex'
import { uriSplit } from '@/utils/util'
import BaseDialog from '@/views/icredit/components/dialog'
import API from '@/api/icredit'

const databaseTypeMapping = {
  mysql: 1,
  oracle: 2,
  psotgresql: 3,
  sqlserver: 4
}

const dataTypeMapping = {
  relational: 1,
  semiStructured: 2,
  noSql: 3,
  doc: 4,
  blockChain: 5
}

export default {
  components: { BaseDialog },

  data() {
    return {
      title: '新增数据源',
      opType: '', // 操作类型
      dataType: '', // 数据结构类型
      databaseType: '', // 数据库类型
      timerId: null,
      btnLoading: false,
      detailLoading: false,
      testBtnLoading: false,
      dialogVisible: false,
      veifyNameLoading: false,

      // 数据源表单参数
      dataSourceForm: {
        name: '',
        databaseName: '',
        ip: '',
        port: '',
        username: '',
        password: '',
        status: 0
      },
      rules: {
        name: [
          { required: true, message: '请输入数据源名称', trigger: 'blur' },
          // { validator: verifySpecialCode, trigger: 'blur' },
          { validator: this.verifyDatasourceName, trigger: 'blur' }
        ],
        databaseName: [
          { required: true, message: '请输入数据库名', trigger: 'blur' }
        ],
        ip: [
          { required: true, message: '请输入数据源连接IP', trigger: 'blur' }
        ],
        port: [{ required: true, message: '请输入端口', trigger: 'blur' }],
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        status: [{ required: true, message: '请选择是否启用', trigger: 'blur' }]
      },
      positionOptions: [
        { label: '第一行', value: 1 },
        { label: '第二行', value: 2 },
        { label: '最后一行', value: 3 }
      ]
    }
  },

  computed: {
    ...mapState('user', ['workspaceId'])
  },

  methods: {
    open(type, name) {
      this.title = '新增数据源'
      this.opType = 'Add'
      this.dataType = type
      this.databaseType = name
      this.$refs.baseDialog.open()
    },

    // 编辑状态下打开弹窗
    openEdit(options) {
      const { data, opType } = options
      this.title = '编辑数据源'
      this.opType = opType
      this.detailLoading = false
      this.dataSourceForm = uriSplit(data.uri, data)
      // this.$refs.baseDialog.open()
    },

    // 拼凑成数据库驱动URI
    completeUri() {
      const databaseType = this.databaseType || 'mysql'
      const { ip, port, databaseName, username, password } = this.dataSourceForm
      return `jdbc:${databaseType}://${ip}:${port}/${databaseName}?allowMultiQueries=true&useSSL=false&useUnicode=true&characterEncoding=utf8|username=${username}|password=${password}`
    },

    // 验证是否已经存在数据源名称
    verifyDatasourceName(rule, value, cb) {
      // 特殊符号
      const regStr = /[`~!@#$%^&*()_\-+=<>?:"{}|,./;'\\[\]·~！@#￥%……&*（）——\-+={}|《》？：“”【】、；‘’，。、]/gi
      // 表情包
      const emojiRegStr = /[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f\u2026\u2022\u20ac\r\n]/gi
      const isValid = regStr.test(value) || emojiRegStr.test(value)
      if (isValid) {
        cb(new Error('该名称中包含不规范字符，请重新输入'))
      } else {
        this.timerId = null
        this.veifyNameLoading = true
        API.verifyDatasourceName({ name: value })
          .then(({ success, data }) => {
            success && data ? cb(new Error('该名称已存在，请重新输入')) : cb()
          })
          .finally(() => {
            this.timerId = setTimeout(() => {
              this.veifyNameLoading = false
            }, 300)
          })
      }
    },

    // 上一步
    handlePrevious() {
      this.handleClose()
      this.$parent.open()
      this.$refs.dataSourceForm.resetFields()
    },

    // 测试链接
    handleTestLink() {
      this.testBtnLoading = true
      const params = {
        type: databaseTypeMapping[this.databaseType],
        uri: this.completeUri()
      }
      this.$refs.dataSourceForm.validate(valid => {
        if (valid) {
          API.datasourceTestLink(params)
            .then(({ success, data }) => {
              if (success && data) {
                console.log(data)
                this.$message.success('测试连接成功')
              } else {
                this.$message.error(data)
              }
            })
            .finally(() => {
              this.testBtnLoading = false
            })
        }
      })
    },

    handleClose() {
      this.$refs.baseDialog.close()
      this.$refs.dataSourceForm.resetFields()
    },

    // 提交新增或编辑数据源表单
    handleConfirm() {
      const { status, name, descriptor, id } = this.dataSourceForm
      const params = {
        name,
        status,
        descriptor,
        category: dataTypeMapping[this.dataType],
        type: databaseTypeMapping[this.databaseType],
        spaceId: this.workspaceId,
        uri: this.completeUri()
      }
      this.opType === 'Edit' && (params.id = id)
      this.$refs.dataSourceForm.validate(valid => {
        if (valid) {
          this.btnLoading = true
          API[`datasource${this.opType === 'Edit' ? 'Update' : 'Add'}`](params)
            .then(({ success }) => {
              if (success) {
                this.$notify.success({
                  title: '操作结果',
                  message: `数据源${
                    this.opType === 'Edit' ? '编辑' : '新增'
                  }成功！`
                })
                this.handleClose()
                this.$router.push('/workspace/datasource')
                this.$emit('on-confirm', true)
              }
            })
            .catch(() => {
              this.$emit('on-confirm', false)
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
.upload-file {
  @include flex;
  overflow: hidden;
  border: 1px dashed rgba(0, 0, 0, 0.15);
  border-radius: 4px;
  box-sizing: border-box;

  .el-upload__text {
    margin-bottom: 20px;
  }

  ::v-deep {
    .el-upload-dragger {
      @include flex(column);

      .el-icon-upload {
        margin: 20px 0;
      }
    }
  }
}
</style>
