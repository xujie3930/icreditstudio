<!--
 * @Author: lizheng
 * @Description: 三级以及四级菜单
 * @Date: 2021-08-10
-->
<template>
  <div v-if="menu.length" class="iframe-layout-container-wrap ">
    <el-aside :class="['iframe-layout-aside', 'sidebar']" width="245px">
      <el-menu
        router
        :default-active="$route.path"
        :background-color="getBaseConfig('menu-color-bg')"
        :active-text-color="getBaseConfig('menu-color-text-active')"
      >
        <template v-for="item in menu">
          <el-submenu :key="item.name" :index="item.url">
            <template #title>
              <div
                class="custom-submenu-item"
                @click="handleLinkOrToggle(item, $event)"
              >
                <i :class="[item.iconPath]" />
                <span>{{ item.name }}</span>
              </div>
            </template>
            <el-menu-item-group>
              <el-menu-item
                v-for="son in item.children.filter(e => e.isShow)"
                :key="son.url"
                :index="renderPath(son)"
                class="menu-left-item"
              >
                {{ son.name }}
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>
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

    // 是否存在二级及以上菜单
    isExistChildren(item) {
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

    /**
     * @desc 返回配置
     * @param {String} key
     * @return {String} 配置值
     */
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
      if (children && children.length >= 1) return
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
