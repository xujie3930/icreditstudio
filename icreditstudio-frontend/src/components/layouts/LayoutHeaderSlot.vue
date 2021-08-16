<template>
  <!-- 模块 -->
  <el-tabs
    class="modal-block label"
    v-model="activeModule"
    @tab-click="handleClick"
  >
    <el-tab-pane
      class="label"
      v-for="item in modules.filter(e => e.id !== 'defaultModule')"
      :key="item.id"
      :label="item.label"
      :name="item.id"
    ></el-tab-pane>
  </el-tabs>
</template>
<script>
export default {
  name: 'LayoutHeaderSlot',
  props: {
    modules: {
      type: Array,
      default() {
        return []
      }
    },
    activeModuleId: {
      type: String
    }
  },
  data() {
    return {
      activeModule: ''
    }
  },
  created() {
    this.activeModule = this.activeModuleId
  },
  methods: {
    handleClick(tab) {
      const curModule = this.modules.find(item => item.id === tab.name)
      if (curModule) {
        const { path, redirectPath } = curModule
        if (path || redirectPath) {
          this.$router.push({ path: redirectPath || path })
        } else {
          this.$emit('handleModuleClick', tab)
        }
      } else {
        this.$emit('handleModuleClick', tab)
      }
    }
  }
}
</script>
