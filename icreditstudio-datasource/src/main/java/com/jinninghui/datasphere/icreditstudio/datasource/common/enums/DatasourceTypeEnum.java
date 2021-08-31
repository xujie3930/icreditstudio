package com.jinninghui.datasphere.icreditstudio.datasource.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum DatasourceTypeEnum {
    //数据源分类（1-关系型，2-半结构化，3-NoSql,4-本地文件，5-区块链）
    MYSQL(1,1, "mysql", "com.mysql.jdbc.Driver"),
    ORACLE(1,2, "oracle", "oracle.jdbc.OracleDriver"),
    POSTGRESQL(1,3, "postgresql", "org.postgresql.Driver"),
    SQLSERVER(1,4, "sqlServer", "om.microsoft.sqlserver.jdbc.SQLServerDriver"),
    DEFAULT(1,1, "mysql", "com.mysql.jdbc.Driver");
    private final Integer category;
    private final Integer type;
    private final String desc;
    private final String driver;

    public static DatasourceTypeEnum findDatasourceTypeByType(Integer category,Integer type) {
        DatasourceTypeEnum result = DatasourceTypeEnum.DEFAULT;
        if (Objects.nonNull(Objects.nonNull(type)) && Objects.nonNull(type)) {
            for (DatasourceTypeEnum value : DatasourceTypeEnum.values()) {
                if (value.category.equals(category) && value.type.equals(type)) {
                    result = value;
                    break;
                }
            }
        }
        return result;
    }
}
