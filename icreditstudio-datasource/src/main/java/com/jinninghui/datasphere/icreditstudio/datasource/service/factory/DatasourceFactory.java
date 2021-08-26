package com.jinninghui.datasphere.icreditstudio.datasource.service.factory;

import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.impl.MysqlDatasource;

/**
 * @author xujie
 * @description 数据源工厂
 * @create 2021-08-25 15:32
 **/
public class DatasourceFactory {

    private static final int MYSQL = 1;

    public static DatasourceSync getDatasource(Integer type){
        switch (type){
            case MYSQL: return new MysqlDatasource();
            default: return new MysqlDatasource();
        }
    }
}
