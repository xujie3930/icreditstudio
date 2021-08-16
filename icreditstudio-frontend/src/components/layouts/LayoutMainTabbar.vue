<template>
  <div
    id="tabs-bar-container"
    class="iframe-tabs-bar-container iframe-flex-row-sp-center"
  >
    <el-tabs
      v-model="tabActive"
      type="card"
      class="iframe-tabs label"
      @tab-click="handleTabClick"
      @tab-remove="handleTabRemove"
    >
      <el-tab-pane
        v-for="item in visitedRoutes"
        :key="item.path"
        :label="item.meta.name"
        :name="item.path"
        :closable="!isAffix(item)"
      ></el-tab-pane>
    </el-tabs>

    <el-dropdown class="iframe-tabs-options label" @command="handleCommand">
      <div class="more">
        <span class="text">更多操作</span>
        <i class="el-icon-arrow-down el-icon--right icon"></i>
      </div>
      <el-dropdown-menu slot="dropdown" class="tabs-more">
        <el-dropdown-item command="handleCloseOthersTabs">
          关闭其他
        </el-dropdown-item>
        <el-dropdown-item command="handleCloseLeftTabs">
          关闭左侧
        </el-dropdown-item>
        <el-dropdown-item command="handleCloseRightTabs">
          关闭右侧
        </el-dropdown-item>
        <el-dropdown-item command="handleCloseAllTabs">
          关闭全部
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
import path from 'path'
import { mapGetters } from 'vuex'
import { SET_ACTIVE_MODULE_ID } from '@/store/mutation-types'

export default {
  name: 'LayoutMainTabsBar',
  data() {
    return {
      affixTabs: [],
      tabActive: ''
    }
  },
  computed: {
    ...mapGetters({
      topModules: 'permission/topModules',
      visitedRoutes: 'tabs-bar/visitedRoutes',
      routes: 'permission/routers'
    })
  },
  watch: {
    $route: {
      handler() {
        this.initTabs()
        this.addTabs()
        this.tabActive =
          this.visitedRoutes.find(x => x.path === this.$route.path)?.path || ''
      },
      immediate: true
    }
  },
  methods: {
    async handleTabRemove(tabActive) {
      let view
      this.visitedRoutes.forEach(item => {
        if (tabActive === item.path) {
          view = item
        }
      })
      const { visitedRoutes } = await this.$store.dispatch(
        'tabs-bar/delRoute',
        view
      )
      if (this.isActive(view)) {
        this.toLastTag(visitedRoutes, view)
      }
    },
    handleTabClick(tab) {
      const route = this.visitedRoutes.find((item, index) => {
        return tab.index === index.toString()
      })
      /* IFrame 1.1 新增需求 当点击首页的时候展示默认路由 ============= start */
      if (route.path === '/index' && this.topModules.find(e => e.id === 'defaultModule')) {
        this.$store.commit(`permission/${SET_ACTIVE_MODULE_ID}`, 'defaultModule')
      }
      /* IFrame 1.1 新增需求 当点击首页的时候展示默认路由 ============= end */
      if (this.$route.path !== route.path) {
        this.$router.push({
          path: route.path,
          query: route.query,
          fullPath: route.fullPath
        })
      } else {
        return false
      }
    },
    isActive(route) {
      return route.path === this.$route.path
    },
    isAffix(tag) {
      return tag.meta && tag.meta.affix
    },
    filterAffixtabs(routes, basePath = '/') {
      let tabs = []
      routes.forEach(route => {
        if (route.meta && route.meta.affix) {
          const tagPath = path.resolve(basePath, route.path)
          tabs.push({
            fullPath: tagPath,
            path: tagPath,
            name: route.name,
            meta: { ...route.meta }
          })
        }
        if (route.children) {
          const temptabs = this.filterAffixtabs(route.children, route.path)
          if (temptabs.length >= 1) {
            tabs = [...tabs, ...temptabs]
          }
        }
      })
      return tabs
    },
    initTabs() {
      this.affixTabs = this.filterAffixtabs(this.routes)
      for (const tag of this.affixTabs) {
        if (tag.name) {
          this.$store.dispatch('tabs-bar/addVisitedRoute', tag)
        }
      }
    },
    addTabs() {
      const { name } = this.$route
      // if (meta.hidden) return false
      if (name) {
        this.$store.dispatch('tabs-bar/addVisitedRoute', this.$route)
      }
      return false
    },
    handleCommand(command) {
      /* handleCloseOthersTabs handleCloseLeftTabs handleCloseRightTabs handleCloseAllTabs */
      this[command]()
    },
    async handleCloseOthersTabs() {
      const view = await this.toThisTag()
      await this.$store.dispatch('tabs-bar/delOthersRoutes', view)
    },
    async handleCloseLeftTabs() {
      const view = await this.toThisTag()
      await this.$store.dispatch('tabs-bar/delLeftRoutes', view)
    },
    async handleCloseRightTabs() {
      const view = await this.toThisTag()
      await this.$store.dispatch('tabs-bar/delRightRoutes', view)
    },
    async handleCloseAllTabs() {
      const view = await this.toThisTag()
      const { visitedRoutes } = await this.$store.dispatch(
        'tabs-bar/delAllRoutes'
      )
      if (this.affixTabs.some(tag => tag.path === view.path)) {
        return
      }
      this.toLastTag(visitedRoutes, view)
    },
    toLastTag(visitedRoutes) {
      const latestView = visitedRoutes.slice(-1)[0]
      if (latestView) {
        this.$router.push(latestView)
      } else {
        this.$router.push('/')
      }
    },
    toThisTag() {
      const view = this.visitedRoutes.find(item => item.path === this.$route.fullPath)
      if (this.$route.path !== view.path) this.$router.push(view)
      return view
    }
  }
}
</script>

<style lang="scss" scoped>
.more {
  display: flex;
  flex-wrap: nowrap;
  justify-content: center;
  align-items: center;

  .text {
    white-space: nowrap;
  }
}

.label .el-tabs__item {
  font-size: unset;
}
</style>
