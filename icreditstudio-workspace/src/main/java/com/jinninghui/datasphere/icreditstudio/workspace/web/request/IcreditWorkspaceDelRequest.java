package com.jinninghui.datasphere.icreditstudio.workspace.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xujie
 * @description 删除工作空间参数
 * @create 2021-08-20 15:06
 **/
@Data
public class IcreditWorkspaceDelRequest {
    @NotBlank(message = "删除主键不能为空")
    private String id;//主键id
}
