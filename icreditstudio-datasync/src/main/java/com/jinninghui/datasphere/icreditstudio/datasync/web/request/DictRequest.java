package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DictRequest {

    @NotBlank
    private String id;

}
