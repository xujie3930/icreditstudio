package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 关联关系
 *
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum AssociatedEnum {
    LEFT_JOIN(0, "左关联"),
    INNER_JOIN(1, "内关联"),
    FULL_JOIN(2, "全关联"),
    ;

    private Integer code;
    private String desc;

    public static AssociatedEnum find(Integer code) {
        for (AssociatedEnum value : AssociatedEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
