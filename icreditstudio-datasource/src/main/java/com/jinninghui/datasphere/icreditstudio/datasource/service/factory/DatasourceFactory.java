package com.jinninghui.datasphere.icreditstudio.datasource.service.factory;

import com.jinninghui.datasphere.icreditstudio.datasource.common.enums.DatasourceTypeEnum;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.impl.MysqlDatasource;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.impl.OracleDatasource;

/**
 * @author xujie
 * @description 数据源工厂
 * @create 2021-08-25 15:32
 **/
public class DatasourceFactory {

    public static DatasourceSync getDatasource(Integer type) {
        DatasourceTypeEnum datasourceTypeEnum = DatasourceTypeEnum.findDatasourceTypeByType(type);
        switch (datasourceTypeEnum) {
            case MYSQL:
                return new MysqlDatasource();
            case ORACLE:
                return new OracleDatasource();
            default:
                return new MysqlDatasource();
        }
    }
}
