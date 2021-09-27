package org.apache.dolphinscheduler.server.worker.service.factory;

import com.alibaba.fastjson.JSONObject;
import org.apache.dolphinscheduler.common.enums.DbType;
import org.apache.dolphinscheduler.server.worker.entity.InstanceCreateEntity;
import org.apache.dolphinscheduler.server.worker.service.reader.MysqlReader;
import org.apache.dolphinscheduler.server.worker.service.reader.OracleReader;
import org.apache.dolphinscheduler.server.worker.service.writer.HiveWriter;

/**
 * @author xujie
 * @description 数据源工厂
 * @create 2021-08-25 15:32
 **/
public class PluginFactory {

    public static JSONObject getReader(InstanceCreateEntity params) {
        String dsType = params.getDsType();
        DbType dbType = DbType.getDbTypeByDesc(dsType.toLowerCase());
        switch (dbType) {
            case MYSQL:
                return new MysqlReader().getJsonReader(params);
            case ORACLE:
                return new OracleReader().getJsonReader(params);
            default:
                return new MysqlReader().getJsonReader(params);
        }
    }

    public static JSONObject getWriter(InstanceCreateEntity params) {
        String dtType = params.getDtType();
        DbType dbType = DbType.getDbTypeByDesc(dtType.toLowerCase());
        switch (dbType) {
            case HIVE:
                return HiveWriter.getJsonWriter(params);
            default:
                return HiveWriter.getJsonWriter(params);
        }
    }
}
