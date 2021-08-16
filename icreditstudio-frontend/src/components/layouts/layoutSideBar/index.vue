<template>
  <el-aside
    class="iframe-layout-aside"
    :class="{ 'is-collapse': isCollapse }"
    width="220px"
  >
    <slot></slot>
    <el-menu
      :background-color="variables['menu-color-bg']"
      :text-color="variables['menu-color-text']"
      :active-text-color="variables['menu-color-text-active']"
      :default-active="activeMenu"
      :collapse="isCollapse"
      :collapse-transition="true"
      :default-openeds="defaultOpens"
      mode="vertical"
    >
      <template v-for="route in routes.filter(e => e.isShow)">
        <side-bar-item
          :key="route.url"
          :full-path="route.url"
          :item="route"
        />
      </template>
    </el-menu>
  </el-aside>
</template>
<script>
import SideBarItem from './components/SideBarItem'
import variables from '@/styles/common/_variables.scss'
import { mapGetters } from 'vuex'

export default {
  name: 'LayoutSideBar',
  components: {
    SideBarItem
  },
  props: {
    routes: {
      type: Array,
      default() {
        return []
      }
    }
  },
  data() {
    return {
    }
  },
  computed: {
    ...mapGetters({
      isCollapse: 'common/isCollapse'
    }),
    defaultOpens() {
      return []
    },
    activeMenu() {
      const route = this.$route
      const { meta, path } = route
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    },
    variables() {
      return variables
    }
  }
}
</script>
