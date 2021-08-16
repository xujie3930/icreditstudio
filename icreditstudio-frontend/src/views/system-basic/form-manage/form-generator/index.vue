<template>
  <j-form-customize
    ref="jnhForm"
    preview
    :form-info="formInfo"
    :data-source="formInfo.source"
    :form-rule="formInfo.rule"
    :xml-info="backXmlInfo"
    :publish="hasPublish"
    :save="formInfo.state !== '0'"
    :bind-external-info="formInfo.externalItems"
    :bind-internal-info="formInfo.formResults"
    :application-id="applicationId"
    :auto-config="{ isSave: false, time: '300000' }"
    :button-loading.sync="loading"
    :digital-dictionary-url="digitalDictionaryUrl"
    @getFormInfo="getFormInfo"
    @getXmlFormInfo="getXmlFormInfo"
    @autoSave="autoSave"
    @goBack="goBack"
    @handleEventEmit="handleEventEmit"
  />
</template>
<script>
import { mapActions, mapGetters } from 'vuex'
import { deepClone } from '@/utils/util'
// import JFormCustomize from '@/components/form-customize/src/index.vue'
import {
  // addFormIntoGroup,
  editFormState,
  editFormInfo
} from '@/api/system-basic/approval-flow'

import {
  formManageOperate
  // formManageDictionary
} from '@/api/system-basic/form-manage'

