<template>
  <div class="h100">
    <crud-basic
      ref="crud"
      title="角色列表"
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
      :handleImport="mixinHandleImport"
      :handleExport="mixinHandleExport"
      :handleSearch="mixinHandleSearch"
      :handleReset="mixinHandleReset"
      :handleUpdate="mixinHandleCreateOrUpdate"
      :handleCancel="mixinHandleCancel"
    ></crud-basic>
    <el-dialog
      title="配置用户"
      :close-on-click-modal="false"
      :visible.sync="userSetDialogFlag"
      top="10vh"
      width="1346px"
    >
      <div id="userSetScroll" class="tree-choose-transfer-box">
        <div class="left">
          <div class="tree-desc">
            <span>部门树</span>
          </div>
          <div class="left-tree">
            <el-tree
              ref="orgTree"
              :key="userSetDialogFlag"
              :data="userSetModels.tree.orgTreeData"
              default-expand-all
              node-key="id"
              show-checkbox
              check-strictly
              :props="userSetModels.tree.defaultOrgProps"
              :expand-on-click-node="false"
              check-on-click-node
              @check="orgHandleCheck"
            >
              <span class="custom-tree-node" slot-scope="{ node }">
                <span :title="node.label" class="org-tree-label">{{
                  node.label
                }}</span>
              </span>
            </el-tree>
          </div>
        </div>
        <div class="right">
          <j-transfer-table
            ref="userTransfer"
            :titles="['待选用户', '已选用户']"
            :table-loading="tableLoading"
            :table-filter-config="userSetModels.transfer.filterConfig"
            :left-table-configuration="
              userSetModels.transfer.leftTableConfiguration
            "
            :left-data.sync="userSetModels.transfer.leftData"
            :right-data.sync="userSetModels.transfer.rightData"
          ></j-transfer-table>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="userSetHandleClose">取 消</el-button>
        <el-button type="primary" @click="userSetHandleCreateOrUpdate"
          >保 存</el-button
        >
      </span>
    </el-dialog>
    <!--end-->
    <el-dialog
      title="配置功能"
      top="10vh"
      :visible.sync="authDialogFlag"
      :close-on-click-modal="false"
      width="1346px"
    >
      <div id="authorityScroll" class="tree-choose-transfer-box">
        <div class="left">
          <div class="tree-desc">
            <span>模块树</span>
          </div>
          <div class="left-tree">
            <el-tree
              ref="authTree"
              :data="authSetModels.tree.authTreeData"
              default-expand-all
              node-key="id"
              show-checkbox
              :check-strictly="false"
              :props="authSetModels.tree.defaultOrgProps"
              :expand-on-click-node="false"
              check-on-click-node
              @check="authHandleCheck"
            >
              <span class="custom-tree-node" slot-scope="{ node }">
                <span :title="node.label" class="org-tree-label">{{
                  node.label
                }}</span>
              </span>
            </el-tree>
          </div>
        </div>
        <div class="right">
          <j-transfer-table
            ref="authTransfer"
            :titles="['待选模块', '已选模块']"
            :table-loading="tableLoading"
            :table-filter-config="authSetModels.transfer.filterConfig"
            :left-table-configuration="
              authSetModels.transfer.leftTableConfiguration
            "
            :left-data.sync="authSetModels.transfer.leftData"
            :right-data.sync="authSetModels.transfer.rightData"
          ></j-transfer-table>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="authHandleCancel">取 消</el-button>
        <el-button type="primary" @click="authHandleCreateOrUpdate"
          >保 存</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import crud from '@/mixins/crud'
import { arrayToTree, deepClone, getObjType } from '@/utils/util'
import { getAuthList } from '@/api/auth'
import {
  setRoleStatus,
  getChildrenRoles,
  getUserInfoByRoleId,
  setRoleToUsers,
  getResourcesFromRole,
  setResourcesToRole
} from '@/api/role'
import { queryAllOrgs, getUserInfosByOrgIds } from '@/api/user'

import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-role'
import authSetTableConfiguration from '@/views/system-basic/configuration/table/manage/manage-auth-set'
import userSetTableConfiguration from '@/views/system-basic/configuration/table/manage/manage-user-set'
import formOption from '@/views/system-basic/configuration/form/manage/manage-role'
import JTransferTable from '@/components/transfer-table'

