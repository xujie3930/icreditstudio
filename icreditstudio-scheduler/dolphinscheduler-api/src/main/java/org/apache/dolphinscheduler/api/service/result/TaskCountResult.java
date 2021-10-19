package org.apache.dolphinscheduler.api.service.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xujie
 * @description 任务数量/天
 * @create 2021-10-09 18:34
 **/
@Data
public class TaskCountResult implements Serializable {
    /**
     * 日期
     */
    private String date;
    /**
     * 数量/天
     */
    private Long value;

    public TaskCountResult() {
    }

    public TaskCountResult(String date, Long value) {
        this.date = date;
        this.value = value;
    }
}
