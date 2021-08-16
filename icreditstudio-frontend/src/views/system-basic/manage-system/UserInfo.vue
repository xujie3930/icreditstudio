<template>
  <div class="container">
    <div class="left">
      <div class="user_image">
        <el-upload
          class="avatar-uploader"
          action="#"
          :show-file-list="false"
          :http-request="uploadSectionFile"
          :on-change="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <img
            v-if="imageUrl"
            :key="userInfo.photo"
            :src="userInfo.photo | base64UrlFilter"
            class="avatar"
          />
          <i
            v-else
            :key="userInfo.photo"
            class="el-icon-plus avatar-uploader-icon"
          ></i>
        </el-upload>
      </div>
      <div class="user_main_info">
        <div class="main_info_item">
          <label for="org" class="main_info_item_label">
            <i class="el-icon-user-solid"></i>
          </label>
          <span id="org" class="main_info_item_text">{{ orgList }}</span>
        </div>
      </div>
    </div>
    <div class="right">
      <el-tabs v-model="activeName">
        <el-tab-pane label="基本信息" name="basicInfo">
          <el-form
            :model="ruleForm"
            :rules="rules"
            ref="ruleForm"
            label-width="120px"
            class="center-ruleForm"
          >
            <el-form-item label="用户姓名" prop="userName">
              <el-input
                v-model="ruleForm.userName"
                placeholder="请输入"
              ></el-input>
            </el-form-item>
            <el-form-item label="工号" prop="userCode">
              <el-input
                v-model="ruleForm.userCode"
                placeholder="请输入"
                maxlength="32"
              />
            </el-form-item>
            <el-form-item label="生日" prop="userBirth">
              <el-date-picker
                v-model="ruleForm.userBirth"
                type="date"
                valueFormat="yyyy-MM-dd"
                format="yyyy-MM-dd"
                placeholder="生日"
              >
              </el-date-picker>
            </el-form-item>
            <el-form-item label="手机号码" prop="telPhone">
              <el-input
                v-model="ruleForm.telPhone"
                placeholder="请输入"
              ></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="userGender">
              <el-radio v-model="ruleForm.userGender" label="1">男</el-radio>
              <el-radio v-model="ruleForm.userGender" label="0">女</el-radio>
            </el-form-item>
            <el-form-item label="备注" prop="userRemark">
              <el-input
                type="textarea"
                placeholder="请输入"
                :autosize="{ minRows: 2, maxRows: 5 }"
                maxlength="200"
                v-model="ruleForm.userRemark"
              />
            </el-form-item>
            <el-form-item label="">
              <el-button type="primary" @click="submitBill"
                >更新基本信息</el-button
              >
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getObjType, base64UrlFilter } from '@/utils/util'
import { editBaseUserInfo, uploadPhoto } from '@/api/user'
import { SET_USERINFO } from '@/store/mutation-types'
import { DEFAULT_HEAD_IMG_URL } from '@/config/constant'

export default {
  name: 'userInfo',
  data() {
    return {
      DEFAULT_HEAD_IMG_URL,
      // left
      imageUrl: '',
      orgList: '',
      // rigth
      activeName: 'basicInfo',
      ruleForm: {
        userName: '',
        userCode: '',
        // accountIdentifier: '',
        userBirth: '',
        telPhone: '',
        userGender: '',
        userRemark: ''
      },
      rules: {
        userName: [
          {
            required: true,
            message: '用户姓名不能为空',
            trigger: 'blur'
          }
        ],
        // accountIdentifier: [
        //   {
        //     required: true,
        //     message: '账号不能为空',
        //     trigger: 'blur'
        //   }
        // ],
        telPhone: [
          {
            pattern: /^1[0-9]{10}$/,
            message: '请输入正确的手机号码'
          }
        ],
        orgList: [
          {
            required: true,
            message: '部门不能为空',
            trigger: ['change', 'blur']
          }
        ]
      }
    }
  },
  filters: {
    base64UrlFilter(url) {
      return base64UrlFilter(url)
    }
  },
  computed: {
    ...mapGetters({
      // permissionList: 'user/permissionList',
      userInfo: 'user/userInfo'
    })
  },
  mounted() {
    this.imageUrl = this.userInfo.photo
    this.getUserInfo()
  },
  methods: {
    getUserInfo() {
      const { orgList } = this.userInfo
      if (getObjType(orgList) === 'array' && orgList.length > 0) {
        this.orgList = orgList.map(e => e.orgName).join(';')
      }
      Object.keys(this.ruleForm).forEach(key => {
        this.ruleForm[key] = this.userInfo?.[key]
      })
    },
    handleAvatarSuccess(res, file) {
      const { length } = file
      this.imageUrl = window.URL.createObjectURL(file[length - 1].raw)
      this.userInfo.photo = this.imageUrl
    },
    beforeAvatarUpload({ type, size }) {
      const imgType = [
        'png',
        'jpg',
        'jpeg',
        'bmp',
        'gif',
        'webp',
        'psd',
        'svg',
        'tiff'
      ]
      const allowUploadType = imgType.includes(type.replace(/image\//g, ''))
      const isLt2M = size / 1024 / 1024 < 2
      if (!allowUploadType) {
        this.$message.error('上传图片格式不正确!')
      } else if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return allowUploadType && isLt2M
    },
    uploadSectionFile(param) {
      const fileObj = param.file
      const reader = new FileReader()
      reader.readAsDataURL(fileObj)
      reader.onload = e => {
        const _file = e.target.result.split('base64')[1]
        const formData = {
          photo: _file,
          userId: this.userInfo.id
        }
        uploadPhoto(formData)
          .then(res => {
            if (res.success) {
              this.$notify.success('上传头像成功')
            }
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    submitBill() {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          const params = {
            id: this.userInfo.id,
            ...this.ruleForm
          }
          editBaseUserInfo(params)
            .then(res => {
              if (res.success) {
                this.$notify.success({
                  title: '成功',
                  message: '修改成功'
                })
                this.$store.commit(`user/${SET_USERINFO}`, { ...this.ruleForm, id: this.userInfo.id })
                this.$router.push({
                  path: '/index'
                })
              }
            })
            .catch(e => {
              console.log(e)
            })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  margin: 16px 24px;
  padding: 20px;
  background-color: #ffffff;
  display: flex;
  flex-flow: row nowrap;
  justify-content: space-between;

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409eff;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 160px;
    height: 160px;
    border-radius: 80px;
    line-height: 160px;
    text-align: center;
    border: 1px dashed gray;
  }

  .avatar {
    width: 160px;
    height: 160px;
    border-radius: 80px;
    display: block;
    margin: 0 auto;
  }

  & .left {
    flex: 1;
    /*border: 1px solid red;*/
    padding-top: 85px;

    .user_main_info {
      /*border: 1px solid green;*/
      width: 70%;
      margin: 10px auto;

      .main_info_item {
        text-align: center;

        .main_info_item_label {
          padding: 0 10px;
        }

        .main_info_item_text {
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          width: 80%;
          /*border: 1px solid red;*/
          /*display: inline-block;*/
        }
      }
    }
  }

  & .right {
    flex: 2;
    /*border: 1px solid orange;*/
  }
}

/deep/ .el-input__inner,
/deep/ .el-textarea__inner {
  width: 350px;
}

.el-dialog__body {
  padding: 20px 40px;
}
</style>
