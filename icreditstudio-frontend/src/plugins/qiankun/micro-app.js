/*
 * @Author: lizheng
 * @Description: 子应用入口参数配置
 * @Date: 2022-01-10
 */
export default [
  {
    name: 'icreditstudio-data-sync',
    entry: '//localhost:3333',
    activeRule: '/sub-app/data-sync/',
    container: '#subapp-container',
    loader: loading => {
      console.log('loading', loading)
    }
    // props: {}
  }
]
