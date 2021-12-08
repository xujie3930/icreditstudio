package com.jinninghui.datasphere.icreditstudio.datasource.service.factory.impl;

import com.alibaba.fastjson.JSONObject;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.DatasourceSync;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.pojo.ColumnSyncInfo;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.pojo.TableSyncInfo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;

/**
 * @author xujie
 * @description mysql
 * @create 2021-08-25 15:25
 **/
public class OracleDatasource implements DatasourceSync {

    @Override
    public Map<String, String> syncDDL(Integer type, String uri) throws Exception {
        Map<String, String> map = new HashMap<>();
        String username = DatasourceSync.getUsername(uri);
        String password = DatasourceSync.getPassword(uri);
        Connection conn = DatasourceSync.getConn(type, uri, username, password);
        if (!Objects.nonNull(conn)) {
            return map;
        }
        DatabaseMetaData metaData = conn.getMetaData();
        String schemaPattern = metaData.getUserName();
        ResultSet tableResultSet = metaData.getTables(conn.getCatalog(), schemaPattern, null, new String[]{"TABLE"});
        List<TableSyncInfo> tableList = new ArrayList<>();
        Integer tablesCount = 0;
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
                // 字段长度
                int datasize = columnResultSet.getInt("COLUMN_SIZE");
                row.setType(columnType + "(" + datasize + ")");
                // 描述
                String remarks = columnResultSet.getString("REMARKS");
                row.setRemark(remarks);
                columnList.add(row);
            }
            tableList.add(table);
        }
        map.put("datasourceInfo", JSONObject.toJSONString(tableList));
        map.put("tablesCount", tablesCount.toString());
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
        String oracleUri = uri.split("\\|")[0];
        int index = oracleUri.lastIndexOf(":") + 1;
        return oracleUri.substring(index);
    }

}
