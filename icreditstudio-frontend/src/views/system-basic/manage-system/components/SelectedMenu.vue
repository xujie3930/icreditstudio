<!--
 * @Author: lizheng
 * @Description: 选择菜单弹窗
 * @Date: 2021-06-18
-->

<template>
  <el-dialog title="快捷菜单选择" width="500px" :close-on-click-modal="false" :visible.sync="visible">
    <h3 class="title">请选择菜单</h3>
    <div class="menu-wrap">
      <div class="all">
        <el-tree
          ref="allMenu"
          node-key="id"
          show-checkbox
          :data="allMenuData"
          :props="defaultProps"
          check-strictly
          default-expand-all
          :expand-on-click-node="false"
          check-on-click-node
          @check="handleCheck"
        >
        </el-tree>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
<!--      <el-button type="primary" @click="checkAllNodes">全部勾选（取消）</el-button>-->
      <el-button type="primary" @click="handleChooseTree">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { mapGetters } from 'vuex'
import { deepClone, treeToArray } from 'utils/util'

export default {
  data() {
    return {
      visible: false,
      allMenuData: [],
      // selectedMenu: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      allNodesIds: [],
      isCheckedAll: true,
      checkedNodes: []
    }
  },

  computed: {
    ...mapGetters({
      permissionList: 'user/permissionList'
    })
  },

  methods: {
    openModal({ visible, initCheckTreeNode, selectedNodeIds }) {
      this.visible = visible
      this.isCheckedAll = true
      this.allMenuData = this.handleChildren(this.permissionList)
      this.allNodesIds = treeToArray(this.allMenuData, 'children', 'id')
      this.$nextTick(() => {
        initCheckTreeNode && this.$refs.allMenu.setCheckedKeys(selectedNodeIds)
      })
    },

    handleChildren(childNode) {
      return deepClone(childNode)
        .map(({ id, name, children }) => {
          return {
            id,
            name,
            children: children?.length ? this.handleChildren(children) : []
          }
        })
    },

    handleCheck(treeObj, selectedTreeObj) {
      const { checkedNodes } = selectedTreeObj
      this.checkedNodes = checkedNodes
      // this.$emit('selectedMenu', checkedNodes)
    },
    // checkAllNodes() {
    //   const ids = this.isCheckedAll ? this.allNodesIds : []
    //   this.$refs.allMenu.setCheckedKeys(ids)
    //   this.isCheckedAll = !this.isCheckedAll
    // },
    handleChooseTree() {
      this.$emit('selectedMenu', this.checkedNodes)
      this.visible = false
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/common/_mixin.scss';
/deep/.el-dialog__body{
  padding: 10px 20px
}
.title {
  font-weight: 700;
  margin-bottom: 20px;
}
.menu-wrap {
  height:450px;
  overflow-y: scroll;

  .all{
    margin: 0 20px;
  }
}
</style>
