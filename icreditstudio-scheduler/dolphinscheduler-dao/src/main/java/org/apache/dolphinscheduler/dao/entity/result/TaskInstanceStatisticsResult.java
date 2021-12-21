package org.apache.dolphinscheduler.dao.entity.result;

import lombok.Data;
import org.h2.api.DatabaseEventListener;

import java.util.Date;

/**
 * @author xujie
 * @description 统计taskInstance返回bean
 * @create 2021-12-16 14:17
 **/
@Data
public class TaskInstanceStatisticsResult {
    //实例id
    private String instanceId;
    //主键id
    private String id;
    //任务id
    private String platformTaskId;
    //工作空间id
    private String workspaceId;
    //用户id
    private String userId;
    //任务名称
    private String taskName;
    //时间花费
    private Double speedTime;
    /**
     * 任务类型：0-同步任务，1-开发任务，2-治理任务
     */
    private Integer scheduleType;
    /**
     * taskInstance状态：
     * 0：运行成功
     * 1：运行失败
     * 2：运行中
     * 3：等待中
     */
    private Integer state;
    //数据量
    private Long totalRecords;
    //记录数
    private Long totalBytes;
    //日期
    private Date date;
    /**
     * 如果startTime字段为null，取提交时间
     */
    private Date submitTime;
}
