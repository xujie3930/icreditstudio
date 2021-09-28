package org.apache.dolphinscheduler.server.worker.service.reader;

import com.alibaba.fastjson.JSONObject;
import org.apache.dolphinscheduler.server.worker.entity.InstanceCreateEntity;

/**
 * @author xujie
 * @description generate mysqlReader json
 * @create 2021-09-27 15:14
 **/
public class MysqlReader extends BaseReader{

    @Override
    public  JSONObject getJsonReader(InstanceCreateEntity params) {
        JSONObject jsonReader = super.getJsonReader(params);
        return jsonReader;
    }
}
