<template>
  <div class="iframe-layout-main-header">
    <div class="iframe-layout-main-header-crumbs" @click="handleCollapse">
      <j-svg
        name="header-collapse"
        :class="['header-collapse', isCollapse ? 'unfold' : 'fold']"
      />
    </div>

    <div class="header-text">
      <j-svg name="logo" class="logo" />
      <span class="system-name">{{ SYSTEM_NAME }}</span>
      <span class="split"></span>
      <span class="menu">{{ workspace }}</span>
    </div>

    <div class="iframe-layout-main-header-user">
      <!-- 工作空间 -->
      <el-select
        class="workspace-select"
        size="mini"
        placeholder="请选择"
        filterable
        v-model="wid"
        @change="workspaceIdChange"
      >
        <el-option
          v-for="(item, idx) in workspaceList"
          :key="`${item.id}-${idx}`"
          :label="item.name"
          :value="item.id"
        >
        </el-option>
      </el-select>

      <!-- 快捷菜单 -->
      <!-- <i
        @click="handleShowShortMenu"
        :class="[
          'el-icon-s-operation',
          'quick-menu',
          isShowQuickMenu === 'Y' ? 'shortmenu-open' : 'shortmenu-close'
        ]"
        :style="{ color: '#fff' }"
      /> -->
      <!-- color: shortMenus.length
            ? getSystemTheme(systemSetting.cssId)
            : 'unset' -->

      <!-- <el-popover
        style="margin-right: 30px;cursor: pointer"
        placement="bottom"
        width="400"
        trigger="hover"
      >
        <el-tabs v-model="activeMessageName" @tab-click="handleMessageClick">
          <el-tab-pane
            :label="`预警消息(${messageNoticeInfo.wunreadCount})`"
            name="W"
          ></el-tab-pane>
          <el-tab-pane
            :label="`通知((${messageNoticeInfo.nunreadCount})`"
            name="N"
          ></el-tab-pane>
          <el-tab-pane
            :label="`系统消息(${messageNoticeInfo.sunreadCount})`"
            name="S"
          ></el-tab-pane>
        </el-tabs>
        <el-badge
          slot="reference"
          :value="messageNoticeInfo.totalUnreadCount"
          :max="99"
          :hidden="messageNoticeInfo.totalUnreadCount === 0"
        >
          <i class="el-icon-message-solid" style="font-size: 22px"></i>
        </el-badge>
      </el-popover> -->
      <el-dropdown @command="handleCommand">
        <p>
          <el-avatar
            :src="userInfo.photo || DEFAULT_HEAD_IMG_URL | base64UrlFilter"
          ></el-avatar>
          <span class="label">{{ userInfo.userName }}</span>
        </p>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="userinfo">
              个人中心
            </el-dropdown-item>
            <el-dropdown-item command="setting">
              个性化设置
            </el-dropdown-item>
            <el-dropdown-item command="changepassword">
              修改密码
            </el-dropdown-item>
            <el-dropdown-item command="logout">
              退出
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { base64UrlFilter } from '@/utils/util'
import { getSystemTheme } from '@/utils/theme'
// import LayoutHeaderSlot from '@/components/layout/LayoutHeaderSlot'
import { pollingUnreadInfos } from '@/api/message'
import { mapGetters, mapActions, mapMutations } from 'vuex'
import {
  SET_ACTIVE_MODULE_ID,
  SET_ACTIVE_MODULE_NAME
} from '@/store/mutation-types'
import { DEFAULT_HEAD_IMG_URL, SYSTEM_NAME } from '@/config/constant'
import { settingUserShortMenuStatus } from '@/api/system'
import { ALL_PRODUCT_NAME } from '@/config/menu'

