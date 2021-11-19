<template>
  <div class="container w100 h100">
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      class="center-ruleForm"
    >
      <el-form-item label="旧密码" prop="oldpassword">
        <el-input
          v-model="ruleForm.oldpassword"
          type="password"
          placeholder="请输入"
        ></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newpassword">
        <el-input
          v-model="ruleForm.newpassword"
          type="password"
          placeholder="请输入"
        ></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confrimpassword">
        <el-input
          v-model="ruleForm.confrimpassword"
          type="password"
          placeholder="请输入"
        ></el-input>
      </el-form-item>
    </el-form>
    <div class="center-div">
      <el-button type="primary" @click="submitBill">确认修改</el-button>
    </div>
  </div>
</template>

<script>
import { editAccountPwd } from '@/api/user'
import { mapActions } from 'vuex'
import _ from 'lodash'
import { sm4Config } from 'config'

export default {
  name: 'changePassword',
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入确认密码'))
      } else if (value !== this.ruleForm.newpassword) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      ruleForm: {
        oldpassword: '',
        newpassword: '',
        confrimpassword: ''
      },
      rules: {
        oldpassword: [
          {
            required: true,
            message: '请输入旧密码',
            trigger: 'blur'
          }
        ],
        newpassword: [
          {
            required: true,
            message: '请输入新密码',
            trigger: 'blur'
          }
        ],
        confrimpassword: [
          {
            required: true,
            message: '请输入确认密码',
            trigger: 'blur'
          },
          { validator: validatePass, trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    console.log(this.$store.state.user.userInfo.id)
  },
  methods: {
    ...mapActions('user', ['logoutAction']),
    checkPwdUpdate(rule, value, callback) {
      if (value) {
        const regExp = /^(?=.*\d)(?=.*[A-Za-z])[\x20-\x7e]{8,16}$/
        if (!regExp.test(value)) {
          callback(new Error('请填写8-16位数字、字符组合'))
        } else {
          callback()
        }
      } else {
        callback()
      }
    },
    submitBill() {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          const obj = _.cloneDeep(this.ruleForm)
          const SM4 = require('gm-crypt').sm4
          const sm4 = new SM4(sm4Config)
          obj.oldpassword = sm4.encrypt(obj.oldpassword)
          obj.newpassword = sm4.encrypt(obj.newpassword)
          console.log(obj)
          editAccountPwd({
            newPassWord: obj.newpassword,
            oldPassWord: obj.oldpassword,
            userId: this.$store.state.user.userInfo.id
          }).then(() => {
            this.$message.success('修改密码成功')
            setTimeout(() => {
              this.logoutAction().then(() => {
                this.$router
                  .replace({
                    path: '/login'
                  })
                  .then(() => {
                    window.location.reload()
                  })
              })
            }, 1000)
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
.container {
  // margin: 16px 24px;
  padding: 20px;
  background-color: #ffffff;
  .center-ruleForm {
    width: 400px;
    margin: 0 auto;
    margin-top: 30px;
  }

  .center-div {
    width: 400px;
    margin: 0 auto;
    display: flex;
    justify-content: center;

    .el-button {
      padding: 10px 40px;
    }
  }
}

.el-dialog__body {
  padding: 20px 40px;
}
</style>
