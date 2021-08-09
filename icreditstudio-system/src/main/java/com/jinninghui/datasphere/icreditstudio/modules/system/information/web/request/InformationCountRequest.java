package com.jinninghui.datasphere.icreditstudio.modules.system.information.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by PPai on 2021/6/18 14:00
 */
@Data
public class InformationCountRequest {

    @NotBlank(message = "50009365")
    private String userId;
    @NotBlank(message = "50009366")
    private String infoType;
}
