<template>
  <crud-basic
    ref="crud"
    title="字典表列表"
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
    :handleSelectChange="mixinHandleSelectChange"
    :handleAdd="mixinHandleAdd"
    :handleImport="mixinHandleImport"
    :handleExport="mixinHandleExport"
    :handleSearch="mixinHandleSearch"
    :handleReset="mixinHandleReset"
    :handleUpdate="mixinHandleCreateOrUpdate"
    :handleCancel="mixinHandleCancel"
    @handleMultipleDelete="mixinHandleMultipleDelete"
  ></crud-basic>
</template>

<script>
import { mapGetters } from 'vuex'
import crud from '@/mixins/crud'
import { deepClone } from '@/utils/util'

import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-code'
import formOption from '@/views/system-basic/configuration/form/manage/manage-code'
import { setCodeStatus } from '@/api/code'

export default {
  name: 'ManageCode',
  mixins: [crud],
  data() {
    return {
      formOption,
      mixinSearchFormConfig: {
        models: {
          orgName: '',
          linkManName: '',
          linkManTel: ''
        },
        retrieveModels: {
          userId: ''
        }
      },
      mixinDialogFormConfig: {
        models: {
          codeName: '',
          codeType: '',
          codeValue: '',
          codeSort: '',
          codeRemark: ''
        },
        rule: {
          codeName: [
            { required: true, message: '字典名称不能为空', trigger: 'blur' }
          ],
          codeType: [
            { required: true, message: '字典类型不能为空', trigger: 'blur' }
          ],
          codeValue: [
            { required: true, message: '字典值不能为空', trigger: 'blur' }
          ],
          codeSort: [
            {
              required: true,
              message: '排序不能为空',
              trigger: ['blur', 'change']
            }
          ]
        }
      },
      tableConfiguration: tableConfiguration(this),
      fetchConfig: {
        retrieve: {
          url: '/system/code/code/pageList',
          method: 'post'
        },
        create: {
          url: '/system/code/code/save',
          method: 'post'
        },
        update: {
          url: '/system/code/code/update',
          method: 'post'
        },
        multipleDelete: {
          url: '/system/code/code/delete',
          method: 'post'
        }
      }
    }
  },
  computed: {
    ...mapGetters({
      permissionList: 'user/permissionList',
      userInfo: 'user/userInfo'
    })
  },
  created() {
    this.mixinSearchFormItems = deepClone(this.formOption).filter(
      e => e.isSearch
    )
    this.mixinSearchFormConfig.retrieveModels.userId = this.userInfo.id || ''
    this.mixinRetrieveTableData()
  },
  methods: {
    handleStatusChange(e) {
      setCodeStatus({ id: e.scope.row.id, deleteFlag: e.value }).then(() => {
        this.$notify.success(`${e.value === 'Y' ? '禁用' : '启用'}成功`)
        this.mixinRetrieveTableData()
      })
    },
    validBeforeMultipleDelete(selection) {
      const validFailure = selection.some(e => e.deleteFlag === 'N')
      validFailure && this.$message.warning('不可删除已启用项')
      return !validFailure
    }
  }
}
</script>