export default {
  name: 'FormGenerator',
  data() {
    return {
      source: [],
      rule: [],
      info: {},
      hasPublish: true,
      externalItems: [],
      loading: false,
      message: '',
      isPublish: false, // 是否已发布
      isSave: false, // 是否已保存
      digitalDictionaryUrl: '/api/code/code/getInfoByKey', // 数据源 - 数字字典接口url
      applicationId: window.__JConfig.baseConfig.applicationId,
      isEdit: false,
      editId: '',
      editType: '',
      backupFormInfo: {}
    }
  },

  computed: {
    ...mapGetters({
      // formInfo: 'approval-form/formInfo',
      approvalInfo: 'approval-form/approvalInfo',
      backXmlInfo: 'approval-form/backXmlInfo',
      userInfo: 'user/userInfo'
    }),
    formInfo() {
      return this.$ls.get('approvalInitiate-formInfo')
    }
  },

  provide() {
    return {
      userInfo: this.userInfo
    }
  },

  mounted() {
    this.backupData()
    if (this.backXmlInfo.hasPublish === 2) {
      this.hasPublish = false
    }
  },

  destroyed() {
    this.setFormInfo({})
  },

  methods: {
    ...mapActions('approval-form', [
      'setFormInfo',
      'setBackXmlInfo',
      'setXmlConfig'
    ]),
    backupData() {
      this.backupFormInfo = deepClone(this.formInfo)
      if (this.backupFormInfo.modelEditorJson) {
        this.backupFormInfo.modelEditorJson = JSON.parse(
          this.backupFormInfo.modelEditorJson
        )
      }
    },
    // 获取表单控件信息并执行保存或发布操作
    getFormInfo(data) {
      this.addSave(data)
    },

    autoSave(data) {
      this.message = '自动保存成功'
      if (this.formInfo.autoSave === 'add') {
        const params = {
          key: this.formInfo.key || new Date().getTime(),
          name: data.formTitle,
          modelEditorJson: data.modelEditorJson,
          description: data.formTitle,
          modelType: 2,
          tenantId: this.formInfo.tenantId
        }

        if (this.formInfo.key) {
          this.addEdit(params, undefined, true)
        } else {
          this.addSave(params, undefined, true)
        }
      }
      if (this.formInfo.autoSave === 'edit') {
        const params = {
          ...this.formInfo,
          name: data.formTitle,
          modelEditorJson: data.modelEditorJson
        }
        if (this.formInfo.state !== '2') {
          this.message = '自动发布成功'
          this.addEdit(params, this.publish, true)
        } else {
          this.addEdit(params, undefined, true)
        }
      }
    },

    getXmlFormInfo(data) {
      this.setXmlConfig(data.xml).then(() => {
        const backXmlInfo = {
          ...data,
          backPath: this.backXmlInfo.backPath
        }
        this.setBackXmlInfo(backXmlInfo)
        this.$router.push(
          `${this.backXmlInfo.backPath}/${this.approvalInfo.formKey}`
        )
      })
    },

    // 表单编辑（旧版接口 未用到此方法）
    addEdit(data, func = undefined, isAutoSave) {
      editFormInfo(data)
        .then(res => {
          if (!func) {
            this.$notify.success(this.message)
          } else {
            func(res.data.keys[0], isAutoSave)
          }
        })
        .finally(() => {
          this.loading = false
        })
    },

    // 表单新增或编辑或发布
    addSave(data, func = undefined, isAutoSave) {
      const { formDesc, formName, id, type, modelEditorJson } = data
      // 操作状态映射
      const operateMapping = {
        save: 'D', // 保存至草稿箱(D)
        edit: 'D', // 编辑至草稿箱(D)
        publishSave: 'P', // 新增后发布(P)
        publishEdit: 'P' // 编辑后发布(p)
      }
      // 表单参数
      const change = ['save', 'publishSave'].includes(this.editType || type)
        ? true
        : this.compareJsonEqual(this.backupFormInfo, deepClone(data))
      if (!change && ['save', 'edit'].includes(type)) {
        this.$notify.warning({
          title: '提示',
          message: '表单数据未发生改变！',
          duration: 2500
        })
        this.loading = false
        this.isSave = false
        this.isPublish = false
        return
      }

      // 表单提交参数
      const params = {
        change, // 表单内容是否更改(有更改： true, 无更改： false)
        formName,
        formDesc,
        userId: this.userInfo.id,
        formStatus: operateMapping[type], // 表单状态： D: 草稿箱状态  P：发布状态
        modelEditorJson: this.handleFilterFormJson(modelEditorJson)
      }

      // 表单控件标识是否重复
      const { modelEditorJson: mdJson } = params
      const signRepeatModel = mdJson.list.map(({ model }) => model)
      const signRepeatModelSet = new Set(signRepeatModel)
      if (signRepeatModel.length !== signRepeatModelSet.size) {
        return this.$notify.error('表单控件标识重复，请重新输入！')
      }

      // 新增表单没有id参数, 编辑时传id参数
      if (['edit', 'publishEdit'].includes(this.editType || type)) {
        params.id = this.editId || id || ''
      }

      this.loading = false
      // 返回提醒
      this.handleGoBackMessage(type)

      formManageOperate(params)
        .then(res => {
          const t = deepClone(data)
          this.backupFormInfo = t
          const { modelEditorJson: json, ...restParams } = params
          const modelJson = {
            authList: json.authList,
            config: JSON.parse(json.config),
            list: json.list.map(({ eleJson, model }) => ({
              model,
              ...JSON.parse(eleJson)
            }))
          }
          this.$ls.set('approvalInitiate-formInfo', {
            ...restParams,
            modelEditorJson: JSON.stringify(modelJson)
          })

          if (res.data.id) {
            this.isEdit = true
            this.editId = res.data.id
            if (type === 'save') {
              this.editType = 'edit'
              this.isSave = true
            }
            if (type === 'publishSave') {
              this.editType = 'publishEdit'
              this.isPublish = true
            }
          }

          if (func) return func(data.key, isAutoSave)
          const msg =
            params.formStatus === 'D' ? '表单保存成功！' : '表单发布成功！'
          params.formStatus === 'P' &&
            this.$router.push('/form/definition/pageList')
          this.$notify.success(msg)
        })
        .finally(() => {
          this.loading = false
        })
    },

    // 编辑操作： 编辑前后的表单JSON进行对比
    compareJsonEqual(backupData, formData) {
      const newBackupData = {
        formDesc: backupData.formDesc,
        formName: backupData.formName,
        id: backupData.id,
        modelEditorJson: backupData.modelEditorJson
      }
      const newFormData = {
        formDesc: formData.formDesc,
        formName: formData.formName,
        id: formData.id,
        modelEditorJson: formData.modelEditorJson
      }
      const t1 = deepClone(newBackupData)
      const keys1 = Object.keys(newBackupData)
      const t2 = deepClone(newFormData)
      for (const key in t2) {
        if (!keys1.includes(key)) {
          delete t2[key]
        }
      }
      delete t1.key
      delete t2.key
      return JSON.stringify(t1) !== JSON.stringify(t2)
    },

    // 处理表单控件参数
    handleFilterFormJson(formJson) {
      const { config, list } = formJson
      // 动态获取=>需要拿到每个控件下的操作属性添加的用户ID
      const authList = list.map(item => {
        return {
          model: item.model,
          permission: {
            canEdit: item.options.canEdit,
            canHidden: item.options.canHidden
          }
        }
      })

      // 处理表单控件列表单数
      const newList = list.map(item => {
        return {
          model: item.model,
          eleJson: JSON.stringify(item)
        }
      })

      return { config: JSON.stringify(config), authList, list: newList }
    },

    // 发布 （旧版接口 未用到此方法）
    publish(key, isAutoSave = false) {
      if (!isAutoSave) {
        this.message = '发布成功'
      }
      editFormState({ type: '2', key, modelType: 2 })
        .then(() => {
          this.$notify.success(this.message)
          if (!isAutoSave) {
            this.$router.push('/form/definition/pageList')
          }
        })
        .finally(() => {
          this.loading = false
        })
    },

    // 点击返回按钮离开当前页面前的提醒信息
    canLeave() {
      return new Promise(resolve => {
        if (this.isPublish) return resolve(true)
        this.$confirm(this.message, '提示', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        })
          .then(() => resolve(true))
          .catch(() => resolve(false))
      })
    },

    // 点击返回按钮
    goBack(data) {
      !this.isPublish && !this.isSave && this.handleGoBackMessage()
      this.canLeave(data).then(res => {
        if (res) {
          this.$router.push('/form/definition/pageList')
          this.isPublish = false
          this.isSave = false
        }
      })
    },

    // 点击返回按钮的相关提醒
    handleGoBackMessage(type) {
      switch (type) {
        case 'save':
          this.isSave = true
          break
        case 'edit':
          this.isSave = true
          break
        case 'publishSave':
          this.isPublish = true
          break
        case 'publishEdit':
          this.isPublish = true
          break
        default:
          this.isSave = false
          this.isPublish = false
          break
      }

      if (!this.isSave && !this.isPublish) {
        this.message = '表单尚未保存，请确认是否返回？'
      } else if (this.isSave && !this.isPublish) {
        this.message = '表单尚未发布，请确认是否返回？'
      }
    },

    handleEventEmit(eventInfo) {
      const { eventValue } = eventInfo
      try {
        this[eventValue](eventInfo)
      } catch (e) {
        console.log(e)
      }
    },
    test({ arg, eventType }) {
      console.log('事件类型', `${eventType}事件`)
      console.log('参数', arg)
      // alert(`${eventType}事件`)
      this.$ls.set('formEvent', `${eventType}事件`)
      this.$ls.set('formArgus', arg)
    }
  }
}
</script>
<style lang="scss" scoped>
.button {
  display: flex;
  justify-content: center;
  margin-top: 20px;

  .button-container {
    margin-right: 10px;
  }
}

