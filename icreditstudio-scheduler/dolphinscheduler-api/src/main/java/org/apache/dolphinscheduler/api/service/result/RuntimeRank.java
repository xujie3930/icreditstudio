package org.apache.dolphinscheduler.api.service.result;

import lombok.Data;

/**
 * @author xujie
 * @description 近一天运行时长排行
 * @create 2021-10-11 16:39
 **/
@Data
public class RuntimeRank {

    private Integer id;
    private String name;
    private Double speedTime;
    private Integer scheduleType;

    public RuntimeRank() {
    }

    public RuntimeRank(Integer id, String name, Double speedTime, Integer scheduleType) {
        this.id = id;
        this.name = name;
        this.speedTime = speedTime;
        this.scheduleType = scheduleType;
    }
}
