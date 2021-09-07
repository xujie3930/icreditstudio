package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum PartitionTypeEnum {
    HOUR(0, "hour"),
    DAY(1, "day"),
    MONTH(2, "month"),
    YEAR(3, "year"),
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
