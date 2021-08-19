<!--
 * @Author: lizheng
 * @Description: 新增或编辑数据源
 * @Date: 2021-08-19
-->

<template>
  <el-dialog
    class="add-datasource"
    width="800px"
    :visible.sync="visible"
    @closed="close"
  >
    <div slot="title" class="add-title-text">新增数据源</div>
    <div class="logo-wrap" v-for="(item, key) in logoList" :key="key">
      <div class="logo-item">
        <div class="title">{{ logoNameMapping[key] }}</div>
        <div class="logo-list-wrap">
          <div class="logo-list" :key="list.logo" v-for="list in item">
            <j-svg class="logo-icon" :name="list.name" />
            <h3 class="logo-text">{{ list.logo }}</h3>
          </div>
        </div>
      </div>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      visible: false,
      logoNameMapping: {
        relational: '关系型数据库',
        semiStructured: '半结构化数据库',
        noSql: 'NOSQL',
        doc: '本地文件',
        blockChain: '区块链'
      },
      logoList: {
        relational: [
          { logo: 'MySQL', name: 'mysql' },
          { logo: 'oracle', name: 'oracle' },
          { logo: 'postgresql', name: 'postgresql' },
          { logo: 'SqlServers', name: 'sql-server' }
        ],
        semiStructured: [
          { logo: 'FTP', name: 'ftp' },
          { logo: 'SFTP', name: 'sftp' },
          { logo: 'HDFS', name: 'hdfs' }
        ],
        noSql: [
          { logo: 'MongoDB', name: 'mongodb' },
          { logo: 'Elasticsearch', name: 'elasticsearch' }
        ],
        doc: [
          { logo: 'EXCEL', name: 'excel' },
          { logo: 'TXT', name: 'txt' },
          { logo: 'CSV', name: 'csv' }
        ],
        blockChain: [{ logo: '区块链', name: 'chain' }]
      }
    }
  },
  methods: {
    open() {
      this.visible = true
    },

    close() {
      this.visible = false
    }
  }
}
</script>

<style lang="scss" scoped>
.add-datasource {
  ::v-deep {
    .el-dialog__header {
      padding: 16px 0;
    }

    .el-dialog__body {
      max-height: 70vh;
      overflow-y: auto;
    }
  }
}

.add-title-text {
  padding-bottom: 16px;
  padding-left: 20px;
  font-size: 16px;
  font-weight: 500;
  text-align: left;
  color: rgba(0, 0, 0, 0.85);
  border-bottom: 1px solid rgba(0, 0, 0, 0.06);
}

.logo-wrap {
  margin: -26px 0 20px;
  .logo-item {
    .title {
      position: relative;
      padding: 12px 0;
      font-size: 16px;
      font-family: PingFangSC, PingFangSC-Medium;
      font-weight: 500;
      text-align: left;
      color: #262626;
      border-bottom: 1px solid #ddd;

      &::after {
        content: '';
        position: absolute;
        bottom: -1px;
        left: 0;
        width: 70px;
        height: 2px;
        background: #2f54eb;
        border-radius: 2px;
      }
    }

    .logo-list-wrap {
      @include flex(row, flex-start);
      padding: 24px 0;
    }

    .logo-list {
      @include flex(column);
      width: 160px;
      height: 110px;
      background: rgba(0, 0, 0, 0.02);
      border-radius: 4px;
      margin-right: 40px;
      cursor: pointer;

      &:hover {
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      }

      &:last-child {
        margin-right: 0;
      }

      .logo-icon {
        width: 87px;
        height: 59px;
      }

      .logo-text {
        margin-top: 10px;
        font-size: 14px;
        font-weight: 500;
        text-align: center;
        color: #262626;
      }
    }
  }
}
</style>
