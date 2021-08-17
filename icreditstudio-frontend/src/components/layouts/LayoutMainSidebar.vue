<template>
  <div class="iframe-layout-aside-wrap">
    <el-aside
      :class="[
        isCollapse ? 'is-collapse' : '',
        'iframe-layout-aside',
        'sidebar'
      ]"
      width="100px"
    >
      <el-menu
        :collapse="isCollapse"
        :default-active="$route.path"
        :background-color="getBaseConfig('menu-color-bg')"
        :active-text-color="getBaseConfig('menu-color-text-active')"
      >
        <template v-for="item in menu.filter(e => e.isShow)">
          <el-menu-item
            :key="item.name"
            :index="item.url"
            class="menu-left-item"
            @click="handleMenuSelected(item)"
          >
            <i :class="[item.iconPath, 'menu-icon']" />
            <span slot="title">{{ item.name }}</span>
          </el-menu-item>
        </template>
      </el-menu>
    </el-aside>

    <div class="iframe-layout-sidebar-crumbs" @click="handleCollapse">
      <j-svg
        name="sidebar-collapse"
        :class="[isCollapse ? 'icon-unfold' : 'icon-fold']"
      />
    </div>
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
      console.log(item, 'item')
      this.$router.push(item.url)
      this.$emit('getChildMenus', item)
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
      if (children && children?.length >= 1) return
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
  margin-bottom: 11px;
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

  ::v-deep {
    .el-menu-item,
    .el-submenu__title {
      height: 70px;
    }
  }
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
