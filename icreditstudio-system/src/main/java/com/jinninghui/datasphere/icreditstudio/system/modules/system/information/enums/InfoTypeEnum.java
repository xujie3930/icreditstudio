package com.jinninghui.datasphere.icreditstudio.system.modules.system.information.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by PPai on 2021/6/16 18:17
 */
@AllArgsConstructor
@Getter
public enum InfoTypeEnum {
    SYSTEM("S", "系统消息"),
    NOTICE("N", "通知消息"),
    WARNING("W", "预警消息"),
    ALL("ALL","全部");
    private String code;
    private String desc;

    public static InfoTypeEnum find(String code) {
        for (InfoTypeEnum value : InfoTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
