package com.jinninghui.datasphere.icreditstudio.datasource.service.factory;

import com.jinninghui.datasphere.icreditstudio.datasource.common.enums.DatasourceTypeEnum;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.impl.MysqlDatasource;

/**
 * @author xujie
 * @description 数据源工厂
 * @create 2021-08-25 15:32
 **/
public class DatasourceFactory {

    public static DatasourceSync getDatasource(Integer category, Integer type){
        DatasourceTypeEnum datasourceTypeEnum = DatasourceTypeEnum.findDatasourceTypeByType(category, type);
        switch (datasourceTypeEnum){
            case MYSQL: return new MysqlDatasource();
            default: return new MysqlDatasource();
        }
    }
}
