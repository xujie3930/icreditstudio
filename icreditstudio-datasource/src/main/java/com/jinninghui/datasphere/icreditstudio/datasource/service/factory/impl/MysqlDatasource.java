package com.jinninghui.datasphere.icreditstudio.datasource.service.factory.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.DatasourceSync;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.pojo.ColumnSyncInfo;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.pojo.TableSyncInfo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xujie
 * @description mysql
 * @create 2021-08-25 15:25
 **/
public class MysqlDatasource implements DatasourceSync {

    @Override
    public Map<String, String> syncDDL(Integer type, String uri) throws Exception {
        Map<String, String> map = new HashMap<>();
        Integer tablesCount = 0;
        Connection conn = null;
        try {
            String username = DatasourceSync.getUsername(uri);
            String password = DatasourceSync.getPassword(uri);
            conn = DatasourceSync.getConn(type, uri, username, password);
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tableResultSet = metaData.getTables(null, null, null,
                    new String[]{"TABLE", "SYSTEM TABLE", "VIEW", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM"});
            List<TableSyncInfo> tableList = new ArrayList<>();
            while (tableResultSet.next()) {
                tablesCount++;
                TableSyncInfo table = new TableSyncInfo();
                String tableName = tableResultSet.getString("TABLE_NAME");
                table.setTableName(tableName);
                List<ColumnSyncInfo> columnList = table.getColumnList();
                // 获取表字段结构
                ResultSet columnResultSet = metaData.getColumns(null, "%", tableName, "%");
                while (columnResultSet.next()) {
                    ColumnSyncInfo row = new ColumnSyncInfo();
                    // 字段名称
                    String columnName = columnResultSet.getString("COLUMN_NAME");
                    row.setField(columnName);
                    // 数据类型
                    String columnType = columnResultSet.getString("TYPE_NAME");
                    row.setType(columnType);
                    // 描述
                    String remarks = columnResultSet.getString("REMARKS");
                    row.setRemark(remarks);
                    columnList.add(row);
                }
                tableList.add(table);
            }
            map.put(DATASOURCEINFO, JSONObject.toJSONString(tableList));
            map.put(TABLESCOUNT, tablesCount.toString());
        }finally {
            if (null != conn){
                conn.close();
            }
        }
        return map;
    }

    /**
     * 取得数据库名称
     *
     * @param uri
     * @return
     */
    @Override
    public String getDatabaseName(String uri) {
        String s = StrUtil.subBefore(uri, "?", false);
        return StrUtil.subAfter(s, "/", true);
    }

}
