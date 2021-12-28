<!--
 * @Author: lizheng
 * @Description: 新增关联关系
 * @Date: 2021-09-08
-->
<template>
  <BaseDialog
    class="link-type"
    ref="baseDialog"
    width="800px"
    :title="title"
    :hideFooter="opType === 'edit'"
    @on-confirm="confirm"
  >
    <el-form ref="form" :model="form" label-width="100px" v-loading="loading">
      <el-form-item
        class="form-item"
        label="关联类型"
        prop="associatedType"
        :rules="{ required: true, message: '必填项不能为空', trigger: 'blur' }"
      >
        <!-- <el-tag
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
        </el-tag> -->
        <div class="link-wrap">
          <div
            :class="[
              'link-item',
              item.code === form.associatedType ? 'link-item-active' : ''
            ]"
            :key="idx"
            v-for="(item, idx) in form.linkTypeData.assocTypes"
            @click="handleChangeLink(item)"
          >
            <j-svg
              class="j-svg"
              :name="
                item.code === form.associatedType ? item.iconActive : item.icon
              "
            />
            <span class="text">{{ item.name }}</span>
          </div>
        </div>
      </el-form-item>

      <el-form-item
        class="form-item"
        style="margin-top:30px"
        label="关联条件"
        prop="linkTypeData"
        :rules="{ required: true, message: '必填项不能为空', trigger: 'blur' }"
      >
        <div class="content">
          <JTable
            ref="table"
            class="form-table"
            :table-data="form.linkTypeData.conditions"
            :table-configuration="tableConfiguration"
          >
            <template #operationColumn="{row, index}">
              <div class="btn-wrap">
                <span class="btn" @click="handleAddClick(index)">
                  <i class="el-icon-plus icon"></i>
                </span>
                <span class="btn" @click="handleMinusClick(index)">
                  <i class="el-icon-minus icon"></i>
                </span>
              </div>
            </template>
          </JTable>
        </div>
        <!-- <div class="link-table">
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
                  @change="handleChangeLeftSelect(item)"
                >
                  <el-option
                    v-for="item in form.linkTypeData.conditions"
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
                  @change="handleChangeRightSelect(item)"
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
                  :disabled="opType === 'edit'"
                  @click="handleAddClick"
                >
                </el-button>
                <el-button
                  type="danger"
                  icon="el-icon-minus"
                  circle
                  :disabled="
                    form.linkTypeData.conditions.length < 2 || opType === 'edit'
                  "
                  @click="handleMinusClick(idx)"
                ></el-button>
              </el-col>
            </el-row>
          </template>
        </div> -->
      </el-form-item>
    </el-form>
  </BaseDialog>
</template>

<script>
import BaseDialog from '@/views/icredit/components/dialog'
import API from '@/api/icredit'
import { deepClone } from '@/utils/util'
import { iconMapping } from '../contant'
import tableConfiguration from '@/views/icredit/configuration/table/workspace-model-link'

