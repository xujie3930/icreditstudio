package com.jinninghui.icreditdatasphere.icreditstudio.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum PermFlagEnum {
    CAN_EDIT("canEdit"),
    CAN_HIDDEN("canHidden");
    private String code;

    public static PermFlagEnum find(String code) {
        PermFlagEnum result = null;
        if (StringUtils.isNotBlank(code)) {
            for (PermFlagEnum value : PermFlagEnum.values()) {
                if (value.code.equals(code)) {
                    result = value;
                    break;
                }
            }
        }
        return result;
    }
}
