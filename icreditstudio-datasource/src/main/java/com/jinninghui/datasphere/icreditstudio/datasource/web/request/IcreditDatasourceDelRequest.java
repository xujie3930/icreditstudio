package com.jinninghui.datasphere.icreditstudio.datasource.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xujie
 * @description 删除数据源参数
 * @create 2021-08-24 11:42
 **/
@Data
public class IcreditDatasourceDelRequest {
    @NotBlank(message = "删除主键不能为空")
    private String id;//主键id
}
