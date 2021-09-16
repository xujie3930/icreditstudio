<!--
 * @Author: lizheng
 * @Description: 新增关联关系
 * @Date: 2021-09-08
-->
<template>
  <BaseDialog
    class="link-type"
    ref="baseDialog"
    width="1000px"
    :title="title"
    @on-confirm="confirm"
  >
    <el-form ref="form" :model="form" label-width="100px" v-loading="loading">
      <el-form-item
        label="关联类型"
        prop="associatedType"
        :rules="{ required: true, message: '必填项不能为空', trigger: 'blur' }"
      >
        <el-tag
          size="medium"
          effect="plain"
          class="link-type-tag"
          :key="tag.code"
          v-for="tag in form.linkTypeData.assocTypes"
          @click.native="handleTagClick(tag)"
        >
          <JSvg :name="tag.icon" />
          <span>{{ tag.name }}</span>
          <i
            v-if="form.associatedType === Number(tag.code)"
            class="el-icon-success is-checked"
          />
        </el-tag>
      </el-form-item>

      <el-form-item label="关联条件">
        <div class="link-table">
          <el-row :gutter="20">
            <el-col class="col-center" :span="7">{{ aTableName }}</el-col>
            <el-col class="col-center" :span="7">关系</el-col>
            <el-col class="col-center" :span="7">{{ bTableName }} </el-col>
          </el-row>

          <template v-for="(item, idx) in form.linkTypeData.conditions">
            <el-row style="margin:10px 0" :gutter="20" :key="idx">
              <el-col :span="7">
                <el-select
                  style="width:100%"
                  v-model="item.left"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in aTableOption"
                    :key="item.name"
                    :label="item.name"
                    :value="item.name"
                  >
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="7">
                <el-select
                  style="width:100%"
                  v-model="item.associate"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in conditionsOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="7">
                <el-select
                  style="width:100%"
                  v-model="item.right"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in bTableOption"
                    :key="item.name"
                    :label="item.name"
                    :value="item.name"
                  >
                  </el-option>
                </el-select>
              </el-col>

              <el-col :span="3">
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  circle
                  @click="handleAddClick"
                >
                </el-button>
                <el-button
                  type="danger"
                  icon="el-icon-minus"
                  circle
                  :disabled="form.linkTypeData.conditions.length < 2"
                  @click="handleMinusClick(idx)"
                ></el-button>
              </el-col>
            </el-row>
          </template>
        </div>
      </el-form-item>
    </el-form>
  </BaseDialog>
</template>

<script>
import BaseDialog from '@/views/icredit/components/dialog'
import API from '@/api/icredit'
import { deepClone } from '@/utils/util'
import { iconMapping } from '../contant'

export default {
  components: { BaseDialog },

  data() {
    return {
      idx: null,
      lfTbIdx: null,
      rhTbIdx: null,

      title: '',
      value: '',
      loading: false,
      aTableName: '',
      bTableName: '',
      aTableOption: [],
      leftTable: {},
      rightTable: {},
      bTableOption: [],
      conditionsOptions: [],

      form: {
        associatedType: null,
        linkTypeData: {
          assocTypes: [],
          conditions: []
        }
      }
    }
  },

  methods: {
    open(options) {
      console.log(options, 'option')
      const {
        idx,
        lfTbIdx,
        rhTbIdx,
        title,
        dialect,
        leftTable,
        rightTable,
        associatedType,
        conditions
      } = options

      this.title = title
      this.idx = idx
      this.lfTbIdx = lfTbIdx
      this.rhTbIdx = rhTbIdx
      this.leftTable = leftTable
      this.rightTable = rightTable
      this.aTableName = leftTable.name
      this.bTableName = rightTable.name
      this.form.associatedType = associatedType
      this.form.linkTypeData.conditions = conditions

      this.$refs.baseDialog.open()
      this.getLinkTypeData(dialect)
      this.getTableField('aTableOption', {
        datasourceId: leftTable.datasourceId,
        tableName: leftTable.name
      })
    },

    close() {
      this.$refs.form.resetFields()
      this.aTableOption = []
      this.aTableOption = []
      this.form.associatedType = null
      this.form.linkTypeData.conditions = [
        { left: '', associate: '', right: '' }
      ]
      this.$refs.baseDialog.close()
    },

    initData() {},

    closeBtnLoading() {
      this.$refs.baseDialog.btnLoadingClose()
    },

    confirm() {
      const {
        form,
        form: { linkTypeData },
        leftTable,
        rightTable
      } = this
      const relationData = {
        idx: this.idx,
        lfTbIdx: this.lfTbIdx,
        rhTbIdx: this.rhTbIdx,
        associatedType: form.associatedType,
        conditions: linkTypeData.conditions,
        leftSource: leftTable.name,
        leftSourceDatabase: leftTable.database,
        rightSource: rightTable.name,
        rightSourceDatabase: rightTable.database
      }
      this.validate(relationData)
    },

    validate(relationData) {
      console.log('relationData', relationData)
      this.$refs.form.validate(valid => {
        if (valid) {
          this.close()

          this.$emit('on-confirm', relationData)
        }
        this.closeBtnLoading()
      })
    },

    handleTagClick(curTag) {
      console.log(curTag)
      this.form.associatedType = curTag.code
    },

    // 新增字段关联
    handleAddClick() {
      this.form.linkTypeData.conditions.push({
        left: '',
        associate: '',
        right: ''
      })
    },

    // 删除字段关联
    handleMinusClick(idx) {
      this.form.linkTypeData.conditions.length > 1 &&
        this.form.linkTypeData.conditions.splice(idx, 1)
    },

    // 获取关联条件数据
    getLinkTypeData(dialect = 'mysql') {
      this.loading = true
      API.dataSyncLinkType({ dialect })
        .then(({ success, data }) => {
          if (success && data) {
            this.form.linkTypeData.assocTypes = deepClone(data.assocTypes).map(
              item => {
                return {
                  icon: iconMapping[item.code].icon,
                  name: iconMapping[item.code].name,
                  ...item
                }
              }
            )
            this.conditionsOptions = deepClone(data.assocConditions).map(
              item => {
                return { label: item, value: item }
              }
            )
          }
        })
        .finally(() => {
          this.loading = false
        })
    },

    // 获取表字段
    getTableField(type, params) {
      this.loading = true
      API.dataSyncFieldSearch(params)
        .then(({ success, data }) => {
          if (success && data) {
            this[type] = data
            if (type !== 'bTableOption') {
              this.getTableField('bTableOption', {
                datasourceId: this.rightTable.datasourceId,
                tableName: this.rightTable.name
              })
            }
          }
        })
        .finally(() => {
          this.loading = false
        })
    }
  }
}
</script>

<style lang="scss" scoped>
.link-type {
  &-tag {
    position: relative;
    display: inline-flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: auto;
    min-height: 50px;
    margin-right: 20px;
    cursor: pointer;
  }

  .is-checked {
    position: absolute;
    font-size: 16px;
    top: -5px;
    right: -5px;
  }

  .link-table {
    .col-center {
      text-align: center;
    }

    .icon {
      cursor: pointer;
    }

    .blank {
      width: 100%;
      height: 32px;
    }
  }
}
</style>
