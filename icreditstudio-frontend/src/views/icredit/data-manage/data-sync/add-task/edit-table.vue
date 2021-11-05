<!--
 * @Description: 编辑表结构
 * @Date: 2021-09-26
-->
<template>
  <div class="edit-table-dialog">
    <BaseDialog
      ref="baseDialog"
      width="850px"
      :before-title-name="titleName"
      :title="title"
      @on-confirm="handleConfirm"
    >
      <div class="header">
        <span class="label">表结构</span>
        <el-button class="btn" plain type="primary" @click="handleBatchDelete"
          >批量删除</el-button
        >
      </div>
      <j-table
        ref="editTable"
        v-loading="tableLoading"
        :table-data="tableData"
        :table-configuration="tableConfiguration"
        @selection-change="handleSelectChange"
      >
      </j-table>
    </BaseDialog>
  </div>
</template>

<script>
import BaseDialog from '@/views/icredit/components/dialog'
import tableConfiguration from '@/views/icredit/configuration/table/data-sync-edit-table'
import { deepClone } from '@/utils/util'

export default {
  components: { BaseDialog },

  data() {
    return {
      title: '编辑表结构',
      titleName: '',
      detailLoading: false,
      tableLoading: false,
      tableData: [],
      selectedRows: [],
      tableConfiguration: tableConfiguration(this)
    }
  },

  methods: {
    open(data) {
      console.log('data', data)
      this.tableData = data
      this.$refs.baseDialog.open()
    },

    handleSelectChange(selectedRows) {
      console.log(selectedRows, ' lplp')
      this.selectedRows = selectedRows.map(({ sort }) => sort)
    },

    handleDelete({ $index, row }) {
      console.log(row, this.selectedRows, 'rows')
      this.$refs.editTable.$refs.dataSyncEditTable.clearSelection()
      this.tableData.splice($index, 1)
    },

    handleBatchDelete() {
      this.tableData = deepClone(this.tableData).filter(
        item => !this.selectedRows.includes(item.sort)
      )
      this.$refs.editTable.$refs.dataSyncEditTable.clearSelection()
    },

    handleConfirm() {
      this.$refs.baseDialog.close()
      this.$refs.editTable.$refs.dataSyncEditTable.clearSelection()
      this.$emit('on-confirm', this.tableData)
    }
  }
}
</script>

<style lang="scss" scoped>
.edit-table-dialog {
  .header {
    @include flex(row, space-between);
    position: relative;
    margin-bottom: 22px;

    .label {
      margin-left: 10px;
      font-size: 14px;
      font-family: PingFangSC, PingFangSC-Regular;
      font-weight: 400;
      text-align: left;
      color: #262626;
    }

    .btn {
      background: #fff;
      border: 1px solid #1890ff;
      border-radius: 4px;
    }

    ::v-deep {
      .el-button--primary.is-plain:focus,
      .el-button--primary.is-plain:hover {
        color: #fff;
        background: #1890ff;
      }
    }

    &::before {
      content: '';
      position: absolute;
      top: 7px;
      left: 0;
      width: 4px;
      height: 18px;
      background: #1890ff;
      border-radius: 0px 2px 2px 0px;
    }
  }
}
</style>
