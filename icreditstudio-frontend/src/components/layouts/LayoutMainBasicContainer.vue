<template>
  <div class="iframe-layout-basic-container">
    <div class="iframe-layout-basic-header" v-if="$slots.header">
      <slot name="header"></slot>
    </div>
    <div class="iframe-layout-basic-main" v-if="$slots.content">
      <div class="iframe-layout-basic-main-top iframe-flex-row-sp-center">
        <slot name="title">
          <span>{{ title }}</span>
        </slot>
        <slot name="menu">
          <div v-if="showMenu">
            <el-button
              type="primary"
              v-if="menuAuth.includes('add')"
              @click="$emit('handleAdd')"
            >新增</el-button>
            <el-upload
              v-if="menuAuth.includes('import')"
              style="display: inline-block"
              name="file"
              :headers="headers"
              :auto-upload="true"
              :before-upload="beforeAvatarUpload"
              :limit="1"
              action=""
              accept=".xlsx,.xls"
              :show-file-list="false">
              <el-button style="margin:0 10px;" type="primary">导入</el-button>
            </el-upload>
            <el-button
              v-if="menuAuth.includes('export')"
              type="primary"
              @click="$emit('handleExport')"
            >导出</el-button>
            <el-button
              v-for="(item,index) in authcustomBtnConfig"
              :key="index"
              :type="item.type||'primary'"
              v-on:[item.options.eventType].native="handleCustomMenuEvent(item)"
            >{{item.label}}</el-button>
          </div>
          <div v-if="showCreate">
            <el-button
              type="primary"
              @click="$emit('handleCreate')"
            >生成代码</el-button>
          </div>
        </slot>
      </div>
      <slot name="content"></slot>
    </div>
  </div>
</template>
<script>
export default {
  name: 'LayoutMainBasicContainer',
  props: {
    title: {
      type: String,
      default: '列表'
    },
    showMenu: {
      type: Boolean,
      default: true
    },
    showCreate: {
      type: Boolean,
      default: false
    },
    customBtnConfig: {
      type: Array,
      default: () => []
    }
  },
  computed: {
    authcustomBtnConfig() {
      return this.customBtnConfig.filter(e => this.menuAuth.includes(e.key))
    }
  },
  data() {
    return {
      headers: {
        'Content-Type': 'multipart/form-data', dataType: 'file'
      },
      menuAuth: []
    }
  },
  created() {
    this.menuAuth = this.$route.meta.permissionList || []
  },
  methods: {
    // 上传前对文件的大小的判断
    beforeAvatarUpload(file) {
      const extension = file.name.split('.')[1] === 'xls';
      const extension2 = file.name.split('.')[1] === 'xlsx';
      const isLt2M = file.size / 1024 / 1024 < 10;
      if (!extension && !extension2) {
        this.$message({
          message: '上传模板只能是 xls、xlsx格式!',
          type: 'error'
        });
        return false
      }
      if (!isLt2M) {
        this.$message.error('上传模板大小不能超过 10MB!')
        return false
      }
      const data = new FormData()
      data.append('file', file)
      this.$emit('handleImport', data)
      return false
    },
    handleCustomMenuEvent(config) {
      this.$emit('handleCustomMenuEvent', config)
    }
  }
}
</script>
<style scoped lang="scss">
.iframe-layout-basic-container{
  height: 100%;
  padding: 0 16px 16px;
  background-color: #FFFFFF;
}

.iframe-layout-basic-header{
  padding: 24px 0 0;
  border-bottom: 1px dashed $--border-color-base;
}

.iframe-layout-basic-main-top{
  padding: 19px 0 16px;

  span{
    color: $--color-text-primary;
    font-size: 16px;
    font-weight: bolder;
    letter-spacing: 2px;
  }
}
</style>
