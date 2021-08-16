<!--
 * @Description: 工作流开发
 * @Date: 2021-08-06
-->

<template>
  <div class="workflow-wrap">
    <!-- <div class="workflow-tabs">
      <div
        v-for="(item, index) in tabList"
        :key="index"
        class="workflow-tabs-item"
        @click="onTabClick(item)"
        :class="{
          active: item.version
            ? currentVal.name === item.name &&
              currentVal.version === item.version
            : currentVal.name === item.name
        }"
        @mouseenter.self="item.isHover = true"
        @mouseleave.self="item.isHover = false"
      >
        <div :title="item.label" class="workflow-tabs-name">
          {{ item.label }}
        </div>
        <Icon
          v-if="item.close"
          type="ios-close"
          class="workflow-tabs-close"
          @click.stop="onTabRemove(item)"
        />
      </div>
    </div> -->
    <template v-for="(item, index) in tabList">
      <process
        :key="index"
        v-show="
          item.version
            ? currentVal.name === item.name &&
              currentVal.version === item.version
            : currentVal.name === item.name
        "
        :query="item.query"
        @updateWorkflowList="updateWorkflowList"
      >
      </process>
    </template>
  </div>
</template>
<script>
import projectDb from '@/plugins/process/service/db/project.js'
import Process from '@/plugins/process'
// import storage from '@/plugins/process/helper/storage'

export default {
  components: {
    process: Process.component
  },
  data() {
    return {
      indexLabel: h => {
        return h('span', {}, '概览')
      },
      tabList: [
        {
          name: 'index',
          label: '概览',
          isHover: true,
          close: false,
          version: 'index'
        }
      ],
      currentVal: { name: 'index', version: 'index' },
      lastVal: null,
      current: null
    }
  },
  watch: {
    currentVal(val, oldVal) {
      this.lastVal = oldVal
      this.currentVal = val
      this.updateProjectCache()
    },
    '$route.query.projectID': {
      handler() {
        this.current = {
          name: 'index',
          label: '概览',
          isHover: true,
          close: false,
          version: 'index'
        }
        this.tabList = [
          {
            ...this.current
          }
        ]
        this.currentVal = { name: 'index', version: 'index' }
        this.lastVal = null
      }
    }
  },
  mounted() {
    this.getCache()
  },
  methods: {
    openWorkflow(params) {
      const isIn = this.tabList.find(
        item => item.name === params.name && item.version === params.version
      )
      if (!isIn || isIn === -1) {
        this.current = {
          version: params.version,
          flowId: params.flowId,
          label: params.name,
          name: params.name,
          query: params,
          isHover: true,
          close: true
        }
        this.tabList.push(this.current)
      }
      this.lastVal = this.currentVal
      this.currentVal = {
        name: params.name,
        version: params.version
      }
      this.updateProjectCache(params.projectID, true)
    },
    onTabRemove(item) {
      const index = this.tabList.findIndex(
        subitem =>
          item.name === subitem.name && item.version === subitem.version
      )
      this.tabList.splice(index, 1)
      // 判断删除的是否是当前打开的，不是不用动
      if (
        item.name === this.currentVal.name &&
        item.version === this.currentVal.version
      ) {
        this.currentVal = this.lastVal
      }
      this.updateProjectCache(null, true)
    },
    onTabClick(item) {
      this.lastVal = this.currentVal
      this.currentVal = {
        name: item.name,
        version: item.version
      }
      this.current = item
    },
    async getCache() {
      const { query } = this.$route
      if (query && query.projectID) {
        const cache = await projectDb.getProjectCache({
          projectID: query.projectID
        })
        if (cache && Number(query.projectID) === Number(cache.projectID)) {
          this.lastVal = cache.lastVal
          if (cache.tabList) {
            this.currentVal = cache.currentVal
            this.tabList = cache.tabList
            this.current = cache.tabList.find(item => {
              if (this.currentVal.version && item.version) {
                return (
                  item.name === this.currentVal.name &&
                  item.version === this.currentVal.version
                )
              } else {
                return item.name === this.currentVal.name
              }
            })
          } else {
            this.current = {
              name: 'index',
              label: '概览',
              isHover: true,
              close: false,
              version: 'index'
            }
          }
        }
      }
    },
    updateProjectCache(projectID, wantUpadateList) {
      const value = {
        currentVal: this.currentVal,
        lastVal: this.lastVal
      }
      if (wantUpadateList) {
        value.tabList = this.tabList
      }
      // eslint-disable-next-line no-param-reassign
      projectID = projectID || this.$route.query.projectID
      projectDb.updateProjectCache({
        projectID,
        value
      })
    },
    updateWorkflowList() {
      this.$refs.workflow[0].fetchFlowData()
    }
  }
}
</script>
<style lang="scss">
@import '~@/plugins/process/styles/workflow.scss';
</style>
