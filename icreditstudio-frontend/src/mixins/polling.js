export default {
  data() {
    return {
      timerId: null
    }
  },

  methods: {
    // 清空定时器
    handleClearInterval() {
      this.timerId && clearInterval(this.timerId)
    },

    // 轮询查询表格数据
    polling() {
      this.handleClearInterval()
      if (!this.mixinRetrieveTableData) return
      this.timerId = setInterval(() => {
        this.mixinRetrieveTableData()
      }, 5000)
    }
  }
}