export default {
  components: { BaseDialog },

  data() {
    return {
      valid: true,
      idx: null,
      leftSelectVal: {},
      rightSelectVal: {},

      title: '',
      opType: '',
      loading: false,
      aTableName: 'tableA',
      bTableName: 'tableB',
      aTableOption: [],
      leftTable: {},
      rightTable: {},
      bTableOption: [],
      conditionsOptions: [],

      form: {
        associatedType: 0,
        linkTypeData: {
          assocTypes: [],
          conditions: []
        }
      }
    }
  },

  computed: {
    tableConfiguration() {
      return tableConfiguration(this)
    }
  },

  methods: {
    open(options) {
      const {
        idx,
        title,
        opType,
        dialect,
        leftTable,
        rightTable,
        associatedType,
        conditions
      } = options

      this.valid = true
      this.title = title
      this.idx = idx
      this.opType = opType
      this.leftTable = leftTable
      this.rightTable = rightTable
      this.aTableName = leftTable.name
      this.bTableName = rightTable.name
      this.form.associatedType = associatedType ?? 0
      this.form.linkTypeData.conditions = conditions

      this.$refs.baseDialog.open()
      this.getLinkTypeData(dialect)
      if (opType === 'edit') return
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

    closeBtnLoading() {
      this.$refs.baseDialog.btnLoadingClose()
    },

    confirm() {
      const { form, leftTable, rightTable } = this
      const relationData = {
        idx: this.idx,
        associatedType: form.associatedType,
        conditions: this.form.linkTypeData.conditions,
        leftSource: leftTable.name,
        leftSourceDatabase: leftTable.database,
        rightSource: rightTable.name,
        rightSourceDatabase: rightTable.database
      }

      // 关联条件必填
      if (relationData.conditions.length) {
        relationData.conditions.forEach(({ left, right, associate }) => {
          this.valid = left && associate && right
        })

        !this.valid && this.$message.error('请先选择要关联的表字段及关联关系！')
        this.closeBtnLoading()
      }

      this.valid && this.validate(relationData)
    },

    validate(relationData) {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.close()
          this.$emit('on-confirm', relationData)
        }
        this.closeBtnLoading()
      })
    },

    handleTagClick(curTag) {
      this.opType !== 'edit' && (this.form.associatedType = curTag.code)
      this.$refs.form.clearValidate()
    },

    handleChangeLink(item) {
      this.form.associatedType = item.code
    },

    // 左表字段选择类型判断
    handleChangeLeftSelect(options) {
      const { left, right } = options.scope.row
      this.leftSelectVal = this.aTableOption.find(({ value }) => value === left)

      if (right) {
        const { fieldType } = this.leftSelectVal
        const { fieldType: rType } = this.rightSelectVal
        if (fieldType !== rType) {
          // eslint-disable-next-line no-param-reassign
          options.scope.row.left = ''
          this.$message.error({
            message: `字段类型不统一, 左表字段类型：${fieldType}, 右表字段类型：${rType}, 请重新选择!`,
            duration: 3500
          })
        }
      }
    },

    // 右表字段选择类型判断
    handleChangeRightSelect(options) {
      const { left, right } = options.scope.row
      this.rightSelectVal = this.bTableOption.find(
        ({ value }) => value === right
      )

      if (left) {
        const { fieldType } = this.leftSelectVal
        const { fieldType: rType } = this.rightSelectVal
        if (fieldType !== rType) {
          // eslint-disable-next-line no-param-reassign
          options.scope.row.right = ''
          this.$message.error({
            message: `字段类型不统一, 左表字段类型：${fieldType}, 右表字段类型：${rType}, 请重新选择!`,
            duration: 3500
          })
        }
      }
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
                const { icon, name, iconActive } = iconMapping[item.code]
                return {
                  icon,
                  name,
                  iconActive,
                  code: Number(item.code),
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
            this[type] = deepClone(data).map(({ name, fieldType }) => ({
              value: name,
              fieldType
            }))

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

  .form-item {
    @include flex(row, flex-start);
    margin: 15px 0;
    font-family: PingFangSC, PingFangSC-Regular;

    .label {
      width: 120px;
      margin-right: 10px;
      text-align: right;
    }

    .content {
      flex: 1;
      font-size: 14px;
      font-weight: 400;
      text-align: left;
      color: #666;

      // .form-table {
      //   width: 580px;
      // }

      .btn-wrap {
        @include flex;
        .btn {
          display: inline-block;
          width: 14px;
          height: 14px;
          border: 1px solid #999;
          margin: 0 10px;
          line-height: 14px;
          text-align: center;
          cursor: pointer;
          font-size: 12px;
          border-radius: 2px;

          .icon {
            font-size: 12px;
            transform: scale(0.6);
          }
        }
      }
    }

    .link-wrap {
      @include flex(row, flex-start);
      flex: 1;

      .link-item {
        @include flex(column);
        width: 110px;
        height: 80px;
        border: 1px solid #00000026;
        border-radius: 4px;
        margin-right: 20px;
        cursor: pointer;

        .j-svg {
          width: 45px;
          height: 30px;
        }

        .text {
          font-size: 14px;
          font-family: PingFangSC, PingFangSC-Regular;
          font-weight: 400;
          text-align: right;
          color: rgba(0, 0, 0, 0.85);
          line-height: 20px;
          margin-top: 10px;
        }
      }

      .link-item-active {
        border: 1px solid #1890ff;
      }
    }

    ::v-deep {
      .form-table .el-input__inner {
        border: none;
        background-color: transparent;
      }

      .el-form-item__content {
        margin-left: 0 !important;
      }
    }
  }
}
</style>
