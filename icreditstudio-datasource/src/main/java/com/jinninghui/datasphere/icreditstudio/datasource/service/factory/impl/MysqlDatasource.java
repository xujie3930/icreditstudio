package com.jinninghui.datasphere.icreditstudio.datasource.service.factory.impl;

import com.alibaba.fastjson.JSONObject;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.DatasourceSync;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.pojo.ColumnSyncnfo;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.pojo.TableISyncnfo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;

/**
 * @author xujie
 * @description mysql
 * @create 2021-08-25 15:25
 **/
public class MysqlDatasource implements DatasourceSync {

    @Override
    public Map<String, String> syncDDL(Integer category, Integer type, String uri) throws Exception{
        Map<String, String> map = new HashMap<>();
        String username = DatasourceSync.getUsername(uri);
        String password = DatasourceSync.getpassword(uri);
        Connection conn = getConn(category, type, uri, username, password);
        if (!Objects.nonNull(conn)) {
            return map;
        }
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet tableResultSet = metaData.getTables(null, null, "%", new String[]{"TABLE"});
        List<TableISyncnfo> tableList = new ArrayList<>();
        Integer tablesCount = 0;
        while (tableResultSet.next()) {
            tablesCount++;
            TableISyncnfo table = new TableISyncnfo();
            String tableName = tableResultSet.getString("TABLE_NAME");
            table.setTableName(tableName);
            List<ColumnSyncnfo> columnList = table.getColumnList();
            // 获取表字段结构
            ResultSet columnResultSet = metaData.getColumns(null, "%", tableName, "%");
            while (columnResultSet.next()) {
                ColumnSyncnfo row = new ColumnSyncnfo();
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
}
