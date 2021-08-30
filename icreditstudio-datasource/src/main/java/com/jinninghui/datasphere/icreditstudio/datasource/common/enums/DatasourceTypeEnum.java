package com.jinninghui.datasphere.icreditstudio.datasource.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum DatasourceTypeEnum {
    MYSQL(1, "mysql", "com.mysql.jdbc.Driver"),
    ORACLE(2, "oracle", "oracle.jdbc.OracleDriver"),
    POSTGRESQL(3, "postgresql", "org.postgresql.Driver"),
    SQLSERVER(4, "sqlServer", "om.microsoft.sqlserver.jdbc.SQLServerDriver"),
    DEFAULT(1, "mysql", "com.mysql.jdbc.Driver");
    private final Integer type;
    private final String desc;
    private final String driver;

    public static DatasourceTypeEnum findDatasourceTypeByType(Integer type) {
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
