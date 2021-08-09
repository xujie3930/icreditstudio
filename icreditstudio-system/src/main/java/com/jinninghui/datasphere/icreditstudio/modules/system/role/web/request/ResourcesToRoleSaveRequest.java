package com.jinninghui.datasphere.icreditstudio.modules.system.role.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class ResourcesToRoleSaveRequest {

    @NotBlank(message = "10000001")
    private String roleId;
    @NotNull(message = "10000001")
    private Set<String> resourcesIds;
}
