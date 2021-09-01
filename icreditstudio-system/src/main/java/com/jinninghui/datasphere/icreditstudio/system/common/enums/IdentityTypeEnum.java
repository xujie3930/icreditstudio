package com.jinninghui.datasphere.icreditstudio.system.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum IdentityTypeEnum {
    USER_NAME("1", "username"),
    EMAIL("2", "email"),
    PHONE("3", "phone"),
    WECHAT("4", "wechat"),
    QQ("5", "qq"),
    DEFAULT("1", "username");
    private String code;
    private String desc;

    public static IdentityTypeEnum find(String code) {
        IdentityTypeEnum result = IdentityTypeEnum.DEFAULT;
        if (StringUtils.isNotBlank(code)) {
            for (IdentityTypeEnum value : IdentityTypeEnum.values()) {
                if (value.code.equals(code)) {
                    result = value;
                    break;
                }
            }
        }
        return result;
    }
}