.nsh-content .cus-dialog-container {
  display: flex;
  flex-direction: inherit;

  .form-container {
    width: 100%;
  }
}

@media screen and (max-width: 1500px) {
  .cus-dialog {
    width: 100%;
  }
}

@media screen and (min-width: 1500px) {
  .cus-dialog {
    width: 80%;
  }
}

.preview-title {
  display: flex;
  justify-content: center;
  font-size: 24px;
  font-weight: 600;
  color: #666;
  margin-bottom: 20px;
}

::v-deep {
  .op-item {
    margin-bottom: 10px;
  }

  .select-wrapper {
    width: 100%;
    min-height: $--input-height;
    border-radius: $--input-border-radius;
    border: $--input-border;
    padding: 0 15px;
    color: $--color-text-placeholder;
    transition: $--border-transition-base;
    line-height: $--input-height;
    &:hover {
      cursor: pointer;
      border-color: $--border-color-hover;
    }
  }

  .scroll-box {
    height: auto;
  }

  .table-desc {
    height: 70px;
    opacity: 1;
    font-size: 18px;
    font-family: PingFangSC, PingFangSC-Medium;
    font-weight: 500;
    text-align: center;
    color: #323232;
    margin: 0 auto;
    line-height: 70px;

    span {
      font-weight: 500;
      text-align: center;
      color: #323232;
      opacity: 1;
      font-size: 18px;
      font-family: PingFangSC, PingFangSC-Medium;
      line-height: 25px;
    }
  }

  .transfer-table-filter {
    margin-bottom: 5px;
  }

  .btn-group {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    .btn {
      margin: 16px 0;
    }
  }

  ::v-deep .el-table--border th {
    border-right: none;
  }

  ::v-deep .el-table--border td {
    border-right: none;
  }
  .tree-choose-transfer-box {
    /*background: rgba(249, 249, 249, 1);*/
    display: flex;
    justify-content: space-between;
    width: 1195px;
    height: 490px;
    opacity: 1;
    border: 1px solid #d9d9d9;
    border-radius: 10px;
    margin: 0 auto;
    padding: 2px;
    .tree-desc {
      height: 70px;
      width: 80px;
      margin: 0 auto;
      line-height: 70px;
      text-align: center;
      border-bottom: 3px solid #2f54eb;
      span {
        opacity: 1;
        font-size: 18px;
        font-family: PingFangSC, PingFangSC-Medium;
        font-weight: 500;
        text-align: center;
        color: #2f54eb;
        line-height: 25px;
      }
    }
    & .left {
      flex: 1;
      /*border: 1px solid red;*/
      & .left-tree {
        overflow-y: scroll;
        overflow-x: hidden;
        height: calc(100% - 70px);
        width: 100%;
        border-top: 1px solid #e8e8e8;
        & .left-tree-label {
          display: inline-block;
          text-overflow: ellipsis;
          white-space: nowrap;
          overflow: hidden;
          /*max-width: 28vw;*/
        }
      }
    }

    & .right {
      margin-left: 10px;
      flex: 3;
      /*border: 1px solid green;*/
      & ::v-deep .el-transfer-panel {
        width: auto;
        height: 100%;
      }

      & ::v-deep .el-transfer-panel__body {
        /*width: 345px;*/
        width: 40vm;
        height: 440px;

        & .el-transfer-panel__list.is-filterable {
          height: 100%;
        }
      }
    }
  }

  .template-select {
    .el-input--mini .el-input__inner {
      height: 23px;
      line-height: 23px;
    }

    .el-input {
      width: 85%;
    }

    .el-button--mini {
      font-size: 12px !important;
    }

    .el-button + .el-button {
      margin-left: 0;
    }
  }
}
</style>
