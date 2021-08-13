package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PhotoSaveRequest {

    @NotBlank(message = "10000000")
    private String userId;
    @NotBlank(message = "10000000")
    private String photo;
//    @NotBlank(message = "10000000")
    private String accountIdentifier;
}
