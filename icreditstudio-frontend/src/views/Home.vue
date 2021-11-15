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
          >
            <j-svg class="jsvg" :name="item.icon" />
            <span class="text">{{ item.name }}</span>
          </div>
        </div>
      </div>
    </div>

    <aside class="home-right">
      <header class="home-right-user">
        <h3 class="user">大聪明</h3>
        <div class="user-admin">
          <j-svg class="jsvg" name="menu-datasource" />
          <span class="text">超级管理员</span>
        </div>
      </header>

      <section class="home-right-section">
        <div class="title-wrap">
          <span class="left">工作台</span>
          <div class="right">
            <i class="el-icon-refresh icon"></i>
            <span class="text">455分钟</span>
          </div>
        </div>

        <div class="container">
          <div class="container-item">
            <div class="count-wrap">
              <span class="count">25</span>
              <span class="unit">个</span>
            </div>
            <div class="label">未运行任务</div>
          </div>
          <div class="container-item">
            <div class="count-wrap">
              <span class="count">25</span>
              <span class="unit">个</span>
            </div>
            <div class="label">正在执行</div>
          </div>
          <div class="container-item">
            <div class="count-wrap">
              <span class="count">25</span>
              <span class="unit">个</span>
            </div>
            <div class="label">运行失败</div>
          </div>
          <div class="container-item">
            <div class="count-wrap">
              <span class="count">25</span>
              <span class="unit">个</span>
            </div>
            <div class="label">运行成功</div>
          </div>
        </div>

        <el-divider class="divider"></el-divider>
      </section>

      <section class="home-right-section">
        <div class="title-wrap">
          <span class="left">菜单</span>
        </div>

        <div class="container">
          <div class="container-item container-row">
            <i class="el-icon-user-solid icon"></i>
            <span class="label">个人中心</span>
          </div>
          <div class="container-item container-row">
            <i class="el-icon-s-tools icon"></i>
            <span class="label">个性化设置</span>
          </div>
          <div class="container-item container-row">
            <i class="el-icon-lock icon"></i>
            <span class="label">修改密码</span>
          </div>
          <div class="container-item container-row">
            <i class="el-icon-s-promotion icon"></i>
            <span class="label">退出登录</span>
          </div>
        </div>

        <el-divider class="divider"></el-divider>
      </section>

      <section class="home-right-section">
        <div class="title-wrap">
          <span class="left">实践教程</span>
        </div>

        <div class="container">
          <div class="container-item container-row">
            <i class="el-icon-collection icon"></i>
            <span class="label">用户手册</span>
          </div>
        </div>

        <!-- <el-divider class="divider"></el-divider> -->
      </section>
    </aside>
  </div>
</template>

<script>
export default {
  name: 'Home',

  data() {
    return {
      // 功能演示
      funcConfigs: [
        {
          icon: 'menu-space-active',
          name: '工作空间'
        },
        {
          icon: 'menu-space-active',
          name: '数据管理'
        },
        {
          icon: 'menu-space-active',
          name: '数据治理'
        },
        {
          icon: 'menu-space-active',
          name: '资产管理'
        },
        {
          icon: 'menu-space-active',
          name: 'BI可视化'
        },
        {
          icon: 'menu-space-active',
          name: '数据服务'
        },
        {
          icon: 'menu-space-active',
          name: '运维&安全'
        }
      ]
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
            width: 20px;
            height: 20px;
          }

          .text {
            margin-left: 10px;
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
