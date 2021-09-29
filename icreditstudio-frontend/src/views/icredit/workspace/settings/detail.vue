<!--
 * @Author: lizheng
 * @Description: 新增或编辑工作空间
 * @Date: 2021-08-17
-->
<template>
  <div class="workspace-setting">
    <div title="返回" class="back-icon" @click="handleBackClick">
      <j-svg name="back" />
    </div>
    <el-form
      v-loading="detailLoading"
      :model="detailForm"
      :rules="detailRules"
      ref="detailForm"
      label-width="125px"
      :disabled="opType === 'view'"
    >
      <el-form-item class="info-title">
        <div class="text" slot="label">基础信息</div>
      </el-form-item>

      <el-row>
        <el-col :span="8">
          <el-form-item label="工作空间名称" prop="name">
            <el-input
              clearable
              show-word-limit
              :maxlength="15"
              v-model="detailForm.name"
              type="text"
              placeholder="请输入工作空间名称"
              size="small"
            >
              <i
                v-if="veifyNameLoading"
                slot="suffix"
                class="el-icon-loading"
              ></i>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="状态" prop="status">
            <el-switch
              v-model="detailForm.status"
              :active-value="0"
              :inactive-value="1"
            ></el-switch>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="20">
          <el-form-item label="工作空间描述" prop="descriptor">
            <el-input
              clearable
              show-word-limit
              :maxlength="250"
              :rows="4"
              type="textarea"
              v-model="detailForm.descriptor"
              placeholder="请输入工作空间描述"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <div class="form-divider" />

      <el-form-item class="info-title">
        <div class="text" slot="label">用户角色信息</div>
      </el-form-item>

      <el-row>
        <el-col :span="8">
          <el-form-item label="负责人" prop="director">
            <el-select
              style="width: 100%"
              filterable
              clearable
              remote
              size="small"
              value-key="name"
              placeholder="请输入负责人名称进行查询"
              v-model="detailForm.director"
              :loading="userSelectLoading"
              :remote-method="getUsersFuzzySearch"
              @clear="handleUserClearClick"
              @change="handleUserChangeClick"
            >
              <el-option
                v-for="(item, idx) in userOptions"
                :key="`${item.id}-${idx}`"
                :label="item.name"
                :value="item"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item v-if="opType !== 'view'">
        <el-button type="primary" @click="handleUserSelect">
          {{
            id && detailForm.director ? '点击修改成员信息' : '点击添加成员信息'
          }}
        </el-button>
      </el-form-item>

      <el-row>
        <el-col :span="20">
          <el-form-item label="成员信息" prop="desc">
            <j-table
              ref="table"
              v-loading="tableLoading"
              :table-data="detailForm.memberList"
              :table-configuration="tableConfiguration"
            ></j-table>
          </el-form-item>
        </el-col>
      </el-row>

      <div class="form-divider" />
    </el-form>

    <div v-if="opType !== 'view'" class="footer-btn">
      <el-button
        class="btn"
        type="primary"
        :loading="btnLoading"
        @click="handleConfirm"
      >
        确定
      </el-button>
    </div>

    <UserSelect
      ref="usersSelect"
      @on-cancel="userSelectCallback"
      @on-confirm="userSelectCallback"
    />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import tableConfiguration from '@/views/icredit/configuration/table/workspace-setting-detail'
import crud from '@/mixins/crud'
import operate from '@/mixins/operate'
import API from '@/api/icredit'
import UserSelect from './users-select.vue'
import { debounce } from 'lodash'
import { verifySpecialStr } from '@/utils/validate'

