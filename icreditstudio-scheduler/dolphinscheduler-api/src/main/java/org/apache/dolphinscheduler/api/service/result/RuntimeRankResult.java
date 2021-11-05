package org.apache.dolphinscheduler.api.service.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xujie
 * @description 近一天运行时长排行
 * @create 2021-10-11 16:39
 **/
@Data
public class RuntimeRankResult implements Serializable {

    private String id;
    private String name;
    private Double speedTime;
    private Integer scheduleType;

    public RuntimeRankResult() {
    }

    public RuntimeRankResult(String id, String name, Double speedTime, Integer scheduleType) {
        this.id = id;
        this.name = name;
        this.speedTime = speedTime;
        this.scheduleType = scheduleType;
    }
}
