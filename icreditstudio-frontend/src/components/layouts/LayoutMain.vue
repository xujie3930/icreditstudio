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
          v-else
          :menu="moduleMenus[activeModuleId]"
          @getChildMenus="getChildMenus"
        />
        <!-- 组件内容 -->
        <div class="layout-content">
          <LayoutBreadcrumd :curBreadcrumb="curBreadcrumb" />
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
import { mapGetters } from 'vuex'

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
      workspace: '工作空间',
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
      isCollapse: 'common/isCollapse'
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
    initPage() {
      this.curBreadcrumb.push(this.topModules[1])
      this.curBreadcrumb.push(this.topModules[1].children[0])
      this.$router.push('/home')
      this.$ls.remove('taskForm')
      this.$ls.remove('selectedTable')
    },

    initBreadCrumbItems(router) {
      console.log(router, 'router')
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

    // 一级菜单
    changeMenu(curMenu, childMenu) {
      console.log('cuName', curMenu, childMenu)
      const { children = [], label } = curMenu
      this.curBreadcrumb = [curMenu]
      this.workspace = label
      this.$ls.remove('taskForm')
      this.$ls.remove('selectedTable')
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
    getChildMenus(curMenu) {
      const { children, ...rest } = curMenu
      this.curBreadcrumb = [this.curBreadcrumb[0], rest]
      this.$ls.remove('taskForm')
      this.$ls.remove('selectedTable')
    },

    // 三级菜单切换
    threeMenuChange(curMenu) {
      const [firstItem, secondItem] = this.curBreadcrumb
      this.curBreadcrumb = [firstItem, secondItem, curMenu]
    },

    // 四级菜单切换
    fourMenuChange(curMenu) {
      const [firstItem, secondItem, thirdItem] = this.curBreadcrumb
      this.curBreadcrumb = [firstItem, secondItem, thirdItem, curMenu]
    }
  }
}
</script>

<style lang="scss" scoped>
.iframe-layout-container {
  .layout-container {
    display: flex;
    margin-top: 64px;
    margin-left: 160px;
    transition: all 0.5s ease 0s;
  }

  .layout-collapse {
    margin-left: 64px;
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
