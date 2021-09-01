package com.jinninghui.datasphere.icreditstudio.datasource.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xujie
 * @description 数据源模板类
 * @create 2021-08-24 11:21
 **/
@Data
public class IcreditDatasourceUpdateRequest {
    @NotBlank(message = "主键id不能为空")
    private String id;//主键id
    private String name;//数据源名称
    private String uri;//连接信息
    private int status = 0;//是否启用：0-启用，1-非启用
    private String descriptor;//数据源描述
}
