package com.jinninghui.datasphere.icreditstudio.system.modules.system.role.service.param;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class RoleEntityConditionParam {

    private Set<String> ids;

    private Set<String> parentIds;

    private String deleteFlag;

    private String roleName;
}
