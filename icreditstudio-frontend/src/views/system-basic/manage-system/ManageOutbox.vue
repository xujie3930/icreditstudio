<template>
  <div class="h100">
    <crud-basic
      ref="crud"
      title="发件列表"
      :form-items-search="mixinSearchFormItems"
      :form-func-search="mixinSearchFormFunc"
      :form-config-search="mixinSearchFormConfig"
      :form-items-dialog="mixinDialogFormItems"
      :form-func-dialog="mixinDialogFormFunc"
      :form-config-dialog="mixinDialogFormConfig"
      :tableLoading="mixinTableLoading"
      :table-configuration="tableConfiguration"
      :table-pagination="mixinTablePagination"
      :table-data="mixinTableData"
      :dialog-type="mixinDialogType"
      :dialog-visible.sync="mixinDialog"
      :handleSizeChange="mixinHandleSizeChange"
      :handleCurrentChange="mixinHandleCurrentChange"
      :handleAdd="mixinHandleAdd"
      :handleImport="mixinHandleImport"
      :handleExport="mixinHandleExport"
      :handleSearch="mixinHandleSearch"
      :handleReset="mixinHandleReset"
      :handleUpdate="mixinHandleCreateOrUpdate"
      :handleCancel="mixinHandleCancel"
    ></crud-basic>
    <el-dialog
      title="配置用户"
      :close-on-click-modal="false"
      :visible.sync="userSetDialogFlag"
      top="10vh"
      width="1346px"
    >
      <div id="userSetScroll" class="tree-choose-transfer-box">
        <div class="left">
          <div class="tree-desc">
            <span>请选择部门</span>
          </div>
          <div class="left-tree">
            <el-tree
              ref="orgTree"
              :key="userSetDialogFlag"
              :data="userSetModels.tree.orgTreeData"
              default-expand-all
              node-key="id"
              show-checkbox
              check-strictly
              :props="userSetModels.tree.defaultOrgProps"
              :expand-on-click-node="false"
              check-on-click-node
              @check="orgHandleCheck"
            >
              <span class="custom-tree-node" slot-scope="{ node }">
                <span :title="node.label" class="org-tree-label">{{
                  node.label
                }}</span>
              </span>
            </el-tree>
          </div>
        </div>
        <div class="right">
          <j-transfer-table
            ref="userTransfer"
            :titles="['请选择人员', '已选人员']"
            :table-loading="tableLoading"
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
        <el-button type="primary" @click="userSetHandleChoose">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import crud from '@/mixins/crud'
import { deepClone, arrayToTree } from '@/utils/util'
import { mapGetters } from 'vuex'
import { queryAllOrgs, getUserInfosByOrgIds } from '@/api/user'
import userSetTableConfiguration from '@/views/system-basic/configuration/table/manage/manage-user-set'
import tableConfiguration from '@/views/system-basic/configuration/table/manage/manage-outbox'
import formOption from '@/views/system-basic/configuration/form/manage/manage-outbox'
import JTransferTable from '@/components/transfer-table'

