# 金宁汇后台管理系统基础框架

### 配置
* 项目相关配置在`/src/config/index.js`
* 接口地址配置
    - 开发环境 `/src/config/index.js` 中 `baseConfig.baseUrl` (开发环境没有使用`.env.development`避免开发时修改接口地址需要频繁重启项目)
    - 测试环境 `/.env.alpha`
    - 生产环境 `/.env.production`
    - 更多环境请自行添加

### 逻辑
* 登录
    - 点击登录 => 调用 `store/modules/user/loginAction` => 得到并存储 `token`
    - 跳转首页路由 `/` 进入 `router/permission` 执行 `beforeEach`
    - `beforeEach` 的逻辑请自行查看代码; 主流程为: 调用 `store/modules/user/getPermissionListAction` 通过token换取用户信息、用户菜单、用户权限等 => 调用 `store/modules/permission/updateAppRouterAction` 更新主路由 => 跳转
    - 进入动态配置的首页

### JSvg
自动注册svg图标
* 使用方式
    1. 在`/src/assets/icons` 中添加svg图
    2. 使用 `<j-svg name=""></j-svg>` ; `name` 即为对应文件名称
  ```
  .
  ├── src
  │  ├── assets
  │     ├── icons
  │     │     ├── admin.svg

  <j-svg name="admin"></j-svg>
  ```

### git hooks
* **`git commit` 会执行 `npm run lint`**
* 请确保代码符合eslint校验规则，否则无法执行 `git commit`
