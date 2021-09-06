<!--
 * @Author: lizheng
 * @Description: 选择负责人穿梭框
 * @Date: 2021-08-31
-->

<template>
  <el-dialog
    title="选择成员"
    top="10vh"
    width="1346px"
    :close-on-click-modal="false"
    :visible.sync="userSetDialogFlag"
    @close="close"
  >
    <div id="userSetScroll" class="tree-choose-transfer-box">
      <div class="left">
        <div class="tree-desc">
          <span>部门树</span>
        </div>
        <div class="left-tree" v-loading="treeLoading">
          <el-tree
            ref="orgTree"
            node-key="id"
            default-expand-all
            show-checkbox
            check-strictly
            check-on-click-node
            :expand-on-click-node="false"
            :key="userSetDialogFlag"
            :data="userSetModels.tree.orgTreeData"
            :props="userSetModels.tree.defaultOrgProps"
            @check="orgHandleCheck"
          >
            <span class="custom-tree-node" slot-scope="{ node }">
              <span :title="node.label" class="org-tree-label">
                {{ node.label }}
              </span>
            </span>
          </el-tree>
        </div>
      </div>
      <div class="right">
        <j-transfer-table
          ref="userTransfer"
          :titles="['待选用户', '已选用户']"
          :table-left-loading="userSetModels.transfer.tableLoading"
          :table-loading="userSetModels.transfer.tableLoading"
          :table-filter-config="userSetModels.transfer.filterConfig"
          :left-table-configuration="
            userSetModels.transfer.leftTableConfiguration
          "
          :left-data.sync="userSetModels.transfer.leftData"
          :right-data.sync="userSetModels.transfer.rightData"
        ></j-transfer-table>
      </div>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="userSetHandleClose">取 消</el-button>
      <el-button type="primary" @click="userSetHandleCreateOrUpdate">
        保 存
      </el-button>
    </span>
  </el-dialog>
</template>

<script>
import crud from '@/mixins/crud'
import userSetTableConfiguration from '@/views/system-basic/configuration/table/manage/manage-user-set'
import JTransferTable from '@/components/transfer-table'
import { queryAllOrgs, getUserInfosByOrgIds } from '@/api/user'
// import { getUserInfoByRoleId } from '@/api/role'
import { arrayToTree } from '@/utils/util'

export default {
  mixins: [crud],
  components: { JTransferTable },

  data() {
    return {
      treeLoading: false,
      userSetDialogFlag: false,
      transferTableLoading: false,
      userSetModels: {
        // 左侧部门树相关
        tree: {
          defaultOrgProps: {
            children: 'children',
            label: 'label'
          },
          filterOrgName: '',
          orgTreeData: []
        },
        // 右侧用户选择穿梭树相关
        transfer: {
          tableLeftLoading: false,
          tableLoading: false,
          filterConfig: {
            placeholder: '请输入用户查询',
            prop: 'userName'
          },
          leftTableConfiguration: userSetTableConfiguration,
          leftData: [],
          rightData: []
        }
      }
    }
  },

  methods: {
    open(opType, rightData) {
      this.userSetDialogFlag = true
      this.fetchAllOrgs()
      if (opType === 'edit') {
        this.userSetModels.transfer.rightData = rightData
        this.$nextTick(() => this.$refs.userTransfer.init())
      }

      // // 获取当前角色的已配置用户数据
      // getUserInfoByRoleId({ roleId })
      //   .then(({ success, data }) => {
      //     if (success) {
      //       // 筛选出部门ids,并通过ids查询所有待选项
      //       this.$nextTick(() => {
      //         this.$refs.orgTree.setCheckedKeys([])
      //         this.$refs.userTransfer.init()
      //       })
      //       const {
      //         userSetModels: {
      //           transfer: { leftData, rightData }
      //         }
      //       } = this
      //       leftData.splice(0, leftData.length)
      //       rightData.splice(0, rightData.length, ...data)
      //       // this.queryUserLeftTableDataByIds(orgIds)
      //     }
      //   })
      //   .finally(() => {
      //     this.transferTableLoading = false
      //   })
    },

    close() {
      this.userSetDialogFlag = false
    },

    // 获取公司组织架构
    fetchAllOrgs() {
      this.treeLoading = true
      queryAllOrgs()
        .then(res => {
          this.userSetModels.tree.orgTreeData = arrayToTree(
            res.data.reduce((pre, cur) => {
              cur.operateFlag === '1' &&
                pre.push({ label: cur.orgName, ...cur })
              return pre
            }, []),
            '0'
          )
        })
        .finally(() => {
          this.treeLoading = false
        })
    },

    // selectTree 选中
    orgHandleCheck(nodeObj, selectObj) {
      const { checkedKeys } = selectObj
      if (!checkedKeys.length) {
        const {
          userSetModels: {
            transfer: { leftData }
          }
        } = this
        leftData.splice(0, leftData.length)
      } else {
        this.queryUserLeftTableDataByIds(checkedKeys)
      }
    },

    // 根据部门ID集合，查询各部门用户列表
    queryUserLeftTableDataByIds(checkedKeys) {
      this.tableLoading = true
      this.userSetModels.transfer.tableLoading = true
      getUserInfosByOrgIds({
        orgIds: checkedKeys,
        deleteFlag: this.mixinUpdate.deleteFlag
      })
        .then(({ data }) => {
          const existIds = this.userSetModels.transfer.rightData.map(x => x.id)
          // 穿梭框leftTable赋值(排除右边已有项)
          this.userSetModels.transfer.leftData.splice(
            0,
            this.userSetModels.transfer.leftData.length,
            ...data.filter(x => !existIds.includes(x.id))
          )
          this.$refs.userTransfer.init()
        })
        .finally(() => {
          this.tableLoading = false
          this.userSetModels.transfer.tableLoading = false
        })
    },

    // 取消
    userSetHandleClose() {
      this.$refs.orgTree.setCheckedKeys([])
      this.userSetDialogFlag = false
      this.$emit('on-cancel', { opType: 'cancel', users: [] })
    },

    // 确认
    userSetHandleCreateOrUpdate() {
      const {
        userSetModels: {
          transfer: { rightData: users }
        }
      } = this
      this.userSetDialogFlag = false
      this.$emit('on-confirm', { opType: 'confirm', users })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/public/user-select';
</style>
