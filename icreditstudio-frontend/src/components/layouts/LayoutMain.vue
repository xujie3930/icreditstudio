<template>
  <el-container class="iframe-layout-container">
    <el-main class="iframe-layout-main">
      <LayoutHeader
        :crumbs-list="breadCrumbItems"
        :modules="topModules"
        :workspace="workspace"
        :active-module-id="activeModuleId"
      />

      <div
        :class="[
          'layout-container',
          $route.path === '/home'
            ? 'hide-home-siderbar'
            : isCollapse
            ? 'layout-collapse'
            : 'layout-bar-expand'
        ]"
      >
        <transition v-if="isHeaderCollapse" name="fade">
          <!-- 一级菜单 -->
          <LayoutHeaderSidebar
            :menu="moduleMenus[activeModuleId]"
            :crumbs-list="breadCrumbItems"
            :modules="topModules"
            :active-module-id="activeModuleId"
            @onChange="changeMenu"
          />
        </transition>

        <!-- 二级菜单 -->
        <LayoutMainSidebar
          v-else-if="$route.path !== '/home'"
          :menu="moduleMenus[activeModuleId]"
          @getChildMenus="getChildMenus"
        />
        <!-- 组件内容 -->
        <div class="layout-content">
          <LayoutBreadcrumd
            v-if="$route.path !== '/home'"
            :curBreadcrumb="curBreadcrumb"
            @jump="handleCrumbJump"
          />
          <main class="iframe-layout-main-container">
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
import LayoutMainFooter from './LayoutMainFooter'
import { mapGetters, mapActions } from 'vuex'

export default {
  components: {
    LayoutHeader,
    LayoutBreadcrumd,
    LayoutHeaderSidebar,
    LayoutMainSidebar,
    LayoutMainFooter
  },

  data() {
    return {
      workspace: '首页',
      curBreadcrumb: [],
      breadCrumbItems: []
    }
  },

  computed: {
    ...mapGetters({
      permissionList: 'user/permissionList',
      topModules: 'permission/topModules',
      moduleMenus: 'permission/moduleMenus',
      activeModuleId: 'permission/activeModuleId',
      isHeaderCollapse: 'common/isHeaderCollapse',
      isCollapse: 'common/isCollapse',
      workspaceId: 'user/workspaceId',
      workspaceList: 'user/workspaceList'
    }),

    keepAlive() {
      return this.$route.meta.keepAlive
    }
  },

  created() {
    this.initPage()
    // this.initBreadCrumbItems(this.$route)
  },

  methods: {
    ...mapActions('user', ['setWorkspaceId']),

    initPage() {
      this.curBreadcrumb.push(this.topModules[1])
      this.curBreadcrumb.push(this.topModules[1].children[0])
      this.$router.push('/home')
      this.$ls.remove('taskForm')
      this.$ls.remove('selectedTable')
    },

    initBreadCrumbItems(router) {
      const breadCrumbItem = []
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

    // 面包屑导航栏跳转
    handleCrumbJump(toMenu) {
      const { path, redirectPath } = toMenu
      this.$router.push(redirectPath || path)
      // this.autoSelectWorkspaceId()
    },

    // 一级菜单
    changeMenu(curMenu, childMenu) {
      const { children = [], label } = curMenu
      this.curBreadcrumb = [curMenu]
      this.workspace = label
      this.$ls.remove('taskForm')
      this.$ls.remove('selectedTable')
      // this.autoSelectWorkspaceId()
      // 自动加载二级菜单的第一个菜单
      if (children.length && !childMenu) {
        this.getChildMenus(children[0])

        const exitShowChild = children[0].children
          ? children[0].children.filter(item => item.isShow && !item.deleteFlag)
          : []

        this.$router.push(
          exitShowChild.length ? exitShowChild[0].url : children[0].url
        )
      } else {
        this.getChildMenus(childMenu)
        this.$router.push(childMenu.url)
      }
    },

    // 二级菜单切换
    getChildMenus(curMenu, parentMenu) {
      const { children, ...rest } = curMenu
      this.curBreadcrumb = parentMenu
        ? [this.curBreadcrumb[0], parentMenu, rest]
        : [this.curBreadcrumb[0], rest]
      this.$ls.remove('taskForm')
      this.$ls.remove('selectedTable')
      // this.autoSelectWorkspaceId()
    }
  }
}
</script>

<style lang="scss" scoped>
.iframe-layout-container {
  .layout-container {
    display: flex;
    margin-top: 64px;
    transition: all 0.5s ease 0s;
  }

  .layout-bar-expand {
    margin-left: 160px;
  }

  .layout-collapse {
    margin-left: 64px;
  }

  .hide-home-siderbar {
    margin-left: 0;
  }

  .container-wrap {
    @include flex(column, center, flex-start);
    margin-top: 64px;
    // transition: all 0.5s;
  }

  .layout-content {
    min-width: calc(100% - 220px);
    flex: 1;
  }

  .fade-enter-active,
  .fade-leave-active {
    transition: opacity 0.5s;
  }

  .fade-enter,
  .fade-leave-to {
    opacity: 0;
  }
}
</style>
