<!--
 * @Description: 查看
 * @Date: 2021-09-26
-->
<template>
  <div class="rule-dialog">
    <BaseDialog ref="baseDialog" width="480px" hideFooter :title="title">
      <div class="rules-detail-wrap">
        <div
          class="rules-details-item"
          v-for="item in formConfig"
          :key="item.key"
        >
          <div class="label-wrap">
            <el-tooltip
              v-if="item.tooltip"
              effect="dark"
              placement="right-start"
            >
              <div slot="content">
                如果选择强规则，则质量校验规则的结果为异常时报警并阻塞下游任务节点；
                <br /><br />
                如果选择的是弱规则，则质量规则校验的结果为异常时报警但并不阻塞下游任务节点。
              </div>
              <j-svg class="j-svg" name="icon-close"></j-svg>
            </el-tooltip>
            <div
              class="label"
              :style="{ marginLeft: item.tooltip ? '0' : '12px' }"
            >
              {{ item.label }}
            </div>
          </div>
          <div class="value">{{ item.value }}</div>
        </div>
      </div>
    </BaseDialog>
  </div>
</template>

<script>
import BaseDialog from '@/views/icredit/components/dialog'
import API from '@/api/icredit'

export default {
  components: { BaseDialog },

  data() {
    return {
      title: '历史执行情况',
      titleName: '',
      logDetail: 'sdddsds',
      detailLoading: false,
      formConfig: [
        { label: '规则名称', value: '空值名称', key: 'name' },
        { label: '规则强度', value: '弱', key: 'Strength', tooltip: true },
        { label: '规则对象', value: '字段', key: 'object' },
        { label: '规则类型', value: '系统规则', key: 'type' },
        { label: '规则描述', value: '对表中的字段进行空值检查', key: 'des' }
      ]
    }
  },

  methods: {
    open(row) {
      this.titleName = row.taskName
      this.title = row.taskName
      // this.getHistoryLogData(row.taskId)
      this.$refs.baseDialog.open()
    },

    handleViewLogDetail(row) {
      console.log('detail', row)
      this.getLogDetailData(row.taskInstanceId)
      this.$refs.detailLogDialog.open()
    },

    // 历史日志列表数据
    getHistoryLogData(taskId) {
      this.tableLoading = true
      API.dataScheduleSyncHistoryLog({ taskId })
        .then(({ success, data }) => {
          if (success && data) {
            this.tableData = data
          }
        })
        .finally(() => {
          this.tableLoading = false
        })
    },

    // 某条历史日志详情数据
    getLogDetailData(taskInstanceId) {
      this.detailLoading = true
      API.dataScheduleSyncLogDetail({ taskInstanceId })
        .then(({ success, data }) => {
          if (success && data) {
            // this.logDetail = data
          }
        })
        .finally(() => {
          this.detailLoading = false
        })
    }
  }
}
</script>

<style lang="scss" scoped>
.rule-dialog {
  .rules-detail-wrap {
    margin-bottom: 15px;

    .rules-details-item {
      @include flex(row, flex-start);
      margin-left: 15px;
      margin-bottom: 15px;

      .label-wrap {
        @include flex;

        .label {
          font-size: 14px;
          font-family: PingFangSC, PingFangSC-Medium;
          font-weight: 500;
          color: rgba(0, 0, 0, 0.85);
        }

        .j-svg {
          width: 13px;
          height: 13spx;
          cursor: pointer;
        }
      }

      .value {
        margin-left: 15px;
        font-size: 14px;
        font-family: PingFangSC, PingFangSC-Regular;
        font-weight: 400;
        text-align: left;
        color: #262626;
      }
    }
  }
}
</style>
