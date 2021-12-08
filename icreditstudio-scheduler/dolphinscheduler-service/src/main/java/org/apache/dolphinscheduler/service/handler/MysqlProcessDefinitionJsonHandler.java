package org.apache.dolphinscheduler.service.handler;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dolphinscheduler.common.model.Configuration;
import org.apache.dolphinscheduler.service.increment.IncrementUtil;
import org.apache.dolphinscheduler.service.quartz.PlatformPartitionParam;
import org.apache.dolphinscheduler.service.time.SyncTimeInterval;
import org.apache.dolphinscheduler.service.time.TimeInterval;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Peng
 */
@Slf4j
@Component
public class MysqlProcessDefinitionJsonHandler extends AbstractProcessDefinitionJsonHandler {

    private static final String QUERY_SQL = "content[0].reader.parameter.connection[0].querySql[0]";

    private static final String PATH = "content[0].writer.parameter.path";

    @Override
    public String handler(PlatformPartitionParam partitionParam, String processDefinitionJson) {
        String result = null;
        TimeInterval interval = new TimeInterval();
        SyncTimeInterval syncTimeInterval = interval.getSyncTimeInterval(partitionParam, n -> true);
        //增量存储开启则创建分区
        String partitionDir = null;
        if (partitionParam.getInc()) {
            Object value = getValue(processDefinitionJson, PATH);
            log.info("路径:" + JSONObject.toJSONString(value));
            if (Objects.nonNull(value)) {
                String timeFormat = syncTimeInterval.getTimeFormat();
                String dataxHdfsPath = IncrementUtil.getDataxHdfsPath(value.toString(), timeFormat);
                log.info("处理后路径:" + dataxHdfsPath);
                partitionDir = dataxHdfsPath;
            }
        }
        Object value = getValue(processDefinitionJson, QUERY_SQL);
        log.info("查询SQL:" + JSONObject.toJSONString(value));
        if (Objects.nonNull(value)) {
            String startTime = syncTimeInterval.formatStartTime();
            String endTime = syncTimeInterval.formatEndTime();
            String mysql = IncrementUtil.getTimeIncQueryStatement(value.toString(), "mysql", partitionParam.getIncrementalField(), startTime, endTime);
            //替换查询语句
            Configuration configuration = setValue(processDefinitionJson, QUERY_SQL, mysql);
            //分区不为空给,替换path
            if (StringUtils.isNotBlank(partitionDir)) {
                configuration = setValue(configuration.toJSON(), PATH, partitionDir);
            }
            log.info("处理后的json语句:" + configuration.toJSON());
            result = configuration.toJSON();
        }
        if (StringUtils.isBlank(result)) {
            result = processDefinitionJson;
        }
        return result;
    }
}
