<template>
  <div :class="['login-container', layout]">
    <!-- 左侧文字描述 -->
    <div class="login-text-box">
      <div class="system">
        <img class="logo" src="@/assets/svg/login/logo.svg" />
        <h3 class="system-name">{{ SYSTEM_NAME }}</h3>
      </div>
      <img class="icon" src="@/assets/svg/login/icon.svg" />
    </div>

    <!-- 右侧登录表单 -->
    <div class="login-form-box">
      <h2 class="login-form-header">登录</h2>
      <el-form
        ref="loginForm"
        class="login-form"
        :model="loginData"
        :rules="loginRules"
        @keyup.enter.native="handleLogin"
      >
        <el-form-item prop="loginName" label="账号">
          <el-input
            v-model="loginData.loginName"
            type="input"
            class="login_input"
            :prefix-icon="getInputIcon(0)"
            placeholder="请输入您的账号"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input
            v-model="loginData.password"
            type="password"
            class="login_input"
            show-password
            :prefix-icon="getInputIcon(1)"
            placeholder="请输入您的密码"
            autocomplete="off"
          >
            <i slot="prefix" class="el-input__icon icon-pass" />
          </el-input>
        </el-form-item>
        <el-form-item prop="code" v-if="isShowCode">
          <div class="login-code-box iframe-flex-row-sp-center">
            <el-input
              v-model="loginData.code"
              type="password"
              class="login_input"
              :prefix-icon="getInputIcon(2)"
              placeholder="请输入验证码"
              autocomplete="off"
            ></el-input>
            <el-button @click="fetchCode">{{ codeText }}</el-button>
          </div>
        </el-form-item>
      </el-form>
      <div class="login-save-pass iframe-flex-row-sp-center">
        <el-checkbox
          class="iframe-flex-row-start-center"
          v-model="isSavePassword"
        >
          记住密码
        </el-checkbox>
        <el-button
          :loading="btnLoading"
          type="primary"
          class="login-submit"
          @click="handleLogin"
        >
          登录
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
import _ from 'lodash'
import dayjs from 'dayjs'
import { mapActions } from 'vuex'
import { sm4Config } from '@/config/index'
import { SYSTEM_NAME } from '@/config/constant'

const ICONS = ['el-icon-s-custom', 'el-icon-s-comment', 'el-icon-s-help']
export default {
  name: 'UserLogin',

  data() {
    const validatePass = (rule, value, callback) => {
      return callback(value ? undefined : new Error('请输入密码'))
    }

    return {
      SYSTEM_NAME,
      btnLoading: false,
      time: dayjs().format('HH:mm:ss'),
      isShowIcon: false, // icon
      isShowCode: false, // code
      layout: 'iframe-flex-row-center-center', // layout
      isSavePassword: false, // 是否记住密码
      codeText: '获取验证码',
      loginData: { loginName: '', password: '', code: '' },
      loginRules: {
        loginName: { required: true, message: '请输入用户名', trigger: 'blur' },
        code: { required: true, message: '请输入验证码', trigger: 'blur' },
        password: { required: true, validator: validatePass, trigger: 'blur' }
      }
    }
  },

  created() {
    const _loginInfo = this.$ls.get('loginInfo')
    if (_loginInfo) {
      this.loginData = Object.assign(this.loginData, _loginInfo)
      this.isSavePassword = true
    }

    // 背景定时器
    const timeInterval = setInterval(() => {
      this.refreshTime()
    }, 1000)
    this.$once('hook:beforeDestroy', () => {
      clearInterval(timeInterval)
    })
  },

  methods: {
    ...mapActions('user', ['loginAction']),
    /**
     * @desc 获取input icon
     * @param {Number} index icon下标
     * @return {string}
     */
    getInputIcon(index) {
      return this.isShowIcon ? ICONS[index] : ''
    },
    fetchCode() {
      if (this.codeText !== '获取验证码') return
      this.codeText = 10
      const timer = setInterval(() => {
        if (this.codeText <= 0) {
          this.codeText = '获取验证码'
          clearInterval(timer)
        } else {
          this.codeText--
        }
      }, 1000)
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          const loginObj = _.cloneDeep(this.loginData)
          const SM4 = require('gm-crypt').sm4
          const sm4 = new SM4(sm4Config)
          this.btnLoading = true
          loginObj.password = sm4.encrypt(loginObj.password)
          this.loginAction({ ...loginObj })
            .then(({ success, data }) => {
              if (success && data) {
                const { userId } = data
                const { loginName, password } = this.loginData
                this.isSavePassword
                  ? this.$ls.set('loginInfo', { loginName, password, userId })
                  : this.$ls.remove('loginInfo')
                this.$router.replace({ path: '/' }).catch(() => {})
                this.btnLoading = false
              }
            })
            .catch(err => {
              console.log(err)
              this.btnLoading = false
            })
        }
      })
    },
    refreshTime() {
      this.time = dayjs().format('HH:mm:ss')
    }
  }
}
</script>

<style lang="scss" scoped>
.center {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.login-container {
  height: 100vh;
  background-color: #fff;
  overflow: hidden;
  font-family: PingFangSC, PingFangSC-Medium;

  .login-text-box {
    @extend .center;
    position: relative;
    width: 50%;
    height: 100%;

    .system {
      width: 60%;
      margin-bottom: 50px;
      margin-left: -60px;

      .logo {
        width: 74px;
        height: 63px;
        margin-bottom: 10px;
      }

      .system-name {
        font-size: 26px;
        font-weight: 500;
        color: #000;
      }
    }

    .icon {
      width: 50%;
    }
  }

  .login-form-box {
    width: 50%;
    padding-left: 160px;
    z-index: 1;

    .login-form {
      width: 500px;

      .login_input {
        ::v-deep .el-input__inner {
          height: 54px;
          padding-left: 15px;
        }
      }

      .el-form-item {
        margin-bottom: 35px;
      }

      ::v-deep .el-form-item__label {
        color: #000;
        font-weight: 400;
      }
    }

    .login-save-pass {
      width: 500px;

      ::v-deep .el-checkbox {
        .el-checkbox__label {
          width: 56px;
          height: 22px;
          opacity: 1;
          font-size: 14px;
          font-family: PingFangSC, PingFangSC-Regular;
          font-weight: 400;
          text-align: left;
          color: rgba(0, 0, 0, 0.65);
          line-height: 22px;
        }
      }

      span {
        color: $--color-text-secondary;
        cursor: pointer;
      }
    }

    .login-form-header {
      height: 40px;
      font-size: 28px;
      font-weight: 500;
      color: #262626;
      line-height: 40px;
      margin-bottom: 40px;
    }

    .login-submit {
      width: 162px;
      height: 48px;
      background: #1890ff;
      border-radius: 8px;
    }
  }

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-image: url(~@/assets/svg/login/bg.png);
    background-size: 100%;
    background-repeat: no-repeat;
    z-index: 0;
  }
}
</style>