export default {
  mixins: [crud, operate],
  components: { UserSelect },

  data() {
    this.getUsersFuzzySearch = debounce(this.getUsersFuzzySearch, 500)
    return {
      id: null,
      userSelectLoading: false,
      tableLoading: false,
      tableConfiguration,
      tableData: [],
      opType: '',
      btnLoading: false,
      veifyNameLoading: false,
      timerId: null,
      oldName: '',
      oldUserId: '',

      // 当前登录用户
      currentUser: {},
      // 选择的负责人
      selectedUser: {},

      // 工作空间表单
      detailForm: {
        name: '',
        status: 0,
        descriptor: '',
        director: '',
        memberList: []
      },
      detailRules: {
        name: [
          { required: true, message: '必填项不能为空', trigger: 'blur' },
          { validator: this.verifyName, trigger: 'blur' }
        ],
        status: [
          { required: true, message: '必填项不能为空', trigger: 'change' }
        ],
        director: [
          { required: true, message: '必填项不能为空', trigger: 'change' }
        ],
        descriptor: [
          { required: false, validator: verifySpecialStr, trigger: 'blur' }
        ]
      },
      userOptions: []
    }
  },

  computed: {
    ...mapGetters({ userInfo: 'user/userInfo' })
  },

  mounted() {
    this.$refs.detailForm.resetFields()
    this.initPage()
  },

  methods: {
    initPage() {
      const { query } = this.$route
      const {
        id,
        roleName,
        orgList,
        userName,
        functionalAuthority,
        dataAuthority
      } = this.userInfo

      // 当前登录用户
      this.currentUser = {
        id,
        username: userName,
        roleName,
        functionalAuthority,
        dataAuthority,
        createTime: new Date().getTime(),
        orgNames: orgList.map(({ orgName }) => orgName)
      }

      this.detailForm.memberList.unshift(this.currentUser)
      this.opType = query?.opType || ''
      this.id = query?.id || null
      this.id && this.handleEditClick('workspaceDetail', this.id)
    },

    // 点击打开添加成员信息弹窗
    handleUserSelect() {
      // id存在说明是编辑情况
      if (this.id) {
        const rightData = this.detailForm.memberList.map(item => {
          const {
            userId: id,
            username: userName,
            userRole: roleName,
            // orgName: orgNames,
            ...rest
          } = item
          return {
            id,
            userName,
            roleName,
            // orgNames,
            ...rest
          }
        })
        this.$refs.usersSelect.open('edit', rightData)
        return
      }
      this.detailForm.director
        ? this.$refs.usersSelect.open('add')
        : this.$refs.detailForm.validateField('director')
    },

    // 清空负责人
    handleUserClearClick() {
      const { oldUserId: uid } = this
      const idx = this.detailForm.memberList.findIndex(({ id }) => id === uid)
      idx > -1 && this.detailForm.memberList.splice(idx, 1)
      this.userOptions = []
    },

    // 选中负责人
    handleUserChangeClick(item) {
      const { id: uid } = item
      if (uid) {
        this.oldUserId = uid
        const user = this.userOptions.find(({ id }) => id === uid)
        const {
          id,
          roleName,
          orgList,
          name,
          functionalAuthority,
          dataAuthority
        } = user || {}

        this.selectedUser = {
          id,
          username: name,
          roleName,
          functionalAuthority,
          dataAuthority,
          createTime: new Date().getTime(),
          orgNames: orgList?.map(({ orgName }) => orgName)
        }
        this.detailForm.memberList.splice(1, 1, this.selectedUser)
      }
    },

    // 选择成员
    userSelectCallback({ opType, users }) {
      this.$refs.usersSelect.close()
      if (opType === 'confirm') {
        this.detailForm.memberList = users
          .map(item => {
            const {
              id: userId,
              userName: username,
              roleName: userRole,
              orgNames,
              functionalAuthority,
              dataAuthority
            } = item
            return {
              userId,
              username,
              userRole,
              orgNames,
              functionalAuthority,
              dataAuthority,
              createTime: new Date().getTime()
            }
          })
          .filter(
            ({ userId }) =>
              this.currentUser.id !== userId && this.selectedUser.id !== userId
          )
        this.detailForm.memberList.unshift(this.selectedUser)
        this.detailForm.memberList.unshift(this.currentUser)
      }
    },

    // 编辑操作数据回显
    mixinDetailInfo(data) {
      this.detailForm = data
      this.oldName = this.detailForm.name
    },

    // 返回按钮提示
    handleBackClick() {
      if (this.id || this.opType === 'view') {
        this.$router.replace('/workspace/space-setting')
      } else {
        this.$confirm('该工作空间内容尚未提交，请确认是否返回?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.$router.replace('/workspace/space-setting')
          })
          .catch(err => {
            console.log(err, 'err')
          })
          .finally(() => {})
      }
    },

    // 新增
    handleConfirm() {
      this.$refs.detailForm.validate(valid => {
        if (valid) {
          const { id: userId, userName: username } = this.userInfo
          const { memberList, ...restParams } = this.detailForm
          const newMemberList = memberList.map(
            ({ createTime, ...item }) => item
          )
          const params = {
            memberList: newMemberList,
            createUser: { userId, username },
            ...restParams
          }

          this.btnLoading = true
          API[`workspace${this.id ? 'Update' : 'Add'}`](params)
            .then(({ success }) => {
              if (success) {
                this.$notify.success({
                  title: '操作提示',
                  message: `工作空间${this.id ? '编辑' : '新增'}成功！`
                })
                this.$router.push('/workspace/space-setting')
              }
            })
            .finally(() => {
              this.btnLoading = false
            })
        }
      })
    },

    // 负责人查询模糊搜索
    getUsersFuzzySearch(name) {
      this.userSelectLoading = true
      API.getUserFluzzyQuery({ name })
        .then(({ success, data }) => {
          if (success) {
            this.userOptions = data
          }
        })
        .finally(() => {
          this.userSelectLoading = false
        })
    },

    // 名称校验
    verifyName(rule, value, cb) {
      // 特殊符号
      const regStr = /[`~!@#$%^&*()_\-+=<>?:"{}|,./;'\\[\]·~！@#￥%……&*（）——\-+={}|《》？：“”【】、；‘’，。、]/gi
      // 表情包
      const emojiRegStr = /[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f\u2026\u2022\u20ac\r\n]/gi
      const isValid = regStr.test(value) || emojiRegStr.test(value)
      if (isValid) {
        cb(new Error('该名称中包含不规范字符，请重新输入'))
      } else {
        this.verifyWorkspaceName(rule, value, cb)
      }
    },

    // 验证是否已经存在工作空间名称
    verifyWorkspaceName(rule, value, cb) {
      this.timerId = null
      if (this.id && this.oldName === value) {
        cb()
      } else {
        this.veifyNameLoading = true
        API.verifyWorkspaceName({ name: value })
          .then(({ success, data }) => {
            success && data ? cb(new Error('该名称已存在，请重新输入')) : cb()
          })
          .finally(() => {
            this.timerId = setTimeout(() => {
              this.veifyNameLoading = false
            }, 300)
          })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.workspace-setting {
  position: relative;
  background: #fff;
  width: 100%;
  height: calc(100vh - 126px);
  overflow-x: hidden;
  overflow-y: auto;

  .back-icon {
    margin: 20px 0 0 20px;
    &:hover {
      cursor: pointer;
    }
  }

  .info-title {
    position: relative;
    margin-top: 24px;
    font-size: 18px;
    font-family: PingFangSC, PingFangSC-Medium;
    font-weight: 600;
    text-align: left;
    color: #262626;
    line-height: 25px;

    .text {
      text-align: left;
      margin-left: 16px;
    }

    &::before {
      content: '';
      position: absolute;
      left: 0;
      bottom: 12px;
      width: 4px;
      height: 18px;
      opacity: 1;
      background: #1890ff;
      border-radius: 0px 2px 2px 0px;
    }
  }

  .status-text {
    @include flex;
    display: inline-flex;
    color: #1890ff;
    margin-left: 12px;
  }

  .form-divider {
    margin: 0 16px;
    border-bottom: 1px dashed #d9d9d9;
  }

  .footer-btn {
    text-align: center;
    margin: 20px 0;

    .btn {
      width: 150px;
      height: 40px;
      background: #1890ff;
      border-radius: 4px;
    }
  }
}
</style>
