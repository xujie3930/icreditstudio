<template>
  <div class="home iframe-flex-row-center-center h100">
    <div class="home-left">
      <div class="home-left-gif">
        <div class="title">一站式大数据开发与治理平台</div>
        <div class="img"><img src="../assets/home.gif" /></div>
      </div>
      <div class="home-left-func">
        <div class="title">
          功能演示
        </div>
        <div class="func-wrap">
          <div
            class="func-wrap-item"
            v-for="item in funcConfigs"
            :key="item.name"
            @click="handleFuncClick(item)"
          >
            <j-svg class="jsvg" :name="item.icon" />
            <span class="text">{{ item.name }}</span>
          </div>
        </div>
      </div>
    </div>

    <aside class="home-right">
      <header class="home-right-user">
        <h3 class="user">{{ userInfo.userName }}</h3>
        <div class="user-admin" v-if="workspaceCreateAuth">
          <j-svg class="jsvg" name="home-diamond" />
          <span class="text">超级管理员</span>
        </div>
      </header>

      <!-- 工作台 -->
      <section class="home-right-section">
        <div class="title-wrap">
          <span class="left">工作台</span>
          <div class="right">
            <i class="el-icon-refresh icon" @click="getStatisticsData"></i>
            <span class="text" v-show="refreshTime >= 0"
              >{{ refreshTime }}分钟前</span
            >
          </div>
        </div>

        <div class="container" v-loading="dataLoading">
          <div
            class="container-item"
            v-for="item in statiscticsData"
            :key="item.key"
          >
            <div class="count-wrap">
              <span class="count">{{ item.value }}</span>
              <span class="unit">个</span>
            </div>
            <div class="label">{{ item.label }}</div>
          </div>
        </div>

        <el-divider class="divider"></el-divider>
      </section>

      <!-- 菜单 -->
      <section class="home-right-section">
        <div class="title-wrap">
          <span class="left">菜单</span>
        </div>

        <div class="container">
          <div
            class="container-item container-row"
            v-for="item in menuConfigs"
            :key="item.iconName"
            @click="handleJumpClick(item.path)"
          >
            <j-svg class="jsvg icon" :name="item.iconName" />
            <span class="label">{{ item.label }}</span>
          </div>
        </div>

        <el-divider class="divider"></el-divider>
      </section>

      <!-- 实践教程 -->
      <section class="home-right-section">
        <div class="title-wrap">
          <span class="left">实践教程</span>
        </div>

        <div class="container">
          <div
            class="container-item container-row"
            v-loading="downloadLoading"
            @click="handleDownload"
          >
            <j-svg class="jsvg icon" name="home-book" />
            <span class="label">用户手册</span>
            <!-- <a
              href="../../static/intro.pdf"
              class="label"
              :download="filename"
              target="_blank"
              >用户手册</a
            > -->
          </div>
        </div>
      </section>
    </aside>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import workspace from '@/mixins/workspace'
import API from '@/api/icredit'
import { deepClone } from '@/utils/util'