export default {
  name: 'ManageRole',
  components: { JTransferTable },
  mixins: [crud],
  data() {
    // 配置用户》上级角色校验：非空且不能与本身角色相同
    const checkParentId = (rule, value, callback) => {
      if (getObjType(value) === 'array') {
        const len = value.length
        if (len === 0) {
          return callback(new Error('上级角色不能为空'))
        } else if (value[value.length - 1] === this.mixinUpdate.id) {
          return callback(new Error('角色类型不能和上级角色相同'))
        } else {
          callback()
        }
      }
    }
    return {
      tableLoading: false,
      formOption: formOption(this),
      mixinSearchFormConfig: {
        models: {
          roleName: '',
          deleteFlag: ''
        },
        retrieveModels: {
          lazy: true,
          userId: ''
        }
      },
      mixinDialogFormConfig: {
        models: {
          roleName: '',
          deleteFlag: '',
          parentId: [],
          roleRemark: ''
        },
        rule: {
          roleName: [
            {
              required: true,
              message: '角色名称不能为空',
              trigger: 'blur'
            }
          ],
          deleteFlag: [
            {
              required: true,
              message: '状态不能为空',
              trigger: 'blur'
            }
          ],
          parentId: [
            {
              required: true,
              validator: checkParentId,
              trigger: 'blur'
            }
          ]
        }
      },
      tableConfiguration: tableConfiguration(this),
      // parentTreeData: [], // 存放所有的parentId的同步树数据
      fetchConfig: {
        retrieve: {
          url: '/system/role/role/getChildrenRoles',
          method: 'post'
        },
        lazy: {
          url: '/system/role/role/getChildrenRoles',
          method: 'post'
        },
        create: {
          url: '/system/role/role/save',
          method: 'post'
        },
        update: {
          url: '/system/role/role/update',
          method: 'post',
          id: 'id'
        },
        delete: {
          url: '/system/role/role/delete',
          method: 'post',
          id: 'id'
        },
        export: {
          url: '/system/role/role/exportExcel',
          method: 'get'
        },
        import: {
          url: '/system/role/role/importExcel',
          method: 'get'
        }
      },
      // 权限相关代码
      authDialogFlag: false,
      authDialogFormItems: [
        {
          type: 'checkbox',
          label: '权限',
          model: [],
          ruleProp: 'menus',
          options: []
        }
      ],
      authDialogFormFunc: [
        {
          btnText: '保存',
          btnEmitName: 'mixinSave',
          loading: false,
          type: 'primary'
        },
        {
          btnText: '取消',
          btnEmitName: 'mixinCancel'
        }
      ],
      authDialogFormConfig: {
        models: {},
        refName: 'submitForm'
      },
      // 配置用户相关 start↓
      userSetDialogFlag: false,
      userSetModels: {
        // 左侧部门树相关
        tree: {
          defaultOrgProps: {
            children: 'children',
            label: 'label'
          },
          filterOrgName: '',
          orgTreeData: []
        },
        // 右侧用户选择穿梭树相关
        transfer: {
          tableLoading: false,
          filterConfig: {
            placeholder: '请输入用户查询',
            prop: 'userName'
          },
          leftTableConfiguration: userSetTableConfiguration,
          leftData: [],
          rightData: []
        }
      },
      // 配置用户相关 end↑
      // 配置功能相关start↓
      authSetModels: {
        resourceIds: [],
        // 左侧部门树相关
        tree: {
          defaultOrgProps: {
            children: 'children',
            label: 'label'
          },
          authTreeData: []
        },
        // 右侧用户选择穿梭树相关
        transfer: {
          tableLoading: false,
          filterConfig: {
            placeholder: '请输入模块查询',
            prop: 'name'
          },
          leftTableConfiguration: authSetTableConfiguration,
          leftData: [],
          rightData: []
        }
      }
      // 配置功能相关end ↑
    }
  },
  computed: {
    ...mapGetters({
      permissionList: 'user/permissionList',
      userInfo: 'user/userInfo'
    })
  },
  created() {
    getAuthList({ deleteFlag: 'N' }).then(res => {
      this.authSetModels.tree.authTreeData = arrayToTree(
        res.data.reduce((pre, cur) => {
          cur.operateFlag === '1' && pre.push({ label: cur.name, ...cur })
          return pre
        }, []),
        '0'
      )
    })
    queryAllOrgs().then(res => {
      this.userSetModels.tree.orgTreeData = arrayToTree(
        res.data.reduce((pre, cur) => {
          cur.operateFlag === '1' && pre.push({ label: cur.orgName, ...cur })
          return pre
        }, []),
        '0'
      )
    })
    this.mixinSearchFormItems = deepClone(this.formOption).filter(
      e => e.isSearch
    )
    this.mixinSearchFormConfig.retrieveModels.userId = this.userInfo.id || ''
    this.mixinRetrieveTableData()
  },
  methods: {
    handleStatusChange(e) {
      const urlObj = {
        Y: '禁用',
        N: '启用'
      }
      setRoleStatus({
        id: e.scope.row.id,
        deleteFlag: e.value
      }).then(() => {
        this.$notify.success(`${urlObj[e.value]}成功`)
        this.mixinRetrieveTableData()
      })
    },
    // 配置用户相关代码 start ↓
    // 配置用户
    mixinHandleUserSet(row) {
      this.userSetDialogFlag = true
      this.mixinUpdate = row.row
      // 获取当前角色的已配置用户数据
      getUserInfoByRoleId({ roleId: this.mixinUpdate.id }).then(res => {
        if (res.success) {
          // 筛选出部门ids,并通过ids查询所有待选项
          // const orgIdsSet = new Set()
          // res.data.forEach(e => {
          //   e.orgIds.forEach(x => {
          //     orgIdsSet.add(x)
          //   })
          // })
          // const orgIds = [...orgIdsSet]
          this.$nextTick(() => {
            this.$refs.orgTree.setCheckedKeys([])
            this.$refs.userTransfer.init()
          })
          const {
            userSetModels: {
              transfer: { leftData, rightData }
            }
          } = this
          leftData.splice(0, leftData.length)
          rightData.splice(0, rightData.length, ...res.data)
          // this.queryUserLeftTableDataByIds(orgIds)
        }
      })
    },
    queryUserLeftTableDataByIds(checkedKeys) {
      this.tableLoading = true
      getUserInfosByOrgIds({
        orgIds: checkedKeys,
        deleteFlag: this.mixinUpdate.deleteFlag
      })
        .then(res => {
          const {
            userSetModels: {
              transfer: { leftData, rightData }
            }
          } = this
          const existIds = rightData.map(x => x.id)
          // 穿梭框leftTable赋值(排除右边已有项)
          leftData.splice(
            0,
            leftData.length,
            ...res.data.filter(x => !existIds.includes(x.id))
          )
        })
        .finally(() => {
          this.tableLoading = false
        })
    },
    orgHandleCheck(nodeObj, selectObj) {
      const { checkedKeys } = selectObj
      if (checkedKeys.length === 0) {
        const {
          authSetModels: {
            transfer: { leftData }
          }
        } = this
        leftData.splice(0, leftData.length)
      } else {
        // 根据部门ID集合，查询各部门用户列表
        this.queryUserLeftTableDataByIds(checkedKeys)
      }
    },
    userSetHandleClose() {
      this.$refs.orgTree.setCheckedKeys([])
      this.userSetDialogFlag = false
    },
    userSetHandleCreateOrUpdate() {
      const {
        userSetModels: {
          transfer: { rightData }
        }
      } = this
      setRoleToUsers({
        userIds: rightData.map(x => x.id),
        roleId: this.mixinUpdate.id
      }).then(() => {
        this.$notify.success('配置用户成功')
        this.userSetDialogFlag = false
      })
    },
    // 配置用户相关代码 end ↑
    // 配置功能相关代码 start ↓
    mixinAuthSet(row) {
      this.mixinUpdate = row.row
      getResourcesFromRole({ roleId: this.mixinUpdate.id }).then(res => {
        if (res.success) {
          // const resourceIds = res.data.map(x => x.id)
          this.authDialogFlag = true
          this.$nextTick(() => {
            // this.$refs.authTree.setCheckedKeys(resourceIds)
            this.$refs.authTree.setCheckedKeys([])
            this.$refs.authTransfer.init()
          })

          const {
            authSetModels: {
              transfer: { leftData, rightData }
            }
          } = this
          leftData.splice(0, leftData.length)
          rightData.splice(0, rightData.length, ...res.data)
          // this.queryAuthLeftTableDataByIds(resourceIds)
        }
      })
    },
    queryAuthLeftTableDataByIds(ids) {
      getAuthList({ ids, deleteFlag: 'N' }).then(res => {
        const {
          authSetModels: {
            transfer: { leftData, rightData }
          }
        } = this
        const existIds = rightData.map(x => x.id)
        // 穿梭框leftTable赋值(排除右边已有项)
        leftData.splice(
          0,
          leftData.length,
          ...res.data.filter(x => !existIds.includes(x.id))
        )
      })
    },
    authHandleCheck(nodeObj, selectObj) {
      const { checkedKeys } = selectObj
      if (checkedKeys.length === 0) {
        const {
          authSetModels: {
            transfer: { leftData }
          }
        } = this
        leftData.splice(0, leftData.length)
      } else {
        // 根据模块ID集合，返回各待选模块列表
        this.queryAuthLeftTableDataByIds(checkedKeys)
      }
    },
    authHandleCreateOrUpdate() {
      const {
        authSetModels: {
          transfer: { rightData }
        }
      } = this
      setResourcesToRole({
        resourcesIds: rightData.map(x => x.id),
        roleId: this.mixinUpdate.id
      }).then(() => {
        this.$notify.success('配置功能成功')
        this.authDialogFlag = false
        // this.mixinRetrieveTableData()
      })
    },
    authHandleCancel() {
      this.$refs.authTree.setCheckedKeys([])
      this.authDialogFlag = false
    },
    // 配置功能相关代码 end ↑
    interceptorsRequestCreate(param) {
      return this._handleParentIdData(param)
    },
    interceptorsRequestUpdate(param) {
      return this._handleParentIdData(param)
    },
    interceptorsResponseTableData(data) {
      // 首次进入时,懒加载
      if (this.lazyFirstInit) {
        // 暂时用不上，如果要重复执行首次懒加载table树的过程，则放开以下注释
        // this.tableConfiguration.lazy = true
        // this.tableConfiguration.tableKey = 'lazy-true'
        // this.tableConfiguration.defaultExpandAll = false
        this.mixinSearchFormConfig.retrieveModels.lazy = false
        this.lazyFirstInit = false
        // this.retrieveAllChildrenRoles()// 查询所有同步树结构
        return this.transToLazyTableData(data)
      } else {
        // 查询请求,将返回的平铺结构转为树结构数据
        // this.retrieveAllChildrenRoles()// 查询所有同步树结构
        this.tableConfiguration.lazy = false
        this.tableConfiguration.tableKey = 'lazy-false'
        try {
          delete this.tableConfiguration.load
        } catch (e) {
          console.log(e)
        }
        this.tableConfiguration.defaultExpandAll = true
        // 根据每条数据的可操作标志，控制操作按钮是否置灰
        const t = data.reduce((pre, cur) => {
          const disabled = cur.operateFlag !== '1'
          const rootDisabled = cur.parentId === '0' || disabled
          pre.push({
            ...cur,
            operationConfig: {
              userSetDisabled: disabled,
              updateDisabled: rootDisabled,
              authDisabled: disabled,
              deleteDisabled: cur.parentId === '0' || disabled
            },
            deleteFlagConfig: {
              switchDisabled: rootDisabled
            }
          })
          return pre
        }, [])
        return arrayToTree(t, '0')
      }
    },
    interceptorsResponseLazyTableData(data) {
      return this.transToLazyTableData(data)
    },
    interceptorsResponseLazyTreeData(data) {
      return this.transToLazyTreeData(data)
    },
    // 新增前置事件:上级角色设置为异步树
    interceptorsBeforeAdd(params) {
      const cur = params.find(x => x.ruleProp === 'parentId')
      cur.options = []
      cur.props.lazy = true
      try {
        delete cur.props.value
        delete cur.props.label
      } catch (e) {
        console.log(e)
      }
      return params
    },
    // 编辑前置事件:上级角色设置为同步树
    async interceptorsBeforeEdit(params, row) {
      const cur = params.find(x => x.ruleProp === 'parentId')
      cur.props.lazy = false
      cur.props.value = 'id'
      cur.props.label = 'roleName'
      // cur.options.splice(0, cur.options.length, ...this.parentTreeData)
      await this.retrieveAllChildrenRoles(parentTreeData => {
        cur.options.splice(0, cur.options.length, ...parentTreeData)
        cur.model = this._handleLinkKeysArray(row)
      })
      return params
    },
    transToLazyTableData(data) {
      if (Array.isArray(data) && data.length > 0) {
        return data.reduce((pre, cur) => {
          const disabled = cur.operateFlag !== '1'
          const rootDisabled = cur.parentId === '0' || disabled
          pre.push({
            ...cur,
            hasChildren: cur.sonNum !== '0',
            operationConfig: {
              userSetDisabled: disabled,
              updateDisabled: rootDisabled,
              authDisabled: disabled,
              deleteDisabled: rootDisabled
            },
            deleteFlagConfig: {
              switchDisabled: rootDisabled
            }
          })
          return pre
        }, [])
      } else {
        return data
      }
    },
    transToLazyTreeData(data) {
      if (Array.isArray(data) && data.length > 0) {
        return data.reduce((pre, cur) => {
          pre.push({
            value: cur.id,
            label: cur.roleName,
            disabled: cur.operateFlag !== '1',
            leaf: cur.sonNum === '0'
          })
          return pre
        }, [])
      } else {
        return data
      }
    },
    // interceptorsResponseCreate() {
    //   this._handleUpdateTable()
    // },
    // interceptorsResponseUpdate() {
    //   this._handleUpdateTable()
    // },
    // interceptorsResponseDelete() {
    //   this._handleUpdateTable()
    // },
    // _handleUpdateTable() {
    //   this.mixinRetrieveTableData()
    // },
    // 配置用户保存前处理上级角色参数 eg:['id1','id2','id3']=>'id3s'
    _handleParentIdData(param) {
      const deepParams = deepClone(param)
      const { parentId } = param
      if (Array.isArray(parentId) && parentId.length > 0) {
        deepParams.parentId = parentId[parentId.length - 1]
      }
      return deepParams
    },
    // 将链式数据的mainKey提取出来，按序放入数组并返回 eg:obj
    _handleLinkKeysArray(row, mainKey = 'id', nextKey = 'parent') {
      // const arr = [row[mainKey]]
      const arr = []
      const transFn = obj => {
        if (obj[nextKey]) {
          if (obj[nextKey][mainKey]) {
            arr.unshift(obj[nextKey][mainKey])
            transFn(obj[nextKey])
          }
        }
      }
      transFn(row)
      return arr
    },
    retrieveAllChildrenRoles(cb) {
      return getChildrenRoles({ userId: this.userInfo.id })
        .then(res => {
          const parentTreeData = arrayToTree(
            res.data.map(e => {
              return {
                ...e,
                disabled: e.operateFlag !== '1'
              }
            }),
            '0'
          )
          cb && cb(parentTreeData)
        })
        .catch(err => {
          console.log('getChildrenRoles -> err', err)
        })
    }
  }
}
</script>

