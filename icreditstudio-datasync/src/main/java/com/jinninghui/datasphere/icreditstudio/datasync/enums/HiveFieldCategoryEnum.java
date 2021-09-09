package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum HiveFieldCategoryEnum {
    NUMERIC(0),
    DATE(1),
    CHARACTER(2),
    ;
    private Integer code;

    public static HiveFieldCategoryEnum find(Integer code) {
        for (HiveFieldCategoryEnum value : HiveFieldCategoryEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