export default {
  name: 'LayoutHeader',
  // components: { LayoutHeaderSlot },
  props: {
    crumbsList: {
      type: Array
    },
    modules: {
      type: Array
    },
    activeModuleId: {
      type: String
    },
    workspace: {
      type: String,
      default: '工作空间'
    }
  },

  data() {
    this.SYSTEM_NAME = SYSTEM_NAME
    this.getSystemTheme = getSystemTheme
    return {
      wid: undefined,
      isShowQuickMenu: 'N',
      activeModule: '',
      count: 1,
      timer: null,
      DEFAULT_HEAD_IMG_URL,
      // 消息中心相关 start ↓
      totalUnreadCount: 0, // 未读消息总数
      sunreadCount: 0, // 未读系统消息数
      wunreadCount: 0, // 未读预警消息数
      nunreadCount: 0, // 未读通知消息数
      activeMessageName: 'W'
      // 消息中心相关 end ↑
    }
  },

  filters: {
    base64UrlFilter(url) {
      return base64UrlFilter(url)
    }
  },

  watch: {
    workspaceId(nVal) {
      this.wid = nVal
      this.$actions.setGlobalState({ workspaceId: nVal })
    }
  },

  computed: {
    ...mapGetters({
      workspaceId: 'user/workspaceId',
      workspaceCreateAuth: 'user/workspaceCreateAuth',
      workspaceList: 'user/workspaceList',
      userInfo: 'user/userInfo',
      isCollapse: 'common/isHeaderCollapse',
      messageNoticeInfo: 'user/messageNoticeInfo',
      systemSetting: 'user/systemSetting',
      shortMenus: 'user/shortMenus',
      activeModuleName: 'permission/activeModuleName'
    }),

    title() {
      return window.__JConfig.baseConfig.projectName
    }
  },

  async created() {
    this.activeModule = this.activeModuleId
    // this.pollingUnreadInfos(60000)
    this.$once('hook:beforeDestroy', () => clearTimeout(this.timer))
  },

  mounted() {
    this.isShowQuickMenu = this.systemSetting.enableCustomMenu
    this.setWorkspaceId(this.$ls.get('workspaceId') || '0')
    this.wid = this.workspaceId
  },

  methods: {
    ...mapMutations('permission', {
      setActinveMenuId: SET_ACTIVE_MODULE_ID,
      setActinveMenuName: SET_ACTIVE_MODULE_NAME
    }),
    ...mapActions('common', ['toggleHeaderCollapseActions']),
    ...mapActions('user', [
      'logoutAction',
      'setMessageNoticeInfo',
      'setIsShowQuickMenu',
      'getPermissionListAction',
      'setWorkspaceId'
    ]),

    workspaceIdChange(id) {
      this.$ls.set('workspaceId', id)
      this.setWorkspaceId(id)
    },

    // 点击展示快捷菜单
    handleShowShortMenu() {
      if (this.isShowQuickMenu && !this.shortMenus.length) {
        return this.$message({
          type: 'warning',
          center: true,
          showClose: true,
          message: '你还未设置快捷菜单！'
        })
      }
      this.updateUserShortMenuStatus({
        enableCustomMenu: this.isShowQuickMenu === 'Y' ? 'N' : 'Y'
      })
      this.isShowQuickMenu = this.isShowQuickMenu === 'Y' ? 'N' : 'Y'
    },

    handleCollapse() {
      if (this.activeModuleName === ALL_PRODUCT_NAME) {
        const { id, label, path, redirectPath } = this.modules[1]
        this.setActinveMenuId(id)
        this.setActinveMenuName(label)
        this.$router.push(redirectPath || path)
      }
      this.toggleHeaderCollapseActions(!this.isCollapse)
    },

    handleCommand(value) {
      if (value === 'logout') {
        this.$confirm('是否退出登录?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.logoutAction().then(() => {
            this.$router.replace('/login')
          })
        })
      } else if (value === 'changepassword') {
        this.$router.push({ path: '/manage/changepassword' })
      } else if (value === 'userinfo') {
        this.$router.push({ path: '/manage/userinfo' })
      } else if (value === 'setting') {
        this.$router.push('/manage/personalized')
      }
    },

    handleModuleClick({ name }) {
      this.$store.commit(`permission/${SET_ACTIVE_MODULE_ID}`, name)
    },

    // 消息中心相关 start ↓
    handleMessageClick({ name }) {
      this.$router.push({
        path: '/manage/messageNotice/',
        query: { infoType: name }
      })
    },

    pollingUnreadInfos(delay = 60000) {
      if (this.timer) {
        clearTimeout(this.timer)
      }
      pollingUnreadInfos().then(res => {
        if (res.success) {
          this.setMessageNoticeInfo(res.data)
          this.timer = setTimeout(() => {
            this.pollingUnreadInfos()
          }, delay)
        }
      })
    },
    // 消息中心相关 end ↑

    // 更新用户快捷菜单点击状态
    updateUserShortMenuStatus(params) {
      settingUserShortMenuStatus(params)
        .then(({ success, data }) => {
          if (success) {
            console.log(data)
            this.$router.go(0)
          }
        })
        .finally(() => {
          console.log('ooooo')
        })
    }
  }
}
</script>

<style lang="scss" scoped>
.quick-menu {
  font-size: 24px;
  cursor: pointer;
  margin: 0 20px;
  transition: transform linear 0.3s;
}

.shortmenu-close {
  transform: translate(0);
}

.shortmenu-open {
  transform: rotate(90deg);
}

.iframe-layout-main-header-crumbs {
  @include flex;
  width: 64px;
  height: 64px;
  background-color: #0072db;
  cursor: pointer;

  .header-collapse {
    width: 22px;
    height: 20px;
    cursor: pointer;
    transition: transform 0.3s ease 0s;
  }

  .fold {
    transform: rotate(0);
  }

  .unfold {
    transform: rotate(180deg);
  }
}

.header-text {
  @include flex;
  margin-left: 15px;
  font-family: PingFangSC, PingFangSC-Semibold;

  .logo {
    width: 42px;
    height: 36px;
    margin-right: 10px;
  }

  .system-name {
    font-size: 18px;
    font-weight: 600;
    color: #fff;
    height: 25px;
    line-height: 25px;
  }

  .split {
    margin: 0 15px;
    width: 2px;
    height: 15px;
    opacity: 0.3;
    background: #fff;
    border-radius: 1px;
    margin-top: 2px;
  }

  .menu {
    font-size: 16px;
    font-weight: 600;
    color: #fff;
    height: 25px;
    line-height: 25px;
  }
}

.iframe-layout-main-header-user {
  .workspace-select {
    width: 150px;
    margin: 0 24px;

    ::v-deep {
      .el-input__inner {
        background-color: #1890ff;
        color: #fff;
        border-color: #fff;
      }

      .el-select__caret {
        color: #fff;
      }

      .el-input.is-focus .el-input__inner {
        border-color: #fff;
      }
    }
  }
}
</style>

<style lang="scss">
// .modal-block,
// .label {
//   .el-tabs__header {
//     margin-bottom: 2px;
//   }
//   .el-tabs__active-bar {
//     height: 3px;
//     background-color: #fff;
//   }
//   .el-tabs__item {
//     font-size: unset;
//     font-weight: 600;
//     color: #fff;
//   }
//   .el-tabs__item.is-active {
//     color: #fff;
//   }
//   .el-tabs__nav-wrap::after {
//     background: none;
//   }
// }
//
</style>