<style lang="scss" scoped>
/deep/.el-dialog__body {
  padding: 10px 20px;
}
.authority-resource {
  max-height: 700px;
  overflow-y: auto;
  border-radius: 10px;
  background: rgba(249, 249, 249, 1);

  .el-tree {
    background: rgba(247, 247, 247, 1);

    .el-tree-node__content {
      height: 50px;
    }

    .el-tree-node__children .el-tree-node {
      background: rgba(252, 252, 252, 1);
    }

    .el-checkbox__input.is-checked {
      .el-checkbox__inner {
        background: #2874ff;
        border-color: #2874ff;
      }
    }
  }
}

/*配置用户和功能弹框相关*/
.tree-choose-transfer-box {
  /*background: rgba(249, 249, 249, 1);*/
  display: flex;
  justify-content: space-between;
  width: 1195px;
  height: 490px;
  opacity: 1;
  border: 1px solid #d9d9d9;
  border-radius: 10px;
  margin: 0 auto;
  padding: 2px;
  .tree-desc {
    height: 70px;
    width: 80px;
    margin: 0 auto;
    line-height: 70px;
    text-align: center;
    border-bottom: 3px solid #2f54eb;
    span {
      opacity: 1;
      font-size: 18px;
      font-family: PingFangSC, PingFangSC-Medium;
      font-weight: 500;
      text-align: center;
      color: #2f54eb;
      line-height: 25px;
    }
  }
  & .left {
    flex: 1;
    /*border: 1px solid red;*/
    & .left-tree {
      overflow-y: scroll;
      overflow-x: hidden;
      height: calc(100% - 70px);
      width: 100%;
      border-top: 1px solid #e8e8e8;
      & .left-tree-label {
        display: inline-block;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
        /*max-width: 28vw;*/
      }
    }
  }

  & .right {
    margin-left: 10px;
    flex: 3;
    /*border: 1px solid green;*/
    & /deep/ .el-transfer-panel {
      width: auto;
      height: 100%;
    }

    & /deep/ .el-transfer-panel__body {
      /*width: 345px;*/
      width: 40vm;
      height: 440px;

      & .el-transfer-panel__list.is-filterable {
        height: 100%;
      }
    }
  }
}
</style>
