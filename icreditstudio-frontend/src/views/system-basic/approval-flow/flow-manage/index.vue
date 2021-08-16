<!--
 * @Author: lizheng
 * @Description: 画流程图界面
 * @Date: 2021-07-05
-->
<template>
  <div class="nsh-content">
    <div class="nsh-operation">
      <el-button @click="goBack">返回</el-button>
      <el-button type="primary" @click="dialogConfig.visible = true">
        保存
      </el-button>
    </div>
    <j-flow
      ref="jflow"
      :xml-info="xmlConfig"
      :xml-form-info="backXmlInfo"
      :bind-form="approvalInfo"
      :form-term="formInfo.list"
      :xml-term="formConfig"
      :xml-modal-data="modelData"
      @getXmlInfo="getXmlInfo"
      @getxmlFormInfo="getXmlFormInfo"
    />

    <!-- <BaseFormDialog
      :dialog-base-config="dialogConfig"
      :dialog-form-items="dialogFormItems"
      :dialog-form-config="dialogFormConfig"
    /> -->
    <!-- 分组名称-新增或修改 -->
    <el-dialog
      append-to-body
      :title="dialogConfig.title"
      :visible.sync="dialogConfig.visible"
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="dialogConfig.form" label-width="80px">
        <el-form-item
          label="流程名称"
          prop="name"
          :rules="[
            { required: true, message: '必填项不能为空', trigger: 'blue' }
          ]"
        >
          <el-input
            clearable
            show-word-limit
            :maxlength="50"
            v-model="dialogConfig.form.name"
            placeholder="请输入流程描述"
          />
        </el-form-item>
        <el-form-item label="流程描述">
          <el-input
            clearable
            type="textarea"
            show-word-limit
            :maxlength="100"
            v-model="dialogConfig.form.name"
            placeholder="请输入流程描述"
          />
        </el-form-item>
        <el-form-item style="text-align:right">
          <el-button type="primary" @click="handleFlowSave">
            保存
          </el-button>
          <el-button @click="handleCloseDialog">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import { saveProcdef } from '@/api/system-basic/approval-flow'
// import BaseFormDialog from '@/components/base/BaseFormDialog'

export default {
  name: 'ManageFlowPaint',

  // components: { BaseFormDialog },

  data() {
    return {
      roleTree: [],
      term: [],
      errTips: [],

      dialogConfig: {
        title: '请输入流程名称',
        visible: false,
        form: {}
      },

      dialogFormItems: {
        name: ''
      },

      dialogFormConfig: {
        refName: 'dialogForm'
      }
    }
  },

  computed: {
    ...mapGetters({
      approvalInfo: 'approval-form/approvalInfo',
      xmlConfig: 'approval-form/xmlConfig',
      backXmlInfo: 'approval-form/backXmlInfo',
      formConfig: 'approval-form/formConfig',
      modelData: 'approval-form/modelData',
      formInfo: 'approval-form/formInfo'
    })
  },

  methods: {
    ...mapActions('approval-form', ['setBackXmlInfo']),

    getXmlInfo(data) {
      const fromData = new FormData()
      fromData.append('formKey', data.formKey)
      fromData.append('tenantId', data.tenantId)
      fromData.append('file', data.file)
      saveProcdef(fromData).then(() => {
        this.$notify.success('保存流程成功')
      })
    },

    getXmlFormInfo(data) {
      console.log('getXmlFormInfo flow', data)
      this.setBackXmlInfo({
        ...data,
        backPath: '/approvalFlow/flow',
        hasPublish: 2
      })
      this.$router.push('/approvalFlow/formGenerator')
    },

    saveFile() {
      this.dialogConfig.visible = true
      this.$refs.jflow.saveFile()
    },

    goBack() {
      this.dialogConfig.visible = false
      this.setBackXmlInfo({})
      this.$router.push('/approvalFlow/form')
    },

    handleFlowSave() {
      this.handleCloseDialog()
    },

    handleCloseDialog() {
      this.dialogConfig.visible = false
    }
  }
}
</script>
<style lang="scss" scoped>
.nsh-content {
  transform: translate(0, 0);
}
.nsh-operation {
  position: absolute;
  top: 35px;
  z-index: 222;
  left: 110px;
}
</style>
