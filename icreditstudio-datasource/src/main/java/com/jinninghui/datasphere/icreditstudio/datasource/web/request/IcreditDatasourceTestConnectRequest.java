package com.jinninghui.datasphere.icreditstudio.datasource.web.request;

import lombok.Data;

/**
 * @author xujie
 * @description 测试数据源连接
 * @create 2021-08-25 11:43
 **/
@Data
public class IcreditDatasourceTestConnectRequest {
    private Integer type;//数据源类型
    private String uri;//连接信息串
    private String username;//用户名
    private String password;//密码
}
