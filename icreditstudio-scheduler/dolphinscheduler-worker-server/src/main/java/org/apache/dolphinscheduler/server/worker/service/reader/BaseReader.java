package org.apache.dolphinscheduler.server.worker.service.reader;

import com.alibaba.fastjson.JSONObject;
import org.apache.dolphinscheduler.common.enums.DbType;
import org.apache.dolphinscheduler.server.utils.DataxUtils;
import org.apache.dolphinscheduler.server.worker.entity.InstanceCreateEntity;

import java.util.ArrayList;
import java.util.List;

public class BaseReader {

    public JSONObject getJsonReader(InstanceCreateEntity params) {
        JSONObject reader = new JSONObject();
        List<JSONObject> readerConnArr = new ArrayList<>();
        JSONObject readerConn = new JSONObject();
        readerConn.put("querySql", new String[] {params.getSql()});
        readerConn.put("jdbcUrl", new String[]{params.getSourceUri()});
        readerConnArr.add(readerConn);

        JSONObject readerParam = new JSONObject();
        readerParam.put("username", params.getUsername());
        readerParam.put("password", params.getPassword());
        readerParam.put("connection", readerConnArr);

        reader.put("name", DataxUtils.getReaderPluginName(DbType.getDbTypeByDesc(params.getDsType().toLowerCase())));
        reader.put("parameter", readerParam);
        return reader;
    }
}
