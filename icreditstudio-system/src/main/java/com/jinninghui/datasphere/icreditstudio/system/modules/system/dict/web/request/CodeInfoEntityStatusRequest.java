package com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CodeInfoEntityStatusRequest {
    @NotBlank(message = "10000000")
    private String id;
    @NotBlank(message = "10000000")
    private String deleteFlag;
}
