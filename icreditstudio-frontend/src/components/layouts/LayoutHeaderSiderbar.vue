<template>
  <div class="icredit-sidebar">
    <el-aside class="iframe-layout-aside header-sidebar" width="180px">
      <el-menu
        :default-active="activeModuleId"
        active-text-color="#fff"
        text-color="#fff"
      >
        <template v-for="item in modules">
          <el-menu-item
            :key="item.id"
            :index="item.id"
            class="menu-left-item"
            @click="handleMenuSelected(item)"
          >
            <j-svg
              class="j-svg"
              v-if="customMenuIcon.includes(item.path)"
              :name="menuIconName(item)"
            />
            <i v-else :class="[item.iconPath, 'menu-icon']" />
            <span slot="title">
              {{ item.label }}
              <i
                v-if="item.label === ALL_PRODUCT_NAME"
                class="el-icon-arrow-right"
                style="color:#fff"
              ></i>
            </span>
          </el-menu-item>
        </template>
      </el-menu>
    </el-aside>

    <div v-if="activeModuleName === ALL_PRODUCT_NAME" class="aside-product">
      <div
        class="aside-product-item"
        v-for="item in allMenuNavMapping"
        :key="item.name"
      >
        <div class="header">{{ item.name }}</div>
        <div class="container">
          <div
            class="container-item"
            v-for="(list, idx) in item.children"
            :key="idx"
          >
            <!-- <j-svg
            class="j-svg"
            v-if="customMenuIcon.includes(item.path)"
            :name="menuIconName(item)"
          />
            <i v-else :class="[item.iconPath, 'menu-icon']" /> -->
            <j-svg class="jsvg" :name="list.icon" />
            <span class="text">{{ list.name }}</span>
          </div>
        </div>
        <div class="divider"></div>
      </div>
    </div>
  </div>
</template>

<script>
import variables from '@/styles/common/_variables.scss'
import { mapGetters, mapActions, mapMutations } from 'vuex'
import {
  SET_ACTIVE_MODULE_ID,
  SET_ACTIVE_MODULE_NAME
} from '@/store/mutation-types'
import { DEFAULT_LOGO_IMG } from '@/config/constant'
import { base64UrlFilter } from '@/utils/util'
import {
  rootMenuMapping,
  allMenuNavMapping,
  ALL_PRODUCT_NAME
} from '@/config/menu'

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
    this.customMenuIcon = Object.keys(rootMenuMapping)

    return {
      allMenuNavMapping,
      ALL_PRODUCT_NAME,
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
      systemSetting: 'user/systemSetting',
      activeModuleName: 'permission/activeModuleName'
    })
  },

  methods: {
    ...mapMutations('permission', {
      setActinveMenuId: SET_ACTIVE_MODULE_ID,
      setActinveMenuName: SET_ACTIVE_MODULE_NAME
    }),
    ...mapActions('common', ['toggleHeaderCollapseActions']),

    handleMenuSelected(item) {
      this.setActinveMenuId(item.id)
      this.setActinveMenuName(item.label)
      if (item.label === ALL_PRODUCT_NAME) return
      this.toggleHeaderCollapseActions(false)
      this.$emit('onChange', item)
    },

    getBaseConfig(key) {
      return variables[key]
    },

    menuIconName(item) {
      const url = item.url || item.path
      const icon = rootMenuMapping[url]?.icon || 'menu-workspace'
      return icon
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

.menu-left-item {
  .j-svg {
    width: 20px;
    height: 20px;
    margin-right: 10px;
  }
}

.menu-icon {
  color: #fff;
  font-size: 24px;
}

.icredit-sidebar {
  display: flex;
  position: fixed;
  left: 0;
  z-index: 9999;
}

.aside-product {
  width: 500px;
  min-height: 840px;
  overflow: hidden;
  background-color: #0072db;
  padding: 15px;

  &-item {
    .header {
      position: relative;
      width: 64px;
      height: 22px;
      font-size: 16px;
      font-family: PingFangSC, PingFangSC-Medium;
      font-weight: 500;
      text-align: left;
      color: #fff;
      line-height: 22px;
      margin-left: 15px;

      &::before {
        content: '';
        position: absolute;
        width: 4px;
        height: 16px;
        background: #faad14;
        border-radius: 2px;
        top: 4px;
        left: -10px;
      }
    }

    .container {
      margin-left: 15px;

      &-item {
        @include flex(row, flex-start);
        display: inline-flex;
        width: 25%;
        margin-top: 25px;
        cursor: pointer;

        .text {
          font-size: 12px;
          font-family: SourceHanSansCN, SourceHanSansCN-Medium;
          font-weight: 500;
          text-align: left;
          color: #fff;
          margin-left: 6px;
        }
      }
    }

    .divider {
      width: 440px;
      height: 1px;
      opacity: 0.1;
      border: 1px solid #fff;
      margin: 30px 0 15px 15px;
    }

    &:last-child {
      .divider {
        display: none;
      }
    }
  }
}
</style>
