<!--
 * @Author: lizheng
 * @Description: 图
 * @Date: 2021-09-06
-->
<template>
  <div class="figure">
    <div :class="['figure-wrap', onlyOneTable ? '' : 'one-table']">
      <!-- 第一张表 -->
      <div class="figure-box">
        <el-tooltip :content="dataSource[0].leftSource" placement="bottom">
          <span class="name"> {{ dataSource[0].leftSource }}</span>
        </el-tooltip>
        <span class="type">{{ dataSource[0].leftSourceDatabase }}</span>
      </div>

      <template v-if="onlyOneTable">
        <FigureLine
          :icon-name="iconNameMapping(dataSource[0].associatedType)"
        />

        <!-- 第二张表 -->
        <div class="figure-box">
          <el-tooltip :content="dataSource[0].rightSource" placement="bottom">
            <span class="name">{{ dataSource[0].rightSource }}</span>
          </el-tooltip>
          <span class="type">{{ dataSource[0].rightSourceDatabase }}</span>
        </div>
      </template>
      <!-- 第一个关联图标 -->
    </div>

    <!-- 第二个关联图标 -->
    <div class="center-line" v-if="dataSource.length > 1">
      <FigureLine
        class="right"
        :icon-name="iconNameMapping(dataSource[1].associatedType)"
      />
    </div>

    <div class="figure-wrap" v-if="dataSource.length > 1">
      <!-- 第三张表 -->
      <div class="figure-box">
        <el-tooltip :content="dataSource[1].rightSource" placement="bottom">
          <span class="name">{{ dataSource[1].rightSource }}</span>
        </el-tooltip>
        <span class="type">{{ dataSource[1].rightSourceDatabase }}</span>
      </div>

      <!-- 第三个关联图标 -->
      <FigureLine
        v-if="dataSource.length > 2"
        :icon-name="iconNameMapping(dataSource[2].associatedType)"
      />
      <!-- 第四张表 -->
      <div class="figure-box" v-if="dataSource.length > 2">
        <el-tooltip :content="dataSource[2].rightSource" placement="bottom">
          <span class="name">{{ dataSource[2].rightSource }}</span>
        </el-tooltip>

        <span class="type">{{ dataSource[2].rightSourceDatabase }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import { iconMapping } from '@/views/icredit/data-manage/data-sync/contant'
import FigureLine from './line'

export default {
  components: {
    FigureLine
  },

  data() {
    return { iconMapping }
  },

  props: {
    dataSource: {
      type: Array,
      default: () => []
    }
  },

  computed: {
    onlyOneTable() {
      const { conditions, rightSourceDatabase } = this.dataSource[0]
      return Boolean(conditions && rightSourceDatabase)
    }
  },

  methods: {
    iconNameMapping(key) {
      return this.iconMapping[key] ? this.iconMapping[key].icon : undefined
    }
  }
}
</script>

<style lang="scss" scoped>
.figure-wrap {
  @include flex(row, flex-end);
  margin: 30px 20px;
  margin-bottom: 0;

  .figure-box {
    @include flex;
    position: relative;
    width: 150px;
    height: 34px;
    background: #f0f0f0;
    border: 1px solid #1890ff;
    border-radius: 8px;
    font-size: 14px;
    font-family: PingFangSC, PingFangSC-Medium;
    font-weight: 500;
    text-align: center;
    color: #262626;

    .name {
      width: 100%;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      padding: 0 5px;
    }

    .type {
      position: absolute;
      top: -24px;
      left: 0;
    }
  }

  .figure-lines {
    position: relative;
    width: calc(100% - 300px);
  }
}

.one-table {
  @include flex;
}

.center-line {
  position: relative;
  width: 100%;
  height: 60px;
  @include flex(row, flex-start);

  .left {
    position: absolute;
    width: 92px;
    top: 45px;
    left: 50px;
    transform: rotate(90deg);
  }

  .right {
    position: absolute;
    width: 92px;
    top: 45px;
    right: 50px;
    transform: rotate(90deg);
  }
}
</style>
