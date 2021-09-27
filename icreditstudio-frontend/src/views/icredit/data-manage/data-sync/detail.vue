<!--
 * @Author: lizheng
 * @Description: 查看任务详情
 * @Date: 2021-08-24
-->
<template>
  <BaseDialog
    hideFooter
    title="查看同步任务"
    ref="taskDialog"
    width="65vw"
    @on-close="close"
  >
    <el-tabs
      class="data-detail-tab"
      v-model="activeName"
      v-loading="detailLoading"
      @tab-click="handleTabClick"
    >
      <el-tab-pane label="任务详情" name="DefineDetial">
        <div class="tab-wrap">
          <div class="tab-wrap__title">任务详情</div>
          <div class="tab-wrap__content">
            <div
              class="content-item"
              :key="item.label"
              v-for="item in taskDetailInfo"
            >
              <div class="label">
                <span v-if="item.key !== 'taskDescribe'" class="required-icon"
                  >*
                </span>
                <span>{{ item.label }}</span>
              </div>
              <span class="text">{{ item.value }}</span>
            </div>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="数据源详情" name="BuildDetial">
        <div class="tab-wrap">
          <!-- <div class="tab-wrap__title">数据源详情</div> -->
          <div class="tab-wrap__content">
            <el-row class="row">
              <el-col class="col" :span="10">
                <span>数据库源：</span>
                <span>{{ datasourceName }}</span>
              </el-col>

              <el-col class="col" :span="14">
                <div>表间关联关系：</div>
                <div v-if="datasourceDetailInfo.view.length" class="pop-wrap">
                  <el-popover placement="right-end" width="450" trigger="hover">
                    <Figure :data-source="datasourceDetailInfo.view" />
                    <div class="svg-wrap" slot="reference">
                      <JSvg name="left-link" class="icon" />
                    </div>
                  </el-popover>
                </div>
                <span v-else>无</span>
              </el-col>
            </el-row>

            <el-row class="row" style="margin-bottom: 20px">
              <el-col class="col" :span="10">
                <span> 宽表信息：</span>
                <el-tooltip placement="top">
                  <div slot="content">
                    <span>{{ datasourceDetailInfo.targetSource }}</span>
                    &nbsp;&nbsp;
                    <span>{{ datasourceDetailInfo.wideTableName }}</span>
                  </div>
                  <div class="width-table-info">
                    <span>{{ datasourceDetailInfo.targetSource }}</span>
                    &nbsp;&nbsp;
                    <span>{{ datasourceDetailInfo.wideTableName }}</span>
                  </div>
                </el-tooltip>
              </el-col>

              <el-col class="col" :span="4">
                <span> 增量字段：</span>
                <span>
                  {{ datasourceDetailInfo.syncCondition.incrementalField }}
                </span>
              </el-col>

              <el-col class="col" :span="4">
                <span> 日期格式：</span>
                <span>{{
                  dateFieldMapping[datasourceDetailInfo.syncCondition.partition]
                }}</span>
              </el-col>

              <el-col class="col" :span="6">
                <span> 时间过滤条件：</span>
                <span
                  v-if="
                    datasourceDetailInfo.syncCondition.n ||
                      datasourceDetailInfo.syncCondition.n === 0
                  "
                  >T + {{ datasourceDetailInfo.syncCondition.n }}</span
                >
              </el-col>
            </el-row>

            <j-table
              ref="leftTable"
              v-loading="tableLoading"
              :table-configuration="tableConfiguration"
              :table-data="datasourceDetailInfo.fieldInfos"
            ></j-table>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="任务调度详情" name="DispatchDetial">
        <div class="tab-wrap">
          <div class="tab-wrap__title">通道控制</div>
          <div class="tab-wrap__content">
            <div class="content-item">
              <div class="label">
                <span class="required-icon">*</span>
                <span>任务期望最大并发数</span>
              </div>
              <span class="text">{{ buildDetailInfo.maxThread }}</span>
            </div>
            <div class="content-item">
              <div class="label">
                <span class="required-icon">*</span>
                <span>同步速率</span>
              </div>
              <el-radio-group disabled v-model="buildDetailInfo.syncRate">
                <el-radio :label="0">不限流</el-radio>
                <el-radio :label="1">限流</el-radio>
              </el-radio-group>
            </div>
          </div>
        </div>
        <div class="tab-wrap">
          <div class="tab-wrap__title">参数设置</div>
          <div class="tab-wrap__content">
            <div class="content-item">
              <div class="label">
                <span class="required-icon">*</span>
                <span>调度类型</span>
              </div>
              <span class="text">
                {{ scheduleTypeMapping[buildDetailInfo.scheduleType] }}
              </span>
            </div>
            <div class="content-item" v-if="buildDetailInfo.scheduleType">
              <div class="label">
                <span class="required-icon">*</span>
                <span>同步任务周期</span>
              </div>
              <span class="text">{{ buildDetailInfo.cron }}</span>
            </div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </BaseDialog>
