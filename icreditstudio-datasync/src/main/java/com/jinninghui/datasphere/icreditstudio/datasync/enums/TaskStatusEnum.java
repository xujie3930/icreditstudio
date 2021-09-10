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
    DRAFT(1, "草稿"),
    DISABLE(2, "停用"),
    ALL(3, "全部"),
    ;
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
