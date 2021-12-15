<!--
 * @Description: 字典表
 * @Date: 2021-09-27
-->
<template>
  <div class="w100 h100 dictionary-page">
    <crud-basic
      ref="crud"
      title="字典表列表"
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
      :handleSearch="mixinHandleSearch"
      :handleReset="mixinHandleReset"
      :handleAdd="mixinHandleAdd"
      @handleAddDict="handleAddDict"
      @handleImportDict="handleImportDict"
    >
      <template #content>
        <j-table
          v-loading="mixinTableLoading"
          :table-configuration="tableConfiguration"
          :table-pagination="mixinTablePagination"
          :table-data="mixinTableData"
          @handleSizeChange="mixinHandleSizeChange"
          @handleCurrentChange="mixinHandleCurrentChange"
        >
          <template #operationColumn="{row}">
            <el-popover
              placement="right"
              width="520"
              trigger="click"
              @show="handleViewClick(row, 'View')"
            >
              <j-table
                ref="viewTable"
                class="view-table"
                v-loading="viewTableLoading"
                :table-configuration="viewTableConfiguration"
                :table-data="viewTableData"
              ></j-table>
              <el-button style="margin-right:10px" slot="reference" type="text">
                查看
              </el-button></el-popover
            >

            <el-button
              type="text"
              @click="
                handleAddDict({ row, opType: 'Edit', title: '编辑字典表' })
              "
            >
              编辑
            </el-button>
            <el-button type="text" @click="handleDeleteDict(row, 'Delete')">
              删除
            </el-button></template
          >
        </j-table></template
      >
    </crud-basic>
    <Message ref="message" @on-confirm="messageOperateCallback" />
    <AddDialog ref="addDialog" @on-confirm="dialogOperateCallback" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import viewTableConfiguration from '@/views/icredit/configuration/table/data-dictionary-add'
import tableConfiguration from '@/views/icredit/configuration/table/data-manage-dictionary'
import formOption from '@/views/icredit/configuration/form/data-manage-dictionary'
import Message from '@/views/icredit/components/message'
import AddDialog from './add'
import crud from '@/mixins/crud'
import operate from '@/mixins/operate'
import workspace from '@/mixins/workspace'
import API from '@/api/icredit'

const { group, ...rest } = viewTableConfiguration
const viewTbGroup = group
  .filter(({ prop }) => prop !== 'operation')
  .map(item => {
    const { type, ...restItem } = item
    return { type: 'text', ...restItem }
  })

export default {
  name: 'dictionaryTable',
  mixins: [crud, operate, workspace],
  components: { Message, AddDialog },

  data() {
    return {
      formOption,
      mixinSearchFormConfig: { models: { dictName: '' } },
      tableConfiguration,
      viewTableConfiguration: { group: viewTbGroup, ...rest },
      viewTableLoading: false,
      viewTableData: [],
      fetchConfig: {
        retrieve: { url: '/datasync/dict/pageList', method: 'post' }
      }
    }
  },

  created() {
    this.mixinRetrieveTableData()
  },

  computed: {
    ...mapGetters('user', ['userInfo'])
  },

  methods: {
    interceptorsRequestRetrieve(params) {
      const newParams = {
        userId: this.userInfo.id,
        workspaceId: this.workspaceId,
        ...params
      }
      return newParams
    },
    handleImportDict(options) {
      this.$refs.addDialog.open(options)
    },

    handleAddDict(options) {
      this.$refs.addDialog.open(options)
    },

    // 查看字典表
    handleViewClick(row) {
      this.viewTableLoading = true
      API.dictionaryViewInfo({ id: row.id })
        .then(({ success, data }) => {
          if (success && data) {
            this.viewTableData = data
          }
        })
        .finally(() => {
          this.viewTableLoading = false
        })
    },

    // 打开删除提示弹窗
    handleDeleteDict(row) {
      const { englishName } = row
      const options = {
        row,
        name: englishName,
        opType: 'Delete',
        title: `删除字典表${englishName}`,
        beforeOperateMsg: '删除后，',
        afterOperateMsg:
          '将不再在列表中呈现，字段不能再关联该字典表，确认删除吗？'
      }
      this.$refs.message.open(options)
    },

    // 确认提示弹窗确认回调
    messageOperateCallback(opType, { id }) {
      // 字典表删除
      this[`handle${opType}Click`]('dictionaryDelete', { id }, 'message')
    },

    // 新增或编辑字典表弹窗确认回调
    dialogOperateCallback(success) {
      if (success) {
        this.$refs.addDialog.handleClose()
        this.mixinRetrieveTableData()
      }
    }
  }
}
</script>
