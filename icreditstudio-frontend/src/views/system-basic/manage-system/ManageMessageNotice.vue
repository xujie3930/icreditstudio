<template>
  <div class="w100 h100 noticeContainer">
    <div class="iframe-layout-basic-main-top">
      <span>{{ pageTitle }}</span>
    </div>
    <el-tabs
      v-model="activeReadStatus"
      @tab-click="handleClick"
      style="padding: 0 16px"
    >
      <el-tab-pane name="ALL">
        <span slot="label"> 全部消息({{ message.allCount }})</span>
      </el-tab-pane>
      <el-tab-pane name="N">
        <span slot="label"> 未读消息({{ message.unReadCount }})</span>
      </el-tab-pane>
      <el-tab-pane name="Y">
        <span slot="label"> 已读消息({{ message.readCount }})</span>
      </el-tab-pane>
    </el-tabs>
    <crud-basic
      ref="crud"
      title="消息列表"
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
      :dialog-title="mixinDialogTitle"
      :dialog-type="mixinDialogType"
      :dialog-visible.sync="mixinDialog"
      :handleSizeChange="mixinHandleSizeChange"
      :handleCurrentChange="mixinHandleCurrentChange"
      :handleAdd="mixinHandleAdd"
      :handleImport="mixinHandleImport"
      :handleExport="mixinHandleExport"
      :handleSearch="mixinHandleSearch"
      :handleReset="mixinHandleReset"
      :handleUpdate="mixinHandleCreateOrUpdate"
      :handleCancel="mixinHandleCancel"
      @handleReadAll="handleReadAll"
      @handleDeleteAll="handleDeleteAll"
    ></crud-basic>
  </div>
</template>

<script>
import crud from '@/mixins/crud'
import { deepClone } from '@/utils/util'
import {
  infoCount,
  deleteAllUserInfos,
  userAllReadInfo,
  queryMessageNoticeInfo,
  pollingUnreadInfos
} from '@/api/message'
import { mapActions, mapGetters } from 'vuex'
import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-message-notice'
import formOption from '@/views/system-basic/configuration/form/manage/manage-message-notice'

const READ_STATUS_MAP = {
  ALL: 'allCount',
  Y: 'readCount',
  N: 'unReadCount'
}
const INFO_TYPE_ENUMS = {
  W: '预警消息',
  N: '通知',
  S: '系统消息'
}
const READ_STATUS_ENUMS = {
  Y: '已读',
  N: '未读'
}
export default {
  name: 'ManageMessageNotice',
  mixins: [crud],
  data() {
    return {
      formOption: formOption(this, { INFO_TYPE_ENUMS }),
      activeReadStatus: 'ALL',
      backupTableConfiguration: null,
      message: {
        allCount: 0,
        readCount: 0,
        unReadCount: 0
      },
      mixinSearchFormConfig: {
        models: {
          title: ''
        },
        retrieveModels: {
          infoType: '',
          readStatus: ''
        }
      },
      mixinDialogFormConfig: {
        models: {
          orgName: '',
          orgCode: '',
          orgAddress: '',
          sortNumber: '',
          linkManName: '',
          linkManTel: '',
          parentId: [],
          orgRemark: ''
        },
        rule: {}
      },
      tableConfiguration: tableConfiguration(this, { READ_STATUS_ENUMS }),
      fetchConfig: {
        retrieve: {
          url: '/system/information/information/infoNoticePage',
          method: 'post'
        },
        delete: {
          url: '/system/information/information/deleteUserInfo',
          method: 'post',
          id: 'id'
        }
      }
    }
  },
  watch: {
    $route(oldVal, newVal) {
      if (/\/manage\/messageNotice/.test(newVal.path)) {
        this.initPage()
      }
    }
  },
  computed: {
    ...mapGetters({
      messageNoticeInfo: 'user/messageNoticeInfo'
    }),
    infoType() {
      return this.$route.query.infoType || ''
    },
    pageTitle() {
      return INFO_TYPE_ENUMS[this.infoType]
    }
  },
  created() {
    this.backupTableConfiguration = deepClone(this.tableConfiguration)
    this.initPage()
  },
  methods: {
    ...mapActions('user', ['setMessageNoticeInfo']),
    initPage() {
      this.mixinSearchFormItems = deepClone(this.formOption).filter(
        e => e.isSearch
      )
      // this.activeReadStatus = 'ALL'
      this.qryInfoCount()
      this.handleRetrieveTableData()
    },
    qryInfoCount() {
      const { infoType } = this
      if (infoType) {
        // 获取初始的全部消息、未读消息、已读消息数量
        infoCount({ infoType }).then(res => {
          if (res.success) {
            Object.keys(this.message).forEach(key => {
              this.message[key] = res.data[key] || 0
            })
          }
        })
      }
    },
    interceptorsResponseTableData(data, res) {
      // 根据列表返回更新消息条数
      this.message[READ_STATUS_MAP[this.activeReadStatus]] = res.data.total || 0
      return data
    },
    handleClick({ name }) {
      this.mixinHandleReset(false)
      if (name === 'Y') {
        // 已读消息tab中，屏蔽<全部标记为已读>按钮
        this.tableConfiguration.customBtnConfig = this.backupTableConfiguration.customBtnConfig.filter(
          e => e.key !== 'readAll'
        )
      } else {
        this.tableConfiguration.customBtnConfig = this.backupTableConfiguration.customBtnConfig
      }
      this.handleRetrieveTableData()
    },
    handleRetrieveTableData() {
      const { infoType } = this
      if (infoType) {
        this.mixinSearchFormConfig.retrieveModels.infoType = infoType // 消息类型
        this.mixinSearchFormConfig.retrieveModels.readStatus = this.activeReadStatus // 阅读状态
        this.mixinRetrieveTableData()
      }
    },
    // 消息全部已读
    handleReadAll() {
      userAllReadInfo({
        readStatus: this.activeReadStatus,
        infoType: this.infoType
      }).then(res => {
        if (res.success) {
          this.$notify.success('全部消息已读')
          this.updatePages()
        }
      })
    },
    // 全部删除
    handleDeleteAll() {
      this.$confirm('是否确定删除全部数据?', '询问', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteAllUserInfos({
          readStatus: this.activeReadStatus,
          infoType: this.infoType
        }).then(res => {
          if (res.success) {
            this.$notify.success('删除成功')
            this.updatePages()
          }
        })
      })
    },
    // 查看详情
    async handleView({ row }) {
      const { infoType } = this
      if (row.readStatus === 'Y') {
        // 已读消息查看详情直接展示行数据
        this.mixinHandleView({ row }, INFO_TYPE_ENUMS[infoType])
      } else {
        // 全部和未读查看详情需实时查询最新状态
        await queryMessageNoticeInfo({ id: row.id }).then(res => {
          if (res.success) {
            this.mixinHandleView({ row: res.data }, INFO_TYPE_ENUMS[infoType])
            this.updatePages()
          }
        })
      }
    },
    updatePages() {
      this.handleRetrieveTableData()
      this.qryInfoCount()
      this.updateUnreadInfos()
    },
    updateUnreadInfos() {
      pollingUnreadInfos().then(res => {
        if (res.success) {
          this.setMessageNoticeInfo(res.data)
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.noticeContainer {
  background: #fff;
}
.iframe-layout-basic-main-top {
  padding: 5px 16px 0;

  span {
    color: $--color-text-primary;
    font-size: 16px;
    font-weight: bolder;
    letter-spacing: 2px;
  }
}
</style>
