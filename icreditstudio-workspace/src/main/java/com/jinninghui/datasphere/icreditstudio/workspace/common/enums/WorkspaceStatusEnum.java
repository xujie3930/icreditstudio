package com.jinninghui.datasphere.icreditstudio.workspace.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WorkspaceStatusEnum {
    ON(0, "启用"),
    OFF(1, "停用");
    private Integer code;
    private String desc;

    public static WorkspaceStatusEnum find(Integer code) {
        WorkspaceStatusEnum result = null;
        for (WorkspaceStatusEnum value : WorkspaceStatusEnum.values()) {
            if (value.code.equals(code)) {
                result = value;
                break;
            }
        }
        return result;
    }
}
