package org.apache.dolphinscheduler.api.service.result;

import lombok.Data;

/**
 * @author xujie
 * @description 任务72小时概况
 * @create 2021-10-09 18:34
 **/
@Data
public class TaskSituation {
    /**
     * 执行情况
     */
    private String taskDesc;
    /**
     * 数量
     */
    private Long count;
    /**
     * 占比
     */
    private double rate;
}
