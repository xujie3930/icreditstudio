module.exports = {
  webpackBarName: 'iCredit',
  webpackBanner: 'iframe: https://gitlab.gzspiral.com/iframework',
  donationConsole() {
    const chalk = require('chalk')

    console.log(chalk.green('iCredit 一站式大数据开发与治理前端平台'))

    console.log(
      chalk.green(
        'iframe 文档地址：http://iframe_doc.thed3chain.com:9001/templates'
      )
    )

    console.log(
      chalk.green(
        'j-ui 文档地址：http://iframe_doc.thed3chain.com:9001/components'
      )
    )

    console.log(
      chalk.green(
        '搭配代码生成可完成大部分基础开发工作，地址：http://192.168.0.32:8999'
      )
    )
    console.log('\n')
  }
}
