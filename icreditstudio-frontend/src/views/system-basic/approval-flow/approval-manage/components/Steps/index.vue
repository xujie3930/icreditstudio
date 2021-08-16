<template>
  <div class="container">
    <div class="header">
      <span>状态</span>
      <tag :type="formInfo.state">{{ changState(formInfo.state) }}</tag>
    </div>
    <ul class="content">
      <li v-for="(item,index) in items" :key="index" class="content-item">
        <div class="process">
          <img class="avater" :src="item.imgSrc">
          <img v-if="item.nodeStatus === 'PASS'"
               class="success" src="../../assets/img/processThrough.png" alt="">
          <img v-if="item.nodeStatus === 'NOPASS'"
               class="success" src="../../assets/img/noProcessThrough.png" alt="">
          <!--           <span-->
          <!--             v-if="item.activityType !== 'serviceTask'"-->
          <!--             class="name-title"-->
          <!--           >{{ item.groupsIdentityName }}</span> -->
          <div
            v-if="index !== items.length- 1"
            :class="{
              'divider':index + 1 <= items.length && items[index + 1].isHistory,
              'dashed':!(index + 1 <= items.length && items[index + 1].isHistory),
              'has-name':item.groupsIdentityName && item.activityType !== 'serviceTask'}"
          />
        </div>
        <div class="item-card">
          <div class="item-title">
            <div class="title">
              <!--              <span :class="item.activityTypeName === '发起人'?'is-title':''">-->
              <span :class="{ 'is-title': item.activityTypeName === '发起人'}">
                {{ item.activityTypeName }}
              </span>
              <el-button
                v-if="item.hasFullMessage"
                type="text"
                @click="searchDetails(
                  item.dialogMessage
                   ? item.dialogMessage
                   : item.comment[item.comment.length-1]
                   )">
                详情
              </el-button>
            </div>
            <span v-if="item.isHistory" class="date">{{ formatDate(item.updateTime) }}</span>
          </div>
          <div class="item-value">
            <span class="have-width">{{ item.assigneeName }}</span>
            <div v-for="list in item.CCArr" :key="list.id" class="list">
              <el-avatar :size="27"
                         src="../../assets/img/avatar.png"/>
              <img class="list-success" src="../../assets/img/CCSuccessful.png" alt="">
              <span class="list-label">{{ list.label }}</span>
            </div>
            <div
              v-if="item.activityTypeName !== '发起人'
                && item.isHistory
                && item.activityType === 'userTask'"
              class="useDay">用时{{ getUserDay(index) }}天
            </div>
          </div>
          <div class="role_value">
            <span class="name-title">{{ item.groupsIdentityName }}</span>
          </div>
        </div>
      </li>
    </ul>
  </div>

</template>

<script>
import { mapGetters } from 'vuex'
import tag from '../tag'
import avatar from '../../assets/img/avatar.png'
import copy from '../../assets/img/copy.png'
import noApproval from '../../assets/img/noApproval.png'
import { processExamples } from '@/api/system-basic/approval-list'
import dayjs from 'dayjs'

