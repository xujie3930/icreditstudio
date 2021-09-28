package org.apache.dolphinscheduler.server.worker.service.writer;

import com.alibaba.fastjson.JSONObject;
import org.apache.dolphinscheduler.common.enums.DbType;
import org.apache.dolphinscheduler.server.utils.DataxUtils;
import org.apache.dolphinscheduler.server.worker.entity.InstanceCreateEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xujie
 * @description generate hiveWriter json
 * @create 2021-09-27 15:14
 **/
public class HiveWriter {

    public static JSONObject getJsonWriter(InstanceCreateEntity params) {
        JSONObject writerParam = new JSONObject();
        writerParam.put("defaultFS", "hdfs://192.168.0.190:9000");
        writerParam.put("fileType", "orc");
        String writePath = "/usr/local/software/hive/warehouse/" + params.getTargetDatabase() + ".db/" + params.getTargetTable();
        writerParam.put("path", writePath);

        List<JSONObject> columnList = new ArrayList<>();
        List<Object> fields = params.getFields();
        for (Object columnJson : fields) {
            JSONObject column = new JSONObject();
            column.put("name", JSONObject.parseObject(columnJson.toString()).get("name"));
            column.put("type", JSONObject.parseObject(columnJson.toString()).get("type"));
            columnList.add(column);
        }

        JSONObject config = new JSONObject();
        config.put("dfs.client.use.datanode.hostname",true);

        writerParam.put("hadoopConfig", config);
        writerParam.put("column", columnList);
        writerParam.put("fileName", "formDefinition");
        writerParam.put("writeMode", "append");
        writerParam.put("fieldDelimiter", ",");
        writerParam.put("compress", "NONE");

        JSONObject writer = new JSONObject();
        writer.put("name", DataxUtils.getWriterPluginName(DbType.getDbTypeByDesc(params.getDtType().toLowerCase())));
        writer.put("parameter", writerParam);
        return writer;
    }
}
