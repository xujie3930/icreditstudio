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
    private Integer type = 1;//数据源类型：1-MySQL，2-oracle（具体值看 DatasourceTypeEnum）
    private String descriptor;//数据源描述
    private Integer showPassword;//是否展示密码：0-隐藏，1-展示，默认0
}
