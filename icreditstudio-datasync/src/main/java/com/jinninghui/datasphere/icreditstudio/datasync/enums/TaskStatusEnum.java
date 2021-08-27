package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum TaskStatusEnum {
    ENABLE(0, "启用"),
    DISABLE(1, "停用"),
    DRAFT(2, "草稿");
    private Integer code;
    private String desc;

    public static TaskStatusEnum find(Integer code) {
        for (TaskStatusEnum value : TaskStatusEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
