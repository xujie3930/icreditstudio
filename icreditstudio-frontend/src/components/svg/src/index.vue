<template>
  <svg class="j-svg" aria-hidden="true" @click="handleClick">
    <use :xlink:href="iconName"></use>
  </svg>
</template>
<script>
export default {
  name: 'JSvg',
  props: {
    name: { type: String, default: '' }
  },
  computed: {
    iconName() {
      return `#${this.name}`
    }
  },
  methods: {
    handleClick() {
      this.$emit('on-click')
    }
  }
}
</script>
<style lang="scss" scoped>
.j-svg {
  width: 1.4em;
  height: 1.4em;
  fill: currentColor;
  overflow: hidden;
  line-height: 1em;
  display: inline-block;
}
</style>