</template>

<script>
import BaseDialog from '@/views/icredit/components/dialog'
import Figure from '@/views/icredit/components/figure'
import tableConfiguration from '@/views/icredit/configuration/table/data-manage-detail'
import API from '@/api/icredit'
import { deepClone } from '@/utils/util'
import {
  taskStatusMapping,
  createModeMapping,
  scheduleTypeMapping,
  taskDetailInfo,
  radioBtnOption,
  dateFieldMapping
} from './contant'

export default {
  components: { BaseDialog, Figure },
  data() {
    this.scheduleTypeMapping = scheduleTypeMapping
    this.dateFieldMapping = dateFieldMapping

    return {
      row: {},
      detailLoading: false,
      activeName: 'DefineDetial',
      radioBtnOption,

      // 表格
      tableConfiguration,
      tableLoading: false,
      tableData: [],

      // 详情
      datasourceDetailInfo: {
        sourceType: undefined,
        view: [],
        syncCondition: {}
      },
      buildDetailInfo: {},
      taskDetailInfo: []
    }
  },

  computed: {
    datasourceName() {
      const { sourceType } = this.datasourceDetailInfo
      return radioBtnOption[sourceType] ? radioBtnOption[sourceType].name : ''
    }
  },

  methods: {
    open({ row }) {
      this.activeName = 'DefineDetial'
      this.row = row
      this.initData()
      this.$refs.taskDialog.open()
      this.getDetailData('dataSyncDefineDetial', { taskId: row.taskId })
    },

    initData() {
      this.datasourceDetailInfo = { view: [], syncCondition: {} }
      this.buildDetailInfo = {}
      this.taskDetailInfo = taskDetailInfo
    },

    close() {
      this.datasourceDetailInfo = {}
      this.buildDetailInfo = {}
    },

    handleTabClick() {
      this.getDetailData(`dataSync${this.activeName}`, {
        taskId: this.row.taskId
      })
    },

    // 接口-详情数据返回值处理
    handleFilterData(data) {
      if (this.activeName === 'DefineDetial') {
        this.taskDetailInfo = deepClone(this.taskDetailInfo).map(
          ({ key, label }) => {
            let value = ''
            if (key === 'createMode') {
              value = createModeMapping[data[key]]
            } else if (key === 'enable') {
              value = taskStatusMapping[data[key]].label
            } else {
              value = data[key]
            }
            return { key, label, value }
          }
        )
      } else if (this.activeName === 'DispatchDetial') {
        this.buildDetailInfo = data
      } else {
        this.datasourceDetailInfo = data
      }
    },

    // 接口-获取详情
    getDetailData(methodName, params) {
      this.detailLoading = true
      API[methodName](params)
        .then(({ success, data }) => {
          if (success && data) {
            console.log(data, 'data')
            this.handleFilterData(data)
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
.data-detail-tab {
  .tab-wrap {
    margin-top: 16px;
    padding-bottom: 26px;
    border-bottom: 1px dashed #d9d9d9;

    &:last-child {
      border: none;
    }

    &__title {
      position: relative;
      font-size: 14px;
      font-family: PingFangSC, PingFangSC-Regular;
      font-weight: 400;
      text-align: left;
      color: #262626;
      margin-left: 10px;
      margin-bottom: 24px;

      &::before {
        position: absolute;
        left: -10px;
        top: 0;
        content: '';
        width: 4px;
        height: 18px;
        opacity: 1;
        background: #1890ff;
        border-radius: 0px 4px 4px 0px;
      }
    }

    &__content {
      @include flex(column, flex-start);

      .content-item {
        @include flex(row, flex-start);
        width: 100%;
        margin-bottom: 15px;

        .label {
          width: 150px;
          text-align: right;
          line-height: 30px;
          margin-right: 37px;

          .required-icon {
            display: inline-block;
            font-size: 16px;
            line-height: 30px;
            margin-right: 5px;
            color: rgb(245, 108, 108);
          }
        }
      }

      .row {
        width: 100%;
        margin: 10px 0;
        height: 26px;
        @include flex;
        .col {
          @include flex(row, flex-start);
          height: 26px;
          line-height: 26px;

          .width-table-info {
            display: inline-block;
            width: 280px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          }
        }

        .pop-wrap {
          @include flex;
          padding-top: 7px;
        }
      }

      .svg-wrap {
        display: inline-flex;
        align-items: center;
        line-height: 20px;
      }

      .icon {
        display: inline-block;
        width: 20px;
        height: 20px;
        cursor: pointer;
      }
    }
  }

  ::v-deep {
    .el-tabs__nav-wrap::after {
      height: 0px;
      background-color: transparent;
      border-bottom: 1px dashed #d9d9d9;
    }

    .el-tabs__nav-scroll {
      @include flex;
    }
  }
}
</style>
