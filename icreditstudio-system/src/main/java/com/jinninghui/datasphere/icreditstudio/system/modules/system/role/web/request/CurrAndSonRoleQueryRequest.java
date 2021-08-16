package com.jinninghui.datasphere.icreditstudio.system.modules.system.role.web.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class CurrAndSonRoleQueryRequest {

    @NotNull(message = "10000001")
    private Set<String> roleIds;

    private String deleteFlag;
}
