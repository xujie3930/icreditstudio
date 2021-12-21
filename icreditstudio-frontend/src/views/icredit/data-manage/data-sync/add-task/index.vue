<template>
  <transition name="add-page">
    <keep-alive>
      <component
        :is="currentComponent"
        :options="options"
        :form="form"
        @change="change"
      />
    </keep-alive>
  </transition>
</template>

<script>
import AddFirst from './add-first'
import AddSecond from './add-second'
import AddThird from './add-third'

export default {
  components: { AddFirst, AddSecond, AddThird },

  data() {
    return {
      currentStep: 1,
      form: {},
      options: {}
    }
  },

  computed: {
    currentComponent() {
      const componentMapping = {
        1: AddFirst,
        2: AddSecond,
        3: AddThird
      }
      return componentMapping[this.currentStep]
    }
  },

  methods: {
    change(step, form, options) {
      step && (this.currentStep = step)
      form && (this.form = form)
      options && (this.options = options)
    }
  }
}
</script>

<style lang="scss" scoped>
.add-page-enter-active {
  opacity: 1;
  transform: translateX(0);
  transition: all 0.5s ease;
}

.add-page-enter,
.add-page-leave-active {
  opacity: 0;
  transform: translateX(10px);
}
</style>
