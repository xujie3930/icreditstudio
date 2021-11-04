package org.apache.dolphinscheduler.api.service.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xujie
 * @description 近一月运行出错排行
 * @create 2021-10-11 17:41
 **/
@Data
public class RunErrorRankResult implements Serializable {

    private String id;
    private String name;
    private Long errorNum;
    private Integer scheduleType;

    public RunErrorRankResult() {
    }

    public RunErrorRankResult(String id, String name, Long errorNum, Integer scheduleType) {
        this.id = id;
        this.name = name;
        this.errorNum = errorNum;
        this.scheduleType = scheduleType;
    }
}
