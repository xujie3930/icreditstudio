<template>
  <crud-basic
    ref="crud"
    title="接口列表"
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
  ></crud-basic>
</template>
<script>
import crud from '@/mixins/crud'
import { deepClone } from 'utils/util'

import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-interface'
import formOption from '@/views/system-basic/configuration/form/manage/manage-interface'

const CONSTANT_IS = ['否', '是']
const CONSTANT_TYPE = ['接口地址', '通配符']

export default {
  name: 'ManageInterface',
  mixins: [crud],
  data() {
    return {
      formOption,
      mixinSearchFormConfig: {
        models: {
        }
      },
      mixinDialogFormConfig: {
        models: {
          name: '',
          method: '',
          needAuth: '',
          module: '',
          supportAuthType: '',
          uri: '',
          uriType: ''
        },
        rule: {
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ],
          method: [
            { required: true, message: '请求类型不能为空', trigger: 'change' }
          ],
          needAuth: [
            { required: true, message: '是否鉴权不能为空', trigger: 'change' }
          ],
          module: [
            { required: true, message: '接口归属模块不能为空', trigger: 'blur' }
          ],
          supportAuthType: [
            { required: true, message: '鉴权方式不能为空', trigger: 'blur' }
          ],
          uri: [
            { required: true, message: '接口地址不能为空', trigger: 'blur' }
          ],
          uriType: [
            { required: true, message: '类型不能为空', trigger: 'change' }
          ]
        }
      },
      tableConfiguration: tableConfiguration(this),
      fetchConfig: {
        retrieve: {
          url: '/interfaces/interfaces/pageList',
          method: 'post'
        },
        create: {
          url: '/interfaces/interfaces/save',
          method: 'post'
        },
        update: {
          url: '/interfaces/interfaces/update',
          method: 'post',
          id: 'interfaceId'
        },
        delete: {
          url: '/interfaces/interfaces/delete',
          method: 'post',
          id: 'interfaceId'
        },
        export: {
          url: '/interfaces/interfaces/exportExcel',
          method: 'get'
        },
        import: {
          url: '/interfaces/interfaces/importExcel',
          method: 'get'
        }
      }
    }
  },
  created() {
    this.mixinSearchFormItems = deepClone(this.formOption)
      .filter(e => e.isSearch)
    this.mixinRetrieveTableData()
  },
  methods: {
    interceptorsResponseTableData(data) {
      data.forEach(e => {
        Object.assign(
          e,
          {
            needAuthStr: CONSTANT_IS[e.needAuth],
            uriTypeStr: CONSTANT_TYPE[e.uriType]
          }
        )
      })
      return data
    },
    interceptorsRequestUpdate(e) {
      return {
        ...e,
        interfaceId: e.id
      }
    }
  }
}
</script>
