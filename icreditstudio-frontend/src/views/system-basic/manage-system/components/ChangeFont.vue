<!--
 * @Author: lizheng
 * @Description: 切换字体大小
 * @Date: 2021-06-23
-->

<template>
  <div class="font">
    <el-slider
      v-model="value"
      :marks="marks"
      :max="20"
      :min="12"
      :format-tooltip="value => `${value}px`"
      @input="changeFontSize"
    />
  </div>
</template>

<script>
export default {
  props: {
    fontSize: {
      type: [String, Number],
      default: '14'
    }
  },
  watch: {
    fontSize() {
      this.value = parseFloat(this.fontSize)
    }
  },
  data() {
    return {
      fontChalk: '',
      value: 14,
      marks: {
        12: {
          style: { fontSize: '12px' },
          label: this.$createElement('span', '小')
        },
        14: {
          style: { fontSize: '16px' },
          label: this.$createElement('span', '中')
        },
        16: '',
        18: '',
        20: {
          style: { fontSize: '20px' },
          label: this.$createElement('span', '大')
        }
      }
    }
  },
  methods: {
    changeFontSize() {
      this.$emit('change-font', this.value)
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/common/_mixin.scss';

.font {
  margin-left: 10px;
  width: 60%;
}
</style>
