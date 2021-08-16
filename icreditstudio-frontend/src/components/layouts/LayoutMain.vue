<template>
  <el-container class="iframe-layout-container">
    <el-main class="iframe-layout-main">
      <LayoutHeader
        :crumbs-list="breadCrumbItems"
        :modules="topModules"
        :active-module-id="activeModuleId"
      />

      <div class="layout-container">
        <LayoutHeaderSidebar
          v-if="isHeaderCollapse"
          :menu="moduleMenus[activeModuleId]"
          :crumbs-list="breadCrumbItems"
          :modules="topModules"
          :active-module-id="activeModuleId"
        />
        <LayoutMainSidebar v-else :menu="moduleMenus[activeModuleId]" />
        <div class="layout-content">
          <!-- <LayoutMainTabBar /> -->
          <LayoutBreadcrumd />
          <main class="iframe-layout-main-container">
            <LayoutContainerSidebar
              :menu="filterChildrenMenu(moduleMenus[activeModuleId])"
            />
            <keep-alive>
              <router-view v-if="keepAlive" />
            </keep-alive>
            <router-view v-if="!keepAlive" />
          </main>
        </div>
      </div>

      <el-backtop target=".el-main" :bottom="100">
        <i class="el-icon-caret-top" />
      </el-backtop>
      <LayoutMainFooter />
    </el-main>
  </el-container>
</template>

<script>
import LayoutHeader from './LayoutMainHeader'
import LayoutBreadcrumd from './LayoutBreadcrumd'
import LayoutHeaderSidebar from './LayoutHeaderSiderbar'
import LayoutMainSidebar from './LayoutMainSidebar'
import LayoutContainerSidebar from './LayoutContainerSidebar'
// import LayoutMainTabBar from './LayoutMainTabbar'
import LayoutMainFooter from './LayoutMainFooter'
import { mapGetters } from 'vuex'

export default {
  components: {
    LayoutHeader,
    LayoutBreadcrumd,
    LayoutHeaderSidebar,
    LayoutMainSidebar,
    LayoutContainerSidebar,
    // LayoutMainTabBar,
    LayoutMainFooter
  },

  data() {
    return {
      breadCrumbItems: []
    }
  },

  computed: {
    ...mapGetters({
      permissionList: 'user/permissionList',
      topModules: 'permission/topModules',
      moduleMenus: 'permission/moduleMenus',
      activeModuleId: 'permission/activeModuleId',
      isHeaderCollapse: 'common/isHeaderCollapse'
    }),
    keepAlive() {
      return this.$route.meta.keepAlive
    }
  },

  watch: {
    $route: {
      immediate: true,
      handler(to) {
        this.initBreadCrumbItems(to)
      }
    }
  },

  created() {
    this.initBreadCrumbItems(this.$route)
  },

  methods: {
    initBreadCrumbItems(router) {
      const breadCrumbItem = [
        { path: '/', title: window.__JConfig.baseConfig.projectName }
      ]
      router.matched.forEach(item => {
        if (item.meta && item.meta.name) {
          breadCrumbItem.push({
            path: item.path,
            title: item.meta.name
          })
        }
      })
      this.breadCrumbItems = breadCrumbItem
    },

    filterChildrenMenu(menu) {
      const secondMenu = menu.filter(
        item => item.isShow && item.children && item.children.length
      )
      const thirdMenu = secondMenu.map(list => list.children)
      return thirdMenu.flat()
    }
  }
}
</script>

<style lang="scss" scoped>
@mixin center() {
  display: flex;
}

.iframe-layout-container {
  .layout-container {
    width: 100%;
    @include center();
  }

  .layout-content {
    min-width: calc(100% - 220px);
    flex: 1;
  }
}
</style>
