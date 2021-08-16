<template>
  <crud-basic
    ref="crud"
    title="收件列表"
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
    :handleAdd="mixinHandleAdd"
    :handleImport="mixinHandleImport"
    :handleExport="mixinHandleExport"
    :handleSearch="mixinHandleSearch"
    :handleReset="mixinHandleReset"
    :handleUpdate="mixinHandleCreateOrUpdate"
    :handleCancel="mixinHandleCancel"
    :handleSelectChange="mixinHandleSelectChange"
    @handleReadAll="handleReadAll"
    @handleMultipleDelete="mixinHandleMultipleDelete"
  ></crud-basic>
</template>

<script>
import crud from '@/mixins/crud'
import { deepClone } from '@/utils/util'
import { mapGetters } from 'vuex'
import { userAllReadInfo, queryMessageNoticeInfo } from '@/api/message'
import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-inbox'
import formOption from '@/views/system-basic/configuration/form/manage/manage-inbox'

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
  name: 'ManageMessage',
  mixins: [crud],
  data() {
    return {
      formOption: formOption(this, { INFO_TYPE_ENUMS }),
      mixinSearchFormConfig: {
        models: {
          infoTitle: '',
          receiverName: '',
          infoType: '',
          operateTime: ''
        }
      },
      mixinDialogFormConfig: {
        models: {
          infoTitle: '',
          receiverName: '',
          infoType: '',
          operateTime: '',
          infoContent: ''
        },
        rule: {
          infoTitle: [{ required: true, message: '标题不能为空', trigger: 'blur' }],
          infoType: [{ required: true, message: '消息类型不能为空', trigger: 'blur' }]
        }
      },
      tableConfiguration: tableConfiguration(this, { INFO_TYPE_ENUMS, READ_STATUS_ENUMS }),
      fetchConfig: {
        retrieve: {
          url: '/information/information/inBoxPage',
          method: 'post'
        },
        delete: {
          url: '/information/information/deleteUserInfo',
          method: 'post'
        },
        multipleDelete: {
          url: '/information/information/deleteUserInfo',
          method: 'post'
        }
      }
    }
  },
  computed: {
    ...mapGetters({
      userInfo: 'user/userInfo'
    })
  },
  created() {
    this.mixinSearchFormItems = deepClone(this.formOption)
      .filter(e => e.isSearch)
    this.mixinRetrieveTableData()
  },
  methods: {
    interceptorsRequestRetrieve(params) {
      const { operateTime, ...restParams } = params
      const newParams = {
        startTime: operateTime && operateTime.length ? operateTime[0] : '',
        endTime: operateTime && operateTime.length ? operateTime[1] + 24 * 60 * 60 * 1000 - 1 : '',
        ...restParams
      }
      return newParams
    },
    interceptorsRequestCreate(params) {
      return {
        ...params,
        senderId: this.userInfo.id || ''
      }
    },
    interceptorsResponseTableData(data) {
      return data.map(e => {
        return {
          ...e,
          sendTime: parseInt(e.sendTime)
        }
      })
    },
    // 消息全部已读
    handleReadAll() {
      userAllReadInfo({
        readStatus: 'ALL',
        infoType: 'ALL'
      })
        .then(res => {
          if (res.success) {
            this.$notify.success('全部消息已读')
            this.mixinRetrieveTableData()
          }
        })
    },
    // 查看详情
    async handleView({ row }) {
      const { infoType } = this
      await queryMessageNoticeInfo({ id: row.id })
        .then(res => {
          if (res.success) {
            this.mixinHandleView({ row: { ...res.data, sendTime: row.sendTime } }, INFO_TYPE_ENUMS[infoType])
          }
        })
    }
  }
}
</script>
