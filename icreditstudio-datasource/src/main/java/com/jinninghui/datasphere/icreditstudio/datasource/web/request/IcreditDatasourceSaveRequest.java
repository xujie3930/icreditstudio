package com.jinninghui.datasphere.icreditstudio.datasource.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xujie
 * @description 数据源模板类
 * @create 2021-08-24 11:21
 **/
@Data
public class IcreditDatasourceSaveRequest {
    @NotBlank(message = "工作空间不能为空")
    private String spaceId;//工作空间id
    private int category;//数据源分类（1-关系型，2-半结构化，3-NoSql,4-本地文件，5-区块链）
    private int type;//数据源类型
    private String name;//数据源名称
    private String uri;//连接信息
    private int status = 0;//是否启用：0-启用，1-非启用
    private String descriptor;//数据源描述
    private Integer showPassword = 0;//是否展示密码：0-隐藏，1-展示，默认0
}
