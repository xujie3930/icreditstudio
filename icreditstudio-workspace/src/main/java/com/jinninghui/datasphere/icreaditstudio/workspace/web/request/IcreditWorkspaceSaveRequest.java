package com.jinninghui.datasphere.icreaditstudio.workspace.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author xujie
 * @description 工作空间模板类
 * @create 2021-08-20 15:06
 **/
@Data
public class IcreditWorkspaceSaveRequest {
    private String id;//主键id
    @NotBlank(message = "工作空间名称不能为空")
    @Length(max = 15, message = "工作空间名称不能超过15个字符")
    private String name;//空间名称
    @NotBlank(message = "工作空间描述不能为空")
    private String descriptor;//空间描述
    private int status = 0;//空间状态
    private String director;//负责人
    private String createUser;//创建人
}
