<!--
 * @Author: lizheng
 * @Description: 三级以及四级菜单
 * @Date: 2021-08-10
-->
<template>
  <div v-if="menu.length" class="iframe-layout-container-wrap ">
    <el-aside
      :class="['iframe-layout-aside', 'sidebar', 'container-sidebar']"
      width="245px"
    >
      <el-menu
        :default-active="menu.filter(e => e.isShow)[0].name"
        :background-color="getBaseConfig('menu-color-bg')"
        :active-text-color="getBaseConfig('menu-color-text-active')"
      >
        <template v-for="item in menu.filter(({ isShow }) => isShow)">
          <el-menu-item
            :key="item.name"
            :index="item.name"
            class="menu-left-item"
            @click="handleMenuSelected(item)"
          >
            <i :class="[item.iconPath, 'menu-icon']" />
            <span slot="title">{{ item.name }}</span>
          </el-menu-item>
          <!-- <el-submenu :key="item.name" :index="item.url">
            <template #title>
              <div
                class="custom-submenu-item"
                @click="handleThreeLevelMenuChange(item, $event)"
              >
                <i :class="[item.iconPath]" />
                <span>{{ item.name }}</span>
              </div>
            </template>
            <el-menu-item-group>
              <el-menu-item
                v-for="son in item.children"
                :key="son.url"
                :index="renderPath(son)"
                class="menu-left-item"
                @click="handleFourLevelMenuChange(son, $event)"
              >
                {{ son.name }}
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu> -->
        </template>
      </el-menu>
    </el-aside>
  </div>
</template>

<script>
import variables from '@/styles/common/_variables.scss'
import { mapGetters, mapActions } from 'vuex'
import { DEFAULT_LOGO_IMG } from '@/config/constant'
import { base64UrlFilter } from '@/utils/util'

export default {
  props: {
    menu: {
      type: Array,
      default: () => []
    },
    mainContainerSidebar: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      DEFAULT_LOGO_IMG
    }
  },
  filters: {
    base64UrlFilter(url) {
      return base64UrlFilter(url)
    }
  },
  computed: {
    ...mapGetters({
      isCollapse: 'common/isCollapse',
      systemSetting: 'user/systemSetting'
    })
  },
  methods: {
    ...mapActions('common', ['toggleCollapseActions']),

    handleMenuSelected(item) {
      const showChildArr = item.children
        ? item.children.filter(({ isShow }) => isShow)
        : []
      console.log(showChildArr, 'showChildArr')
      // !showChildArr.length && this.$router.push(item.url)
      this.$router.push(item.url)
      this.$emit('getChildMenus', item)
    },

    handleThreeLevelMenuChange(curMenu, evt) {
      console.log(curMenu, evt)
      this.$emit('threeMenuChange', curMenu)
    },

    handleFourLevelMenuChange(curMenu, evt) {
      console.log(curMenu, evt)
      this.$emit('fourMenuChange', curMenu)
    },

    isExistChildren(item) {
      console.log('三级菜单item::', item)
      const { children } = item
      const moreThirdMore = []
      if (!children || !children.length) return 0
      children.forEach(list => {
        if (!list.children || !list.children.length) return
        list.children.forEach(
          cList => cList.isShow && moreThirdMore.push(cList)
        )
      })

      return moreThirdMore.length
    },

    getBaseConfig(key) {
      return variables[key]
    },

    renderPath(item) {
      let _path = item.url
      if (item.component === 'main/LayoutIframe' && item.src) {
        _path = _path.substring(0, _path.indexOf(':')) + item.src
      }
      return _path
    },

    // 点击一级菜单，如没有子菜单则跳转，有则展开/收缩菜单
    handleLinkOrToggle({ children, url, redirectPath }, e) {
      console.log(url, children, e)
      if (children?.length) return
      e.stopPropagation()
      this.$router.push({ path: redirectPath || url })
    },

    handleCollapse() {
      this.toggleCollapseActions(!this.isCollapse)
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/common/_mixin.scss';
.iframe-layout-aside-header,
.label {
  span {
    font-size: 120%;
  }

  .system-logo {
    margin-left: 10px;
  }

  .system-name {
    max-width: calc(100% - 50px);
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin-right: 10px;
  }
}

.menu-icon {
  font-size: 24px;
}

.menu-left-item {
  /deep/ .el-tooltip {
    height: unset !important;
    width: unset !important;
    left: unset !important;
    top: unset !important;
  }
}

.iframe-layout-aside-wrap {
  @include flex(column, space-between);
  height: calc(100vh - 64px);
  overflow: hidden;
}

.iframe-layout-container-wrap {
  margin-right: 16px;
}

.iframe-layout-sidebar-crumbs {
  width: 100%;
  padding-bottom: 23px;
  cursor: pointer;
  background: #fff;
  text-align: center;

  .j-svg {
    width: 19px;
    height: 17px;
    transition: $--all-transition;
  }

  .icon-fold {
    transform: rotateY(0);
  }

  .icon-unfold {
    transform: rotateY(180deg);
  }
}
</style>
