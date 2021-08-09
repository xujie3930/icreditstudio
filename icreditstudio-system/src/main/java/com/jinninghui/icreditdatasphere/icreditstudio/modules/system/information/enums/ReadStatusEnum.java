package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by PPai on 2021/6/16 18:15
 */
@AllArgsConstructor
@Getter
public enum ReadStatusEnum {
    Y("Y", "已读"),
    N("N", "未读"),
    ALL("ALL", "全部");
    private String code;
    private String desc;

    public static ReadStatusEnum find(String code) {
        for (ReadStatusEnum value : ReadStatusEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return ReadStatusEnum.ALL;
    }
}
