<template>
  <div class="h100 w100">
    <crud-basic
      ref="crud"
      title="模块列表"
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
import { mapActions, mapGetters } from 'vuex'
import crud from '@/mixins/crud'
import { deepClone, arrayToTree, treeToArray } from '@/utils/util'
import { icons } from '@/utils/menu-icons.js'
import { changeMenuStatus } from '@/api/menu'
import { getChildrenRoles } from '@/api/role'
import { setRoleToResources, getRoleInfoByMenuIds } from '@/api/auth'

import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-menu'
import formOption from '@/views/system-basic/configuration/form/manage/manage-menu'

const CONSTANT = {
  Y: '是',
  N: '否'
}

const MODULETYPES = {
  M: '菜单',
  D: '顶部模块',
  B: '按钮'
}

/**
 * 处理字段
 * @param keys
 * @returns {function(Array, Boolean): Array}
 */
function hideItems(keys) {
  return (arr, flag) => {
    keys.forEach(s => {
      // eslint-disable-next-line no-param-reassign
      arr.find(item => item.ruleProp === s).isHide = flag
    })
    return arr
  }
}
// 当类型为按钮时 需要隐藏的字段
const hideForTypeIsButton = hideItems([
  'filePath',
  'redirectPath',
  'isShow',
  'keepAlive',
  'sortNumber',
  'iconPath'
])
// 当类型不为按钮时需要隐藏的字段
const hideForTypeIsNotButton = hideItems(['authIdentification'])

export default {
  name: 'ManageMenu',
  mixins: [crud],
  data() {
    return {
      formOption: formOption(this),
      mixinSearchFormConfig: {
        models: {
          name: ''
        }
      },
      mixinDialogFormConfig: {
        models: {
          type: '',
          name: '',
          orderBy: '',
          url: '',
          pictureName: '',
          remark: '',
          status: 0
        },
        rule: {
          type: [
            { required: true, message: '模块类型不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '模块名称不能为空', trigger: 'blur' }
          ]
        }
      },
      tableConfiguration: tableConfiguration(this),
      fetchConfig: {
        retrieve: {
          url: '/system/resources/resources/queryList',
          method: 'post'
        },
        create: {
          url: '/system/resources/resources/save',
          method: 'post'
        },
        update: {
          url: '/system/resources/resources/update',
          method: 'post'
        },
        delete: {
          url: '/system/resources/resources/delete',
          method: 'post'
        },
        export: {
          url: '/system/resources/resources/exportExcel',
          method: 'get'
        },
        import: {
          url: '/system/resources/resources/importExcel',
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
    this.formOption.find(e => e.ruleProp === 'iconPath').options = icons
    getChildrenRoles({ userId: this.userInfo.id })
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
    this.mixinRetrieveTableData()
  },
  methods: {
    ...mapActions('user', ['getPermissionListAction']),
    // 配置角色相关代码 start ↓
    // 配置角色
    mixinHandleRoleSet(row) {
      this.mixinUpdate = row.row
      // 获取当前模块的已配置角色数据
      getRoleInfoByMenuIds({ resourcesIdList: [this.mixinUpdate.id] })
        .then(res => {
          this.roleSetDialogFlag = true
          this.$nextTick(() => {
            this.$refs.roleTree.setCheckedKeys(res.data.map(e => e.id))
          })
        })
        .catch(err => {
          console.log('getRoleInfoByMenuIds -> err', err)
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
      setRoleToResources({
        roleIds: this.roleSetModels.tree.roleIds,
        resourcesId: this.mixinUpdate.id
      }).then(() => {
        this.$notify.success('配置角色成功')
        this.roleSetHandleClose()
        this.mixinRetrieveTableData()
      })
    },
    // 配置角色相关代码 end ↑
    interceptorsBeforeEdit(e) {
      const _currentType = e[0].model // 当前类型
      // 通过类型隐藏相关字段
      hideForTypeIsButton(e, _currentType === 'B')
      hideForTypeIsNotButton(e, _currentType !== 'B')
      return e
    },
    interceptorsResponseTableData(data) {
      data.forEach(e => {
        Object.assign(e, {
          isShowStr: CONSTANT[e.isShow],
          keepAliveStr: CONSTANT[e.keepAlive],
          typeStr: MODULETYPES[e.type]
        })
      })
      const _first = arrayToTree(data, '0')
      this.formOption.find(e => e.ruleProp === 'parentId').options = _first
      return _first
    },
    interceptorsRequestCreate(e) {
      return {
        ...e,
        parentId: (e.parentId && e.parentId.pop()) || undefined
      }
    },
    interceptorsRequestUpdate(data, row) {
      // pid 编辑为array，未操作为string
      let pid = data.parentId
      pid = Array.isArray(pid) ? pid.pop() : pid
      const _source = {
        id: row.id,
        parentId: pid || undefined
      }
      return Object.assign(data, _source)
    },
    /**
     * 表格 是否启用切换
     * @param { Y | N } value 状态
     * @param { row } scope 行数据
     */
    handleStatusChange({ value, scope }) {
      const { row } = scope
      // const resourcesIdList = [row.id]
      const resourcesIdList = treeToArray([row], 'children', 'id')
      // if (row.children) resourcesIdList.push(...row.children.map(e => e.id))
      changeMenuStatus({
        deleteFlag: value,
        resourcesIdList
      })
        .then(() => {
          this.getPermissionListAction()
          this.mixinHandleReset()
          this.$notify.success('状态修改成功')
        })
        .catch(err => {
          this.mixinHandleReset()
          console.log(err)
        })
    },
    /**
     * 表单类型change
     * @param e
     */
    handleTypeChange(e) {
      hideForTypeIsButton(this.mixinDialogFormItems, e === 'B')
      hideForTypeIsNotButton(this.mixinDialogFormItems, e !== 'B')
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
</style>
