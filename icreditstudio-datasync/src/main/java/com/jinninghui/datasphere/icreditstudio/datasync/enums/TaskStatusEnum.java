package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum TaskStatusEnum {
    ENABLE(0, "启用", EnableStatusEnum.ENABLE),
    DRAFT(1, "草稿", EnableStatusEnum.ENABLE),
    DISABLE(2, "停用", EnableStatusEnum.DISABLE),
    ALL(3, "全部", null),
    ;
    private Integer code;
    private String desc;
    private EnableStatusEnum statusEnum;

    public static TaskStatusEnum find(Integer code) {
        for (TaskStatusEnum value : TaskStatusEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static TaskStatusEnum find(EnableStatusEnum statusEnum) {
        for (TaskStatusEnum value : TaskStatusEnum.values()) {
            if (value.statusEnum.equals(statusEnum)) {
                return value;
            }
        }
        return null;
    }
}
