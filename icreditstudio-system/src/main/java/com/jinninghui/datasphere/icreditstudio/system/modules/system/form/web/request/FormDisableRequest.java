package com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FormDisableRequest {

    @NotBlank(message = "50009379")
    private String id;

    private String userId;
}
