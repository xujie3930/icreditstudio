<!--
 * @Author: lizheng
 * @Description: 系统设置 - 个性化设置页面
 * @Date: 2021-06-17
-->

<template>
  <div class="params-wrap" :loading="loading">
    <div class="params-wrap-content">
      <!-- 基本设置 -->
      <div class="base">
        <div class="base-label">基本设置：</div>
        <div class="base-form">
          <el-row class="row">
            <el-col :span="12">
              <div class="base-form-item">
                <span class="label">我的字号:</span>
                <ChangeFont
                  :fontSize="fontSize"
                  @change-font="changeFontSize"
                />
              </div>
            </el-col>
          </el-row>
        </div>
      </div>

      <!-- 主题设置 -->
      <div class="theme">
        <div class="base-label">主题设置：</div>
        <div class="base-form">
          <!-- 此版本隐藏 下版本开放
          <el-row class="row">
            <el-col>
              <div class="base-form-item">
                <span class="label">布局方式:</span>
                <div class="layout">
                  <div class="layout-item">
                    <div class="box mixin">
                      <div class="box-inner"></div>
                    </div>
                    <el-radio label="1">混合菜单</el-radio>
                  </div>
                  <div class="layout-item">
                    <div class="box left">
                      <div class="box-left"></div>
                      <div class="box-top"></div>
                      <div class="box-inner"></div>
                    </div>
                    <el-radio label="2">左侧菜单</el-radio>
                  </div>
                  <div class="layout-item">
                    <div class="box top">
                      <div class="box-left"></div>
                      <div class="box-top"></div>
                      <div class="box-inner"></div>
                    </div>
                    <el-radio label="3">顶部菜单</el-radio>
                  </div>
                </div>
              </div>
            </el-col>
          </el-row>
          -->
          <el-row class="row">
            <el-col>
              <div class="base-form-item">
                <span class="label">我的主题:</span>
                <ChangeColor :cssId="cssId" @change-color="changeTheme" />
              </div>
            </el-col>
          </el-row>
        </div>
      </div>

      <!-- 其他参数设置 -->
      <div class="business-wrap">
        <div class="business">
          <div class="base-label">快捷菜单设置：</div>
          <div class="base-form">
            <el-button type="primary" @click="handleOpenModal">
              请单击选择快捷菜单
            </el-button>
            <div class="selected-menu" v-if="selectedMenu.length">
              <div class="label">已选菜单项:</div>
              <div class="menu-item">
                {{ selectedMenu.map(({ name }) => name).join('、') }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <footer class="footer">
      <el-button type="primary" :loading="btnLoading" @click="handleConfirm">
        确认
      </el-button>
      <el-button class="btn" @click="handleCancel">重置</el-button>
    </footer>

    <SelectedMenu ref="selectedMenu" @selectedMenu="getSelectedMenu" />
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import ChangeFont from './components/ChangeFont.vue'
import ChangeColor from './components/ChangeColor.vue'
import SelectedMenu from './components/SelectedMenu.vue'
import {
  queryPersonalizedSettings,
  savePersonalizedSettings,
  updatePersonalizedSettings
} from '@/api/system'
import { font, theme } from '@/utils/theme'
import { DEFAULT_FONT_SIZE, DEFAULT_CSS_ID } from '@/config/constant'

export default {
  components: { ChangeFont, ChangeColor, SelectedMenu },

  data() {
    return {
      id: '',
      form: '',
      fontSize: '',
      cssId: '',
      otherParamsConfig: [],
      loading: false,
      isUpdate: false,
      btnLoading: false,
      selectedMenu: []
    }
  },

  computed: {
    ...mapGetters({
      userInfo: 'user/userInfo'
    })
  },

  mounted() {
    this.init()
  },

  beforeRouteLeave(to, form, next) {
    this.handleCancel()
    next()
  },

  methods: {
    ...mapActions('user', ['getPermissionListAction']),

    init() {
      this.loading = true
      queryPersonalizedSettings().then(({ data }) => {
        this.loading = false
        if (data) {
          this.isUpdate = true
          const { fontSize, cssId, id, menus } = data
          this.fontSize = fontSize || DEFAULT_FONT_SIZE
          this.cssId = cssId || DEFAULT_CSS_ID
          this.id = id
          this.selectedMenu = menus.map(e => ({ id: e.id, name: e.name }))
          font(fontSize || DEFAULT_FONT_SIZE)
          theme(cssId || DEFAULT_CSS_ID)
        }
      })
    },

    // 更改字体字号
    changeFontSize(fontSize) {
      this.fontSize = fontSize
      font(fontSize)
    },

    // 更改主题颜色
    changeTheme(cssId) {
      this.cssId = cssId
      theme(cssId)
    },

    // 快捷菜单设置
    getSelectedMenu(selectedMenu) {
      console.log('selectedMenu', selectedMenu)
      this.selectedMenu = selectedMenu
    },

    // 打开快捷菜单弹窗
    handleOpenModal() {
      const options = {
        visible: true,
        initCheckTreeNode: true,
        selectedNodeIds: this.selectedMenu.map(e => e.id) || []
      }
      this.$refs.selectedMenu.openModal(options)
    },

    // 接口参数处理
    handleParams() {
      const obj = {
        id: this.id,
        userId: this.userInfo.id,
        fontSize: this.fontSize,
        cssId: this.cssId,
        layout: '',
        resourceIds: this.selectedMenu.map(x => x.id)
      }
      return obj
    },

    // 接口响应处理
    handleResponse(success, data, msg) {
      if (success && data) {
        this.$message.success(msg)
        this.init()
        this.getPermissionListAction()
      }
    },

    // 个性化设置新增
    handleSave(params) {
      savePersonalizedSettings(params)
        .then(({ success, data }) => {
          this.handleResponse(success, data, '设置成功！')
        })
        .finally(() => {
          this.btnLoading = false
        })
    },

    // 个性化设置修改
    handleUpdate(params) {
      updatePersonalizedSettings(params)
        .then(({ success, data }) => {
          this.handleResponse(success, data, '个性化设置更新成功！')
        })
        .finally(() => {
          this.btnLoading = false
        })
    },

    handleConfirm() {
      // 更新操作以及保存操作
      const methodName = `handle${this.isUpdate ? 'Update' : 'Save'}`
      this.btnLoading = true
      this[methodName](this.handleParams())
    },

    handleCancel() {
      // 取消时根据userInfo中的个性化配置参数重置组件状态
      const { fontSize, cssId } = this.userInfo
      this.fontSize = fontSize
      this.cssId = cssId
      font(fontSize)
      theme(cssId)
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/common/_mixin.scss';

.params-wrap {
  min-height: calc(100vh - 170px);
  background-color: #fff;

  &-content {
    .base {
      @include flex(row, flex-start);
      padding: 0 0 20px 20px;
      margin-bottom: 20px;
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

      &-form {
        width: calc(100% - 150px);

        &-item {
          @include flex(row, flex-start);

          .label {
            min-width: 70px;
            display: inline-block;
            margin-right: 10px;
            font-size: 14px;
          }
        }

        .selected-menu {
          @include flex(wrap, flex-start);
          margin-top: 20px;

          .label {
            margin-right: 10px;
            width: 100px;
          }
          .menu-item {
            text-align: left;
            width: 80%;
            line-height: 2;
          }
        }

        .row {
          margin: 20px 0;
        }
      }

      &-label {
        width: 112px;
        margin-right: 30px;
        font-size: 16px;
      }
    }

    .theme {
      @extend .base;
      padding-bottom: 0;

      .layout {
        @include flex;
        margin-left: 9px;

        &-item {
          @include flex(column);
          margin-right: 25px;

          /deep/ .el-radio__label {
            padding-left: 0;
          }

          .box {
            position: relative;
            width: 60px;
            height: 60px;
            margin-bottom: 10px;
            box-sizing: border-box;

            &-inner {
              position: absolute;
              right: 0;
              bottom: 0;
              width: 35px;
              height: 35px;
              box-sizing: border-box;
              border-left: 1px solid #d9d9d9;
              border-top: 1px solid #d9d9d9;
              background: linear-gradient(
                180deg,
                rgba(255, 255, 255, 1) 0%,
                rgba(255, 255, 255, 1) 0%,
                rgba(228, 228, 228, 1) 100%,
                rgba(228, 228, 228, 1) 100%
              );
            }

            &-left {
              width: 25px;
              height: 60px;
              background-color: #333;
              box-sizing: border-box;
            }
          }

          .mixin {
            border: 1px solid #d9d9d9;
          }

          .left {
            .box-inner {
              width: 35px;
              border: 1px solid #d9d9d9;
            }

            .box-top {
              position: absolute;
              top: 0;
              right: 0;
              width: 35px;
              height: 25px;
              border: 1px solid #d9d9d9;
              border-bottom: none;
            }
          }

          .top {
            .box-left {
              box-sizing: border-box;
              width: 60px;
              height: 25px;
            }

            .box-top {
              width: 25px;
              height: 35px;
              border: 1px solid #d9d9d9;
              border-right: none;
            }

            .box-inner {
              border: 1px solid #d9d9dd;
            }
          }
        }
      }
    }

    .business-wrap {
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

      .business {
        @extend .base;
        border-bottom: none;
        box-shadow: none;
        padding-top: 20px;
      }
    }
  }

  .footer {
    width: 100%;
    text-align: right;

    .btn {
      margin: 20px;
    }
  }
}
</style>
