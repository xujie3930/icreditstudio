package com.jinninghui.datasphere.icreditstudio.datasource.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DatasourceSyncStatusEnum {
    //最后一次同步状态：0-成功，1-失败,2未执行
    SUCCESS(0),
    FAIL(1),
    UN_EXECUTED(2);
    private final Integer status;
}
