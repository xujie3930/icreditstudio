<template>
  <div class="h100">
    <crud-basic
      ref="crud"
      title="组织列表"
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
      :handleUpdate="mixinHandleCreateOrUpdate"
      :handleCancel="mixinHandleCancel"
    ></crud-basic>
    <el-dialog
      title="授予菜单"
      :visible.sync="menuDialogFlag"
      width="70%"
      :close-on-click-modal="false"
    >
      <div id="authorityScroll" class="authority-resource">
        <el-tree
          ref="tree"
          :data="menuTreeData"
          default-expand-all
          node-key="id"
          show-checkbox
          :props="defaultProps"
          :expand-on-click-node="false"
          check-on-click-node
        />
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="menuHandleCancel">取 消</el-button>
        <el-button type="primary" @click="menuHandleCreateOrUpdate"
          >保 存</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import crud from '@/mixins/crud'
import { arrayToTree, deepClone } from '@/utils/util'

import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-auth'
import formOption from '@/views/system-basic/configuration/form/manage/manage-auth'
import {
  getAuthResourceList,
  getAllAuthResourceList,
  editAuthResource
  // addAuthResource,
  // deleteAuthResource
} from '@/api/auth'

export default {
  name: 'ManageAuth',
  mixins: [crud],
  data() {
    return {
      formOption,
      mixinSearchFormConfig: {
        models: {
          name: '',
          status: '',
          menus: []
        }
      },
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      mixinDialogFormConfig: {
        models: {
          name: '',
          phone: '',
          status: '',
          menus: [],
          textarea: ''
        }
      },
      tableConfiguration: tableConfiguration(this),
      fetchConfig: {
        retrieve: {
          url: '/system/res/auth/query',
          method: 'post'
        },
        create: {
          url: '/system/res/auth/add',
          method: 'post'
        },
        update: {
          url: '/system/res/auth/edit',
          method: 'post'
        },
        delete: {
          url: '/system/res/auth/delete',
          method: 'post'
        }
      },
      // 菜单权限相关代码
      menuDialogFlag: false,
      menuDialogFormItems: [
        {
          type: 'selectTree',
          label: '权限',
          model: [],
          ruleProp: 'menus',
          defaultExpandAll: false,
          multiple: true,
          treeProps: {
            label: 'label'
          },
          treeData: []
        }
      ],
      menuList: [],
      resourceList: [],
      menuDialogFormFunc: [
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
      menuTreeData: [],
      menuDialogFormConfig: {
        models: {},
        refName: 'submitForm'
      }
    }
  },
  computed: {
    ...mapGetters({
      permissionList: 'user/permissionList'
    })
  },
  created() {
    // const _i = this.menuDialogFormItems.findIndex(e => e.ruleProp === 'menus')
    getAllAuthResourceList({}).then(res => {
      this.menuList = res.data
      this.menuTreeData = arrayToTree(
        res.data.map(e => {
          return {
            label: e.name,
            ...e
          }
        }),
        '0'
      )
      // this.menuDialogFormItems[_i].treeData
    })
    this.mixinSearchFormItems = deepClone(this.formOption).filter(
      e => e.isSearch
    )
    this.mixinRetrieveTableData()
  },
  methods: {
    interceptorsRequestRetrieve() {
      return {}
    },
    mixinSetMenu(row) {
      this.mixinUpdate = row.row
      getAuthResourceList({ authorizationId: this.mixinUpdate.id }).then(
        res => {
          this.menuDialogFormItems[0].model = res.data.map(el => el.resourceId)
          this.resourceList = res.data.map(el => el.resourceId)
          this.menuDialogFlag = true
          this.$nextTick(() => {
            this.$refs.tree.setCheckedKeys(this.resourceList)
          })
        }
      )
    },
    interceptorsRequestCreate(e) {
      return {
        parentId: '',
        name: e.name
      }
    },
    menuHandleCreateOrUpdate() {
      // const postMenuList = this.$refs.tree.getCheckedKeys()
      const postMenuList = this.$refs.tree
        .getCheckedNodes(false, true)
        .map(e => e.id)
      editAuthResource({
        resourceIds: postMenuList,
        authorizationId: this.mixinUpdate.id
      }).then(() => {
        this.$notify.success('授予菜单成功')
        this.menuDialogFlag = false
        this.mixinRetrieveTableData()
      })
    },
    getArrChange(arr1, arr2, type) {
      const changeList = []
      arr1.forEach(item1 => {
        let exist = false
        if (type === 'add') {
          for (let i = 0; i < arr2.length; i++) {
            if (item1 === arr2[i]) {
              exist = true
              break
            }
          }
          if (!exist) {
            changeList.push(item1)
          }
        }
        if (type === 'delete') {
          for (let j = 0; j < arr2.length; j++) {
            if (item1 === arr2[j]) {
              exist = true
              break
            }
          }
          if (!exist) {
            changeList.push(item1)
          }
        }
      })
      return changeList
    },
    menuHandleCancel() {
      this.$refs.tree.setCheckedKeys([])
      this.menuDialogFlag = false
    }
  }
}
</script>
<style lang="scss">
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
</style>
