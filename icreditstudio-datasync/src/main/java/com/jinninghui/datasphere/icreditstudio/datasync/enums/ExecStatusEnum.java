package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum ExecStatusEnum {

    SUCCESS(0, "成功"),
    FAILURE(1, "失败"),
    EXEC(2, "执行中"),
    ALL(3, "全部"),
    ;
    private Integer code;
    private String desc;

    public static ExecStatusEnum find(Integer code) {
        for (ExecStatusEnum value : ExecStatusEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
