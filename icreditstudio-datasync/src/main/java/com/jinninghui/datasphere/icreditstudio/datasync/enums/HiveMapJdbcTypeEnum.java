package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum HiveMapJdbcTypeEnum {
    VARCHAR("VARCHAR", "STRING", HiveFieldCategoryEnum.CHARACTER),
    CHAR("CHAR", "STRING", HiveFieldCategoryEnum.CHARACTER),
    CLOB("CLOB", "STRING", HiveFieldCategoryEnum.CHARACTER),

//    BLOB("BLOB", "BINARY",HiveFieldCategoryEnum.NUMERIC),

    BIT("BIT", "TINYINT", HiveFieldCategoryEnum.NUMERIC),
    INTEGER("INTEGER", "INT", HiveFieldCategoryEnum.NUMERIC),
    INT("INT", "INT", HiveFieldCategoryEnum.NUMERIC),
    BIGINT("BIGINT", "BIGINT", HiveFieldCategoryEnum.NUMERIC),
    FLOAT("FLOAT", "FLOAT", HiveFieldCategoryEnum.NUMERIC),
    DOUBLE("DOUBLE", "DOUBLE", HiveFieldCategoryEnum.NUMERIC),
    DECIMAL("DECIMAL", "DECIMAL", HiveFieldCategoryEnum.NUMERIC),
    NUMBER("NUMBER", "INT", HiveFieldCategoryEnum.NUMERIC),

    DATETIME("DATETIME", "DATE", HiveFieldCategoryEnum.DATE),
    DATE("DATE", "DATE", HiveFieldCategoryEnum.DATE),
    TIMESTAMP("TIMESTAMP", "TIMESTAMP", HiveFieldCategoryEnum.DATE),
    ;
    private String jdbcType;
    private String hiveType;
    private HiveFieldCategoryEnum categoryEnum;

    public static HiveMapJdbcTypeEnum find(String jdbcType) {
        for (HiveMapJdbcTypeEnum value : HiveMapJdbcTypeEnum.values()) {
            if (value.jdbcType.equals(jdbcType)) {
                return value;
            }
        }
        return VARCHAR;
    }
}
