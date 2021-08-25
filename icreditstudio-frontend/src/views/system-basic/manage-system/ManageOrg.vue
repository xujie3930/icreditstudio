<template>
  <crud-basic
    ref="crud"
    title="部门列表"
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
</template>

<script>
import { mapGetters } from 'vuex'
import crud from '@/mixins/crud'
import { deepClone, arrayToTree, getObjType } from '@/utils/util'

import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-org'
import formOption from '@/views/system-basic/configuration/form/manage/manage-org'
import { setOrgStatus } from '@/api/org'

export default {
  name: 'ManageOrg',
  mixins: [crud],
  data() {
    // 编辑 上级部门校验：非空且不能与本身角色相同
    const checkParentId = (rule, value, callback) => {
      if (getObjType(value) === 'array') {
        const len = value.length
        if (len === 0) {
          return callback(new Error('上级部门不能为空'))
        } else if (value[value.length - 1] === this.mixinUpdate.id) {
          return callback(new Error('部门不能和上级部门相同'))
        } else {
          callback()
        }
      }
    }
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
          // onlyReturnCurrAndSon: true
        }
      },
      mixinDialogFormConfig: {
        models: {
          orgName: '',
          orgCode: '',
          orgAddress: '',
          sortNumber: '',
          linkManName: '',
          linkManTel: '',
          parentId: [],
          orgRemark: ''
        },
        rule: {
          orgName: [
            { required: true, message: '部门名称不能为空', trigger: 'blur' }
          ],
          orgCode: [
            { required: true, message: '部门编码不能为空', trigger: 'blur' }
          ],
          linkManName: [
            { required: true, message: '联系人不能为空', trigger: 'blur' }
          ],
          orgAddress: [
            { required: true, message: '部门地址不能为空', trigger: 'blur' }
          ],
          linkManTel: [
            { required: true, message: '联系方式不能为空', trigger: 'blur' },
            { pattern: /^1[0-9]{10}$/, message: '请输入正确的联系方式' }
          ],
          parentId: [
            { required: true, validator: checkParentId, trigger: ['blur'] }
          ]
        }
      },
      tableConfiguration: tableConfiguration(this),
      fetchConfig: {
        retrieve: {
          url: '/system/org/organization/queryList',
          method: 'post'
        },
        create: {
          url: '/system/org/organization/save',
          method: 'post'
        },
        update: {
          url: '/system/org/organization/update',
          method: 'post'
        },
        delete: {
          url: '/system/org/organization/delete',
          method: 'post'
        },
        export: {
          url: '/system/org/organization/exportExcel',
          method: 'get'
        },
        import: {
          url: '/system/org/organization/importExcel',
          method: 'get'
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
    interceptorsResponseTableData(data) {
      // const rootParentId = data.find(x => x.currOrg)?.parentId || '0'
      const _data =
        arrayToTree(
          data.map(e => {
            const disabled = e.parentId === '0' || e.operateFlag !== '1'
            return {
              ...e,
              deleteFlagConfig: {
                switchDisabled: disabled
              },
              operationConfig: {
                updateDisabled: disabled,
                deleteDisabled: disabled
              },
              disabled: e.deleteFlag === 'Y' // 弹框中的树结构，禁用部门不可选
            }
          }),
          '0'
        ) || []
      this.formOption.find(e => e.ruleProp === 'parentId').options = _data
      return _data
    },
    interceptorsRequestCreate(e) {
      let pid = e.parentId
      pid = Array.isArray(pid) ? pid[pid.length - 1] : pid
      return {
        id: '',
        type: 1,
        ...e,
        parentId: pid || undefined
      }
    },
    interceptorsRequestUpdate(e) {
      let pid = e.parentId
      pid = Array.isArray(pid) ? pid[pid.length - 1] : pid
      return {
        type: 1,
        ...e,
        parentId: pid || undefined
      }
    },
    handleStatusChange(e) {
      setOrgStatus({ id: e.scope.row.id, deleteFlag: e.value }).then(() => {
        this.$notify.success(`${e.value === 'Y' ? '禁用' : '启用'}成功`)
        this.mixinRetrieveTableData()
      })
    }
  }
}
</script>
