<!--
 * @Author: lizheng
 * @Description: 新增或编辑工作空间
 * @Date: 2021-08-17
-->
<template>
  <div class="workspace-setting">
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="125px"
      class="demo-ruleForm"
    >
      <el-form-item class="info-title">
        <div class="text" slot="label">基础信息</div>
      </el-form-item>

      <el-row>
        <el-col :span="4">
          <el-form-item label="工作空间名称" prop="region">
            <el-input
              type="text"
              placeholder="请输入工作空间名称"
              size="small"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="状态" prop="delivery">
            <el-switch v-model="ruleForm.delivery"></el-switch>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="20">
          <el-form-item label="工作空间描述" prop="desc">
            <el-input
              :rows="4"
              type="textarea"
              v-model="ruleForm.desc"
              placeholder="请输入工作空间描述"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <div class="form-divider" />

      <el-form-item class="info-title">
        <div class="text" slot="label">用户角色信息</div>
      </el-form-item>

      <el-row>
        <el-col :span="4">
          <el-form-item label="负责人" prop="region">
            <el-select
              filterable
              clearable
              size="small"
              placeholder="请选择"
              v-model="ruleForm.user"
            >
              <el-option
                v-for="item in userOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="20">
          <el-form-item label="成员信息" prop="desc">
            <j-table
              ref="table"
              v-loading="tableLoading"
              :table-configuration="tableConfiguration"
              :table-pagination="tablePagination"
              :table-data="tableData"
              @handleSizeChange="handleSizeChange"
              @handleCurrentChange="handleCurrentChange"
              @selection-change="_handleCurrentChange"
            ></j-table>
          </el-form-item>
        </el-col>
      </el-row>

      <div class="form-divider" />
    </el-form>

    <div v-if="isAddOperate" class="footer-btn">
      <el-button :loading="btnLoading" class="btn" type="primary">
        确定
      </el-button>
    </div>
  </div>
</template>

<script>
import tableConfiguration from '@/views/icredit/configuration/table/workspace-setting-detail'
import crud from '@/mixins/crud'

export default {
  mixins: [crud],
  data() {
    return {
      tableConfiguration,
      isAddOperate: true,
      btnLoading: false,
      ruleForm: {
        user: '',
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      userOptions: [
        { value: 'admin', label: 'admin' },
        { value: 'zhangsan', label: '张三' },
        { value: 'mon', label: 'monkeyCode' },
        { value: 'lisi', label: '里斯' }
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
.workspace-setting {
  position: relative;
  background: #fff;
  width: 100%;
  height: calc(100vh - 126px);
  overflow: hidden;

  .info-title {
    position: relative;
    margin-top: 24px;
    font-size: 18px;
    font-family: PingFangSC, PingFangSC-Medium;
    font-weight: 600;
    text-align: left;
    color: #262626;
    line-height: 25px;

    .text {
      text-align: left;
      margin-left: 16px;
    }

    &::before {
      content: '';
      position: absolute;
      left: 0;
      bottom: 12px;
      width: 4px;
      height: 18px;
      opacity: 1;
      background: #1890ff;
      border-radius: 0px 2px 2px 0px;
    }
  }

  .form-divider {
    margin: 0 16px;
    border-bottom: 1px dashed #d9d9d9;
  }

  .footer-btn {
    position: absolute;
    bottom: 20px;
    left: 45%;
    text-align: center;
    margin-top: 20px;

    .btn {
      width: 150px;
      height: 40px;
      background: #1890ff;
      border-radius: 4px;
    }
  }
}
</style>
