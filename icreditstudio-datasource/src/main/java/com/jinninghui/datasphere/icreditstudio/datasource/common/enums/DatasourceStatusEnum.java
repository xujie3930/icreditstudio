package com.jinninghui.datasphere.icreditstudio.datasource.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum DatasourceStatusEnum {
    ENABLE(0),
    DISABLE(1),
    ;
    private Integer code;

    public static DatasourceStatusEnum find(Integer code) {
        for (DatasourceStatusEnum value : DatasourceStatusEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
