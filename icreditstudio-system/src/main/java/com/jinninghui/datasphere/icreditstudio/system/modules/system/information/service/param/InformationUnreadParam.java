package com.jinninghui.datasphere.icreditstudio.system.modules.system.information.service.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InformationUnreadParam {
    @NotBlank(message = "50009365")
    private String userId;
}
