package com.jinninghui.datasphere.icreditstudio.system.modules.system.information.service.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by PPai on 2021/6/18 16:55
 */
@Data
public class InformationUserInfoParam {
    @NotBlank(message = "50009372")
    private String id;
    @NotBlank(message = "50009365")
    private String userId;
}