const INFO_TYPE_ENUMS = {
  W: '预警消息',
  N: '通知',
  S: '系统消息'
}
export default {
  name: 'ManageMessage',
  mixins: [crud],
  components: { JTransferTable },
  data() {
    return {
      formOption: formOption(this, { INFO_TYPE_ENUMS }),
      mixinSearchFormConfig: {
        models: {
          infoTitle: '',
          receiverName: '',
          infoType: '',
          operateTime: ''
        }
      },
      mixinDialogFormConfig: {
        models: {
          infoTitle: '',
          infoType: '',
          operateTime: '',
          infoContent: '',
          receiverNames: '',
          receiverIds: []
        },
        rule: {
          infoTitle: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ],
          infoType: [
            { required: true, message: '消息类型不能为空', trigger: 'blur' }
          ]
        }
      },
      tableConfiguration: tableConfiguration(this, { INFO_TYPE_ENUMS }),
      fetchConfig: {
        retrieve: {
          url: '/system/information/information/outBoxPage',
          method: 'post'
        },
        create: {
          url: '/system/information/information/send',
          method: 'post'
        },
        delete: {
          url: '/system/information/information/deleteOutBoxInfo',
          method: 'post'
        }
      },
      // 配置接收人相关 start↓
      tableLoading: false,
      userSetDialogFlag: false,
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
          tableLoading: false,
          filterConfig: {
            placeholder: '请输入人员姓名查询',
            prop: 'userName'
          },
          leftTableConfiguration: userSetTableConfiguration,
          leftData: [],
          rightData: [],
          existRightData: []
        }
      }
      // 配置接收人相关 end↑
    }
  },
  computed: {
    ...mapGetters({
      userInfo: 'user/userInfo'
    })
  },
  created() {
    this.mixinSearchFormItems = deepClone(this.formOption).filter(
      e => e.isSearch
    )
    queryAllOrgs().then(res => {
      this.userSetModels.tree.orgTreeData = arrayToTree(
        res.data.map(e => {
          return {
            label: e.orgName,
            ...e,
            disabled: e.operateFlag !== '1'
          }
        }),
        '0'
      )
    })
    this.mixinRetrieveTableData()
  },
  methods: {
    interceptorsRequestRetrieve(params) {
      const { operateTime, ...restParams } = params
      const newParams = {
        startTime: operateTime && operateTime.length ? operateTime[0] : '',
        endTime:
          operateTime && operateTime.length
            ? operateTime[1] + 24 * 60 * 60 * 1000 - 1
            : '',
        ...restParams
      }
      return newParams
    },
    interceptorsRequestCreate(params) {
      const {
        userSetModels: {
          transfer: { existRightData }
        }
      } = this
      return {
        ...params,
        senderId: this.userInfo.id || '',
        receiverIds: existRightData.map(x => x.id)
      }
    },
    interceptorsResponseTableData(data) {
      return data.map(e => {
        return {
          ...e,
          sendTime: parseInt(e.sendTime),
          receiverNames: Array.isArray(e.receiverNames)
            ? e.receiverNames.join(';')
            : e.receiverNames
        }
      })
    },
    // 配置接收用户相关代码 start ↓
    // 配置接收用户
    _handleChooseReceiver() {
      this.userSetDialogFlag = true
      this.$nextTick(() => {
        this.$refs.userTransfer.init()
      })
      const {
        userSetModels: {
          transfer: { leftData, rightData }
        }
      } = this
      let {
        userSetModels: {
          transfer: { existRightData }
        }
      } = this
      if (!this.mixinDialogFormConfig.models.receiverNames) {
        existRightData = []
      }
      leftData.splice(0, leftData.length)
      rightData.splice(0, rightData.length, ...existRightData)
    },
    queryUserLeftTableDataByIds(checkedKeys) {
      this.tableLoading = true
      getUserInfosByOrgIds({
        orgIds: checkedKeys
      })
        .then(res => {
          const {
            userSetModels: {
              transfer: { leftData, rightData }
            }
          } = this
          const existIds = rightData.map(x => x.id)
          // 穿梭框leftTable赋值(排除右边已有项)
          leftData.splice(
            0,
            leftData.length,
            ...res.data.filter(x => !existIds.includes(x.id))
          )
        })
        .finally(() => {
          this.tableLoading = false
        })
    },
    orgHandleCheck(nodeObj, selectObj) {
      const { checkedKeys } = selectObj
      if (checkedKeys.length === 0) {
        const {
          userSetModels: {
            transfer: { leftData }
          }
        } = this
        leftData.splice(0, leftData.length)
      } else {
        // 根据部门ID集合，查询各部门用户列表
        this.queryUserLeftTableDataByIds(checkedKeys)
      }
    },
    userSetHandleClose() {
      this.$refs.orgTree.setCheckedKeys([])
      this.userSetDialogFlag = false
      // this.mixinDialogFormConfig.models.receiverIds = []
    },
    userSetHandleChoose() {
      const {
        userSetModels: {
          transfer: { rightData, existRightData }
        }
      } = this
      existRightData.splice(0, existRightData.length, ...rightData)
      this.mixinDialogFormItems.find(
        e => e.ruleProp === 'receiverNames'
      ).model = rightData.map(e => e.userName).join(';')
      // this.mixinDialogFormConfig.models.receiverName = rightData.map(e => e.userName).join(';')
      // this.mixinDialogFormConfig.models.receiverIds = rightData.map(e => e.id)
      this.userSetHandleClose()
    }
    // 配置接收用户相关代码 end ↑
  }
}
</script>
<style scoped lang="scss">
/*配置接收用户弹框相关*/
.tree-choose-transfer-box {
  /*background: rgba(249, 249, 249, 1);*/
  display: flex;
  justify-content: space-between;
  width: 1195px;
  height: 490px;
  opacity: 1;
  border: 1px solid #d9d9d9;
  border-radius: 10px;
  margin: 0 auto;
  padding: 2px;
  .tree-desc {
    height: 70px;
    width: 100px;
    margin: 0 auto;
    line-height: 70px;
    text-align: center;
    border-bottom: 3px solid #2f54eb;
    span {
      opacity: 1;
      font-size: 18px;
      font-family: PingFangSC, PingFangSC-Medium;
      font-weight: 500;
      text-align: center;
      color: #2f54eb;
      line-height: 25px;
    }
  }
  & .left {
    flex: 1;
    /*border: 1px solid red;*/
    & .left-tree {
      overflow-y: scroll;
      overflow-x: hidden;
      height: calc(100% - 70px);
      width: 100%;
      border-top: 1px solid #e8e8e8;
      & .left-tree-label {
        display: inline-block;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
        /*max-width: 28vw;*/
      }
    }
  }

  & .right {
    margin-left: 10px;
    flex: 3;
    /*border: 1px solid green;*/
    & /deep/ .el-transfer-panel {
      width: auto;
      height: 100%;
    }

    & /deep/ .el-transfer-panel__body {
      /*width: 345px;*/
      width: 40vm;
      height: 440px;

      & .el-transfer-panel__list.is-filterable {
        height: 100%;
      }
    }
  }
}
</style>
