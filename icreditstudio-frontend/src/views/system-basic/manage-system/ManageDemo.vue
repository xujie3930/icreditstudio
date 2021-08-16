<template>
  <div class="ui-container">
    <j-panel title="搜索">
      <j-form
        ref="searchForm"
        :form-items="mixinSearchFormItems"
        :form-func="mixinSearchFormFunc"
        :form-config="mixinSearchFormConfig"
        @mixinSearch="mixinHandleSearch"
        @mixinReset="mixinHandleReset"
      ></j-form>
    </j-panel>
    <j-panel title="列表">
      <template #title>
        <el-button type="primary" size="mini" @click="mixinHandleAdd">新增</el-button>
      </template>
      <j-table
        v-loading="mixinTableLoading"
        :table-configuration="tableConfiguration"
        :table-pagination="mixinTablePagination"
        :table-data="mixinTableData"
        @handleSizeChange="mixinHandleSizeChange"
        @handleCurrentChange="mixinHandleCurrentChange"
      ></j-table>
    </j-panel>

    <el-dialog
      :title="mixinDialogType ? '编辑' : '新增'"
      :visible.sync="mixinDialog"
      width="70%"
      :close-on-click-modal="false"
    >
      <j-form
        ref="form"
        :form-items="mixinDialogFormItems"
        :form-func="mixinDialogFormFunc"
        :form-config="mixinDialogFormConfig"
        @mixinSave="mixinHandleCreateOrUpdate"
        @mixinCancel="mixinHandleCancel"
      ></j-form>
    </el-dialog>
    <input type="text" v-model="test" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import crud from '@/mixins/crud';
import { deepClone } from '@/utils/util'

import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-demo'
import formOption from '@/views/system-basic/configuration/form/manage/manage-demo'

export default {
  name: 'ManageDemo',
  mixins: [crud],
  data() {
    return {
      test: '',
      formOption: formOption(this),
      mixinSearchFormConfig: {
        models: {
          name: '',
          status: '',
          menus: []
        }
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
          url: '/sys/get/userRoles',
          method: 'post'
        },
        create: {
          url: '/sys/get/userRoles',
          method: 'post'
        },
        update: {
          url: '/sys/get/userRoles',
          method: 'post'
        },
        delete: {
          url: '/sys/get/userRoles',
          method: 'post'
        }
      }
    }
  },
  computed: {
    ...mapGetters({
      permissionList: 'user/permissionList'
    })
  },
  created() {
    const _i = this.formOption.findIndex(e => e.ruleProp === 'menus')

    this.formOption[_i].treeData = this.permissionList.map(e => {
      // TODO 错误教学 纯属偷懒 待删除
      const _children = e.children.map(c => {
        return {
          label: c.meta.name,
          ...c
        }
      })
      return {
        ...e,
        label: e.meta.name,
        children: _children
      }
    })

    this.mixinSearchFormItems = deepClone(this.formOption)
      .filter(e => e.isSearch)
    this.mixinRetrieveTableData()
  },
  methods: {
    /* 模拟字段间的联动 */
    phoneChange() {
      // 不确定下标可以通过 findIndex(e => e.ruleProp === 'xxx')
      this.mixinDialogFormItems[2].disabled = false
    }
  }
}
</script>
<style lang="scss" scoped>
.ui-container {
  padding: 0 10px 10px;
}

.pagination {
  padding: 40px 0 20px;
}
</style>
