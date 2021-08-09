package com.jinninghui.datasphere.icreditstudio.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum DeleteFlagEnum {
    Y("Y", "禁用"),
    N("N", "启用"),
    ALL("ALL", "全部");
    private String code;
    private String desc;

    public static DeleteFlagEnum find(String code) {
        DeleteFlagEnum result = DeleteFlagEnum.ALL;
        if (StringUtils.isNotBlank(code)) {
            for (DeleteFlagEnum value : DeleteFlagEnum.values()) {
                if (value.code.equals(code)) {
                    result = value;
                    break;
                }
            }
        }
        return result;
    }
}
