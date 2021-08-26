package com.jinninghui.datasphere.icreditstudio.datasource.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum DatasourceTypeEnum {
    MYSQL(1, "mysql", "com.mysql.jdbc.Driver"),
    DEFAULT(1, "mysql", "com.mysql.jdbc.Driver");
    private Integer type;
    private String desc;
    private String driver;

    public static DatasourceTypeEnum findDriverByType(Integer type) {
        DatasourceTypeEnum result = DatasourceTypeEnum.DEFAULT;
        if (Objects.nonNull(type)) {
            for (DatasourceTypeEnum value : DatasourceTypeEnum.values()) {
                if (value.desc.equals(type)) {
                    result = value;
                    break;
                }
            }
        }
        return result;
    }
}