export default {
  name: 'Home',

  mixins: [workspace],

  data() {
    return {
      lastRefreshTime: null,
      curRefreshTime: null,
      dataLoading: false,
      downloadLoading: false,
      filename: '一站式大数据开发与治理平台（iCredit）用户手册V0.0.1版本.pdf',

      // 功能演示
      funcConfigs: [
        {
          icon: 'menu-workspace-setting-black',
          iconActive: 'menu-space-active',
          name: '工作空间',
          path: '/workspace/space-setting',
          isHover: false
        },
        {
          icon: 'menu-data-manage-black',
          iconActive: 'menu-data-active',
          name: '数据管理',
          path: '/workspace/datasource',
          isHover: false
        },
        {
          icon: 'menu-govern-black',
          name: '数据治理',
          path: '',
          isHover: false
        },
        {
          icon: 'menu-assets-manage-black',
          name: '资产管理',
          path: '',
          isHover: false
        },
        {
          icon: 'menu-bi-black',
          name: 'BI可视化',
          path: '',
          isHover: false
        },
        {
          icon: 'menu-data-service-black',
          name: '数据服务',
          path: '',
          isHover: false
        },
        {
          icon: 'menu-depovs-black',
          name: '运维&安全',
          path: '',
          isHover: false
        }
      ],

      // 菜单
      menuConfigs: [
        { path: '/manage/userinfo', iconName: 'home-user', label: '个人中心' },
        {
          path: '/manage/personalized',
          iconName: 'home-setting',
          label: '个性化设置'
        },
        {
          path: '/manage/changepassword',
          iconName: 'home-lock',
          label: '修改密码'
        },
        { path: '/login', iconName: 'home-exit', label: '退出登录' }
      ],

      // 工作台
      statiscticsData: [
        { key: 'notRun', label: '未运行任务', value: '' },
        { key: 'running', label: '正在执行', value: '' },
        { key: 'failure', label: '运行失败', value: '' },
        { key: 'success', label: '运行成功', value: '' }
      ]
    }
  },

  computed: {
    ...mapGetters('user', ['userInfo', 'workspaceCreateAuth']),
    refreshTime() {
      const min = (this.curRefreshTime - this.lastRefreshTime) / 60000
      return Math.floor(min)
    }
  },

  watch: {
    refreshTime(nVal) {
      // 工作台的显示数据，默认一个小时更新一次
      if (nVal >= 60) {
        this.getStatisticsData()
      }
    }
  },

  created() {
    this.getStatisticsData()
    this.updateRefreshTime()
  },

  methods: {
    ...mapActions('user', ['logoutAction']),

    mixinChangeWorkspaceId() {
      this.getStatisticsData()
    },

    handleJumpClick(path) {
      if (path === '/login') {
        this.$confirm('是否退出登录?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.logoutAction()
            .then(() => {
              this.$router.replace('/login')
            })
            .catch(() => {})
        })
      } else {
        this.$router.push(path)
      }
    },

    handleFuncClick(item) {
      const { path } = item
      if (path) {
        this.$router.push(path)
      } else {
        this.$message.info('该模块功能暂未上线， 敬请期待！')
      }
    },

    handleDownload() {
      this.downloadLoading = true
      const filename =
        '一站式大数据开发与治理平台（iCredit）用户手册V0.0.1版本.pdf'
      const url = '../../static/intro.pdf'
      const link = document.createElement('a')
      link.href = url
      link.download = filename
      link.target = '_blank'
      document.body.appendChild(link)
      // link.click()
      window.open(link.href)
      document.body.removeChild(link)

      setTimeout(() => {
        this.downloadLoading = false
      }, 500)
    },

    updateRefreshTime() {
      this.timerId = setInterval(() => {
        this.curRefreshTime = new Date().getTime()
      }, 1000)
    },

    // 获取工作台统计信息
    getStatisticsData() {
      this.dataLoading = true
      this.lastRefreshTime = new Date().getTime()
      API.getHomeSpaceInfo({ id: this.workspaceId })
        .then(({ success, data }) => {
          if (success) {
            this.statiscticsData = deepClone(this.statiscticsData).map(
              ({ key, label }) => {
                return {
                  key,
                  label,
                  value: data[key]
                }
              }
            )
          }
        })
        .finally(() => {
          this.dataLoading = false
        })
    }
  }
}
</script>

