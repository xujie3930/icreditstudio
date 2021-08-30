<!--
 * @Author: lizheng
 * @Description: 系统设置 - 参数设置页面
 * @Date: 2021-06-17
-->
<template>
  <div class="params-wrap w100">
    <div class="params-wrap-content" v-loading="loading">
      <!-- 基本设置 -->
      <div class="base">
        <div class="base-label">基本设置：</div>
        <div class="base-form">
          <el-row class="row">
            <el-col :sm="6" :md="8" :xl="12" :span="12">
              <div class="base-form-item">
                <span class="label">Logo上传:</span>
                <el-upload
                  class="avatar-uploader"
                  action="#"
                  :auto-upload="false"
                  :show-file-list="false"
                  :on-change="handleAvatarChange"
                >
                  <img
                    v-if="imageUrl"
                    :src="imageUrl | base64UrlFilter"
                    class="avatar"
                  />
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="base-form-item">
                <span class="label">系统主题:</span>
                <ChangeColor
                  ref="color"
                  :cssId="defaultCssId"
                  @change-color="!systemSetting.cssId && theme"
                />
              </div>
            </el-col>
          </el-row>

          <el-row class="row">
            <el-col :sm="6" :md="8" :xl="12" :span="12">
              <div class="base-form-item">
                <span class="label">系统名称:</span>
                <el-input
                  clearable
                  show-word-limit
                  style="width: 60%"
                  type="textarea"
                  :maxlength="40"
                  v-model="systemName"
                  placeholder="请输入系统名称"
                />
              </div>
            </el-col>
            <el-col :span="12">
              <div class="base-form-item">
                <span class="label">系统字号:</span>
                <ChangeFont
                  ref="font"
                  :fontSize="defaultFontSize"
                  @change-font="!systemSetting.fontSize && font"
                />
              </div>
            </el-col>
          </el-row>
        </div>
      </div>

      <!-- 版权设置 -->
      <div class="copyright">
        <div class="base-label">版权设置：</div>
        <div class="base-form">
          <el-row class="row">
            <el-col :span="12">
              <div class="base-form-item">
                <span class="label">版权声明:</span>
                <el-input
                  clearable
                  show-word-limit
                  type="textarea"
                  style="width: 60%"
                  placeholder="请输入版权声明"
                  v-model="copyright"
                  :maxlength="100"
                  :autosize="{ minRows: 2, maxRows: 6 }"
                  @input="settingCopyrightAction"
                />
              </div>
            </el-col>
          </el-row>
        </div>
      </div>

      <!-- 其他参数设置 -->
      <div class="business-wrap">
        <div class="business">
          <div class="base-label">其他参数设置：</div>
          <div class="base-form">
            <el-row class="row" :gutter="10">
              <el-col :span="8">
                <div class="base-form-item">
                  <span class="label">应用参数1:</span>
                  <el-input
                    clearable
                    v-model="paramOne"
                    placeholder="请输入应用参数1"
                  />
                </div>
              </el-col>
              <el-col :span="4">
                <el-button
                  circle
                  plain
                  size="small"
                  type="primary"
                  icon="el-icon-plus"
                  style="margin-top:4px"
                  @click="handleAddItem"
                />
              </el-col>
            </el-row>
          </div>
        </div>

        <div class="business-item" v-if="otherParamsConfig.length">
          <el-row class="row" :gutter="10">
            <template v-for="(item, index) in otherParamsConfig">
              <el-col
                :key="`${item.label}${index}`"
                style="margin-bottom:20px"
                :span="8"
              >
                <div class="base-form-item">
                  <span class="label"> {{ item.label }}{{ index + 2 }}: </span>
                  <el-input
                    clearable
                    v-model="item.value"
                    :placeholder="`请输入${item.label}${index + 2}`"
                  />
                </div>
              </el-col>
              <el-col :key="index" style="margin-bottom:20px" :span="4">
                <el-button
                  circle
                  plain
                  size="small"
                  type="danger"
                  icon="el-icon-minus"
                  style="margin-top:4px"
                  @click="handleMinusItem(index)"
                />
              </el-col>
            </template>
          </el-row>
        </div>
      </div>
    </div>
    <footer class="footer">
      <el-button type="primary" :loading="btnLoading" @click="handleConfirm">
        确认
      </el-button>
      <el-button class="btn" @click="init">重置</el-button>
    </footer>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import ChangeFont from './components/ChangeFont.vue'
import ChangeColor from './components/ChangeColor.vue'
import {
  querySystemSettings,
  setSystemSettings,
  uploadLogo
} from '@/api/system'
import { base64UrlFilter } from '@/utils/util'
import { font, theme } from '@/utils/theme'

