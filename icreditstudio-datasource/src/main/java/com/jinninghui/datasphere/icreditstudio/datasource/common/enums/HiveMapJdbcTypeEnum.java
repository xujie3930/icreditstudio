package com.jinninghui.datasphere.icreditstudio.datasource.common.enums;

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

    DATETIME("DATETIME", "DATE", HiveFieldCategoryEnum.DATE),
    DATE("DATE", "DATE", HiveFieldCategoryEnum.DATE),
    TIMESTAMP("TIMESTAMP", "TIMESTAMP", HiveFieldCategoryEnum.DATE),
    ;
    private String jdbcType;
    private String hiveType;
    private HiveFieldCategoryEnum categoryEnum;

    public static HiveMapJdbcTypeEnum find(String jdbcType) {
        jdbcType = jdbcType.replaceAll("[^a-zA-Z\\u4E00-\\u9FA5]", "");  //去除数字，英文，汉字  之外的内容
        for (HiveMapJdbcTypeEnum value : HiveMapJdbcTypeEnum.values()) {
            if (value.jdbcType.equalsIgnoreCase(jdbcType)) {
                return value;
            }
        }
        return VARCHAR;
    }
}
