package com.jinninghui.datasphere.icreditstudio.datasource.web.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xujie
 * @description 分页查询数据源
 * @create 2021-08-24 14:54
 **/
@Data
public class IcreditDatasourceEntityPageRequest extends BusinessBasePageForm {
    private String name;//数据源名称
    private Integer type;//数据源类型
    private Integer status;//是否启用：0-启用，1-非启用
    @NotBlank(message = "工作空间不能为空")
    private String workspaceId;//工作空间id
}
