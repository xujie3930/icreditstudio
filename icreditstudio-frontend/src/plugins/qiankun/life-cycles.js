/*
 * @Author: lizheng
 * @Description: 乾坤全局的微应用生命周期钩子
 * @Date: 2022-01-10
 */
// TODO Loading

export default {
  beforeLoad: [
    app =>
      console.log('[LifeCycle] before load %c%s', 'color: green;', app.name)
  ],

  beforeMount: [
    app =>
      console.log('[LifeCycle] before mount %c%s', 'color: green;', app.name)
  ],

  afterMount: app =>
    console.log('[LifeCycle] before load %c%s', 'color: green;', app.name)
}
