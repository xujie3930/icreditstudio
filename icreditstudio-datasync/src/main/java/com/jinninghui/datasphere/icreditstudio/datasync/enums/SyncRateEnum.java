package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum SyncRateEnum {
    NOT_LIMIT(0, "不限流"),
    LIMIT(1, "限流"),
    ;
    private Integer code;
    private String desc;

    public static SyncRateEnum find(Integer code) {
        for (SyncRateEnum value : SyncRateEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return NOT_LIMIT;
    }
}
