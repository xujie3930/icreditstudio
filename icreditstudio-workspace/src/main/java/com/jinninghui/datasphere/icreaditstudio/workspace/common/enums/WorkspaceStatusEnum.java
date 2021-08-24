package com.jinninghui.datasphere.icreaditstudio.workspace.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WorkspaceStatusEnum {
    ON(0, "启用"),
    OFF(1, "停用");
    private int code;
    private String desc;

    public static WorkspaceStatusEnum find(int code) {
        WorkspaceStatusEnum result = null;
        for (WorkspaceStatusEnum value : WorkspaceStatusEnum.values()) {
            if (value.code == code) {
                result = value;
                break;
            }
        }
        return result;
    }
}
