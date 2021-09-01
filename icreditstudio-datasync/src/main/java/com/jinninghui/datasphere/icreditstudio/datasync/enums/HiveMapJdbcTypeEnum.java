package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum HiveMapJdbcTypeEnum {
    VARCHAR("VARCHAR", "STRING"),
    CHAR("CHAR", "CHAR"),
    CLOB("CLOB","STRING"),

    BLOB("BLOB","BINARY"),

    BIT("BIT", "TINYINT"),
    INTEGER("INTEGER", "INT"),
    INT("INTEGER", "INT"),
    BIGINT("BIGINT", "BIGINT"),
    FLOAT("FLOAT", "FLOAT"),
    DOUBLE("DOUBLE", "DOUBLE"),
    DECIMAL("DECIMAL", "DECIMAL"),

    DATETIME("DATETIME", "DATE"),
    DATE("DATE", "DATE"),
    TIMESTAMP("TIMESTAMP", "TIMESTAMP"),
    ;
    private String jdbcType;
    private String hiveType;

    public static HiveMapJdbcTypeEnum find(String jdbcType) {
        for (HiveMapJdbcTypeEnum value : HiveMapJdbcTypeEnum.values()) {
            if (value.jdbcType.equals(jdbcType)) {
                return value;
            }
        }
        return VARCHAR;
    }
}
