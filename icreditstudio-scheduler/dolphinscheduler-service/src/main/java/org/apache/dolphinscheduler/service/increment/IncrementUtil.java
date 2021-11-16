package org.apache.dolphinscheduler.service.increment;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.dolphinscheduler.service.quartz.PlatformPartitionParam;
import org.apache.dolphinscheduler.service.time.CronParse;

import java.util.Objects;
import java.util.StringJoiner;

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
    public static PlatformPartitionParam getSyncCondition(PlatformPartitionParam condition, String cron) {
        if (StringUtils.isNotBlank(condition.getIncrementalField()) && condition.isInc()) {
            condition.setPartition(getPartition(cron));
        }
        return condition;
    }

    /**
     * 取得datax的路径
     *
     * @param oldPath
     * @param partitionDir
     * @return
     */
    public static String getDataxHdfsPath(String oldPath, String partitionDir) {
        if (StringUtils.isNotBlank(oldPath)) {
            String last = StrUtil.subAfter(oldPath, "/", true);
            if (StringUtils.contains(last, "-")) {
                return new StringJoiner("/").add(StrUtil.subBefore(oldPath, "/", true)).add(partitionDir).toString();
            } else {
                return new StringJoiner("/").add(oldPath).add(partitionDir).toString();
            }
        }
        return oldPath;
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
    public static PlatformPartitionParam parseSyncConditionJson(String syncConditionJson) {
        PlatformPartitionParam condition = new PlatformPartitionParam();
        if (JSONUtil.isJson(syncConditionJson)) {
            condition = JSONObject.parseObject(syncConditionJson).toJavaObject(PlatformPartitionParam.class);
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
            throw new RuntimeException("60000048");
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
