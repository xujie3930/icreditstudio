package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum SyncModeEnum {
    INC(0, "增量同步"),
    FULL(1, "全量同步");

    private Integer code;
    private String desc;

    public static SyncModeEnum find(Integer code) {
        for (SyncModeEnum value : SyncModeEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
