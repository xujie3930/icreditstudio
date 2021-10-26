<!--
 * @Author: lizheng
 * @Description:  数字动画
 * @Date: 2021-10-26
-->
<template>
  <div :id="id"></div>
</template>

<script>
import { CountUp } from 'countup.js'

export default {
  data() {
    return {
      instance: null
    }
  },

  props: {
    id: { type: String },

    count: {
      type: Number,
      default: 0,
      require: true
    },

    animationOptions: {
      type: Object,
      require: false
      // 相关参数如下
      // startVal?: number; // number to start at (0)
      // decimalPlaces?: number; // number of decimal places (0)
      // duration?: number; // animation duration in seconds (2)
      // useGrouping?: boolean; // example: 1,000 vs 1000 (true)
      // useEasing?: boolean; // ease animation (true)
      // smartEasingThreshold?: number;
      // smartEasingAmount?: number; // amount to be eased for numbers above threshold (333)
      // separator?: string; // grouping separator (',')
      // decimal?: string; // decimal ('.')
      // // easingFn: easing function for animation (easeOutExpo)
      // easingFn?: (t: number, b: number, c: number, d: number) => number;
      // formattingFn?: (n: number) => string; // this function formats result
      // prefix?: string; // text prepended to result
      // suffix?: string; // text appended to result
      // numerals?: string[]; // numeral glyph substitution
    }
  },

  mounted() {
    this.start()
  },

  beforeDestroy() {
    this.instance && (this.instance = null)
  },

  methods: {
    start() {
      this.instance = new CountUp(this.id, this.count, this.animationOptions)
      this.instance && this.instance.start()
    },

    update() {
      this.instance && this.instance.update(this.count)
    },

    reset() {
      this.instance && this.instance.reset()
    },

    pauseResume() {
      this.instance && this.instance.pauseResume()
    }
  }
}
</script>
