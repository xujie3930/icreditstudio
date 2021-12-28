const CompressionWebpackPlugin = require('compression-webpack-plugin')

const IS_PROD = ['production', 'prod'].includes(process.env.NODE_ENV)
const productionGzipExtensions = /\.(js|css|json|txt|html|ico|svg)(\?.*)?$/i

const path = require('path')
const { baseConfig } = require('./src/config')
const { donationConsole } = require('./illustrate.config')

const resolve = dir => path.join(__dirname, dir)

if (baseConfig.illustrate) donationConsole()

module.exports = {
  publicPath: '/',
  outputDir: 'dist',
  indexPath: 'index.html',
  filenameHashing: true,
  // runtimeCompiler: true,

  // 配置scss全局变量
  css: {
    loaderOptions: {
      scss: {
        prependData: `
          @import "@/styles/common/_variables.scss";
          @import "@/styles/common/_mixin.scss";
        `
      }
    }
  },

  // 配置主题色
  // configureWebpack: {
  //   plugins: [
  //     new ThemeColorReplacer({
  //       fileName: 'style/theme-colors.[contenthash:8].css',
  //       matchColor: [...forElementUI.getElementUISeries(config.themeColor)],
  //       changeSelector: forElementUI.changeSelector,
  //       isJsUgly: true
  //     })
  //   ]
  // },
  configureWebpack: config => {
    const plugins = []
    if (IS_PROD) {
      plugins.push(
        new CompressionWebpackPlugin({
          filename: '[path][base].gz',
          algorithm: 'gzip',
          test: productionGzipExtensions,
          threshold: 10240,
          minRatio: 0.8
        })
      )
    }
    // eslint-disable-next-line no-param-reassign
    config.plugins = [...config.plugins, ...plugins]
  },
  // 设置路径别名
  chainWebpack: config => {
    config.resolve.alias
      .set('@', resolve('src'))
      .set('components', resolve('./src/components'))
      .set('config', resolve('./src/config'))
      .set('utils', resolve('./src/utils'))
      .set('mixins', resolve('./src/mixins'))
      .set('config', resolve('./src/config'))

    // js文件最小化处理
    config.optimization.minimize(true)
    config.optimization.splitChunks({
      chunks: 'all'
    })

    // svg-icon loader =================================== start ↓
    config.module
      .rule('svg')
      .exclude.add(resolve('src/assets/icons'))
      .end()

    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('src/assets/icons'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
    // svg-icon loader =================================== end ↑
  },
  devServer: {
    proxy: {
      '/api': {
        target: baseConfig.baseUrl,
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
}