export default {
  name: 'Steps',
  components: {
    tag
  },
  props: {
    state: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      items: []
    }
  },
  computed: {
    ...mapGetters({
      formInfo: 'approval-form/formInfo'
    })
  },
  mounted() {
    this.getProctask()
  },
  methods: {
    searchDetails(row) {
      this.$emit('searchDetails', row)
    },
    changState(val) {
      let result = ''
      switch (val) {
        case 'PASS':
          result = '通过'
          break
        case 'NOPASS':
          result = '不通过'
          break
        case 'RECALL':
          result = '撤回'
          break
        case 'GOBACK':
          result = '退回'
          break
        case 'SUBMIT':
          result = '提交'
          break
        case 'RESUBMIT':
          result = '重新提交'
          break
        case 'CLAIM':
          result = '认领'
          break
        case 'UNCLAIM':
          result = '取消认领'
          break
        case 'APPROVAL':
          result = '审批'
          break
        case 'COMPLETE':
          result = '完成'
          break
        case 'STAGING':
          result = '暂存'
          break
        case 'ASSIGN':
          result = '转办'
          break
        case 'DELEGATE':
          result = '委派'
          break
        case 'TERMINATE':
          result = '终止'
          break
        case 'COMMENT':
          result = '评论'
          break
        case 'CARBONCOPY':
          result = '抄送'
          break
        case 'TOAUDIT':
          result = '待审核'
          break
        case 'INITIATOR':
          result = '发起人'
          break
        default:
          result = '-'
          break
      }
      return result
    },
    sortTime(x, y) {
      if (!x.updateTime || !y.updateTime) {
        return 1
      }
      return x.updateTime - y.updateTime
    },
    /**
     * 格式化时间
     * @param {Date | String | Number} date
     * @param {String} fmt 时间格式 默认 'YYYY-MM-DD HH:mm:ss'
     * @returns {string}
     */
    formatDate(date, fmt = 'YYYY-MM-DD HH:mm:ss') {
      return dayjs(date).format(fmt)
    },
    getProctask() {
      const params = {
        processInstanceId: this.formInfo.id,
        includeComment: true
      }
      // const res = {
      //   returnCode: '0000',
      //   returnMsg: 'Success',
      //   nonceStr: '76454d92ec334139ab06f69a04a8df67',
      //   success: true,
      //   data: [
      //     {
      //       activityId: 'Activity_0ah2ndh',
      //       activityName: '发起人节点',
      //       activityType: 'userTask',
      //       assigneeId: '776764450383331328',
      //       assigneeName: '管理员',
      //       taskId: 'e8fc22c1-5a30-11eb-9ee1-0242c8ad1bad',
      //       processInstanceId: 'e8fbad87-5a30-11eb-9ee1-0242c8ad1bad',
      //       startTime: '2021-01-19T08:32:47.868+0000',
      //       endTime: '2021-01-19T08:32:47.938+0000',
      //       durationInMillis: null,
      //       usersIdentity: [],
      //       groupsIdentity: [
      //         {
      //           id: '684790963624738816',
      //           name: '功能角色超级管理员'
      //         }
      //       ],
      //       comment: [
      //         {
      //           id: 'e9046027-5a30-11eb-9ee1-0242c8ad1bad',
      //           userId: '776764450383331328',
      //           userName: '管理员',
      //           type: 'INITIATOR',
      //           typeDesc: '发起人',
      //           time: '2021-01-19 16:32:47',
      //           taskId: 'e8fc22c1-5a30-11eb-9ee1-0242c8ad1bad',
      //           taskName: '发起人节点',
      //           activityId: 'Activity_0ah2ndh',
      //           parentTaskId: null,
      //           processInstanceId: 'e8fbad87-5a30-11eb-9ee1-0242c8ad1bad',
      //           processDefinitionId: 'fbac5ccd-5a2c-11eb-9ee1-0242c8ad1bad',
      //           fullMessage: 'INITIATOR'
      //         }
      //       ],
      //       initiator: true,
      //       history: true
      //     },
      //     {
      //       activityId: 'Activity_1mjhujd',
      //       activityName: '审批人节点1',
      //       activityType: 'userTask',
      //       assigneeId: null,
      //       assigneeName: null,
      //       taskId: 'e9076d6b-5a30-11eb-9ee1-0242c8ad1bad',
      //       processInstanceId: 'e8fbad87-5a30-11eb-9ee1-0242c8ad1bad',
      //       startTime: '2021-01-19T08:32:47.942+0000',
      //       endTime: null,
      //       durationInMillis: null,
      //       usersIdentity: [],
      //       groupsIdentity: [
      //         {
      //           id: '684790963624738816',
      //           name: '功能角色超级管理员'
      //         }
      //       ],
      //       comment: [],
      //       initiator: false,
      //       history: false
      //     }
      //   ]
      // }
      // this.$api.form.processExamples(params)
      processExamples(params)
        .then(res => {
          this.$emit('setAgainStart', res.data)
          this.items = res.data.map(i => {
            const hasComment = i.comment && i.comment.length
            const updateTime = new Date(i.endTime || i.startTime).getTime()
            const commentList = i.comment || null
            let fullMessage

            // 用户或者角色list
            const groupsIdentityList = i.groupsIdentity ? i.groupsIdentity.map(j => j.name + (hasComment ? `(${i.comment[0].typeDesc})` : '')) : null
            const groupsIdentityName = (i.groupsIdentity && i.groupsIdentity.length)
              ? i.groupsIdentity
                .map(l => l.name)
                .filter(e => e)
                .join('、')
              : ''

            let nodeStatus = 'unknown'
            let activityTypeName
            // 这部分是节点状态
            if (i.startTime && i.endTime && hasComment) {
              if (i.comment[0].type !== 'NOPASS') {
                nodeStatus = 'PASS'
              } else {
                nodeStatus = 'NOPASS'
              }
            }
            let isHistory = false // 虚线
            if (i.startTime && i.endTime && (hasComment || i.activityType === 'serviceTask')) {
              isHistory = true // 实线
            }
            if (i.activityType === 'userTask') {
              activityTypeName = i.initiator ? '发起人' : '审批人'
            } else if (i.activityType === 'serviceTask') {
              activityTypeName = '抄送人'
            }
            const tl = i
            // 这部分是描述内容 例：账号三级(发起人)
            if (commentList && commentList.length) {
              if (i.comment[0] && i.comment[0].type !== 'AUTO_PASS') {
                tl.assigneeName = commentList.map(j => j.userName + (j.type !== 'INITIATOR' && j.type !== 'CARBONCOPY' ? `(${j.typeDesc})` : '')).join(',')
              } else {
                tl.assigneeName = commentList.map(j => j.typeDesc).join(',')
              }
            } else {
              tl.assigneeName = groupsIdentityList ? groupsIdentityList.join(',') : ''
            }

            // 三种类型头像
            let imgSrc = i.activityType !== 'serviceTask' ? avatar : copy
            if (hasComment && i.comment[0].type === 'AUTO_PASS') {
              imgSrc = noApproval
            }
            // 这部分是判断是否有详情（弹窗）
            try {
              fullMessage = JSON.parse(i.comment[0].fullMessage).list.length
              this.$set(i, 'dialogMessage', i.comment[0])
            } catch (e) {
              fullMessage = ''
            }
            this.$set(i, 'nodeStatus', nodeStatus)
            this.$set(i, 'isHistory', isHistory)
            this.$set(i, 'updateTime', updateTime)
            this.$set(i, 'activityTypeName', activityTypeName)
            this.$set(i, 'groupsIdentityName', groupsIdentityName)
            this.$set(i, 'imgSrc', imgSrc)
            this.$set(i, 'hasFullMessage', hasComment && fullMessage)
            return i
          })
        })
    },
    getUserDay(index) {
      const lastIndex = index - 1
      const thisTime = this.items[index].updateTime
      if (lastIndex < 0) return
      const lastTime = this.items[lastIndex].updateTime
      const resultDay = Math.ceil((thisTime - lastTime) / (3600 * 24 * 1000))
      return resultDay
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  min-width: 320px;
  width: 350px;
  background-color: #fff;

  .header {
    font-size: 16px;
    font-weight: 400;
    color: #333333;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 14px 0px;
    border-bottom: 1px solid #f0f0f0;
    height: 60px;
  }

  .content {
    margin: 20px;

    .content-item {
      display: flex;

      .process {
        position: relative;
        display: flex;
        flex-direction: column;

        .avater {
          height: 44px;
          width: 44px;
        }

        .success {
          height: 17px;
          width: 17px;
          position: absolute;
          top: 30px;
          left: 30px;
        }

        .divider {
          width: 2px;
          min-height: 41px;
          flex: 1;
          background-color: #f17d1d;
          margin: 5px 0 7px 21px;
        }

        .dashed {
          width: 1px;
          min-height: 41px;
          flex: 1;
          // background-color: #f17d1d;
          margin: 12px 0 10px 21px;
          border: 1px dashed #f17d1d;
        }

        .has-name {
          margin: 12px 0 10px 21px !important;
        }
      }

      .item-card {
        flex: 1;
        margin-left: 17px;

        .item-title {
          display: flex;
          justify-content: space-between;
          align-items: center;

          .title {
            font-size: 16px !important;
            display: flex;
            align-items: center;
            color: #333;

            .is-title {
              color: #333333;
            }

            .button-container ::v-deep {
              margin-left: 12px;

              .el-button--text {
                color: #3794FF;
              }
            }
          }

          .date {
            font-size: 12px;
            color: #999999;
          }
        }

        .item-value {
          font-size: 14px;
          color: #999999;
          display: flex;
          margin-bottom: 5px;
          justify-content: space-between;
          font-size: 12px;
          margin-top: 5px;
          color: #333333;

          .list {
            position: relative;
            margin: 16px 22px 0px 0px;
            display: flex;
            flex-direction: column;
            align-items: center;

            .list-success {
              height: 10px;
              width: 10px;
              position: absolute;
              left: 20px;
              top: 20px;
            }

            .list-label {
              font-size: 12px;
              color: #999999;
              text-align: center;
              white-space: nowrap;
              margin-top: 5px;
              margin-bottom: 41px;
            }
          }

          .useDay {
            margin-top: -5px;
            color: #999999;
          }

          .have-width {
            max-width: 160px;
          }
        }

        .role_value {
          max-width: 160px;

          .name-title {
            font-size: 12px;
            color: #999999;
          }
        }

      }
    }
  }
}
</style>
