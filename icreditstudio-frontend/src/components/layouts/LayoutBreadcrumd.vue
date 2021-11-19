<!--
 * @Description: 头部导航栏面包屑
 * @Date: 2021-08-09
-->
<template>
  <div class="iframe-header-breadcrumb">
    <Back
      v-if="canBackPages.includes($route.path)"
      @on-jump="handleBackClick"
    />

    <el-breadcrumb
      v-else-if="curBreadcrumb.length"
      separator-class="el-icon-arrow-right"
    >
      <el-breadcrumb-item
        v-for="(item, index) in curBreadcrumb"
        :key="index"
        @click.native="handleCrumbJump(item)"
      >
        <!-- :to="{ path: item.redirect || item.path }" -->
        {{ item.label || item.name }}
      </el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
import Back from '@/components/back'
import { canBackPages } from '@/config/menu'

export default {
  components: { Back },

  data() {
    return {
      // 需要返回的页面
      canBackPages,
      backPathMapping: {
        '/data-manage/data-schedule/dag': '/data-shcedule/cycle-task',
        '/workspace/data-model/add': '/workspace/data-model',
        '/data-quality/rule-category/add-rules':
          '/data-quality/rule-category/quality-rules'
      },
      backHome: [
        '/manage/userinfo',
        '/manage/personalized',
        '/manage/changepassword'
      ]
    }
  },

  props: {
    curBreadcrumb: {
      type: Array,
      default: () => []
    }
  },

  methods: {
    // 返回
    handleBackClick() {
      const { path, query } = this.$route
      if (path === '/workspace/detail') {
        if (query.id || query.opType === 'view') {
          this.$router.replace('/workspace/space-setting')
        } else {
          this.$confirm('该工作空间内容尚未提交，请确认是否返回?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
            .then(() => {
              this.$router.replace('/workspace/space-setting')
            })
            .catch(err => {
              console.log(err, 'err')
            })
            .finally(() => {})
        }
      } else if (path === '/data-manage/data-schedule/dag') {
        this.$router.replace('/data-shcedule/cycle-task')
      } else if (path === '/workspace/data-model/add') {
        this.$router.replace('/workspace/data-model')
      } else if (path === '/data-quality/rule-category/add-rules') {
        this.$router.replace('/data-quality/rule-category/quality-rules')
      } else if (this.backHome.includes(path)) {
        this.$router.replace('/home')
      } else {
        this.$ls.remove('taskForm')
        this.$ls.remove('selectedTable')
        this.$router.push('/data-manage/data-sync')
      }
    },

    handleCrumbJump(toMenu) {
      console.log(toMenu, 'koko')
      this.$emit('jump', toMenu)
    }
  }
}
</script>

<style lang="scss" scoped>
.iframe-header-breadcrumb {
  margin: 13px 12px;
  font-size: 14px;
  font-family: PingFangSC, PingFangSC-Regular;
  font-weight: 400;
  text-align: left;
  color: #8c8c8c;
  height: 20px;
  line-height: 20px;

  ::v-deep {
    .el-breadcrumb__item {
      .el-breadcrumb__inner {
        color: #8c8c8c;
        font-weight: 400;
        cursor: pointer;

        &:hover {
          color: #1890ff;
        }
      }

      &:last-child {
        .el-breadcrumb__inner {
          color: #262626;
          font-weight: 400;

          &:hover {
            color: #262626;
          }
        }
      }
    }
  }
}
</style>
