package org.apache.dolphinscheduler.api.service.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xujie
 * @description 任务72小时概况
 * @create 2021-10-09 18:34
 **/
@Data
public class TaskSituationResult implements Serializable {
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

    public TaskSituationResult() {
    }

    public TaskSituationResult(String taskDesc, Long count, double rate) {
        this.taskDesc = taskDesc;
        this.count = count;
        this.rate = rate;
    }
}
