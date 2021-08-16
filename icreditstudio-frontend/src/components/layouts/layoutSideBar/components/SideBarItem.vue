<template>
  <component
    :is="menuComponent"
    v-if="!item.hidden"
    :item="item"
    :full-path="fullPath"
    :route-children="routeChildren"
  >
    <template v-if="item.children && item.children.length">
      <side-bar-item
        v-for="route in item.children.filter(e => e.isShow)"
        :key="route.url"
        :full-path="handlePath(route.url)"
        :item="route"
      />
    </template>
  </component>
</template>

<script>
import SideBarMenu from './SideBarMenu'
import SideBarSubMenu from './SideBarSubMenu'
import { isExternal } from '@/utils/util'
// import path from 'path'

export default {
  name: 'SideBarItem',
  components: {
    SideBarMenu,
    SideBarSubMenu
  },
  props: {
    item: {
      type: Object,
      required: true
    },
    fullPath: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      routeChildren: {}
    }
  },
  computed: {
    menuComponent() {
      if (
        this.handleChildren(this.item.children, this.item) &&
        (!this.routeChildren.children ||
          this.routeChildren.notShowChildren) &&
        !this.item.alwaysShow
      ) {
        return 'SideBarMenu'
      } else {
        return 'SideBarSubMenu'
      }
    }
  },
  methods: {
    handleChildren(children = [], parent) {
      // eslint-disable-next-line no-param-reassign
      if (children === null) children = []
      const showChildren = children.filter(item => {
        if (item.hidden) {
          return false
        } else {
          this.routeChildren = item
          return true
        }
      })
      if (showChildren.length === 1) {
        return true
      }

      if (showChildren.length === 0) {
        this.routeChildren = {
          ...parent,
          path: '',
          notShowChildren: true
        }
        return true
      }
      return false
    },
    handlePath(routePath) {
      console.log(routePath)
      if (isExternal(routePath)) {
        return routePath
      }
      if (isExternal(this.fullPath)) {
        return this.fullPath
      }
      return routePath
      // return path.resolve(this.fullPath, routePath)
    }
  }
}
</script>

<style lang="scss" scoped>
.vab-nav-icon {
  margin-right: 4px;
}

::v-deep {
  .el-tag {
    float: right;
    height: 16px;
    padding-right: 4px;
    padding-left: 4px;
    line-height: 16px;
    border: 0;
  }
}
</style>
