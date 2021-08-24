<!--
 * @Author: lizheng
 * @Description: 新增数据源第二步
 * @Date: 2021-08-19
-->

<template>
  <BaseDialog
    footer
    ref="baseDialog"
    title="新增数据源"
    width="600px"
    top="20vh"
    @onClose="handleClose"
    @onConfirm="handleConfirm"
  >
    <el-form
      :model="dataSourceForm"
      :rules="rules"
      ref="dataSourceForm"
      label-width="100px"
    >
      <el-form-item label="数据源名称" prop="name">
        <el-input
          clearable
          show-word-limit
          :maxlength="15"
          v-model="dataSourceForm.name"
          placeholder="请输入自定义数据源名称"
        ></el-input>
      </el-form-item>

      <el-form-item
        v-if="dataType === 'relational'"
        label="数据库名"
        prop="name"
      >
        <el-input
          v-model="dataSourceForm.name"
          placeholder="请输入数据库名"
        ></el-input>
      </el-form-item>

      <template v-if="['semiStructured', 'doc'].includes(dataType)">
        <el-form-item
          v-if="dataType === 'semiStructured'"
          label="数据源路径"
          prop="name"
        >
          <el-input
            v-model="dataSourceForm.name"
            placeholder="请输入数据库名"
          ></el-input>
        </el-form-item>

        <el-form-item
          v-if="dataType === 'semiStructured'"
          label="文件格式"
          prop="resource"
        >
          <el-radio-group v-model="dataSourceForm.resource">
            <el-radio label="TXT"></el-radio>
            <el-radio label="XLS"></el-radio>
            <el-radio label="CSV"></el-radio>
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

        <el-form-item label="分隔符" prop="name">
          <el-input
            v-model="dataSourceForm.name"
            placeholder="请输入分隔符"
          ></el-input>
        </el-form-item>
      </template>

      <el-row v-if="dataType !== 'doc'">
        <el-col :span="12">
          <el-form-item label="IP" prop="name">
            <el-input
              v-model="dataSourceForm.name"
              placeholder="请输入数据源连接IP"
            >
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="端口" prop="name">
            <el-input
              v-model="dataSourceForm.name"
              placeholder="请输入端口"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row v-if="dataType !== 'doc'">
        <el-col :span="12">
          <el-form-item label="用户名" prop="name">
            <el-input
              v-model="dataSourceForm.name"
              placeholder="请输入数据源连接用户名"
            >
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="密码" prop="name">
            <el-input
              v-model="dataSourceForm.name"
              placeholder="请输入数据源连接密码"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="启用" prop="resource">
        <el-radio-group v-model="dataSourceForm.resource">
          <el-radio label="是"></el-radio>
          <el-radio label="否"></el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="数据源描述" prop="desc">
        <el-input
          show-word-limit
          :maxlength="250"
          type="textarea"
          v-model="dataSourceForm.desc"
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
      <el-button size="mini" @click="handlePrevious">上一步</el-button>
      <el-button
        size="mini"
        type="primary"
        v-if="dataType !== 'doc'"
        @click="handleTestLink"
      >
        测试连接
      </el-button>
      <el-button size="mini" type="primary" @click="handleConfirm">
        确 定
      </el-button>
    </div>
  </BaseDialog>
</template>

<script>
import BaseDialog from '@/views/icredit/components/dialog'

export default {
  components: { BaseDialog },

  data() {
    return {
      dataType: '',
      positionOptions: [
        { label: '第一行', value: 1 },
        { label: '第二行', value: 2 },
        { label: '最后一行', value: 3 }
      ],
      dialogVisible: false,
      dataSourceForm: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
        file: '',
        position: ''
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
        ],
        file: [
          { required: true, message: '请选择活动资源', trigger: 'change' }
        ],
        position: [
          { required: true, message: '请选择活动资源', trigger: 'change' }
        ]
      }
    }
  },

  props: {
    title: String
  },

  methods: {
    open(type) {
      console.log(type)
      this.dataType = type
      this.$refs.baseDialog.open()
    },

    // 上一步
    handlePrevious() {
      this.handleClose()
      this.$parent.open()
    },

    handleTestLink() {
      this.$message.success('测试连接成功')
    },

    handleClose() {
      this.$refs.baseDialog.close()
    },

    handleConfirm() {
      this.$emit('on-confirm')
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
