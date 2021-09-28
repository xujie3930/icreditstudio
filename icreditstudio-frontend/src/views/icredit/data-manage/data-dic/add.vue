<!--
 * @Author: lizheng
 * @Description:  查看
 * @Date: 2021-09-28
-->
<template>
  <BaseDialog
    ref="baseDialog"
    width="800px"
    :title="`${opType === 'add' ? '新增' : '编辑'}字典表`"
    @onClose="handleClose"
    @onConfirm="handleConfirm"
  >
    <el-form
      :model="dictForm"
      :rules="dictRules"
      ref="dictForm"
      label-width="150px"
    >
      <el-form-item label="字典表英文名称" prop="name">
        <el-input v-model="dictForm.name"></el-input>
      </el-form-item>
      <el-form-item label=" 字典表中文名称" prop="name">
        <el-input v-model="dictForm.name"></el-input>
      </el-form-item>
      <el-form-item label="字典表描述" prop="name">
        <el-input type="textarea" v-model="dictForm.name"></el-input>
      </el-form-item>
      <el-form-item label="字典表内容" prop="table">
        <el-table border :data="tableData" style="width: 100%">
          <el-table-column prop="date" label="日期" width="180">
          </el-table-column>
          <el-table-column prop="name" label="姓名" width="180">
          </el-table-column>
          <el-table-column prop="address" label="地址"> </el-table-column>
          <el-table-column prop="operate" label="操作">
            <el-button
              size="mini"
              plain
              type="primary"
              icon="el-icon-plus"
              circle
            ></el-button>
            <el-button
              size="mini"
              plain
              type="error"
              icon="el-icon-minus"
              circle
            ></el-button>
          </el-table-column>
        </el-table>
      </el-form-item>
    </el-form>
  </BaseDialog>
</template>

<script>
import BaseDialog from '@/views/icredit/components/dialog'

export default {
  components: { BaseDialog },

  data() {
    return {
      opType: '',
      dictForm: {},
      dictRules: {
        name: [{ required: true, message: '必填项不能为空', trigger: 'blur' }]
      },
      tableData: [
        {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        },
        {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        },
        {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄'
        },
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄'
        }
      ]
    }
  },

  methods: {
    open(options) {
      const { row, opType = 'add' } = options
      this.opType = opType
      console.log('deddeded', row, options)

      this.$nextTick(() => this.$refs.baseDialog.open())
    },

    handleClose() {
      this.$refs.baseDialog.close()
    },

    handleConfirm() {
      this.handleClose()
      this.$emit('on-confirm')
    }
  }
}
</script>

<style></style>
