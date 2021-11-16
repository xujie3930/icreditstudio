package org.apache.dolphinscheduler.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum PartitionTypeEnum {

    MINUTE(0, "minute_"),
    HOUR(1, "hour_"),
    DAY(2, "day_"),
    MONTH(3, "month_"),
    YEAR(4, "year_"),
    ;
    private Integer code;
    private String name;

    public static PartitionTypeEnum find(String name) {
        for (PartitionTypeEnum value : PartitionTypeEnum.values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return DAY;
    }
}
