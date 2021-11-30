package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum SyncRateEnum {
    LIMIT(0, "限流"),
    NOT_LIMIT(1, "不限流"),
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
