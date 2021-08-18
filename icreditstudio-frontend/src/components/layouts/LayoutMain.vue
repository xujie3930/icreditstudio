<template>
  <el-container class="iframe-layout-container">
    <el-main class="iframe-layout-main">
      <LayoutHeader
        :crumbs-list="breadCrumbItems"
        :modules="topModules"
        :workspace="workspace"
        :active-module-id="activeModuleId"
      />

      <div :class="['layout-container', isCollapse ? 'layout-collapse' : '']">
        <!-- 一级菜单 -->
        <LayoutHeaderSidebar
          v-if="isHeaderCollapse"
          :menu="moduleMenus[activeModuleId]"
          :crumbs-list="breadCrumbItems"
          :modules="topModules"
          :active-module-id="activeModuleId"
          @onChange="changeMenu"
        />
        <!-- 二级菜单 -->
        <LayoutMainSidebar
          v-else
          :menu="moduleMenus[activeModuleId]"
          @getChildMenus="getChildMenus"
        />
        <div class="layout-content">
          <!-- <LayoutMainTabBar /> -->
          <LayoutBreadcrumd :curBreadcrumb="curBreadcrumb" />
          <main class="iframe-layout-main-container">
            <!-- 三级以及四级菜单 -->
            <LayoutContainerSidebar
              v-if="isExistThreeMenus"
              :menu="threeChildrenMenus"
              @threeMenuChange="threeMenuChange"
              @fourMenuChange="fourMenuChange"
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
      workspace: '工作空间',
      curBreadcrumb: [],
      breadCrumbItems: [],

      // 存在三级以及四级菜单
      isExistThreeMenus: true,
      threeChildrenMenus: []
    }
  },

  computed: {
    ...mapGetters({
      permissionList: 'user/permissionList',
      topModules: 'permission/topModules',
      moduleMenus: 'permission/moduleMenus',
      activeModuleId: 'permission/activeModuleId',
      isHeaderCollapse: 'common/isHeaderCollapse',
      isCollapse: 'common/isCollapse'
    }),

    keepAlive() {
      return this.$route.meta.keepAlive
    }
  },

  // watch: {
  //   $route: {
  //     immediate: true,
  //     handler(to) {
  //       this.initBreadCrumbItems(to)
  //     }
  //   }
  // },

  created() {
    this.curBreadcrumb.push(this.topModules[0])
    this.initBreadCrumbItems(this.$route)
  },

  methods: {
    initBreadCrumbItems(router) {
      const breadCrumbItem = [
        // { path: '/', title: window.__JConfig.baseConfig.projectName }
      ]
      router.matched.forEach(item => {
        if (item.meta && item.meta.name) {
          breadCrumbItem.push({
            path: item.path,
            name: item.meta.name
          })
        }
      })
      this.breadCrumbItems = breadCrumbItem
      this.curBreadcrumb.push(breadCrumbItem[0])
    },

    // 一级菜单
    changeMenu(curMenu) {
      const { children = [], label } = curMenu
      this.threeChildrenMenus = []
      this.curBreadcrumb = [curMenu]
      this.workspace = label
      // 自动加载二级菜单的第一个菜单
      children.length && this.getChildMenus(children[0])
    },

    // 二级菜单
    getChildMenus(curMenu) {
      const { children: childMenus, ...rest } = curMenu
      this.curBreadcrumb = [this.curBreadcrumb[0], rest]
      this.isExistThreeMenus = !!childMenus?.length
      this.threeChildrenMenus = childMenus?.filter(item => item.isShow)
    },

    // 三级菜单更改
    threeMenuChange(curMenu) {
      console.log(curMenu, 'lololo')
      const [firstItem, secondItem] = this.curBreadcrumb
      this.curBreadcrumb = [firstItem, secondItem, curMenu]
    },

    // 四级菜单更改
    fourMenuChange(curMenu) {
      const [firstItem, secondItem, thirdItem] = this.curBreadcrumb
      console.log(curMenu, 'lololo')
      this.curBreadcrumb = [firstItem, secondItem, thirdItem, curMenu]
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
    @include center();
    margin-top: 64px;
    margin-left: 100px;
    transition: all 0.5s;
  }

  .layout-content {
    min-width: calc(100% - 220px);
    flex: 1;
  }

  .layout-collapse {
    margin-left: 64px;
  }
}
</style>
