<template>
  <div class="w100 h100">
    <crud-basic
      class="user-container"
      ref="crud"
      title="用户列表"
      :form-items-search="mixinSearchFormItems"
      :form-func-search="mixinSearchFormFunc"
      :form-config-search="mixinSearchFormConfig"
      :form-items-dialog="mixinDialogFormItems"
      :form-func-dialog="mixinDialogFormFunc"
      :form-config-dialog="mixinDialogFormConfig"
      :tableLoading="mixinTableLoading"
      :table-configuration="tableConfiguration"
      :table-pagination="mixinTablePagination"
      :table-data="mixinTableData"
      :dialog-type="mixinDialogType"
      :dialog-visible.sync="mixinDialog"
      :handleSizeChange="mixinHandleSizeChange"
      :handleCurrentChange="mixinHandleCurrentChange"
      :handleAdd="mixinHandleAdd"
      :handleSearch="mixinHandleSearch"
      :handleReset="mixinHandleReset"
      :handleImport="mixinHandleImport"
      :handleExport="mixinHandleExport"
      :handleUpdate="mixinHandleCreateOrUpdate"
      :handleCancel="mixinHandleCancel"
      @handleExportTemplate="handleExportTemplate"
    >
    </crud-basic>
    <el-dialog
      title="配置角色"
      :visible.sync="roleSetDialogFlag"
      width="500px"
      :close-on-click-modal="false"
    >
      <div id="roleSetScroll" class="role-set-resource">
        <div class="role-tree-filter">
          <el-input
            placeholder="输入关键字进行过滤"
            v-model="roleSetModels.tree.filterRoleName"
          >
          </el-input>
        </div>
        <div class="role-tree">
          <el-tree
            ref="roleTree"
            :key="roleSetDialogFlag"
            :data="roleSetModels.tree.roleTreeData"
            :filter-node-method="filterRoleTreeNode"
            default-expand-all
            node-key="id"
            show-checkbox
            check-strictly
            :props="roleSetModels.tree.defaultOrgProps"
            :expand-on-click-node="false"
            check-on-click-node
            @check="roleHandleCheck"
          >
            <span class="custom-tree-node" slot-scope="{ node }">
              <span :title="node.label" class="role-tree-label">{{
                node.label
              }}</span>
            </span>
          </el-tree>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="roleSetHandleClose">取 消</el-button>
        <el-button type="primary" @click="roleSetHandleCreateOrUpdate"
          >保 存</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import crud from '@/mixins/crud'
import { arrayToTree, deepClone, getObjType } from '@/utils/util'
import {
  queryAllOrgs,
  setUserStatus,
  setUserToRoles,
  resetPassword,
  modelExport
} from '@/api/user'
import { getChildrenRoles, getRoleInfoByUserId } from '@/api/role'
import fileDownload from 'js-file-download'
import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-user'
import formOption from '@/views/system-basic/configuration/form/manage/manage-user'
import { SET_USERINFO } from '@/store/mutation-types'
import { DEFAULT_HEAD_IMG_URL } from '@/config/constant'

