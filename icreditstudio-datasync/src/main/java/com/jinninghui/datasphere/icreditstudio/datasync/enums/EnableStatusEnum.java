package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum EnableStatusEnum {
    ENABLE(1, "启用"),
    DISABLE(0, "未启用"),
    ;
    private Integer code;
    private String desc;

    public static EnableStatusEnum find(Integer code) {
        for (EnableStatusEnum value : EnableStatusEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return ENABLE;
    }
}
