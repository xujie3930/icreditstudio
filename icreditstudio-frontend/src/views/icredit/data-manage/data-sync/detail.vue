<!--
 * @Author: lizheng
 * @Description: 查看任务详情
 * @Date: 2021-08-24
-->
<template>
  <BaseDialog hideFooter title="查看同步任务" ref="baseDialog">
    <el-tabs
      class="data-detail-tab"
      v-model="activeName"
      @tab-click="handleClick"
    >
      <el-tab-pane label="任务详情" name="task">
        <div class="tab-wrap">
          <div class="tab-wrap__title">任务详情</div>
          <div class="tab-wrap__content">
            <div
              class="content-item"
              :key="item.label"
              v-for="item in taskDetailInfo"
            >
              <div class="label">
                <span class="required-icon">*</span>
                <span>{{ item.label }}</span>
              </div>
              <span class="text">{{ item.content }}</span>
            </div>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="数据源详情" name="second">
        <div class="tab-wrap">
          <div class="tab-wrap__title">数据源详情</div>
          <div class="tab-wrap__content">
            <el-row>
              <el-col :span="8">
                <span>数据库源：</span>
                <span>表间关联关系</span>
              </el-col>

              <el-col :span="16">
                <span>表间关联关系：</span>
                <span class="icon icon-solid"></span>
                <span class="icon"></span>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="8">
                <span> 宽表信息：</span>
                <span>表间关联关系</span>
              </el-col>
              <el-col :span="16">
                <span> 分区字段：</span>
                <span>表间关联关系</span>
              </el-col>
            </el-row>
            <j-table
              ref="leftTable"
              v-loading="tableLoading"
              :table-configuration="tableConfiguration"
              :table-data="tableData"
            ></j-table>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="任务调度详情" name="third">
        <div class="tab-wrap">
          <div class="tab-wrap__title">通道控制</div>
          <div class="tab-wrap__content">
            <div class="content-item">
              <div class="label">
                <span class="required-icon">*</span>
                <span>任务期望最大并发数</span>
              </div>
              <span class="text">222</span>
            </div>
            <div class="content-item">
              <div class="label">
                <span class="required-icon">*</span>
                <span>同步速率</span>
              </div>
              <el-radio-group v-model="radio">
                <el-radio :label="3">备选项</el-radio>
                <el-radio :label="6">备选项</el-radio>
                <el-radio :label="9">备选项</el-radio>
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
              <span class="text">周期执行</span>
            </div>
            <div class="content-item">
              <div class="label">
                <span class="required-icon">*</span>
                <span>同步任务周期</span>
              </div>
              <span class="text">5天02小时07分19秒</span>
            </div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </BaseDialog>
</template>

<script>
import BaseDialog from '@/views/icredit/components/dialog'
import tableConfiguration from '@/views/icredit/configuration/table/data-manage-detail'

export default {
  components: { BaseDialog },
  data() {
    return {
      tableConfiguration,
      radio: 3,
      tableLoading: false,
      tableData: [],
      activeName: 'task',
      taskDetailInfo: [
        { label: '任务名', content: '222' },
        { label: '任务启用', content: '2' },
        { label: '创建方式', content: 'dwd' },
        { label: '任务描述', content: 'dddsw' }
      ]
    }
  },

  methods: {
    open() {
      this.$refs.baseDialog.open()
    },

    handleClick(tab, event) {
      console.log(tab, event)
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

      .icon {
        display: inline-block;
        width: 30px;
        height: 30px;
        border-radius: 50%;
        border: 1px solid #1890ff;
      }

      .icon-solid {
        background-color: #1890ff;
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
