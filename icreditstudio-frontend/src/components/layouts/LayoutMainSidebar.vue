<template>
  <div class="iframe-layout-aside-wrap">
    <el-aside
      :class="[
        isCollapse ? 'is-collapse' : '',
        'iframe-layout-aside',
        'sidebar'
      ]"
      width="160px"
    >
      <el-menu
        :default-active="defalutActived"
        :collapse="isCollapse"
        :background-color="getBaseConfig('menu-color-bg')"
        :active-text-color="getBaseConfig('menu-color-text-active')"
      >
        <template v-for="item in menu">
          <el-submenu
            v-if="isExistChildren(item)"
            :key="item.name"
            :index="item.name"
          >
            <template #title>
              <div class="menu-left-item">
                <j-svg
                  class="j-svg"
                  v-if="customMenuIcon.includes(item.url)"
                  :name="
                    item.name === defalutActived
                      ? `${menuIconName(item)}-active`
                      : menuIconName(item)
                  "
                />
                <i v-else :class="[item.iconPath, 'menu-icon']" />
                <span>{{ item.name }}</span>
              </div>
            </template>
            <el-menu-item-group>
              <el-menu-item
                v-for="son in item.children.filter(e => e.isShow)"
                :key="son.url"
                :index="son.name"
                @click="handleMenuSelected(son)"
              >
                {{ son.name }}
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>

          <!-- 无子级菜单 -->
          <el-menu-item
            v-else
            :key="item.name"
            :index="item.name"
            class="menu-left-item"
            @click="handleMenuSelected(item)"
          >
            <j-svg
              class="j-svg"
              v-if="customMenuIcon.includes(item.url)"
              :name="
                item.name === defalutActived
                  ? `${menuIconName(item)}-active`
                  : menuIconName(item)
              "
            />
            <i v-else :class="[item.iconPath, 'menu-icon']" />
            <span slot="title" style="margin-top:10px">{{ item.name }}</span>
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
import { secondMenuMapping } from '@/config/menu'

export default {
  props: {
    menu: {
      type: Array,
      default: () => []
    }
  },

  data() {
    this.customMenuIcon = Object.keys(secondMenuMapping)
    return {
      DEFAULT_LOGO_IMG,
      defalutActived: ''
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
      systemSetting: 'user/systemSetting',
      workspaceList: 'user/workspaceList',
      workspaceId: 'user/workspaceId'
    })
  },

  created() {
    this.defalutActived = this.menu.filter(
      e => e.isShow && !e.deleteFlag
    )[0]?.name
  },

  methods: {
    ...mapActions('common', ['toggleCollapseActions']),
    ...mapActions('user', ['setWorkspaceId']),

    isExistChildren(item) {
      return (
        item.children &&
        item.children.length &&
        item.children.map(child => child.isShow).filter(list => list).length
      )
    },

    handleMenuSelected(item) {
      this.defalutActived = item.name
      this.menuIconName(item)
      this.$router.push(item.url)
      this.$emit('getChildMenus', item)
    },

    // 切换菜单前必须先切换工作空间（不能为 全部 选项）
    changeWorkspaceMsg(item) {
      if (item.url === '/workspace/datasource' && this.workspaceId === 'all') {
        this.setWorkspaceId(this.workspaceList[1].id)
        return false
      }
      return true
    },

    getBaseConfig(key) {
      return variables[key]
    },

    menuIconName({ url }) {
      const icon = secondMenuMapping[url]?.icon || 'menu-workspace'
      return icon
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

.iframe-layout-aside-wrap {
  @include flex(column, space-between);
  height: calc(100vh - 64px);
  overflow: hidden;
  position: fixed;
  left: 0;
  z-index: 999;

  .menu-left-item {
    .j-svg {
      width: 18px;
      height: 18px;
      margin-right: 10px;
    }

    .menu-icon {
      font-size: 24px;
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
