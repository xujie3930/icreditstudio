package com.jinninghui.datasphere.icreditstudio.datasource.web.request;

import lombok.Data;

/**
 * @author xujie
 * @description 测试数据源连接
 * @create 2021-08-25 11:43
 **/
@Data
public class IcreditDatasourceTestConnectRequest {
    private Integer category;//数据源分类
    private Integer type;//数据源类型
    private String uri;//连接信息串
}
