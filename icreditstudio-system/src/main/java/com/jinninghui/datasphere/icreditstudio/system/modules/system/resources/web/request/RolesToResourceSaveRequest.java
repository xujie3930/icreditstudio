package com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class RolesToResourceSaveRequest {
    @NotNull(message = "10000001")
    private Set<String> roleIds;

    @NotBlank(message = "10000001")
    private String resourcesId;
}
