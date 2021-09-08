package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum EnableStatusEnum {
    ENABLE(1, "启用", TaskStatusEnum.ENABLE),
    DISABLE(0, "未启用", TaskStatusEnum.DRAFT),
    ;
    private Integer code;
    private String desc;
    private TaskStatusEnum taskStatus;

    public static EnableStatusEnum find(Integer code) {
        for (EnableStatusEnum value : EnableStatusEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return ENABLE;
    }
}