<style lang="scss" scoped>
.home {
  width: 100%;
  margin-top: 16px;
  overflow-x: hidden;
  min-height: calc(100vh - 96px);

  &-left {
    @include flex(column, space-between, flex-start);
    width: calc(100% - 300px);
    height: 100%;
    background-color: #fff;
    margin-right: 6px;

    &-gif {
      width: 100%;
      height: calc(100% - 205px);

      .title {
        height: 40px;
        font-size: 28px;
        font-family: PingFangSC, PingFangSC-Medium;
        font-weight: 500;
        text-align: left;
        color: #333;
        line-height: 40px;
        margin: 24px;
      }

      .img {
        @include flex;
        height: 100%;
      }
    }

    &-func {
      @include flex(column, flex-start, flex-start);
      width: 100%;
      height: 120px;

      .title {
        position: relative;
        height: 20px;
        font-size: 14px;
        font-family: PingFangSC, PingFangSC-Medium;
        font-weight: 500;
        color: #262626;
        line-height: 20px;
        margin-left: 13px;
        margin-bottom: 16px;

        &::before {
          content: '';
          position: absolute;
          top: 0;
          left: -13px;
          width: 4px;
          height: 18px;
          background: #1890ff;
          border-radius: 0 2px 2px 0;
        }
      }

      .func-wrap {
        @include flex;
        margin-left: 13px;

        &-item {
          @include flex;
          width: 130px;
          height: 50px;
          color: #000;
          font-size: 16px;
          cursor: pointer;
          font-family: PingFangSC, PingFangSC-Regular;
          font-weight: 400;
          background: #f5f9fd;
          border-radius: 4px;
          margin-right: 20px;

          .jsvg {
            width: 34px;
            height: 34px;
          }

          .text {
            margin-left: 5px;
            height: 34px;
            line-height: 34px;
          }

          &:hover {
            color: #1890ff;
          }
        }
      }
    }
  }

  &-right {
    width: 300px;
    height: 100%;
    background-color: #fff;

    &-user {
      @include flex(row, flex-start);
      width: 100%;
      height: 50px;
      background-color: #f5f9fd;
      border-radius: 0 0 10px 10px;
      margin-bottom: 24px;

      .user {
        font-size: 16px;
        font-family: PingFangSC, PingFangSC-Semibold;
        font-weight: 600;
        color: #333;
        margin-right: 10px;
        margin-left: 12px;
      }

      .user-admin {
        @include flex;
        color: #faad14;

        .text {
          font-size: 12px;
          font-family: PingFangSC, PingFangSC-Regular;
          font-weight: 400;
          text-align: center;
          color: #faad14;
          margin-left: 2px;
        }
      }
    }

    &-section {
      .title-wrap {
        @include flex(row, space-between);

        .left {
          position: relative;
          height: 20px;
          font-size: 14px;
          font-family: PingFangSC, PingFangSC-Medium;
          font-weight: 500;
          text-align: left;
          color: #262626;
          line-height: 20px;
          margin-left: 13px;

          &::before {
            content: '';
            position: absolute;
            top: 0;
            left: -13px;
            width: 4px;
            height: 18px;
            background: #1890ff;
            border-radius: 0 2px 2px 0;
          }
        }

        .right {
          @include flex;
          margin-right: 12px;

          .icon {
            font-size: 17px;
            cursor: pointer;
            color: #999;
          }

          .text {
            height: 17px;
            line-height: 17px;
            font-size: 12px;
            font-family: PingFangSC, PingFangSC-Medium;
            font-weight: 500;
            text-align: left;
            color: #999;
            margin-left: 6px;
          }
        }
      }

      .container {
        // @include flex;

        &-item {
          @include flex(column, flex-start);
          display: inline-flex;
          width: 50%;

          .count-wrap {
            margin-top: 24px;

            .count {
              height: 33px;
              font-size: 24px;
              font-family: PingFangSC, PingFangSC-Medium;
              font-weight: 500;
              color: #1890ff;
              line-height: 33px;
              margin-right: 5px;
            }
          }

          .label {
            font-size: 14px;
            font-family: PingFangSC, PingFangSC-Regular;
            font-weight: 400;
            color: #666;
            margin-top: 5px;
          }
        }

        &-row {
          flex-direction: row;
          align-items: center;
          padding: 13px 0;
          padding-left: 13px;

          &:hover {
            .label,
            .icon {
              color: #1890ff;
            }
          }

          .label {
            color: #262626;
            margin-left: 5px;
            cursor: pointer;
          }

          .icon {
            display: inline-block;
            cursor: pointer;
            font-size: 14px;
            color: #333;
            padding-top: 3px;
          }
        }
      }

      .divider {
        width: calc(100% - 24px);
        margin: 24px 12px;
        height: 1px;
        background-color: #d9d9d9;
      }
    }
  }
}
</style>
