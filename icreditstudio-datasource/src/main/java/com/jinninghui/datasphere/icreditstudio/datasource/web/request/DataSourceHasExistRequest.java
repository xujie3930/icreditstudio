package com.jinninghui.datasphere.icreditstudio.datasource.web.request;

import lombok.Data;

/**
 * @author xujie
 * @description 判断数据源重复
 * @create 2021-09-01 14:39
 **/
@Data
public class DataSourceHasExistRequest {
    private String name;//数据源名称
    private String id;//数据源id

    public DataSourceHasExistRequest() {
    }

    public DataSourceHasExistRequest(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
