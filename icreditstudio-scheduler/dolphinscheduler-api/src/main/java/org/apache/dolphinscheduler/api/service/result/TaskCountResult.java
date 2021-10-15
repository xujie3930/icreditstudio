package org.apache.dolphinscheduler.api.service.result;

import lombok.Data;

/**
 * @author xujie
 * @description 任务数量/天
 * @create 2021-10-09 18:34
 **/
@Data
public class TaskCountResult {
    /**
     * 日期
     */
    private String date;
    /**
     * 数量/天
     */
    private Long count;

    public TaskCountResult() {
    }

    public TaskCountResult(String date, Long count) {
        this.date = date;
        this.count = count;
    }
}
