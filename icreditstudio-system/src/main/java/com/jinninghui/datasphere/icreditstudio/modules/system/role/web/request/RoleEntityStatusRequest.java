package com.jinninghui.datasphere.icreditstudio.modules.system.role.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by Pengpai on 2021/5/24 11:05
 */
@Data
public class RoleEntityStatusRequest {
    @NotBlank(message = "50009338")
    private String id;
    @NotBlank(message = "50000017")
    private String deleteFlag;

    private String accessUserId;
}
