package com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeleteFlagEnum {
    NOT_DELETED(0, "未删除"),
    DELETED(1, "已删除"),
    ;
    private Integer code;
    private String desc;
}