export default {
  name: 'ManageUser',
  mixins: [crud],
  data() {
    return {
      formOption,
      mixinSearchFormConfig: {
        models: {
          userName: '',
          accountIdentifier: '',
          telPhone: '',
          orgList: []
        },
        retrieveModels: {
          userId: ''
        }
      },
      mixinDialogFormConfig: {
        models: {
          userName: '',
          userCode: '',
          userBirth: '',
          sortNumber: '',
          telPhone: '',
          accountIdentifier: '',
          userGender: '',
          deleteFlag: 'N',
          orgList: [],
          userRemark: ''
        },
        rule: {
          userName: [
            { required: true, message: '用户姓名不能为空', trigger: 'blur' }
          ],
          accountIdentifier: [
            { required: true, message: '账号不能为空', trigger: 'blur' }
          ],
          telPhone: [
            { pattern: /^1[0-9]{10}$/, message: '请输入正确的手机号码' }
          ],
          orgList: [
            {
              required: true,
              message: '部门不能为空',
              trigger: ['change', 'blur']
            }
          ]
        }
      },
      tableConfiguration: tableConfiguration(this),
      DEFAULT_HEAD_IMG_URL,
      fetchConfig: {
        retrieve: {
          url: '/system/user/user/pageList',
          method: 'post'
        },
        create: {
          url: '/system/user/user/save',
          method: 'post'
        },
        update: {
          url: '/system/user/user/update',
          method: 'post'
        },
        delete: {
          url: '/system/user/user/delete',
          method: 'post'
        },
        export: {
          url: '/system/user/user/exportExcel',
          method: 'get'
        },
        import: {
          url: '/system/user/user/importExcel',
          method: 'get'
        }
      },
      // 配置角色相关 start↓
      roleSetDialogFlag: false,
      roleSetModels: {
        // 角色树
        tree: {
          defaultOrgProps: {
            children: 'children',
            label: 'label'
          },
          filterRoleName: '',
          roleTreeData: [],
          roleIds: []
        }
      }
      // 配置角色相关 end↑
    }
  },
  computed: {
    ...mapGetters({
      permissionList: 'user/permissionList',
      userInfo: 'user/userInfo'
    })
  },
  watch: {
    'roleSetModels.tree.filterRoleName': val => {
      this.$refs.roleTree.filter(val)
    }
  },
  created() {
    this.mixinSearchFormItems = deepClone(this.formOption).filter(
      e => e.isSearch
    )
    getChildrenRoles({ userId: this.userInfo.id, deleteFlag: 'N' })
      .then(res => {
        this.roleSetModels.tree.roleTreeData = arrayToTree(
          res.data.map(e => {
            return {
              label: e.roleName,
              children: e.sonNum !== '0',
              disabled: e.operateFlag !== '1',
              ...e
            }
          }),
          '0'
        )
      })
      .catch(err => {
        console.log('getChildrenRoles -> err', err)
      })
    this.mixinSearchFormConfig.retrieveModels.userId = this.userInfo.id || ''
    this.mixinRetrieveTableData()
    // this.retrieveAllRoles()
    this.retrieveAllOrgs()
  },
  methods: {
    ...mapActions('user', ['logoutAction', 'setUserInfo']),
    // 配置角色相关代码 start ↓
    // 配置角色
    mixinHandleRoleSet(row) {
      this.mixinUpdate = row.row
      // 获取当前用户的已配置角色数据
      getRoleInfoByUserId({ userId: this.mixinUpdate.id })
        .then(res => {
          this.roleSetDialogFlag = true
          this.$nextTick(() => {
            this.$refs.roleTree.setCheckedKeys(res.data.map(e => e.id))
          })
        })
        .catch(err => {
          console.log('getRoleInfoByUserId -> err', err)
        })
    },
    // 过滤查询角色树
    filterRoleTreeNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    roleHandleCheck(nodeObj, selectObj) {
      const { checkedKeys } = selectObj
      this.roleSetModels.tree.roleIds = checkedKeys
    },
    roleSetHandleClose() {
      this.roleSetDialogFlag = false
    },
    roleSetHandleCreateOrUpdate() {
      setUserToRoles({
        roleIds: this.roleSetModels.tree.roleIds,
        userId: this.mixinUpdate.id
      }).then(() => {
        this.$notify.success('配置角色成功')
        this.roleSetHandleClose()
        this.mixinRetrieveTableData()
      })
    },
    // 配置角色相关代码 end ↑
    interceptorsRequestRetrieve(params) {
      const { orgList } = params
      if (Array.isArray(orgList) && orgList.length > 0) {
        return {
          ...params,
          orgIds: orgList.map(e => e[e.length - 1])
        }
      } else {
        return params
      }
    },
    interceptorsResponseTableData(list) {
      return list.map(e => {
        const _org = e.orgList || []
        return {
          ...e,
          photo: e.photo || this.DEFAULT_HEAD_IMG_URL,
          orgList: _org.reduce((pre, cur) => {
            pre.push(cur.orgPath ? cur.orgPath.split(',') : cur.orgPath)
            return pre
          }, [])
        }
      })
    },
    retrieveAllOrgs() {
      // onlyReturnCurrAndSon: false
      queryAllOrgs({ userId: this.userInfo.id, deleteFlag: 'N' })
        .then(res => {
          // const rootParentId = res.data.find(x => x.currOrg)?.parentId || '0'
          const temp = arrayToTree(
            res.data.map(e => {
              return {
                ...e,
                disabled: e.operateFlag !== '1'
              }
            }),
            '0'
          )
          this.formOption.find(e => e.ruleProp === 'orgList').options = temp
          this.mixinSearchFormItems.find(
            e => e.ruleProp === 'orgList'
          ).options = temp
        })
        .catch(err => {
          console.log('queryAllOrgs -> err', err)
        })
    },
    interceptorsRequestCreate(e) {
      return {
        ...e,
        orgList: this._handleOrgData(e.orgList)
      }
    },
    interceptorsRequestUpdate(e) {
      return {
        ...e,
        orgList: this._handleOrgData(e.orgList)
      }
    },
    interceptorsResponseUpdate(data) {
      // 如果修改的是当前登录用户，刷新当前登录信息
      if (this.mixinUpdate.id === this.userInfo.id) {
        this.$store.commit(`user/${SET_USERINFO}`, data)
      }
      return false
    },
    // 导出前置拦截 搜索条件部门必选
    interceptorsRequestImport(file) {
      const {
        mixinSearchFormConfig: {
          models: { orgList }
        }
      } = this
      // 校验部门必填
      const orgListArr = this._handleOrgData(orgList)
      const valid =
        getObjType(orgListArr) === 'array' && orgListArr.length === 1
      if (valid) {
        file.append('orgId', orgListArr[0].orgId)
        file.append('orgPath', orgListArr[0].orgPath)
        return file
      } else {
        this.$message.warning('请选择一条要导入用户的部门！')
        return null
      }
    },
    handleExportTemplate() {
      modelExport()
        .then(res => {
          fileDownload(res, '列表数据模板导出.xlsx')
        })
        .catch(err => {
          console.log('modelExport -> err', err)
        })
    },
    _handleOrgData(ids) {
      return ids.map(e => {
        return {
          orgId: e[e.length - 1],
          orgPath: e.join(',')
        }
      })
    },
    handleStatusChange(e) {
      setUserStatus({ userId: e.scope.row.id, deleteFlag: e.value }).then(
        () => {
          this.$notify.success(`${e.value === 'Y' ? '禁用' : '启用'}成功`)
          this.mixinRetrieveTableData()
          if (
            e.scope.row.userName === this.userInfo.userName &&
            e.value === 'Y'
          ) {
            setTimeout(() => {
              this.logoutAction().then(() => {
                this.$router
                  .replace({
                    path: '/login'
                  })
                  .then(() => {
                    this.$notify.warning('当前用户已被禁用!')
                  })
              })
            }, 1000)
          }
        }
      )
    },
    mixinHandleResetPwd(e) {
      this.$confirm('是否重置密码?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          resetPassword({ userIdList: [e.row.id] }).then(() => {
            this.$notify.success('重置成功')
          })
        })
        .catch(err => {
          console.log(err)
        })
    }
  }
}
</script>
<style lang="scss" scoped>
/deep/.el-dialog__body {
  padding: 10px 20px;
}
.role-set-resource {
  height: 450px;
  border-radius: 10px;
  /*background: rgba(249, 249, 249, 1);*/
  /*display: flex;*/
  /*justify-content: space-between;*/
  & .role-tree {
    margin-top: 5px;
    overflow-y: scroll;
    overflow-x: hidden;
    height: 90%;

    & .role-tree-label {
      display: inline-block;
      text-overflow: ellipsis;
      white-space: nowrap;
      overflow: hidden;
      max-width: 360px;
    }
  }
}
/deep/.user-container .el-table tr td {
  padding: 8px 0 0;
}
</style>
