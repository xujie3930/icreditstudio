<template>
  <div class="common-dialog">
    <el-dialog
      :title="type === 'credit' ? '授信审批' : '立项审批'"
      :visible.sync="dialogVisible"
      width="850px"
      :close-on-click-modal="false"
    >
      <div class="list-container">
        <div v-for="element in creditItems" :key="element.id"
             class="list-item" @click="getFormExternal(element)">
          <img src="../../assets/img/approval_3.png" alt="">
          <el-tooltip effect="dark" :content="element.name"
                      placement="bottom" :disabled="isShowTooltip(element.name)">
            <span class="title">{{ element.name }}</span>
          </el-tooltip>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
/* eslint-disable */
import { mapGetters } from 'vuex'

export default {
  name: 'ProjectCredit',
  data() {
    return {
      creditItems: [],
      dialogVisible: false,
      sourceData: {},
      type: ''
    }
  },
  computed: {
    ...mapGetters(['buttonList', 'roleList', 'userData', 'userInfo'])
  },
  created() {

  },
  mounted() {

  },
  methods: {
    getCreditItems({ data, type }) {
      this.sourceData = data
      this.type = type
      // 去授信还是去立项  credit去授信 project去立项
      const formProcdefGroupId = type === 'credit' ? '722023494350536704' : '722023536914333696'
      this.$api.approval
        .procdefStart({})
        .then(res => {
          if (res.success) {
            const creditItems = res.data.filter(i => i.id === formProcdefGroupId)
            if (!res.data.length || !creditItems.length) {
              this.$message.error(`该角色下没有${type === 'credit' ? '授信' : '立项'}表单发起`)
              return
            }
            this.creditItems = creditItems[0].formInfoResults.filter(j => j.state === '0')
            this.dialogVisible = true
          } else {
            this.$message.error(res.returnMsg)
          }
        })
    },
    viewFormDetail(data) {
      if (this.userData.organizationId) {
        const params = {
          ...data
        }
        if (this.type === 'credit') {
          // 立项表单去授信时要把立项id带上
          params.apprProcInstId = this.sourceData.id
          params.tabs = 'sponsor'
          params.form = '/ApprovalManagement/approveList'
        } else {
          // 订单去立项时需要把订单id带上，一起传给后端
          params.orderId = this.sourceData.orderId
          params.form = '/OrderManage/OrderManage'
        }
        this.$store.dispatch('approval-form/setFormInfo', params)
          .then(() => {
            this.$router.push('/ApprovalManagement/approveFlow')
          })
      } else {
        this.$message.error('请先配置所属机构')
      }
    },
    async getFormExternal(data) {
      if (data.modelEditorJson) {
        const model = JSON.parse(data.modelEditorJson)
        for (let i = 0; i < model.list.length; i++) {
          if (model.list[i].type === 'grid') {
            for (let j = 0; j < model.list[i].columns.length; j++) {
              for (let v = 0; v < model.list[i].columns[j].list.length; v++) {
                const list = model.list[i].columns[j].list[v]
                if (list.options.bindType === 'external' && list.options.bindValue.external) {
                  await this.setExternal(list, model.config.checkedExternals)
                }
                if (list.options.bindType === 'internal'
                  && list.options.bindValue.internal.length
                  && this.type !== 'project') {
                  this.setInternal(list)
                }
              }
            }
          } else if (model.list[i].type === 'childForm') {
            for (let j = 0; j < model.list[i].tableColumns.length; j++) {
              const tableColumns = model.list[i].tableColumns[j]
              if (tableColumns.options.bindType === 'external' && tableColumns.options.bindValue.external) {
                await this.setExternal(tableColumns, model.config.checkedExternals)
              }
              if (tableColumns.options.bindType === 'internal' && tableColumns.options.bindValue.internal.length && this.type !== 'project') {
                this.setInternal(tableColumns)
              }
            }
          } else {
            if (model.list[i].options.bindType === 'external' && model.list[i].options.bindValue.external) {
              await this.setExternal(model.list[i], model.config.checkedExternals)
            }
            if (model.list[i].options.bindType === 'internal' && model.list[i].options.bindValue.internal.length && this.type !== 'project') {
              this.setInternal(model.list[i])
            }
          }
        }
        await this.viewFormDetail({ ...data, modelEditorJson: JSON.stringify(model) })
      }
    },
    setExternal(datas, config) {
      let bindValue = []
      config.forEach((i, index) => {
        const labelScreen = i.options.filter(j => {
          return j.label === datas.options.bindValue.external.label
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
        this.$set(this.sourceData, 'orderId', this.sourceData.id)
        inParameter.forEach(i => {
          params[i.value] = this.sourceData[i.value]
        })
      }
      return new Promise(resolve => {
        this.$api.approval.customRequest({ params, method: bindValue.type, url: bindValue.url })
          .then(res => {
            if (res.success) {
              const normal = datas
              normal.options.defaultValue = res.data[datas.options.bindValue.external.value]
              resolve('success')
            } else {
              this.$message.error(res.returnMsg)
            }
          })
      })
    },
    setInternal(data) {
      const bindValue = data.options.bindValue.internal
      const formJson = JSON.parse(this.sourceData.processVariables.formJson)
      formJson.list.forEach(item => {
        if (item.type === 'childForm') {
          item.tableColumns.forEach(i => {
            if (bindValue.indexOf(i.model) !== -1) {
              this.setDefaultValue(data, i)
            }
          })
        } else if (item.type === 'grid') {
          item.columns.forEach(i => {
            i.list.forEach(j => {
              if (bindValue.indexOf(j.model) !== -1) {
                this.setDefaultValue(data, j)
              }
            })
          })
        } else if (bindValue.indexOf(item.model) !== -1) {
          this.setDefaultValue(data, item)
        }
      })
    },
    setDefaultValue(targets, source) {
      const n = targets
      n.options = source.options
      n.options.defaultValue = this.sourceData.processVariables[source.model]
    },
    isShowTooltip(name) {
      const maxWidth = 198
      const length = name.length || 0
      const width = length * 18
      if (width >= maxWidth) {
        return false
      }
      return true
    }

  }
}
</script>
<style lang="scss" scoped>
.list-container{
    display: flex;
    flex-wrap: wrap;
    margin: 0 auto;
    .list-item{
        width: 250px;
        height: 60px;
        border: 1px solid #ebebeb;
        border-radius: 4px;
        margin: 10px;
        display: flex;
        font-size: 18px;
        align-items: center;
        cursor: pointer;
        img{
            width: 43px;
            height: 43px;
            margin: 0 17px;
        }
        .title{
            width: 170px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            font-size: 18px;
        }
    }
}
</style>

