package com.jinninghui.datasphere.icreditstudio.workspace.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xujie
 * @description 工作空间参数
 * @create 2021-11-17 15:06
 **/
@Data
public class IcreditWorkBenchRequest {
    @NotBlank(message = "空间id不能为空")
    private String id;//主键id
}
