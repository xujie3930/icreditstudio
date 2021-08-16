<template>
  <el-submenu
    ref="subMenu"
    :index="handlePath(item.url)"
    :popper-append-to-body="false"
  >
    <template slot="title">
      <!-- TODO icon 待修改为 字体图标和svg均支持 -->
      <i :class="item.iconPath"></i>
      <span>{{ item.name }}</span>
    </template>
    <slot/>
  </el-submenu>
</template>

<script>
import { isExternal } from '@/utils/util';
import path from 'path';

export default {
  name: 'SideBarSubMenu',
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
    }
  }
};
</script>
