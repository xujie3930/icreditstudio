<template>
  <el-menu-item
    :index="handlePath(routeChildren.url)"
    @click="handleLink"
  >
    <!-- TODO icon 待修改为 字体图标和svg均支持 -->
    <i :class="routeChildren.iconPath"></i>
    <span>{{ routeChildren.name }}</span>
    <el-tag
      v-if="routeChildren.meta && routeChildren.meta.badge"
      type="danger"
      effect="dark"
    >
      {{ routeChildren.meta.badge }}
    </el-tag>
  </el-menu-item>
</template>

<script>
import { isExternal } from '@/utils/util';
import path from 'path';

export default {
  name: 'SideBarMenu',
  props: {
    routeChildren: {
      type: Object,
      default() {
        return null;
      }
    },
    item: {
      type: Object,
      default() {
        return null;
      }
    },
    fullPath: {
      type: String,
      default: ''
    }
  },
  methods: {
    handlePath(routePath) {
      if (isExternal(routePath)) {
        return routePath;
      }
      if (isExternal(this.fullPath)) {
        return this.fullPath;
      }
      return path.resolve(this.fullPath, routePath);
    },
    handleLink() {
      const routePath = this.routeChildren.url;
      // TODO 后端暂不支持外链配置 后续需添加是否当前窗口打开 是否需要内部显示
      // const { target } = this.routeChildren.meta;

      // if (target === '_blank') {
      //   if (isExternal(routePath)) {
      //     window.open(routePath);
      //   } else if (isExternal(this.fullPath)) {
      //     window.open(this.fullPath);
      //   } else if (
      //     this.$route.path !== path.resolve(this.fullPath, routePath)
      //   ) {
      //     const routeData = this.$router.resolve(
      //       path.resolve(this.fullPath, routePath)
      //     );
      //     window.open(routeData.href);
      //   }
      // } else
      if (isExternal(routePath)) {
        window.location.href = routePath;
      } else if (isExternal(this.fullPath)) {
        window.location.href = this.fullPath;
      } else if (
        this.$route.path !== path.resolve(this.fullPath, routePath)
      ) {
        console.log(this.fullPath)
        console.log(routePath)
        this.$router.push(path.resolve(this.fullPath, routePath));
      }
    }
  }
};
</script>
