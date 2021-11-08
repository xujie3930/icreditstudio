<template>
  <div class="icredit-cron-select">
    <el-input
      readonly
      style="width:100%"
      placeholder="请选择"
      v-model="expression"
      @focus="handleFocus"
      @blur="handleBlur"
      @change="$emit('change', $event.target.value)"
    >
      <i
        slot="suffix"
        style="cursor:pointer"
        :class="showCorn ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"
        @click.self="handleClose"
      ></i
    ></el-input>
    <div class="cron-wrap" v-if="showCorn">
      <JCron
        footer
        :expression="expression"
        @change="handleChange"
        @reset="handleReset"
        @fill="handleConfirm"
        @hide="handleClose"
      />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showCorn: false,
      expression: ''
    }
  },

  model: {
    prop: 'value',
    event: 'change'
  },

  prop: {
    value: {
      type: String,
      default: undefined
    }
  },

  watch: {
    value(nVal) {
      this.expression = nVal
    }
  },

  methods: {
    handleFocus() {
      this.showCorn = !this.showCron
      this.$emit('open', this.showCorn)
    },

    handleBlur() {
      // this.showCorn = false
      // this.suffixIcon = 'el-icon-arrow-down'
    },

    handleChange(options) {
      console.log(options)
      this.expression = options.expression
      this.$emit('change', this.expression)
    },

    handleConfirm(val) {
      console.log(val, ';mllp')
      this.value = val
      this.expression = val
      this.showCorn = false
      this.$emit('change', val)
    },

    handleClose() {
      this.showCorn = !this.showCorn
      this.$emit('open', this.showCorn)
      // this.suffixIcon = 'el-icon-arrow-down'
    },

    handleReset(expression) {
      this.expression = expression
      this.$emit('reset', expression)
    }
  }
}
</script>

<style lang="scss" scoped>
.icredit-cron-select {
  position: relative;
  width: 100%;
  height: auto;

  .cron-wrap {
    position: relative;
    width: 100%;

    ::v-deep {
      .el-tabs--border-card {
        border-top: none;
      }
    }

    // &::before {
    //   content: '';
    //   position: absolute;
    //   top: 0;
    //   left: 0;
    //   width: 0;
    //   border: 10px solid transparent;
    //   border-bottom: 10px solid #f0f0f0;
    // }
  }
}
</style>
