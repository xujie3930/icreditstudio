<!--
 * @Author: lizheng
 * @Description: 表单预览
 * @Date: 2021-07-15
-->
<template>
  <!-- 预览弹窗 -->
  <cus-dialog
    form
    ref="templatePreview"
    width="1000px"
    :title="dialogTitle"
    :visible="previewVisible"
    :is-back-edge="isBackEdge"
    @on-close="close"
  >
    <div v-loading="modelLoading">
      <j-form-preview
        :previewOpType="previewOpType"
        :isPrint="isPrint"
        insite="true"
        ref="previewForm"
        v-if="previewVisible"
        :form-title="formTitle"
        :data="templateForm"
        :remote="remoteFuncs"
        @handleEventEmit="handleEventEmit"
        @print="handlePrint"
        @on-change="changeFieldValue"
      >
        <template v-slot:blank="scope">
          Width
          <el-input v-model="scope.model.blank.width" style="width: 100px" />
          Height
          <el-input v-model="scope.model.blank.height" style="width: 100px" />
        </template>
      </j-form-preview>

      <div style="margin-top: 20px">
        <slot v-if="footer" name="footer" />
        <div v-else style="text-align:center">
          <el-button
            :loading="saveBtnLoading"
            v-if="confirmBtn"
            type="primary"
            @click="handleSave"
          >
            {{ confirmText }}
          </el-button>
          <el-button v-if="resetBtn" type="primary" @click="handleReset">
            重置
          </el-button>
          <el-button v-if="cancelBtn" @click="previewVisible = false">
            {{ cancelText }}
          </el-button>
        </div>
      </div>
    </div>
  </cus-dialog>
</template>

<script>
import CusDialog from './cus-dialog'
import {
  formListPreview,
  formHistoryPreview,
  formInstanceView,
  formInstanceCreateView
} from '@/api/system-basic/form-manage'
import { deepClone } from '@/utils/util'
import { mapGetters } from 'vuex'

