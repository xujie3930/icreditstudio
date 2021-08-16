<template>
  <crud-basic
    ref="crud"
    title="审批列表"
    :hide-header="true"
    :hideMenu="true"
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
    :handleUpdate="mixinHandleCreateOrUpdate"
    :handleCancel="mixinHandleCancel"
  ></crud-basic>
</template>
<script>
import { mapActions } from 'vuex'
import { axios } from 'utils/axios'
import _ from 'lodash'
import crud from '@/mixins/crud'
import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-approval-initiate'

export default {
  name: 'FlowInitiate',
  mixins: [crud],
  data() {
    return {
      tableConfiguration: tableConfiguration(this),
      fetchConfig: {
        retrieve: {
          url: '/process/definition/info/page/myself/start',
          method: 'get'
        }
      }
    }
  },
  created() {
    this.mixinRetrieveTableData()
  },
  methods: {
    ...mapActions('approval-form', ['setFormInfo']),
    interceptorsResponseTableData(data) {
      const _data = data
      _data.forEach(item => {
        Object.assign(item, { blackList: ['publish'] })
      })
      return _data
    },
    handleFlowPublish({ row }) {
      console.log(row)
      this.getFormExternal(row)
    },
    async getFormExternal(data) {
      if (data.modelEditorJson) {
        // TODO 业务相关代码
        // const model = JSON.parse(data.modelEditorJson)
        // for (let i = 0; i < model.list.length; i++) {
        //   const _listItem = model.list[i]
        //   if (_listItem.type === 'grid') {
        //     for (let j = 0; j < _listItem.columns.length; j++) {
        //       for (let v = 0; v < _listItem.columns[j].list.length; v++) {
        //         const list = _listItem.columns[j].list[v]
        //         if (list.options.bindType === 'external' && list.options.bindValue.external) {
        //           await this.setExternal(list, model.config.checkedExternals)
        //         }
        //       }
        //     }
        //   } else if (_listItem.type === 'childForm') {
        //     for (let j = 0; j < _listItem.tableColumns.length; j++) {
        //       const tableColumns = _listItem.tableColumns[j]
        //       if (tableColumns.options.bindType === 'external'
        //       && tableColumns.options.bindValue.external) {
        //         await this.setExternal(tableColumns, model.config.checkedExternals)
        //       }
        //     }
        //   } else if (_listItem.options.bindType === 'external'
        //   && _listItem.options.bindValue.external) {
        //     await this.setExternal(_listItem, model.config.checkedExternals)
        //   }
        // }
        // await this.viewFormDetail({ ...data, modelEditorJson: JSON.stringify(model) })

        await this.viewFormDetail(data)
      }
    },
    setExternal(data, config) {
      let bindValue = []
      config.forEach((i, index) => {
        const labelScreen = i.options.filter(j => {
          return j.label === data.options.bindValue.external.label
        })
        if (labelScreen.length) {
          bindValue = config[index]
        }
      })
      const params = {}
      const inParameter = JSON.parse(bindValue.inParameter)
      if (bindValue.dataType === 'user') {
        inParameter.forEach(i => {
          params[i.value] = this.userInfo[i.value]
        })
      } else if (bindValue.dataType === 'order') {
        return
      }
      return axios({
        url: bindValue.url,
        method: bindValue.type,
        [bindValue.type === 'get' ? 'params' : 'data']: params
      }).then(res => {
        // eslint-disable-next-line no-param-reassign
        data.options.defaultValue = res.data[data.options.bindValue.external.value]
      })
    },
    viewFormDetail(data) {
      this.$ls.set('approvalInitiate-formInfo', JSON.stringify(_.cloneDeep(data)))
      this.$router.push('/approvalFlow/formApproval')
      // this.$router.push('/approvalFlow/formApproval')
    //   this.setFormInfo(Object.assign(
    //     {},
    //     data,Vue.use
    //     { form: '/approvalFlow/approvalInitiate', processInstanceId: data.id }
    //   ))
    //     .then(() => {
    //       this.$router.push('/approvalFlow/formApproval')
    //     })
    }
  }
}
</script>
