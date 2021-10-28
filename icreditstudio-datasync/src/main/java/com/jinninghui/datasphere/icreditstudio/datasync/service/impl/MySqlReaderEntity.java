package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jinninghui.datasphere.icreditstudio.datasync.service.DataxReader;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictInfo;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Peng
 */
@Data
public class MySqlReaderEntity implements DataxReader {

    @Override
    public Map<String, Object> getReaderEntity(Map<String, Object> needTransferColumns, List<DictInfo> transferDict) {
        Map<String, Object> reader = new HashMap<>();
        reader.put("name", "mysqlreader");

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("needTransferColumns", needTransferColumns);
        parameter.put("transferDict", transferDict);
        parameter.put("username", "root");
        parameter.put("password", "root");

        List<Map<String, List<String>>> connections = Lists.newArrayList();
        Map<String, List<String>> connection = Maps.newHashMap();
        connection.put("querySql", Lists.newArrayList("select * from t_ds_project;"));
        connection.put("jdbcUrl", Lists.newArrayList("jdbc:mysql://192.168.110.45:3306/ds"));
        connections.add(connection);
        parameter.put("connection", connections);
        reader.put("parameter", parameter);
        return reader;
    }
}
