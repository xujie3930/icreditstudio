<!--
 * @Description: Table-无限下拉加载数据
 * @Date: 2021-10-26
-->
<template>
  <!-- v-loadmore="loadMore" -->
  <JTable
    ref="table"
    v-loading="tableLoading"
    :table-configuration="tbConfig"
    :table-data="tableTable"
  />
</template>

<script>
export default {
  data() {
    return {
      instance: null,
      count: 0,
      tbConfig: {},
      tableTable: [
        { fieldName: 'ssd' },
        { fieldName: 'ssd' },
        { fieldName: 'ssd' },
        { fieldName: 'ssd' },
        { fieldName: 'ssd' },
        { fieldName: 'ssd' },
        { fieldName: 'ssd' },
        { fieldName: 'ssd' },
        { fieldName: 'ssd' },
        { fieldName: 'ssd' }
      ],
      tableLoading: false
    }
  },

  props: {
    tableConfiguration: {
      type: Object,
      default: () => ({}),
      require: true
    }

    // tableData: {
    //   type: Array,
    //   default: () => [],
    //   require: true
    // }
  },

  mounted() {
    // window.onscroll = () => {
    //   this.loadMore()
    // }
    this.tbConfig = this.tableConfiguration
    this.loadMore()
  },

  destroyed() {
    this.instance.onscroll = null
  },

  methods: {
    loadMore() {
      const bottom = 10
      const that = this
      this.instance = this.$refs.table.$el.querySelector(
        '.el-table__body-wrapper'
      )
      const { scrollHeight, scrollTop, clientHeight } = this.instance
      this.instance.addEventListener('scroll', () => {
        if (scrollHeight - scrollTop - clientHeight < bottom) {
          // 调用数据接口
          this.tableLoading = true
          that.getData()
        }
      })
    },

    getData() {
      console.log('req')
      this.tbConfig = Object.assign(this.tableConfiguration, { append: true })
      if (this.tableTable.length < 100) {
        this.tableTable.push({ fieldName: 'oooo' })
      } else {
        this.tbConfig = Object.assign(this.tableConfiguration, {
          isMore: false
        })
      }

      setTimeout(() => {
        this.tableLoading = false
      }, 300)
    }
  }
}
</script>
