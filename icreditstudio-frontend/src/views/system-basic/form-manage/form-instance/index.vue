<!--
 * @Description: 表单实例化
 * @Date: 2021-07-19
-->
<template>
  <div>
    <crud-basic
      ref="crud"
      title="表单实例列表"
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
      :handleSelectChange="mixinHandleSelectChange"
      @handleMultipleDelete="mixinHandleMultipleDelete"
    />

    <!-- 表单实例-编辑 -->
    <form-preview
      ref="editFormInstance"
      :confirm-btn="true"
      :reset-btn="false"
      confirm-text="提交"
      cancel-text="取消"
      dialog-title="表单实例编辑"
      previewOpType="instancePreview"
      @onSave="handleFormInstanceSave"
      @onFieldChange="handleFormFieldChange"
    />

    <!-- 表单实例-查看 -->
    <form-preview
      ref="viewFormInstance"
      :confirm-btn="false"
      :reset-btn="false"
      :cancel-btn="true"
      previewOpType="instancePreview"
      cancel-text="关闭"
      dialog-title="表单实例查看"
    />
  </div>
</template>

<script>
import crud from '@/mixins/crud'
import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-form-instance'
import formOption from '@/views/system-basic/configuration/form/manage/manage-form-instance'
import FormPreview from '../components/form-preview'
import { formInstanceCreate } from '@/api/system-basic/form-manage'

export default {
  mixins: [crud],
  components: { FormPreview },

  data() {
    return {
      formOption,
      mixinSearchFormConfig: {
        models: { formName: '' }
      },
      tableConfiguration: tableConfiguration(this),
      fetchConfig: {
        retrieve: {
          url: '/form/instance/page',
          method: 'post'
        },
        delete: {
          url: '/form/instance/delete',
          method: 'post'
        }
      },
      formDataList: [],
      formInstance: {}
    }
  },

  created() {
    this.mixinRetrieveTableData()
  },

  methods: {
    handleFormPreview({ row, opType }) {
      this.$refs[`${opType}FormInstance`].open(
        row,
        'formInstanceView',
        false,
        opType
      )
    },

    // 表单值发生变化
    handleFormFieldChange(option) {
      const { models, field, value, row, formDataList } = option
      this.formDataList = formDataList
      this.formInstance = models

      const idx = this.formDataList.findIndex(({ paramKey }) => {
        return row.type === 'childForm'
          ? paramKey === row.model
          : paramKey === field
      })

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
            const filterValueItem =
              Array.isArray(item) && item.map(list => list.trim())
            return Array.isArray(item) && item !== ''
              ? filterValueItem.join('===')
              : item
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

    // 表单实例保存
    handleFormInstanceSave(cb) {
      const { close, row, editId, formDataList, templateForm } = cb
      const {
        userId,
        formVersion,
        formStatus = 'P',
        formName,
        formDesc,
        defJson
      } = row

      const params = {
        id: editId,
        formDesc,
        formName,
        formStatus,
        formVersion,
        userId,
        formDataList: this.formDataList,
        formDescJson: defJson
      }

      this.formDataList = formDataList
      // 保存前进行表单校验
      if (this.handleSaveBeforeVerify(templateForm)) {
        close({ saveBtnLoading: false })
        return
      }

      formInstanceCreate(params)
        .then(({ success }) => {
          if (success) {
            this.$notify.success({
              message: '表单实例编辑成功',
              duration: 2500
            })
            this.mixinRetrieveTableData()
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
    }
  }
}
</script>
