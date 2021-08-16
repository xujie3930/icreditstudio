<!--
 * @Author: lizheng
 * @Description: 切换主题颜色
 * @Date: 2021-06-23
-->

<template>
  <div class="color">
    <div class="color-wrap" v-for="item in themeConfig" :key="item.name">
      <div class="color-item">
        <span class="color" :style="{ backgroundColor: item.color }" />
        <el-radio
          class="radio"
          v-model="value"
          :label="item.label"
          @change="changeTheme(item.value)"
        >
          <span class="name"> {{ item.name }}</span>
        </el-radio>
      </div>
    </div>
  </div>
</template>

<script>
import THEME_CONFIG from '@/config/theme'
import { deepClone } from '@/utils/util'

export default {
  props: {
    cssId: {
      type: [String],
      default: '1'
    }
  },

  watch: {
    cssId() {
      this.value = this.cssId || '1'
      this.filterThemeConfig()
    }
  },

  data() {
    return {
      value: '1', // 当前主题值
      themeConfig: THEME_CONFIG
    }
  },

  methods: {
    changeTheme(cssId) {
      this.$emit('change-color', cssId)
      this.filterThemeConfig()
    },

    // 修改默认主题名称
    filterThemeConfig() {
      const idx = this.themeConfig.findIndex(
        ({ value }) => value === String(this.value)
      )
      if (idx > -1) {
        console.log(idx, this.themeConfig[idx])
        this.themeConfig = deepClone(THEME_CONFIG)
        const { name } = this.themeConfig[idx]
        this.themeConfig[idx].name = `${name}(默认)`
      }
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/common/_mixin.scss';

.color {
  @include flex;
  .color-wrap {
    @include flex;
    .color-item {
      width: 100%;
      @include flex(column);
      margin: 0 10px;

      .color {
        display: block;
        width: 30px;
        height: 30px;
        border-radius: 2px;
        cursor: pointer;
        background-color: #061178;
      }
      .radio {
        margin-top: 5px;

        /deep/ .el-radio__label {
          padding-left: 0;
        }

        .name {
          font-size: 12px;
        }
      }
    }
  }
}
</style>
