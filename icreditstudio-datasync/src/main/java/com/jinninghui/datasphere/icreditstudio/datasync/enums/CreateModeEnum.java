package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum CreateModeEnum {
    VISUAL(0, "可视化"),
    SQL(1, "sql");

    private Integer code;
    private String desc;

    public static CreateModeEnum find(Integer code) {
        for (CreateModeEnum value : CreateModeEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return VISUAL;
    }
}
