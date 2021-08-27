package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum CollectModeEnum {
    MANUAL(0, "手动执行"),
    CYCLE(1, "周期执行");

    private Integer code;
    private String desc;

    public static CollectModeEnum find(Integer code) {
        for (CollectModeEnum value : CollectModeEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
