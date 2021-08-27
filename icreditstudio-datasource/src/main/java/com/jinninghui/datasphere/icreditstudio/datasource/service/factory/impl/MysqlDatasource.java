package com.jinninghui.datasphere.icreditstudio.datasource.service.factory.impl;

import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.DatasourceSync;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author xujie
 * @description mysql
 * @create 2021-08-25 15:25
 **/
public class MysqlDatasource implements DatasourceSync {

    @Override
    public Map<String, String> syncDDL(Integer type, String uri) throws Exception{
        Map<String, String> map = new HashMap<>();
        String username = getUsername(uri);
        String password = getpassword(uri);
        Connection conn = getConn(type, uri, username, password);
        if (!Objects.nonNull(conn)){
            return map;
        }
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet tableResultSet = metaData.getTables(null, null, "%", new String[]{"TABLE"});
        String datasourceInformation = "[";
        Integer tablesCount = 0;
        while (tableResultSet.next()) {
            tablesCount ++;
            String tableName = tableResultSet.getString("TABLE_NAME");
            // 获取表字段结构
            ResultSet columnResultSet = metaData.getColumns(null, "%", tableName, "%");
            String tableInformation = "{tableName:" + tableName +",tableColumn:[";
            while (columnResultSet.next()) {
                // 字段名称
                String columnName = columnResultSet.getString("COLUMN_NAME");
                String columnInformation = "{field:" + columnName + ",";
                // 数据类型
                String columnType = columnResultSet.getString("TYPE_NAME");
                // 字段长度
                int datasize = columnResultSet.getInt("COLUMN_SIZE");
                columnInformation += "type:" + columnType + "(" + datasize + ")" + ",";
                // 描述
                String remarks = columnResultSet.getString("REMARKS");
                columnInformation += "remarks:" + remarks + "},";
                tableInformation += columnInformation;
            }
            //去掉最后一个字符，然后加上中括号"]"
            tableInformation = tableInformation.substring(0, tableInformation.length()-1);
            tableInformation += "]},";
            datasourceInformation += tableInformation;
        }
        datasourceInformation = datasourceInformation.substring(0, datasourceInformation.length()-1);
        datasourceInformation += "]";
        map.put("datasourceInfo", datasourceInformation);
        map.put("tablesCount", tablesCount.toString());
        return map;
    }
}
