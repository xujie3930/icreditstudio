package com.jinninghui.datasphere.icreditstudio.datasource.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DatasourceDelFlagEnum {
    //是否删除:N-否，Y-删除
    N("N"),
    Y("Y");
    private final String delFlag;
}
