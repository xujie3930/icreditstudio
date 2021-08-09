package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResourcesFromRoleQueryRequest {

    @NotBlank(message = "10000001")
    private String roleId;
}
