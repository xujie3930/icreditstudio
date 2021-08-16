// 导入组件
import TransferTable from './src/TransferTable'
// 为组件提供 install 安装方法，供按需引入
TransferTable.install = function (Vue) {
  Vue.component(TransferTable.name, TransferTable)
}
// 默认导出组件
export default TransferTable