export default {
  components: { CusDialog },

  data() {
    this.formHistoryPreview = formHistoryPreview // 表单历史版本预览方法
    this.formListPreview = formListPreview // 表单列表预览方法
    this.formInstanceView = formInstanceView //  表单实例列表查看方法
    this.formInstanceCreateView = formInstanceCreateView // 创建实例表单详情

    return {
      wrapZIndex: 0,
      modalZIndex: 0,
      isPrint: false,
      modelLoading: false,
      saveBtnLoading: false,
      previewVisible: false,
      formTitle: '',
      templateForm: {
        config: {},
        list: []
      },
      remoteFuncs: {},
      msgInstance: '',
      row: {},
      renderData: {},
      formDataList: [],
      editId: '',
      isBackEdge: false // 表单黑边框
    }
  },

  computed: {
    ...mapGetters({
      permissionList: 'user/permissionList',
      userInfo: 'user/userInfo'
    })
  },

  props: {
    previewOpType: {
      type: String,
      default: 'preview'
    },
    // 弹窗标题
    dialogTitle: {
      type: String,
      default: ''
    },
    // 页面底部取消按钮
    cancelBtn: {
      type: Boolean,
      default: true
    },
    // 页面底部取消按钮文案
    cancelText: {
      type: String,
      default: '返回'
    },
    // 页面底部确认按钮
    confirmBtn: {
      type: Boolean,
      default: false
    },
    // 页面底部确认按钮文案
    confirmText: {
      type: String,
      default: '保存'
    },
    // 页面底部重置按钮
    resetBtn: {
      type: Boolean,
      default: true
    },
    // 页面底部
    footer: {
      type: Boolean,
      default: false
    },
    // 是否展示预览
    preview: {
      type: Boolean,
      default: false
    },
    save: {
      type: Boolean,
      default: false
    },
    // 是否显示发布
    publish: {
      type: Boolean,
      default: false
    },
    previewCode: {
      type: Boolean,
      default: false
    },
    previewJson: {
      type: Boolean,
      default: false
    },
    upload: {
      type: Boolean,
      default: false
    },
    clearable: {
      type: Boolean,
      default: false
    },
    basicFields: {
      type: Array,
      default: () => [
        'input',
        'textarea',
        'editor',
        'text',
        'flow',
        'inputList',
        'label'
      ]
    },
    advanceFields: {
      type: Array,
      default: () => [
        'radio',
        'checkbox',
        'select',
        'time',
        'date',
        'datetime',
        'blank',
        'cascader'
      ]
    },
    uploadFields: {
      type: Array,
      default: () => ['imgupload', 'fileupload']
    },
    layoutFields: {
      type: Array,
      default: () => ['grid', 'childForm']
    },
    applicationId: {
      type: String,
      default: () => ''
    },
    formInfo: {
      type: Object,
      default: () => {
        return {}
      }
    },
    xmlInfo: {
      type: Object,
      default: () => {
        return {}
      }
    },
    dataSource: {
      type: Array,
      default: () => {
        return []
      }
    },
    formRule: {
      type: Array,
      default: () => {
        return []
      }
    },
    // 文件上传系统
    uploadSystem: {
      type: String,
      default: 'fsnsh'
    },
    buttonLoading: {
      type: Boolean,
      default: false
    },
    bindExternalInfo: {
      type: Array,
      default: () => {
        return []
      }
    },
    bindInternalInfo: {
      type: Array,
      default: () => {
        return []
      }
    },
    autoConfig: {
      type: Object,
      default: () => {
        return {
          isSave: true,
          time: '300000'
        }
      }
    },
    digitalDictionaryUrl: {
      type: String,
      default: ''
    }
  },

  methods: {
    open(row, method, isPrint = false, opType) {
      this.saveBtnLoading = false
      this.previewVisible = true
      this.row = row
      if (row && row.id) {
        this.modelLoading = true
        this[method]({ id: row.id })
          .then(({ success, data }) => {
            if (success) {
              this.formTitle = data.formName
              this.renderData = data.modelEditorJson
              opType
                ? this.handleFilterInstanceData(data, opType) // 若为表单实例的编辑或查看则执行
                : this.handleFilterPreviewData(data, method) // 预览

              // 若为导出操作则执行
              isPrint && this.handleBeforePrint()
            }
          })
          .finally(() => {
            this.modelLoading = false
          })
      }
    },

    // 表单定义—预览的表单组件数据
    handleFilterPreviewData(data) {
      const {
        modelEditorJson: { config, list },
        ...rest
      } = data

      // 如果是表单定义列表的预览释放权限控制
      const newList = list.map(({ eleJson, ...restItem }) => {
        const { options, model, ...restJson } = JSON.parse(eleJson)
        options.disabled = false
        options.readonly = false
        options.isHidden = false

        // 处理子表单
        const { type, tableColumns } = restJson
        if (type === 'childForm') {
          const newTbCol = deepClone(tableColumns).map(item => {
            const { options: tbOptions, ...tbRest } = item
            tbOptions.disabled = false
            tbOptions.readonly = false
            tbOptions.isHidden = false
            return {
              options: tbOptions,
              ...tbRest
            }
          })
          restJson.tableColumns = newTbCol
        }
        return {
          options,
          ...restJson,
          ...restItem
        }
      })

      this.templateForm = {
        config: JSON.parse(config),
        list: newList,
        ...rest
      }
      this.isBackEdge = this.templateForm.config.isBackEdge
    },

    // 表单实例-编辑或查看的表单组件数据
    handleFilterInstanceData(data, opType) {
      this.formDataList = data.formDataList
      this.editId = data.id
      const { id } = this.userInfo

      const { modelEditorJson, ...restData } = data
      const { formDataList } = restData
      const { authList } = modelEditorJson
      const list = modelEditorJson.list.map(({ eleJson, ...restItem }) => {
        const { options, model, ...restJson } = JSON.parse(eleJson)
        // 赋值操作
        deepClone(formDataList).forEach(item => {
          if (item.paramKey === model) {
            // 值可以多选的控件
            const paramValueArrType = [
              'checkbox',
              'select',
              'cascader',
              'date',
              'datetime',
              'childForm',
              'grid'
              // 'time',
              // 'imgupload',
              // 'fileupload'
            ]

            const componentType = item.paramKey.split('_')[0]
            const isValueMutiple = paramValueArrType.includes(componentType)
            const childFormValue =
              ['childForm', 'grid'].includes(componentType) &&
              !item.paramValue.includes('&')
                ? [item.paramValue]
                : item.paramValue
            let strOrArrVal = item.paramValue.includes('&')
              ? item.paramValue.split('&')
              : childFormValue

            // 级联多选
            if (componentType === 'cascader' && options.multiple) {
              const source =
                typeof strOrArrVal === 'string' ? [strOrArrVal] : strOrArrVal
              strOrArrVal = deepClone(source).map(casItem => {
                return casItem.includes('===')
                  ? casItem.split('===')
                  : [casItem]
              })
            }

            console.log('strOrArrVal', strOrArrVal)

            // 子表单组件赋值
            const defaultObjVal = {}
            if (componentType === 'childForm' || componentType === 'grid') {
              strOrArrVal.length &&
                strOrArrVal.forEach(strItem => {
                  const [key, value] = strItem.split('===')
                  const otherTypeDefVal = value.includes(',')
                    ? value.split(',')
                    : value
                  const cascaderVal = key.includes('cascader')
                    ? JSON.parse(value === '' ? '[]' : value)
                    : otherTypeDefVal
                  defaultObjVal[key] = cascaderVal
                })
            }

            // 栅格组件赋值
            if (componentType === 'grid') {
              restJson.columns = restJson.columns.map(
                ({ list: clist, ...restList }) => {
                  const nList = clist.map(({ options: gOptions, ...cItem }) => {
                    const { model: cModel, type } = cItem
                    const otherTypeDefVal =
                      paramValueArrType.includes(type) &&
                      defaultObjVal[cModel].includes(',')
                        ? defaultObjVal[cModel].split(',')
                        : defaultObjVal[cModel]
                    const cascaderDefVal = JSON.parse(defaultObjVal[cModel])
                    // eslint-disable-next-line no-param-reassign
                    gOptions.defaultValue =
                      type === 'cascader' ? cascaderDefVal : otherTypeDefVal

                    return {
                      options: gOptions,
                      ...cItem
                    }
                  })
                  return {
                    list: nList,
                    ...restList
                  }
                }
              )
            }

            const complexDefVal =
              componentType === 'childForm' ? [defaultObjVal] : strOrArrVal

            options.defaultValue = isValueMutiple
              ? complexDefVal
              : item.paramValue || options.defaultValue

            console.log(options.defaultValue, 'options.defaultValue')
          }
        })

        const filterParams = { id, opType, options, authList, restJson }
        const op = this.handleComponentAuth(filterParams)

        // 权限-若表单控件为 "childForm" 类型(子表单)
        if (restJson.type === 'childForm') {
          restJson.tableColumns = this.handleChildFormComp(filterParams)
        }

        // 权限-若表单控件为 "grid" 类型(栅格布局)
        if (restJson.type === 'grid') {
          restJson.columns = this.handleGridComponent(filterParams)
        }

        return {
          options: op,
          ...restJson,
          ...restItem
        }
      })

      this.templateForm = {
        list,
        config: JSON.parse(modelEditorJson.config)
      }
      this.isBackEdge = this.templateForm.config.isBackEdge
    },

    close(options) {
      // 只关闭保存按钮loading
      if (options && !options.saveBtnLoading) {
        this.saveBtnLoading = options.saveBtnLoading
        return
      }

      this.isBackEdge = false
      this.previewVisible = false
      this.saveBtnLoading = false
      this.templateForm = {
        config: {},
        list: []
      }
      this.formTitle = ''
    },

    // 表单实例 - 处理栅格控件权限控制
    handleGridComponent(filterParams) {
      const { id, opType, restJson, authList } = filterParams
      const { columns } = restJson
      const newColumns = deepClone(columns).map(columnItem => {
        const { options, list, ...restItem } = columnItem
        const newList = list.map(listItem => {
          const { options: listOp, ...restList } = listItem
          const params = { options: listOp, id, opType, authList }
          return {
            options: this.handleComponentAuth(params),
            ...restList
          }
        })
        return {
          options: this.handleComponentAuth(filterParams),
          list: newList,
          ...restItem
        }
      })
      return newColumns
    },

    // 表单实例 - 处理子表单控件权限控制
    handleChildFormComp(filterParams) {
      const { id, opType, restJson, authList } = filterParams
      const { tableColumns } = restJson
      const newTbColumns = deepClone(tableColumns).map(tbItem => {
        const { options, ...tbRest } = tbItem
        const params = { id, opType, options, authList }
        return {
          options: this.handleComponentAuth(params),
          ...tbRest
        }
      })
      return newTbColumns
    },

    // 表单实例 - 表单控件权限控制
    handleComponentAuth(filterParams) {
      const { id, opType, options, authList } = filterParams
      // 没有权限 authList 为空数组时， 组件为不可编辑状态
      if (!authList.length) {
        options.disabled = true
        options.isHidden = false
      }

      // 表单控件可编辑权限
      const isAuth = options.canEdit && !options.canEdit.includes(id)
      options.readonly = isAuth
      options.disabled = isAuth

      // 表单控件隐藏权限
      options.isHidden = options.canHidden && options.canHidden.includes(id)

      // 查看操作下所有表单组件都为不可编辑状态
      if (opType === 'view') {
        options.disabled = true
        options.isHidden = options.canHidden && options.canHidden.includes(id)
      }

      return options
    },

    // 保存
    handleSave() {
      this.$refs.previewForm.$refs.formPreview.validate(valid => {
        if (valid) {
          this.saveBtnLoading = true
          this.$emit('onSave', {
            row: this.row,
            form: this.renderData,
            editId: this.editId,
            formDataList: this.formDataList,
            templateForm: this.templateForm,
            reset: this.handleReset,
            close: this.close
          })
        }
      })
    },

    // 表单控件值发生改变
    changeFieldValue(field, value, row, models) {
      this.$emit('onFieldChange', {
        field,
        value,
        row,
        models,
        editId: this.editId,
        form: this.renderData,
        formDataList: this.formDataList,
        templateForm: this.templateForm
      })
    },

    // 打印PDF文件前的一些处理
    handleBeforePrint() {
      this.templateForm.formName &&
        this.$nextTick(() => {
          this.handleHideenDom('-9999', '-10000', 'start')
          this.msgInstance = this.$message.info({
            message: '表单导出中...',
            duration: 0,
            center: true
          })
          setTimeout(() => {
            this.isPrint = true
          }, 500)
        })
    },

    // 导出表单前的隐藏dialog以及modal组件
    handleHideenDom(wrapZIndex, modalZIndex, type = 'end') {
      const wrapperDom = []
      const modalDom = document.querySelectorAll('.v-modal')
      document.querySelectorAll('.cus-dialog').forEach(item => {
        item.ariaLabel.includes('预览') && wrapperDom.push(item.parentNode)
      })
      wrapperDom.forEach(item => {
        type === 'start' && (this.wrapZIndex = item.style.zIndex)
        // eslint-disable-next-line no-param-reassign
        wrapZIndex && (item.style.zIndex = wrapZIndex)
      })

      modalDom.forEach(item => {
        type === 'start' && (this.modalZIndex = item.style.zIndex)
        // eslint-disable-next-line no-param-reassign
        type === 'end' && (item.style.zIndex = this.modalZIndex)
        // eslint-disable-next-line no-param-reassign
        modalZIndex && (item.style.zIndex = modalZIndex)
      })
    },

    // 打印PDF - 方法回调
    handlePrint() {
      this.isPrint = false
      this.previewVisible = false
      this.msgInstance && this.msgInstance.close()
      this.msgInstance = null
      this.handleHideenDom()
    },

    handleEventEmit(eventInfo) {
      this.$emit('handleEventEmit', eventInfo)
    },

    handleReset() {
      this.$refs.previewForm.reset()
    }
  }
}
</script>

<style></style>
