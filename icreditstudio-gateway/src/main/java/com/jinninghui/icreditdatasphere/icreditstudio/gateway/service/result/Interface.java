package com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.result;

import lombok.Data;

/**
 * 接口授权
 * 每个Interface指一个对外接口
 * @author LIYANHUI
 */
@Data
public class Interface {
    /**
     * 数据库主键
     */
    private Long interfaceId;
    /**
     * 接口URI
     */
    private String uri;
    /**
     * GET POST PUT DELETE
     */
    private String method;
    /**
     * 接口归属系统模块
     */
    private String module;
    /**
     * 接口权限代码
     */
    private String code;
    /**
     * 中文名称
     */
    private String name;
    /**
     * 说明
     */
    private String remark;
    /**
     * 是否需要鉴权，0：不需要鉴权，1：需要鉴权
     */
    private Integer needAuth;
    /**
     * 支持的鉴权方式，字符串，以下划线分隔。如：cert,token,cert_token
     */
    private String supportAuthType;
    /**
     * 链接的类型，0：接口地址，1：通配符
     */
    private String uriType;


}
