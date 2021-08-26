<template>
  <div>
    <crud-basic
      ref="crud"
      title="表单列表"
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
      :handleAdd="handleAddForm"
      :handleImport="mixinHandleImport"
      :handleExport="mixinHandleExport"
      :handleSearch="mixinHandleSearch"
      :handleReset="mixinHandleReset"
      :handleUpdate="mixinHandleCreateOrUpdate"
      :handleCancel="mixinHandleCancel"
      :handleSelectChange="mixinHandleSelectChange"
      @handleMultipleDelete="handleMultipleDelete"
    />

    <!-- 表单预览 -->
    <form-preview ref="formListPreview" dialog-title="表单预览" />

    <!-- 表单历史 -->
    <form-history ref="formListHistory" />

    <!-- 表单实例 -->
    <form-preview
      ref="formInstance"
      :confirm-btn="true"
      :reset-btn="false"
      confirm-text="提交"
      cancel-text="取消"
      dialog-title="表单实例创建"
      @onSave="handleFormInstanceSave"
      @onFieldChange="handleFormFieldChange"
    />
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import crud from '@/mixins/crud'
import { deepClone } from '@/utils/util'
import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-form'
import formOption from '@/views/system-basic/configuration/form/manage/manage-form'
import {
  formListPublish,
  qryFormDetail,
  formDisable,
  formInstanceCreate
} from '@/api/system-basic/form-manage'

import FormPreview from './components/form-preview'
import FormHistory from './components/form-history'

