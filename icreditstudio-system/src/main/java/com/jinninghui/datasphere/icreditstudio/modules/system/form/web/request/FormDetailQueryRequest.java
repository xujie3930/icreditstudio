package com.jinninghui.datasphere.icreditstudio.modules.system.form.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FormDetailQueryRequest {

    @NotBlank(message = "50009379")
    private String id;
}
