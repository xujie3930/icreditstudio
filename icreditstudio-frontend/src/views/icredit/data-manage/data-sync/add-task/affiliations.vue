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
          :key="item.code"
          v-for="item in linkTypeData.assocTypes"
          @click.native="handleTagClick(item)"
        >
          <JSvg :name="item.icon" />
          <span>{{ item.name }}</span>
          <i
            v-if="form.associatedType === Number(item.code)"
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

          <template v-for="(item, idx) in linkTypeData.conditions">
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
                    v-for="item in linkTypeData.conditionsOptions"
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
                  :disabled="linkTypeData.conditions.length < 2"
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

export default {
  components: { BaseDialog },

  data() {
    return {
      idx: null,
      title: '',
      value: '',
      loading: false,
      aTableName: '',
      bTableName: '',
      aTableOption: [],
      leftTable: {},
      rightTable: {},
      bTableOption: [],
      linkTypeData: {
        assocTypes: [],
        conditions: [{ left: '', associate: '', right: '' }],
        conditionsOptions: []
      },
      form: { radio: 1, associatedType: null }
    }
  },

  methods: {
    open(options) {
      const { title, idx, dialect, leftTable, rightTable } = options
      this.title = title
      this.idx = idx
      this.leftTable = leftTable
      this.rightTable = rightTable
      this.aTableName = leftTable.name
      this.bTableName = rightTable.name
      this.$refs.baseDialog.open()
      this.getLinkTypeData(dialect)
      this.getTableField('aTableOption', {
        datasourceId: leftTable.datasourceId,
        tableName: leftTable.name
      })
    },

    close() {
      // this.aTableOption = []
      // this.aTableOption = []
      // this.$refs.form.resetFields()
      // this.linkTypeData.conditions = [{ left: '', associate: '', right: '' }]
      this.$refs.baseDialog.close()
    },

    closeBtnLoading() {
      this.$refs.baseDialog.btnLoadingClose()
    },

    confirm() {
      const { form, linkTypeData, leftTable, rightTable } = this
      const relationData = {
        idx: this.idx,
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
      this.linkTypeData.conditions.push({ left: '', associate: '', right: '' })
    },

    // 删除字段关联
    handleMinusClick(idx) {
      this.linkTypeData.conditions.length > 1 &&
        this.linkTypeData.conditions.splice(idx, 1)
    },

    // 获取关联条件数据
    getLinkTypeData(dialect = 'mysql') {
      this.loading = true
      const iconMapping = {
        0: { icon: 'left-link', name: '左关联' },
        1: { icon: 'cover-link', name: '内关联' },
        2: { icon: 'all-link', name: '全关联' }
      }
      API.dataSyncLinkType({ dialect })
        .then(({ success, data }) => {
          if (success && data) {
            this.linkTypeData.assocTypes = deepClone(data.assocTypes).map(
              item => {
                return {
                  icon: iconMapping[item.code].icon,
                  name: iconMapping[item.code].name,
                  ...item
                }
              }
            )
            this.linkTypeData.conditionsOptions = deepClone(
              data.assocConditions
            ).map(item => {
              return { label: item, value: item }
            })
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
