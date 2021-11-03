package com.jinninghui.datasphere.icreditstudio.datasync.service.mysql;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jinninghui.datasphere.icreditstudio.datasync.service.DataxReader;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Peng
 */
@Data
@AllArgsConstructor
public class MySqlReaderEntity implements DataxReader {
    /**
     * 需要字典转换的列
     */
    private Map<String, String> needTransferColumns;
    /**
     * 字典列表
     */
    private List<DictInfo> transferDict;
    /**
     * mysqlreader配置信息
     */
    private MysqlReaderConfigParam configParam;

    @Override
    public Map<String, Object> getReaderEntity() {
        Map<String, Object> reader = new HashMap<>();
        reader.put("name", "mysqlreader");

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("needTransferColumns", getNeedTransferColumns());
        parameter.put("transferDict", getTransferDict());
        parameter.put("username", configParam.getUsername());
        parameter.put("password", configParam.getPassword());

        List<Map<String, List<String>>> connections = Lists.newArrayList();
        Map<String, List<String>> connection = Maps.newHashMap();
        connection.put("querySql", Lists.newArrayList(configParam.getQuerySql()));
        connection.put("jdbcUrl", Lists.newArrayList(configParam.getJdbcUrl()));
        connections.add(connection);
        parameter.put("connection", connections);
        reader.put("parameter", parameter);
        return reader;
    }

    public Map<String, String> getNeedTransferColumns() {
        if (MapUtils.isEmpty(needTransferColumns)) {
            return Maps.newHashMap();
        }
        return needTransferColumns;
    }

    public List<DictInfo> getTransferDict() {
        if (CollectionUtils.isEmpty(transferDict)) {
            return Lists.newArrayList();
        }
        return transferDict;
    }
}
