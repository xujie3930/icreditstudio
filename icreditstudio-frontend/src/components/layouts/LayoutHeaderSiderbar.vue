<template>
  <el-aside class="iframe-layout-aside header-sidebar" width="180px">
    <el-menu :default-active="activeModuleId">
      <template v-for="item in modules">
        <el-menu-item
          :key="item.id"
          :index="item.id"
          class="menu-left-item"
          @click="handleMenuSelected(item)"
        >
          <i :class="[item.iconPath, 'menu-icon']" />
          <span slot="title">{{ item.label }}</span>
        </el-menu-item>
      </template>
    </el-menu>
  </el-aside>
</template>

<script>
import variables from '@/styles/common/_variables.scss'
import { mapGetters, mapActions, mapMutations } from 'vuex'
import { SET_ACTIVE_MODULE_ID } from '@/store/mutation-types'
import { DEFAULT_LOGO_IMG } from '@/config/constant'
import { base64UrlFilter } from '@/utils/util'

export default {
  props: {
    menu: {
      type: Array,
      default: () => []
    },
    crumbsList: {
      type: Array
    },
    modules: {
      type: Array
    },
    activeModuleId: {
      type: String
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
      isHeaderCollapse: 'common/isHeaderCollapse',
      systemSetting: 'user/systemSetting'
    })
  },

  methods: {
    ...mapMutations('permission', { setActinveMenuId: SET_ACTIVE_MODULE_ID }),
    ...mapActions('common', ['toggleHeaderCollapseActions']),

    handleMenuSelected(item) {
      this.setActinveMenuId(item.id)
      this.toggleHeaderCollapseActions(false)
      this.$emit('onChange', item)
    },

    getBaseConfig(key) {
      return variables[key]
    },

    // 点击一级菜单，如没有子菜单则跳转，有则展开/收缩菜单
    handleLinkOrToggle({ children, url, redirectPath }, e) {
      if (children && children.length >= 1) return
      e.stopPropagation()
      this.$router.push({ path: redirectPath || url })
    }
  }
}
</script>

<style lang="scss" scoped>
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
  color: #fff;
  font-size: 24px;
}

.header-sidebar {
  position: fixed;
  left: 0;
  z-index: 999;
}
</style>
