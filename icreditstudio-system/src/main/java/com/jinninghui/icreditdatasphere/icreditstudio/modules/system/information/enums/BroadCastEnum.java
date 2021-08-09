package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by PPai on 2021/6/16 18:40
 */
@AllArgsConstructor
@Getter
public enum BroadCastEnum {
    Y("Y","广播消息"),
    N("N","普通消息");
    private String code;
    private String desc;
}
