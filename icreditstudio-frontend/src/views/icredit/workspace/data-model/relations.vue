<!--
 * @Author: lizheng
 * @Description: 新增关联关系
 * @Date: 2021-10-11
-->
<template>
  <BaseDialog
    ref="baseDialog"
    width="800px"
    :title="title"
    :hide-footer="true"
    @onClose="handleClose"
    @onConfirm="handleConfirm"
  >
    <div class="relation-wrap">
      <div class="form-item">
        <div class="label">主表</div>
        <div class="content">tableA</div>
      </div>

      <div class="form-item">
        <div class="label">关联表</div>
        <div class="content">
          <el-select style="width:500px" v-model="value" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>
      </div>

      <div class="form-item">
        <div class="label">关联关系</div>
        <div class="link-wrap">
          <div
            :class="[
              'link-item',
              item.link === form.link ? 'link-item-active' : ''
            ]"
            :key="idx"
            v-for="(item, idx) in linkConfig"
            @click="handleChangeLink(item)"
          >
            <j-svg
              class="j-svg"
              :name="item.link === form.link ? item.iconActive : item.icon"
            />
            <span class="text">{{ item.name }}</span>
          </div>
        </div>
      </div>

      <div class="form-item">
        <div class="label">关联字段</div>
        <div class="content">
          <JTable
            ref="table"
            class="form-table"
            :table-data="tableData"
            :table-configuration="tableConfiguration"
          >
            <template #operationColumn="{row, index}">
              <div class="btn-wrap">
                <span class="btn" @click="handleAddClick(row, index)">
                  <i class="el-icon-plus icon"></i>
                </span>
                <span class="btn" @click="handleMinusClick(row, index)">
                  <i class="el-icon-minus icon"></i>
                </span>
              </div>
            </template>
          </JTable>
        </div>
      </div>
    </div>
  </BaseDialog>
</template>

<script>
import BaseDialog from '@/views/icredit/components/dialog'
import tableConfiguration from '@/views/icredit/configuration/table/workspace-model-link'

export default {
  components: { BaseDialog },
  data() {
    return {
      title: '新增关联关系',
      options: [{ value: '选项1', label: 'tableB' }],
      value: '',
      form: {
        link: 'left',
        tableA: 'tableA',
        tableB: 'tableB'
      },
      linkConfig: [
        {
          name: '左关联',
          link: 'left',
          iconActive: 'left-link',
          icon: 'left-link-gray'
        },
        {
          name: '内关联',
          link: 'cover',
          iconActive: 'cover-link',
          icon: 'cover-link-gray'
        },
        {
          name: '全关联',
          link: 'all',
          iconActive: 'all-link',
          icon: 'all-link-gray'
        }
      ],
      tableData: [{}],
      tableConfiguration
    }
  },

  methods: {
    open() {
      this.$refs.baseDialog.open()
    },

    handleAddClick(row, idx) {
      console.log(row, idx)
      this.tableData.splice(idx + 1, 0, {})
    },

    handleMinusClick(row, idx) {
      console.log(row, idx)
      if (this.tableData.length < 2) return
      this.tableData.splice(idx, 1)
    },

    handleClose() {
      this.dialogVisible = false
    },

    handleConfirm() {
      this.handleClose()
      this.$emit('on-confirm')
    },

    handleChangeLink(item) {
      this.form.link = item.link
    },

    handleViewLogDetail() {}
  }
}
</script>

<style lang="scss" scoped>
.relation-wrap {
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

      .form-table {
        width: 580px;
      }

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
  }
}
</style>