const STATUS_ENUMS = {
  D: '草稿箱',
  P: '已发布',
  T: '已停用'
}
export default {
  name: 'ManageOrg',
  mixins: [crud],
  components: { FormPreview, FormHistory },
  data() {
    return {
      formOption: formOption(this, { STATUS_ENUMS }),
      mixinSearchFormConfig: {
        models: {}
      },
      tableConfiguration: tableConfiguration(this, { STATUS_ENUMS }),
      fetchConfig: {
        retrieve: {
          url: '/system/form/definition/pageList',
          method: 'post'
        },
        delete: {
          url: '/system/form/definition/delete',
          method: 'post'
        },
        multipleDelete: {
          url: '/system/form/definition/delete',
          method: 'post'
        }
      },

      formInstance: {}, // 表单实例参数
      formDataList: []
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
    this.mixinRetrieveTableData()
  },
  methods: {
    ...mapActions('approval-form', ['setFormInfo']),
    handleAddForm() {
      this.$ls.set('approvalInitiate-formInfo', {})
      this.$router.push('/approvalFlow/formGenerator')
    },

    // 更多 - 发布操作
    handlePublishOperate(row) {
      formListPublish({ id: row.id }).then(({ success }) => {
        if (success) {
          this.$notify.success({ message: '发布成功！', duration: 2500 })
          this.mixinRetrieveTableData()
        }
      })
    },

    // 表单修改
    handleFormEdit({ row }) {
      qryFormDetail({ id: row.id }).then(res => {
        if (res.success) {
          this.formInfo = res.data
          const { authList, config, list } = res.data.modelEditorJson
          this.formInfo.modelEditorJson = JSON.stringify({
            authList,
            config: { ...JSON.parse(config) },
            list: list.map(({ eleJson, ...restArgs }) => {
              return {
                ...restArgs,
                ...JSON.parse(eleJson)
              }
            })
          })
          this.$ls.set('approvalInitiate-formInfo', this.formInfo)
          this.$router.push({ path: '/approvalFlow/formGenerator' })
        }
      })
    },

    // 表单停用
    handleFormDisable({ row }) {
      formDisable({ id: row.id }).then(() => {
        this.$notify.success('表单已停用！')
        this.mixinRetrieveTableData()
      })
    },

    handleMultipleDelete({ validBeforeEvent }) {
      const { mixinSelectedData } = this
      const statusArr = mixinSelectedData.map(({ formStatus }) => formStatus)
      const msg = statusArr.includes('P')
        ? '待删除项中含有发布中的表单，请确认是否删除？'
        : '确定删除选中数据？'
      if (!validBeforeEvent || validBeforeEvent(mixinSelectedData)) {
        // 自定义的删除前置校验拦截
        this.$confirm(msg, '询问', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            const request = this._request('multipleDelete', mixinSelectedData)
            if (!request) return
            request
              .then(res => {
                if (res.success) {
                  // 拦截
                  if (this.interceptorsResponseMultipleDelete()) return
                  this.$notify.success({
                    title: '成功',
                    message: '删除成功'
                  })
                  this.mixinHandleReset()
                }
              })
              .catch(err => {
                console.log(err)
              })
          })
          .catch(() => {})
      }
    },

    // 更多 - 预览操作
    handleFormPreview(row) {
      this.$refs.formListPreview.open(row, 'formListPreview')
    },

    // 更多 - 导出操作
    handleFormExport({ row }) {
      this.$refs.formListPreview.open(row, 'formListPreview', true)
    },

    // 更多 - 历史版本
    OpenHistoryListModal({ row }) {
      this.$refs.formListHistory.open(row)
    },

    // 更多 - 创建实例
    createFormInstance({ row }) {
      this.$refs.formInstance.open(row, 'formInstanceCreateView')
    },

    // 表单值发生变化
    handleFormFieldChange(option) {
      const { models, field, value, row, form } = option
      this.formInstance = models

      if (!this.formDataList.length) {
        this.formDataList = this.handleFormInstanceFilter(form.list)
      }

      const idx = this.formDataList.findIndex(({ paramKey }) => {
        return row.type === 'childForm'
          ? paramKey === row.model
          : paramKey === field
      })

      // 更新组件绑定值
      if (idx > -1) {
        const childVal = []
        // 针对组件类型为子表单进行赋值
        if (models[row.model][0]) {
          for (const [key, val] of Object.entries(models[row.model][0])) {
            const cvalue = Array.isArray(val) ? JSON.stringify(val) : val
            childVal.push(`${key}===${cvalue}`)
          }
        }

        // 级联组件多选值特殊处理
        const casMulVal =
          Array.isArray(value) &&
          value.map(item => {
            if (!Array.isArray(item)) return item
            const filterValueItem = item.map(list => list.trim())
            return Array.isArray(item) ? filterValueItem.join('===') : item
          })

        const cascaderMultiple =
          row.type === 'cascader' && row.options.multiple
            ? casMulVal || []
            : value
        const isArrayVal = Array.isArray(value)
          ? cascaderMultiple
          : String(value)

        this.formDataList[idx].paramValue =
          row.type === 'childForm' ? childVal : isArrayVal
      }
    },

    // 表单定义-创建表单实例-保存
    handleFormInstanceSave(cb) {
      const { close, row, form, templateForm } = cb
      const {
        id,
        userId,
        formVersion,
        formStatus,
        formName,
        formDesc,
        defJson
      } = row

      if (!this.formDataList.length) {
        this.formDataList = this.handleFormInstanceFilter(form.list)
      }

      // 保存前进行表单校验
      if (this.handleSaveBeforeVerify(templateForm)) {
        close({ saveBtnLoading: false })
        return
      }

      const params = {
        formDesc,
        formName,
        formStatus,
        formVersion,
        userId,
        formDataList: this.formDataList,
        formId: id,
        formDescJson: defJson
      }

      formInstanceCreate(params)
        .then(({ success }) => {
          if (success) {
            this.$notify.success({ message: '实例创建成功', duration: 2500 })
          }
        })
        .finally(() => {
          close()
        })
    },

    // 必填项验证
    handleSaveBeforeVerify(templateForm) {
      const requireArr = []
      const { list } = templateForm
      list.forEach(({ model, type, options, columns, tableColumns }, idx) => {
        if (model === this.formDataList[idx].paramKey) {
          const value = this.formDataList[idx].paramValue
          // 栅格组件特殊处理
          if (type === 'grid') {
            const noneValArr = []
            this.formDataList[idx].paramValue = []
            columns.forEach(({ list: gridList }) => {
              gridList.forEach(item => {
                const modelValue = this.formInstance[item.model]
                const isValueArr =
                  Array.isArray(modelValue) && !modelValue.length
                const isNone = !modelValue || isValueArr
                const jsonValue =
                  item.type === 'cascader'
                    ? JSON.stringify(modelValue)
                    : modelValue
                this.formDataList[idx].paramValue.push(
                  `${item.model}===${jsonValue}`
                )
                item.options.required && isNone && noneValArr.push(item.model)
              })
            })
            noneValArr.length && requireArr.push(model)
          } else if (type === 'childForm') {
            // 子表单组件特殊处理
            const noneValArr = []
            tableColumns.forEach(item => {
              const modelValueObj = this.formInstance[model]
                ? this.formInstance[model][0]
                : {}
              const modelValue = modelValueObj[item.model]
              const isArrNone = Array.isArray(modelValue) && !modelValue.length
              const isNone = !modelValue || isArrNone
              const { required } = item.options
              required && isNone && noneValArr.push(`${model}::${item.model}`)
            })
            noneValArr.length && requireArr.push(model)
          } else {
            options.required && value.trim() === '' && requireArr.push(model)
          }
        }
      })
      requireArr.length &&
        this.$message.error({
          message: '必填项不能为空!',
          duration: 1500,
          center: true
        })
      return !!requireArr.length
    },

    handleFormInstanceFilter(list = []) {
      return list.map(({ id: eleId, model }) => {
        return {
          eleId,
          paramKey: model,
          paramValue: model.includes('childForm_') ? [] : ''
        }
      })
    }
  }
}
</script>
