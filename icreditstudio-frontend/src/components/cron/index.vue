<template>
  <el-dialog title="Cron表达式" :visible.sync="dialogVisible">
    <div class="cron" :val="cronValue">
      <el-tabs v-model="activeName">
        <el-tab-pane label="秒" name="s">
          <second-minute v-model="sVal" lable="秒"></second-minute>
        </el-tab-pane>
        <el-tab-pane label="分" name="m">
          <second-minute v-model="mVal" lable="分"></second-minute>
        </el-tab-pane>
        <el-tab-pane label="时" name="h">
          <hour v-model="hVal" lable="时"></hour>
        </el-tab-pane>
        <el-tab-pane label="日" name="d">
          <day v-model="dVal" lable="日"></day>
        </el-tab-pane>
        <el-tab-pane label="月" name="month">
          <month v-model="monthVal" lable="月"></month>
        </el-tab-pane>
        <el-tab-pane label="周" name="week">
          <week v-model="weekVal" lable="周"></week>
        </el-tab-pane>
        <el-tab-pane label="年" name="year">
          <year v-model="yearVal" lable="年"></year>
        </el-tab-pane>
      </el-tabs>
      <!-- table -->
      <el-table :data="tableData" size="mini" border style="width: 100%;">
        <el-table-column prop="sVal" label="秒" width="70"> </el-table-column>
        <el-table-column prop="mVal" label="分" width="70"> </el-table-column>
        <el-table-column prop="hVal" label="时" width="70"> </el-table-column>
        <el-table-column prop="dVal" label="日" width="70"> </el-table-column>
        <el-table-column prop="monthVal" label="月" width="70">
        </el-table-column>
        <el-table-column prop="weekVal" label="周" width="70">
        </el-table-column>
        <el-table-column prop="yearVal" label="年"> </el-table-column>
      </el-table>
    </div>

    <footer slot="footer">
      <el-button size="mini" @click="close">取 消</el-button>
      <el-button size="mini" type="primary" @click="confirm">
        确 定
      </el-button>
    </footer>
  </el-dialog>
</template>

<script>
import SecondMinute from './SecondMinute'
import Hour from './Hour'
import Day from './Day'
import Month from './Month'
import Week from './Week'
import Year from './Year'

export default {
  components: {
    SecondMinute,
    Hour,
    Day,
    Month,
    Week,
    Year
  },

  data() {
    return {
      dialogVisible: false,
      activeName: 's',
      sVal: '',
      mVal: '',
      hVal: '',
      dVal: '',
      monthVal: '',
      weekVal: '',
      yearVal: ''
    }
  },

  props: {
    value: {
      type: String
    }
  },

  watch: {
    value() {
      this.updateVal()
    }
  },

  computed: {
    tableData() {
      return [
        {
          sVal: this.sVal,
          mVal: this.mVal,
          hVal: this.hVal,
          dVal: this.dVal,
          monthVal: this.monthVal,
          weekVal: this.weekVal,
          yearVal: this.yearVal
        }
      ]
    },

    cronValue() {
      if (!this.dVal && !this.weekVal) {
        return ''
      }
      if (this.dVal === '?' && this.weekVal === '?') {
        this.$message.error('日期与星期不可以同时为“不指定”')
      }
      if (this.dVal !== '?' && this.weekVal !== '?') {
        this.$message.error('日期与星期必须有一个为“不指定”')
      }
      const v = `${this.sVal} ${this.mVal} ${this.hVal} ${this.dVal} ${this.monthVal} ${this.weekVal} ${this.yearVal}`
      if (v !== this.value) {
        this.$emit('input', v)
      }
      return v
    }
  },

  created() {
    this.updateVal()
  },

  methods: {
    updateVal() {
      const arr = this.value
        ? this.value.split(' ')
        : ['*', '*', '*', '?', '*', '*', '*']
      const field = [
        'sVal',
        'mVal',
        'hVal',
        'dVal',
        'monthVal',
        'weekVal',
        'yearVal'
      ]
      field.forEach((item, idx) => {
        this[item] = arr[idx]
      })
    },

    open() {
      this.dialogVisible = true
    },

    close() {
      this.dialogVisible = false
      this.$emit('on-close')
    },

    confirm() {
      this.close()
      this.$emit('on-confirm', this.cronValue)
    }
  }
}
</script>

<style lang="css">
.cron {
  text-align: left;
  padding: 10px;
  background: #fff;
  border: 1px solid #dcdfe6;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.12), 0 0 6px 0 rgba(0, 0, 0, 0.04);
}
</style>
