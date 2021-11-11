package com.jinninghui.datasphere.icreditstudio.datasync.service.increment;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.jinninghui.datasphere.icreditstudio.datasync.service.increment.type.SyncQueryStatement;
import com.jinninghui.datasphere.icreditstudio.datasync.service.increment.type.SyncQueryStatementContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.SyncCondition;
import com.jinninghui.datasphere.icreditstudio.datasync.service.time.CronParse;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author Peng
 */
public class IncrementUtil {

    /**
     * 填充分区类型
     *
     * @param condition
     * @param cron
     * @return
     */
    public static SyncCondition getSyncCondition(SyncCondition condition, String cron) {
        if (StringUtils.isNotBlank(condition.getIncrementalField()) && condition.getInc()) {
            condition.setPartition(getPartition(cron));
        }
        return condition;
    }

    /**
     * 获取分区类型
     *
     * @param cron
     * @return
     */
    public static String getPartition(String cron) {
        return CronParse.getPartition(cron).getName();
    }

    /**
     * 解析syncConditionJson
     *
     * @param syncConditionJson
     * @return
     */
    public static SyncCondition parseSyncConditionJson(String syncConditionJson) {
        SyncCondition condition = new SyncCondition();
        if (JSONUtil.isJson(syncConditionJson)) {
            condition = JSONObject.parseObject(syncConditionJson).toJavaObject(SyncCondition.class);
        }
        return condition;
    }

    /**
     * 处理同步语句时间范围条件
     *
     * @param oldStatement
     * @param dialect
     * @param field
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getTimeIncQueryStatement(String oldStatement, String dialect, String field, String startTime, String endTime) {
        SyncQueryStatement syncQueryStatement = SyncQueryStatementContainer.getInstance().find(dialect);
        if (Objects.isNull(syncQueryStatement)) {
            throw new AppException("60000048");
        }
        return syncQueryStatement.queryStatement(oldStatement, field, startTime, endTime);
    }

    public static void main(String[] args) {
       /* SyncCondition condition = new SyncCondition();
        condition.setInc(true);
        condition.setIncrementalField("create_time");
        condition.setN(2);
        SyncCondition syncCondition = getSyncCondition(condition, "0 * * * * ? ");
        System.out.println(JSONObject.toJSONString(syncCondition));*/

        String oldStatement = "select * from icredit_sync_task";
        String dialect = "mysql";
        String field = "create_time";
        String startTime = "2021-09-23 00:00:00";
        String endTime = "2021-09-23 23:59:59";
        String timeIncQueryStatement = getTimeIncQueryStatement(oldStatement, dialect, field, startTime, endTime);
        System.out.println(timeIncQueryStatement);
    }
}