export default {
  components: { ChangeFont, ChangeColor },

  data() {
    this.font = font
    this.theme = theme

    return {
      allowUploadType: false,
      isLt2M: false,
      oldImageUrl: '',
      uploadFile: [],
      settingId: '', // 系统配置id
      copyright: '',
      systemName: '',
      defaultCssId: '',
      defaultFontSize: '',
      form: '',
      imageUrl: '',
      paramOne: '',
      otherParamsConfig: [],
      loading: false,
      btnLoading: false,
      paramsArr: [
        'paramTwo',
        'paramThree',
        'paramFour',
        'paramFive',
        'paramSix'
      ]
    }
  },

  computed: {
    ...mapGetters({
      userInfo: 'user/userInfo',
      systemSetting: 'user/systemSetting'
    })
  },

  filters: {
    base64UrlFilter(url) {
      return base64UrlFilter(url)
    }
  },

  mounted() {
    this.init()
  },

  methods: {
    ...mapActions('common', ['settingCopyrightAction']),
    ...mapActions('user', ['getPermissionListAction']),

    init() {
      this.loading = true
      querySystemSettings()
        .then(({ data }) => {
          const {
            id,
            logo,
            appName,
            copyRight,
            defaultCssId,
            defaultFontSize
          } = data

          if (id) {
            this.settingId = id
            this.imageUrl = logo
            this.oldImageUrl = logo
            this.systemName = appName
            this.copyright = copyRight
            this.defaultCssId = defaultCssId
            this.defaultFontSize = defaultFontSize
            this.systemSetting.logo = logo
            this.systemSetting.copyRight = copyRight
            this.processParamsConfig(data)
            this.settingCopyrightAction(data.copyRight)
          }
        })
        .finally(() => {
          this.loading = false
        })
    },

    // 其他参数渲染处理
    processParamsConfig(data) {
      const LABEL = '应用参数'
      this.paramOne = data.paramOne

      this.otherParamsConfig = []
      this.paramsArr.forEach(item => {
        if (data[item]) {
          this.otherParamsConfig.push({
            label: LABEL,
            value: data[item]
          })
        }
      })
    },

    // 新增参数
    handleAddItem() {
      if (this.otherParamsConfig.length > 4) {
        return this.$message.warning('最多只能新增6个业务参数属性！')
      }
      this.otherParamsConfig.push({ label: '应用参数', value: '' })
    },

    // 删除参数
    handleMinusItem(index) {
      this.otherParamsConfig.splice(index, 1)
    },

    // 上传文件更改
    handleAvatarChange(res, file) {
      console.log(file, 'ooo')
      const { raw } = file[file.length - 1]
      const { type, size } = raw

      const imgType = ['png', 'jpg', 'jpeg', 'bmp', 'webp']
      const allowUploadType = imgType.includes(type.replace(/image\//g, ''))
      const isLt2M = size / 1024 / 1024 < 2

      this.uploadFile = file
      if (!allowUploadType) {
        return this.$notify.error('上传图片格式不正确!')
      } else if (!isLt2M) {
        return this.$notify.error('上传头像图片大小不能超过 2MB!')
      }

      this.imageUrl = window.URL.createObjectURL(raw)
      this.systemSetting.logo = this.imageUrl
    },

    // 上传文件
    uploadSectionFile() {
      const fileObj = this.uploadFile[this.uploadFile.length - 1].raw
      const reader = new FileReader()
      reader.readAsDataURL(fileObj)
      reader.onload = e => {
        const _file = e.target.result.split('base64')[1]
        const formData = {
          id: this.settingId,
          userId: this.userInfo.id,
          logo: _file
        }
        uploadLogo(formData)
          .then(res => {
            if (res.success) {
              const { length } = this.uploadFile
              if (length) {
                const { raw } = this.uploadFile[length - 1]
                this.imageUrl = window.URL.createObjectURL(raw)
              }
            }
          })
          .catch(err => {
            console.log(err)
            this.btnLoading = false
          })
      }
    },

    // 保存
    handleConfirm() {
      const otherParams = {}
      this.btnLoading = true
      this.paramsArr.forEach((item, index) => {
        if (index < this.otherParamsConfig.length) {
          otherParams[item] = this.otherParamsConfig[index]
            ? this.otherParamsConfig[index].value
            : null
        }
      })

      const formData = {
        appName: this.systemName || '',
        copyRight: this.copyright || '',
        defaultCssId: this.$refs.color.value,
        defaultFontSize: this.$refs.font.value,
        paramOne: this.paramOne || '',
        ...otherParams
      }

      // 上传系统logo文件
      this.uploadFile.length && this.uploadSectionFile()

      // 保存相应的参数
      setSystemSettings(formData)
        .then(res => {
          if (res.success) {
            this.$notify.success('参数设置成功！')
            this.init()
          }
        })
        .finally(() => {
          this.btnLoading = false
        })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/common/_mixin.scss';

/deep/ .avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

/deep/ .avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 14px;
  color: #8c939d;
  width: 48px;
  height: 48px;
  line-height: 48px;
  text-align: center;
}

.avatar {
  width: 48px;
  height: 48px;
  display: block;
}

.params-wrap {
  min-height: calc(100vh - 170px);
  background-color: #fff;

  &-content {
    .base {
      @include flex(row, flex-start);
      padding: 0 0 20px 20px;
      margin-bottom: 20px;
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

      &-form {
        width: calc(100% - 150px);
        &-item {
          @include flex(row, flex-start);

          .label {
            min-width: 70px;
            display: inline-block;
            margin-right: 10px;
            font-size: 14px;
          }
        }

        .row {
          margin: 20px 0;
        }
      }

      &-label {
        width: 112px;
        margin-right: 30px;
        font-size: 16px;
      }
    }

    .copyright {
      @extend .base;
      padding-bottom: 0;
    }

    .business-wrap {
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

      .business {
        @extend .base;
        border-bottom: none;
        box-shadow: none;
        padding-bottom: 0;
        margin-bottom: 0;
      }

      .business-item {
        padding: 0 0 20px 20px;
        margin: 0 0 20px 142px;
      }
    }

    .color-wrap {
      @include flex;

      .color-item {
        width: 100%;
        @include flex(column);
        margin: 0 10px;

        .color {
          display: block;
          width: 30px;
          height: 30px;
          border-radius: 2px;
          cursor: pointer;
          background-color: #061178;
        }
        .radio {
          margin-top: 5px;

          /deep/ .el-radio__label {
            padding-left: 0;
          }

          .name {
            font-size: 12px;
          }
        }
      }
    }
  }

  .footer {
    width: 100%;
    text-align: right;

    .btn {
      margin: 20px;
    }
  }
}
</style>
